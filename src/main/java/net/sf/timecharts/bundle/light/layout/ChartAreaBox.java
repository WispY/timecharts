/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.timecharts.bundle.light.layout;

import net.sf.timecharts.bundle.light.style.ChartAreaStyle;
import net.sf.timecharts.bundle.light.style.TimelineStyle;
import net.sf.timecharts.bundle.light.style.ValuesStyle;
import net.sf.timecharts.core.bean.model.Item;
import net.sf.timecharts.core.bean.model.Model;
import net.sf.timecharts.core.bean.model.Value;
import net.sf.timecharts.core.layout.base.LayoutBox;
import net.sf.timecharts.core.style.TextStyle;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.*;
import java.util.List;

import static java.util.Map.Entry;
import static net.sf.timecharts.core.utils.GraphUtils.drawString;
import static net.sf.timecharts.core.utils.GraphUtils.getPosition;

/**
 * @author WispY
 */
public class ChartAreaBox extends LayoutBox {
    private static final double GRANULARITY_THRESHOLD = 1.5;

    private ChartAreaStyle style;
    private ValuesStyle valuesStyle;
    private TimelineStyle timelineStyle;
    private Color defaultItemColor;

    private int graphWidth;
    private int graphHeight;
    private int graphX;
    private int graphY;
    private double valueInterval;

    private int valuesWidth;
    private Map<Double, String> values;

    private int timelineHeight;
    private Map<Long, String> times;
    private Set<Long> specialTimes;
    private long timeWidth;

    private long end;
    private long start;
    private long granularity;

    public ChartAreaBox(Model model, Map<Double, String> values, int valuesWidth, Map<Long, String> times, Set<Long> specialTimes, int timelineHeight, ChartAreaStyle style, Color defaultItemColor) {
        super(model, boxWidth(valuesWidth, style), boxHeight(timelineHeight, style));
        this.graphWidth = 0;
        this.graphHeight = 0;
        this.style = style;
        this.valuesStyle = style.getValuesStyle();
        this.timelineStyle = style.getTimelineStyle();
        this.defaultItemColor = defaultItemColor;

        this.valuesWidth = valuesWidth;
        this.values = values;

        this.timelineHeight = timelineHeight;
        this.times = times;
        this.specialTimes = specialTimes;
    }

    private static int boxWidth(int valuesWidth, ChartAreaStyle style) {
        return style.getBoxSpacing() + style.getValuesSpacing() * 2 + valuesWidth;
    }

    private static int boxHeight(int timelineHeight, ChartAreaStyle style) {
        return style.getValuesStyle().getText().getHeight() + timelineHeight + style.getTimelineStyle().getSpacing();
    }

    @Override
    public void draw(Graphics2D graphics) {
        init();
        drawGrid(graphics);
        drawBorders(graphics);
        drawItems(graphics);
        drawTimes(graphics);
        drawValues(graphics);
    }

    private void init() {
        graphWidth = size.width - boxWidth(valuesWidth, style);
        graphHeight = size.height - boxHeight(timelineHeight, style);
        graphX = style.getValuesSpacing() * 2 + valuesWidth;
        graphY = style.getValuesStyle().getText().getHeight();
        valueInterval = model.getMaxY() - model.getMinY();
        end = model.getTimeEnd();
        start = model.getTimeStart();
        timeWidth = end - start;
        granularity = model.getGranularity();
    }

    private void drawGrid(Graphics2D graphics) {
        graphics.setColor(style.getGrid());

        // draw horizontal lines
        int count = 1;
        double gapHeight = graphHeight;
        while (gapHeight / 2 >= style.getMinimumGrid()) {
            gapHeight = gapHeight / 2;
            count = count * 2;
        }
        for (int index = 0; index < count; index++) {
            int y = graphY + getPosition(index, 0, count, graphHeight);
            graphics.drawLine(0, y, size.width, y);
        }

        // draw bottom line
        graphics.drawLine(0, graphY + graphHeight, size.width, graphY + graphHeight);
    }

