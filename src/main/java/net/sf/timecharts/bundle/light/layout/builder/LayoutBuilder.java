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
package net.sf.timecharts.bundle.light.layout.builder;

import net.sf.timecharts.bundle.light.layout.BigNumberBox;
import net.sf.timecharts.bundle.light.layout.ChartAreaBox;
import net.sf.timecharts.bundle.light.style.*;
import net.sf.timecharts.core.bean.layout.IFeature;
import net.sf.timecharts.core.bean.layout.Layout;
import net.sf.timecharts.core.bean.model.Item;
import net.sf.timecharts.core.bean.model.Model;
import net.sf.timecharts.core.bean.model.Timeline;
import net.sf.timecharts.core.bean.unit.Unit;
import net.sf.timecharts.core.layout.align.HorizontalAlign;
import net.sf.timecharts.core.layout.align.VerticalAlign;
import net.sf.timecharts.core.layout.base.LayoutBox;
import net.sf.timecharts.core.layout.base.LayoutOptions;
import net.sf.timecharts.core.layout.base.common.TextBox;
import net.sf.timecharts.core.layout.builder.BaseLayoutBuilder;
import net.sf.timecharts.core.style.IStyle;
import net.sf.timecharts.core.utils.UnitsUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static org.apache.commons.lang3.text.WordUtils.capitalize;

/**
 * @author WispY
 */
public class LayoutBuilder extends BaseLayoutBuilder<ILightStyle> {

    protected LayoutBuilder() {
        super(ILightStyle.class);
    }

    @Override
    public ILightStyle getDefaultStyle() {
        return new LightStyle();
    }

    @Override
    public Layout buildLayout(Model model, LayoutOptions options) {
        IStyle commonStyle = options.getStyle();
        if (!(commonStyle instanceof LightStyle)) {
            throw new IllegalArgumentException("Style " + commonStyle.getClass() + " is not supported by this builder: " + styleType);
        }
        LightStyle style = (LightStyle) commonStyle;

        Timeline timeline = new Timeline(model.getTimeStart(), model.getTimeEnd(), model.getWidth(), style.getChartAreaStyle().getTimelineStyle().getGap());

        Layout layout = new Layout(model);
        layout.fill = style.getBackground();
        layout.border = style.getBorder();
        layout.padding = style.getPadding();
        layout.spacing = style.getSpacing();
        layout.forcedSize.width = model.getWidth();

        Map<String, LayoutBox> features = new HashMap<String, LayoutBox>();
        layout.setFeatureBoxes(features);

        int gridY = 0;
        if (!isDisabled(options, IFeature.TITLE)) {
            LayoutBox titleBox = buildTitle(model, style);
            layout.addChild(titleBox, 0, gridY++);
            features.put(IFeature.TITLE, titleBox);
        }

        if (!isDisabled(options, IFeature.CHART)) {
            LayoutBox chartArea = buildChartArea(model, style.getChartAreaStyle(), style.getDefaultItemColor(), timeline);
            layout.addChild(chartArea, 0, gridY++);
            features.put(IFeature.CHART, chartArea);
        }

        if (!isDisabled(options, IFeature.LEGEND)) {
            LayoutBox legendBox = buildLegend(model, style.getLegendStyle());
            layout.addChild(legendBox, 0, gridY++);
            features.put(IFeature.LEGEND, legendBox);
        }

        if (!isDisabled(options, IFeature.BIG_NUMBER)) {
            LayoutBox bigNumbersBox = buildBigNumbersBox(model, style.getBigNumberStyle(), style.getDefaultItemColor());
            if (bigNumbersBox != null) {
                layout.addChild(bigNumbersBox, -1, 0);
            }
            features.put(IFeature.BIG_NUMBER, bigNumbersBox);
        }

        if (options.isDebug()) {
            addColors(layout, 0.0f);
        }

        return layout;
    }

