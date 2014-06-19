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
package net.sf.timecharts.bundle.functional.layout;

import net.sf.timecharts.bundle.functional.style.GraphGridStyle;
import net.sf.timecharts.core.bean.model.Item;
import net.sf.timecharts.core.bean.model.Model;
import net.sf.timecharts.core.bean.model.Timeline;
import net.sf.timecharts.core.bean.model.Value;
import net.sf.timecharts.core.layout.base.LayoutBox;
import net.sf.timecharts.core.utils.GraphUtils;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.Iterator;
import java.util.List;


/**
 * @author WispY
 */
public class GraphBodyBox extends LayoutBox {
    private GraphGridStyle style;
    private Color defaultItemColor;
    private Color defaultBottomColor;
    private Color defaultTopColor;

    private int graphWidth;
    private int graphHeight;
    private double yHeight;
    private long timeWidth;
    private long end;
    private long start;
    private long granularity;
    private Timeline timeline;

    public GraphBodyBox(Model model, int width, int height, GraphGridStyle style, Color defaultItemColor, Color defaultBottomColor, Color defaultTopColor, Timeline timeline) {
        super(model, width, height);
        this.style = style;
        this.defaultItemColor = defaultItemColor;
        this.defaultBottomColor = defaultBottomColor;
        this.defaultTopColor = defaultTopColor;
        this.timeline = timeline;
    }

    @Override
    public void draw(Graphics2D graphics) {
        init();
        drawGrid(graphics);
        drawAxes(graphics);
        drawValues(graphics);
    }

    private void init() {
        graphWidth = size.width;
        graphHeight = size.height;
        yHeight = model.getMaxY() - model.getMinY();
        end = model.getTimeEnd();
        start = model.getTimeStart();
        timeWidth = end - start;
        granularity = model.getGranularity();
    }

    private void drawGrid(Graphics2D graphics) {
        // draw vertical lines
        graphics.setColor(style.getGrid());

        long currentTime = timeline.start + timeline.offset;
        while (currentTime < timeline.end) {
            if (currentTime >= timeline.start && currentTime <= timeline.end) {
                int x = GraphUtils.getPosition(currentTime, start, timeWidth, graphWidth);
                graphics.drawLine(x, 0, x, graphHeight);
            }
            currentTime += timeline.gap / style.getSubdivision();
        }

        // draw horizontal lines
        int count = 1;
        double gapHeight = size.height;
        while (gapHeight / 2 >= style.getMinimumGrid()) {
            gapHeight = gapHeight / 2;
            count = count * 2;
        }
        for (int index = 0; index < count; index++) {
            int y = GraphUtils.getPosition(index, 0, count, size.height);
            graphics.drawLine(0, y, graphWidth, y);
        }
    }

    private void drawAxes(Graphics2D graphics) {
        int arrow = style.getArrowSize();
        graphics.setColor(style.getAxis());

        // draw axes
        //  time axis
        graphics.drawLine(-arrow, graphHeight, graphWidth + arrow, graphHeight);
        //  value axis
        graphics.drawLine(0, graphHeight + arrow, 0, -arrow);

        // draw arrows
        //   arrow on time axis
        graphics.translate(graphWidth + arrow, graphHeight);
        graphics.drawLine(0, -arrow, 0, arrow);
        graphics.drawLine(0, -arrow, arrow * 2, 0);
        graphics.drawLine(0, arrow, arrow * 2, 0);
        graphics.translate(-(graphWidth + arrow), -graphHeight);
        //   arrow on value axis
        graphics.translate(0, -arrow);
        graphics.drawLine(-arrow, 0, +arrow, 0);
        graphics.drawLine(-arrow, 0, 0, -arrow * 2);
        graphics.drawLine(arrow, 0, 0, -arrow * 2);
        graphics.translate(0, arrow);
    }

    private void drawValues(Graphics2D graphics) {
        for (Item item : model.getItems()) {
            Color color = item.getColor();
            if (color == null) {
                color = defaultItemColor;
            }
            graphics.setColor(color);

            if (item.isUnchanged()) {
                int y = getY(item.getLast(), yHeight);
                graphics.drawLine(1, y, graphWidth, y);
            } else {
                Path2D mainPath = buildPath(item, PathType.MAIN);
                Path2D topPath = buildPath(item, PathType.TOP);
                Path2D bottomPath = buildPath(item, PathType.BOTTOM);

                graphics.setColor(defaultBottomColor);
                graphics.draw(bottomPath);
                graphics.setColor(defaultTopColor);
                graphics.draw(topPath);
                graphics.setColor(color);
                graphics.draw(mainPath);
            }
        }
    }

    private Path2D buildPath(Item item, PathType type) {
        long naturalGranularity = item.getGranularity();
        Path2D.Double path = new Path2D.Double();
        boolean restartPath = true;

        Long lastTimestamp = null;
        for (Iterator<List<Value>> iterator = item.getValueGroups().iterator(); iterator.hasNext(); ) {
            List<Value> group = iterator.next();
            for (Value value : group) {
                int pointX = GraphUtils.getPosition(value.getTimestamp(), start, timeWidth, graphWidth);
                Double doubleValue = null;
                switch (type) {
                    case MAIN:
                        doubleValue = value.getValue();
                        break;
                    case TOP:
                        doubleValue = value.getTop();
                        break;
                    case BOTTOM:
                        doubleValue = value.getBottom();
                        break;
                }
                if (doubleValue == null) {
                    continue;
                }
                int pointY = getY(doubleValue, yHeight);
                if (restartPath) {
                    if (value.getTimestamp() - naturalGranularity < start) {
                        path.moveTo(1, pointY);
                        path.lineTo(pointX, pointY);
                    } else {
                        path.moveTo(pointX, pointY);
                    }
                    restartPath = false;
                } else {
                    path.lineTo(pointX, pointY);
                }
                lastTimestamp = value.getTimestamp();
            }
            if (iterator.hasNext()) {
                restartPath = true;
            }
        }
        if (lastTimestamp != null && lastTimestamp > end - naturalGranularity && !restartPath) {
            path.lineTo(graphWidth, path.getCurrentPoint().getY());
        }
        return path;
    }

    private enum PathType {
        MAIN, TOP, BOTTOM
    }

    private int getY(double value, double yHeight) {
        int pointY = GraphUtils.getPosition(value, model.getMinY(), yHeight, graphHeight);
        if (pointY == 0) {
            pointY = 1;
        }
        return graphHeight - pointY;
    }

}