    private void drawBorders(Graphics2D graphics) {
        //graphics.setColor(style.getGrid());

        // draw bottom line
        //graphics.drawLine(0, height, width, height);

        // draw top line
        //graphics.drawLine(0, 0, size.width, 0);
    }

    private void drawItems(Graphics2D graphics) {
        boolean placeDots = getPosition(model.getGranularity(), 0, timeWidth, graphWidth) >= style.getDotGap();
        int dotSize = style.getDotSize();
        boolean placeMaxDots = model.getItems().size() == 1;
        Map<Value, Color> maxDots = null;
        for (Item item : model.getItems()) {
            Color color = item.getColor();
            if (color == null) {
                color = defaultItemColor;
            } else {
                color = style.stylize(color);
            }

            if (item.isUnchanged()) {
                int y = getY(item.getLast(), valueInterval);
                graphics.setColor(color);
                graphics.drawLine(graphX, y, graphX + graphWidth, y);
            } else {
                List<Path2D> spreadPaths = buildSpreadPaths(item);
                graphics.setColor(spreadColor(color));
                for (Path2D path : spreadPaths) {
                    graphics.fill(path);
                }

                Path2D mainPath = buildMainPath(item);
                graphics.setColor(color);
                graphics.draw(mainPath);

                if (placeDots) {
                    List<int[]> dots = buildMainPathDots(item);
                    for (int[] point : dots) {
                        int dotX = point[0] - dotSize / 2;
                        int dotY = point[1] - dotSize / 2;
                        graphics.fillOval(dotX, dotY, dotSize, dotSize);
                    }
                }

                // get max dots
                if (placeMaxDots) {
                    Value maxPoint = getSingleMaxPoint(item);
                    if (maxPoint != null) {
                        if (maxDots == null) {
                            maxDots = new HashMap<Value, Color>();
                        }
                        maxDots.put(maxPoint, color);
                    }
                }
            }
        }

        // draw max dots
        if (maxDots != null) {
            int maxDotSize = style.getMaxDotSize();
            for (Entry<Value, Color> entry : maxDots.entrySet()) {
                Value maxPoint = entry.getKey();
                int dotX = getX(maxPoint.getTimestamp()) - maxDotSize / 2;
                int dotY = getY(maxPoint.getTop() != null ? maxPoint.getTop() : maxPoint.getValue(), valueInterval) - maxDotSize / 2;
                graphics.setColor(style.getMaxDot());
                graphics.fillOval(dotX, dotY, maxDotSize, maxDotSize);
                graphics.setColor(entry.getValue());
                graphics.drawOval(dotX, dotY, maxDotSize, maxDotSize);
            }
        }

    }

    private List<Path2D> buildSpreadPaths(Item item) {
        List<Path2D> results = new LinkedList<Path2D>();

        long naturalGranularity = item.getGranularity();
        long maxGranularity = (long) (naturalGranularity * GRANULARITY_THRESHOLD);

        Long lastTimestamp = null;
        boolean firstGroup = true;
        for (List<Value> group : item.getValueGroups()) {
            Value[] values = group.toArray(new Value[group.size()]);

            Path2D.Double path = new Path2D.Double();
            boolean startPath = true;
            for (int index = 0; index < values.length; index++) {
                Value value = values[index];
                addPointToPath(path, value, value.getTop(), startPath, maxGranularity, false, firstGroup && index == 0);
                startPath = false;
                lastTimestamp = value.getTimestamp();
            }
            if (lastTimestamp != null && lastTimestamp > end - maxGranularity && !startPath) {
                path.lineTo(getX(end), path.getCurrentPoint().getY());
            }
            for (int index = values.length - 1; index >= 0; index--) {
                Value value = values[index];
                addPointToPath(path, value, value.getBottom(), startPath, maxGranularity, true, firstGroup && index == 0);
                startPath = false;
            }
            path.closePath();
            results.add(path);
            firstGroup = false;
        }
        return results;
    }