    private ChartAreaBox buildChartArea(Model model, ChartAreaStyle style, Color defaultItemColor, Timeline timeline) {
        int valueWidth;
        Map<Double, String> values;
        // build values
        {
            ValuesStyle valuesStyle = style.getValuesStyle();

            values = new HashMap<Double, String>();
            final int graphHeight = model.getHeight();

            int valueCount = 1;
            double gapHeight = graphHeight;
            while (gapHeight / 2 >= valuesStyle.getGap()) {
                gapHeight = gapHeight / 2;
                valueCount = valueCount * 2;
            }

            valueWidth = 0;
            DecimalFormat format = new DecimalFormat(valuesStyle.getFormat());
            format.setDecimalFormatSymbols(new DecimalFormatSymbols(new Locale(valuesStyle.getLocale())));
            for (int index = 0; index < valueCount + 1; index++) {
                double position = (double) index / valueCount;
                double value = (model.getMaxY() - model.getMinY()) * position + model.getMinY();
                String label = buildSimpleValueLabel(value, model.getUnit(), format);
                valueWidth = Math.max(valueWidth, valuesStyle.getText().getWidth(label));
                values.put(position, label);
            }
        }


        Set<Long> specialTimes;
        Map<Long, String> times;
        int timesHeight;
        // build time labels
        {
            TimelineStyle timelineStyle = style.getTimelineStyle();

            times = new HashMap<Long, String>();
            specialTimes = new HashSet<Long>();

            SimpleDateFormat format = new SimpleDateFormat(timelineStyle.getFormat());
            format.setTimeZone(TimeZone.getTimeZone(timelineStyle.getTimeZone()));
            timesHeight = 0;

            SimpleDateFormat timeZoneFormat = new SimpleDateFormat(timelineStyle.getFormat() + " '(" + timelineStyle.getTimeZone() + ")'");
            timeZoneFormat.setTimeZone(TimeZone.getTimeZone(timelineStyle.getTimeZone()));

            long currentTime = timeline.start + timeline.offset;
            while (currentTime < timeline.end) {
                if (currentTime >= timeline.start && currentTime <= timeline.end) {
                    DateTime dateTime = new DateTime(currentTime);
                    boolean special = dateTime.getMinuteOfHour() == 0 && dateTime.getSecondOfMinute() == 0;
                    timesHeight = buildTimeLabelAndGetHeight(currentTime, times.isEmpty() ? timeZoneFormat : format, timesHeight, special, times, specialTimes, timelineStyle);
                }
                currentTime += timeline.gap;
            }
        }

        ChartAreaBox chartAreaBox = new ChartAreaBox(model, values, valueWidth, times, specialTimes, timesHeight, style, defaultItemColor);
        chartAreaBox.forcedSize.height = model.getHeight();
        chartAreaBox.weight.x = 1.0;
        chartAreaBox.stretch.x = true;
        return chartAreaBox;
    }

    private LayoutBox buildLegend(Model model, LegendStyle style) {
        LayoutBox legend = new LayoutBox(model);
        legend.spacing = style.getSpacing();
        legend.stretch.x = true;
        DecimalFormat format = new DecimalFormat(style.getFormat());
        format.setDecimalFormatSymbols(new DecimalFormatSymbols(new Locale(style.getLocale())));
        // x = 1 will be color circle
        legend.addChild(buildLegendHeader("METRIC NAME", style, 2.0), 2, -1);
        legend.addChild(buildLegendHeader("MIN", style, 1.0), 3, -1);
        legend.addChild(buildLegendHeader("AVG", style, 1.0), 4, -1);
        legend.addChild(buildLegendHeader("MAX", style, 1.0), 5, -1);
        legend.addChild(buildLegendHeader("LAST", style, 1.0), 6, -1);
        legend.addChild(buildLegendHeader("AGGREGATION", style, 1.5), 7, -1);

        int gridY = 0;
        for (final Item item : model.getItems()) {
            legend.addChild(buildSeparator(model, style.getBorder(), 7), 1, gridY);
            gridY++;

            if (item.getColor() != null) {
                legend.addChild(buildCircleBox(model, item.getColor(), style), 1, gridY);
            }

            legend.addChild(new TextBox(capitalize(item.getName()), style.getBody()), 2, gridY);

            String lastValue;
            String minValue;
            String avgValue;
            String maxValue;
            if (item.isEmpty()) {
                lastValue = minValue = avgValue = maxValue = style.getEmptyValue();
            } else {
                List<Unit> valueModel = UnitsUtils.buildPreciseModel(item.getMin(), item.getMax(), style.getPrecision(), model.getUnit());

                lastValue = swapLabel(buildPreciseValueLabel(item.getLast(), valueModel, model.getUnit(), format, style.getPrecision()), style);
                minValue = swapLabel(buildPreciseValueLabel(item.getMin(), valueModel, model.getUnit(), format, style.getPrecision()), style);
                avgValue = swapLabel(buildPreciseValueLabel(item.getAvg(), valueModel, model.getUnit(), format, style.getPrecision()), style);
                maxValue = swapLabel(buildPreciseValueLabel(item.getMax(), valueModel, model.getUnit(), format, style.getPrecision()), style);
            }

            legend.addChild(new TextBox(minValue, style.getBody()), 3, gridY);
            legend.addChild(new TextBox(avgValue, style.getBody()), 4, gridY);
            legend.addChild(new TextBox(maxValue, style.getBody()), 5, gridY);
            legend.addChild(new TextBox(lastValue, style.getBody()), 6, gridY);

            legend.addChild(new TextBox(capitalize(item.getFunction()), style.getBody()), 7, gridY);
            gridY++;
        }

        return legend;
    }