    private Color spreadColor(Color color) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (255 * style.getSpreadAlpha()));
    }

    private Path2D buildMainPath(Item item) {
        long naturalGranularity = item.getGranularity();
        long maxGranularity = (long) (naturalGranularity * GRANULARITY_THRESHOLD);
        Path2D.Double path = new Path2D.Double();
        boolean restartPath = true;

        Long lastTimestamp = null;
        boolean first = true;
        for (Iterator<List<Value>> iterator = item.getValueGroups().iterator(); iterator.hasNext(); ) {
            List<Value> group = iterator.next();
            for (Value value : group) {
                addPointToPath(path, value, value.getValue(), restartPath, maxGranularity, false, first);
                restartPath = false;
                lastTimestamp = value.getTimestamp();
            }
            if (iterator.hasNext()) {
                restartPath = true;
            }
            first = false;
        }
        if (lastTimestamp != null && lastTimestamp > end - maxGranularity && !restartPath) {
            path.lineTo(getX(end), path.getCurrentPoint().getY());
        }
        return path;
    }

    private List<int[]> buildMainPathDots(Item item) {
        List<int[]> result = new LinkedList<int[]>();
        for (List<Value> group : item.getValueGroups()) {
            for (Value value : group) {
                int[] point = new int[2];
                point[0] = getX(value.getTimestamp());
                point[1] = getY(value.getValue(), valueInterval);
                result.add(point);
            }
        }
        return result;
    }

    private void addPointToPath(Path2D path, Value value, Double doubleValue, boolean startPath, long maxGranularity, boolean reverse, boolean first) {
        if (doubleValue == null) {
            doubleValue = value.getValue();
        }
        int pointX = getX(value.getTimestamp());
        int pointY = getY(doubleValue, valueInterval);
        if (startPath) {
            if (first && !reverse && value.getTimestamp() - maxGranularity < start) {
                path.moveTo(getX(start), pointY);
                path.lineTo(pointX, pointY);
            } else {
                path.moveTo(pointX, pointY);
            }
        } else {
            path.lineTo(pointX, pointY);
            if (first && reverse && value.getTimestamp() - maxGranularity < start) {
                path.lineTo(getX(start), pointY);
            }
        }
    }

    private Value getSingleMaxPoint(Item item) {
        Value max = null;
        double maxValue = 0.0;
        boolean single = true;
        for (List<Value> group : item.getValueGroups()) {
            for (Value value : group) {
                double currentValue = value.getValue();
                if (value.getTop() != null) {
                    currentValue = value.getTop();
                }
                if (max == null) {
                    max = value;
                    maxValue = currentValue;
                    continue;
                }
                if (currentValue == maxValue) {
                    single = false;
                }
                if (currentValue > maxValue) {
                    max = value;
                    maxValue = currentValue;
                    single = true;
                }
            }
        }
        if (!single) {
            return null;
        }
        return max;
    }

    private void drawTimes(Graphics2D graphics) {
        for (Entry<Long, String> entry : times.entrySet()) {
            long time = entry.getKey();
            String label = entry.getValue();
            int x = getX(time);
            int y = graphY + graphHeight + timelineStyle.getSpacing();
            TextStyle textStyle;
            if (specialTimes.contains(time)) {
                textStyle = timelineStyle.getSpecialText();
            } else {
                textStyle = timelineStyle.getSimpleText();
            }
            y += textStyle.getAscent();
            x -= textStyle.getWidth(label) / 2;
            drawString(label, x, y, graphics, textStyle);
        }
    }

    private void drawValues(Graphics2D graphics) {
        TextStyle textStyle = valuesStyle.getText();
        for (Entry<Double, String> entry : values.entrySet()) {
            Double position = entry.getKey();
            String label = entry.getValue();

            int x = style.getValuesSpacing() + valuesWidth - textStyle.getWidth(label);
            int y = graphY + graphHeight - (int) (position * graphHeight) - valuesStyle.getLift();

            drawString(label, x, y, graphics, textStyle);
        }
    }

    private int getX(long timestamp) {
        return graphX + getPosition(timestamp, start, timeWidth, graphWidth);
    }

    private int getY(double value, double yHeight) {
        int pointY = getPosition(value, model.getMinY(), yHeight, graphHeight);
        if (pointY == 0) {
            pointY = 1;
        }
        return graphY + graphHeight - pointY;
    }
}