    private String swapLabel(String value, LegendStyle style) {
        return StringUtils.isNotBlank(value) ? value : style.getEmptyValue();
    }

    private LayoutBox buildLegendHeader(String label, LegendStyle style, double weight) {
        TextBox textBox = new TextBox(label, style.getHeader());
        textBox.weight.x = weight;
        return textBox;
    }

    private LayoutBox buildSeparator(final Model model, final Color color, int gridWidth) {
        LayoutBox separatorBox = new LayoutBox(model, 0, 1) {
            @Override
            public void draw(Graphics2D graphics) {
                graphics.setColor(color);
                graphics.drawLine(0, 0, this.size.width, 0);
            }
        };
        separatorBox.span.width = gridWidth;
        separatorBox.stretch.x = true;
        return separatorBox;
    }

    private LayoutBox buildCircleBox(final Model model, final Color color, final LegendStyle style) {
        LayoutBox box = new LayoutBox(model, style.getCircle(), style.getCircle()) {
            @Override
            public void draw(Graphics2D graphics) {
                graphics.setColor(style.stylize(color));
                graphics.fillOval(0, 0, size.width, size.height);
            }
        };
        box.align.x = HorizontalAlign.CENTER;
        box.align.y = VerticalAlign.CENTER;
        return box;
    }

    private LayoutBox buildBigNumbersBox(Model model, BigNumberStyle style, Color defaultItemColor) {
        if (model.getItems().isEmpty()) {
            return null;
        }

        LayoutBox bigNumbersBox = new LayoutBox(model);
        bigNumbersBox.spacing = style.getSpacing();

        BigNumberAggregator lastAggregator = new BigNumberAggregator();
        BigNumberAggregator minAggregator = new BigNumberAggregator(true);
        BigNumberAggregator avgAggregator = new BigNumberAggregator();
        BigNumberAggregator maxAggregator = new BigNumberAggregator();
        for (Item item : model.getItems()) {
            lastAggregator.add(item, item.getLast());
            minAggregator.add(item, item.getMin());
            avgAggregator.add(item, item.getAvg());
            maxAggregator.add(item, item.getMax());
        }

        DecimalFormat format = new DecimalFormat(style.getFormat());
        format.setDecimalFormatSymbols(new DecimalFormatSymbols(new Locale(style.getLocale())));
        bigNumbersBox.addChild(buildBigNumberBox("LAST", lastAggregator, model, format, defaultItemColor, style), 0, 0);
        bigNumbersBox.addChild(buildBigNumberBox("AVG", avgAggregator, model, format, defaultItemColor, style), 0, 1);
        bigNumbersBox.addChild(buildBigNumberBox("MAX", maxAggregator, model, format, defaultItemColor, style), 0, 2);
        bigNumbersBox.addChild(buildBigNumberBox("MIN", minAggregator, model, format, defaultItemColor, style), 0, 3);

        return bigNumbersBox;
    }

    private BigNumberBox buildBigNumberBox(String label, BigNumberAggregator aggregator, Model model, DecimalFormat format, Color defaultItemColor, BigNumberStyle style) {
        String[] valueParts = buildSimpleValueLabelParts(aggregator.value, model.getUnit(), format);
        String fullLabel = label;
        if (valueParts.length >= 2 && valueParts[1] != null) {
            fullLabel = fullLabel + ", " + valueParts[1];
        }
        return new BigNumberBox(
                model,
                fullLabel,
                valueParts[0],
                aggregator.item.getColor(),
                defaultItemColor,
                style
        );
    }

    private int buildTimeLabelAndGetHeight(long timestamp, SimpleDateFormat format, int current, boolean special, Map<Long, String> labels, Set<Long> specials, TimelineStyle style) {
        buildTimeLabel(timestamp, format, special, labels, specials);
        int height;
        if (special) {
            height = style.getSpecialText().getHeight();
        } else {
            height = style.getSimpleText().getHeight();
        }
        return Math.max(height, current);
    }

    protected LayoutBox buildTitle(Model model, LightStyle style) {
        TextBox labelLayoutBox = new TextBox(model.getName(), style.getLabel());
        labelLayoutBox.align.x = HorizontalAlign.CENTER;
        return labelLayoutBox;
    }

    private class BigNumberAggregator {
        private Item item;
        private double value;
        private boolean findMinimum;

        public BigNumberAggregator() {
            this(false);
        }

        public BigNumberAggregator(boolean findMinimum) {
            this.findMinimum = findMinimum;
        }

        public void add(Item item, double value) {
            if (this.item == null || !findMinimum && this.value < value || findMinimum && this.value > value) {
                this.item = item;
                this.value = value;
            }
        }
    }

}