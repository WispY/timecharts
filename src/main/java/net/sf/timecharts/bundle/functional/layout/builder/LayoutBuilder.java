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
package net.sf.timecharts.bundle.functional.layout.builder;

import net.sf.timecharts.bundle.functional.layout.GraphBodyBox;
import net.sf.timecharts.bundle.functional.layout.TimelineBox;
import net.sf.timecharts.bundle.functional.layout.ValuesBox;
import net.sf.timecharts.bundle.functional.style.*;
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
import org.joda.time.DateTime;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static net.sf.timecharts.core.utils.GraphUtils.getPosition;

/**
 * @author WispY
 */
public class LayoutBuilder extends BaseLayoutBuilder<IFunctionalStyle> {

    public LayoutBuilder() {
        super(IFunctionalStyle.class);
    }

    @Override
    public IFunctionalStyle getDefaultStyle() {
        return new ChartStyle();
    }

    @Override
    public Layout buildLayout(Model model, LayoutOptions options) {
        IStyle commonStyle = options.getStyle();
        if (!(commonStyle instanceof ChartStyle)) {
            throw new IllegalArgumentException("Style " + commonStyle.getClass() + " is not supported by this builder: " + styleType);
        }
        ChartStyle style = (ChartStyle) commonStyle;

        Timeline timeline = new Timeline(model.getTimeStart(), model.getTimeEnd(), model.getWidth(), style.getTimelineStyle().getMinimumGap());

        Layout layout = new Layout(model);
        layout.fill = style.getBackground();
        layout.border = style.getBorder();
        layout.padding = style.getPadding();
        layout.spacing = style.getSpacing();

        Map<String, LayoutBox> features = new HashMap<String, LayoutBox>();
        layout.setFeatureBoxes(features);

        int gridY = 0;
        if (!isDisabled(options, IFeature.TITLE)) {
            LayoutBox labelBox = buildLabel(model, style);
            layout.addChild(labelBox, 0, gridY++);
            features.put(IFeature.TITLE, labelBox);
        }

        if (!isDisabled(options, IFeature.CHART)) {
            LayoutBox chartArea = new LayoutBox(model);
            chartArea.spacing = style.getGraphSpacing();
            chartArea.align.x = HorizontalAlign.CENTER;

            chartArea.addChild(buildChart(model, style.getGraphGridStyle(), style, timeline), 1, 0);
            chartArea.addChild(buildTime(model, style.getTimelineStyle(), timeline), 1, 1);
            chartArea.addChild(buildValue(model, style.getValuesStyle()), 0, 0);
            chartArea.addChild(buildTimeZone(model, style.getTimelineStyle()), 0, 1);
            layout.addChild(chartArea, 0, gridY++);
            features.put(IFeature.CHART, chartArea);
        }

        if (!isDisabled(options, IFeature.LEGEND)) {
            LayoutBox legendBox = buildLegend(model, style.getLegendStyle(), style.getDefaultItemColor());
            layout.addChild(legendBox, 0, gridY++);
            features.put(IFeature.LEGEND, legendBox);
        }

        if (options.isDebug()) {
            addColors(layout, 0.0f);
        }

        return layout;
    }

    protected GraphBodyBox buildChart(Model model, GraphGridStyle style, ChartStyle chartStyle, Timeline timeline) {
        GraphBodyBox graphLayoutBox = new GraphBodyBox(model, model.getWidth(), model.getHeight(), style, chartStyle.getDefaultItemColor(), chartStyle.getDefaultBottomColor(), chartStyle.getDefaultTopColor(), timeline);
        graphLayoutBox.fill = style.getBackground();
        return graphLayoutBox;
    }

    protected LayoutBox buildLabel(Model model, ChartStyle style) {
        TextBox labelLayoutBox = new TextBox(model.getName(), style.getLabel());
        labelLayoutBox.align.x = HorizontalAlign.CENTER;
        return labelLayoutBox;
    }

    protected TimelineBox buildTime(Model model, TimelineStyle style, Timeline timeline) {
        Map<Long, String> labels = new HashMap<Long, String>();
        Set<Long> specialLabels = new HashSet<Long>();

        int graphWidth = model.getWidth();
        long timeWidth = model.getTimeEnd() - model.getTimeStart();

        SimpleDateFormat format = new SimpleDateFormat(style.getFormat());
        format.setTimeZone(TimeZone.getTimeZone(style.getTimeZone()));
        SimpleDateFormat endsFormat = new SimpleDateFormat(style.getEndsFormat());
        endsFormat.setTimeZone(TimeZone.getTimeZone(style.getTimeZone()));
        int height = 0;

        height = buildTimeLabelAndGetWidth(timeline.start, endsFormat, height, true, labels, specialLabels, style);
        height = buildTimeLabelAndGetWidth(timeline.end, endsFormat, height, true, labels, specialLabels, style);

        int gapTimeWidth = getPosition(style.getMinimumEndsGap(), 0, graphWidth, timeWidth);
        long currentTime = timeline.start + timeline.offset;
        while (currentTime < timeline.end) {
            if (currentTime >= timeline.start + gapTimeWidth && currentTime <= timeline.end - gapTimeWidth) {
                DateTime dateTime = new DateTime(currentTime);
                boolean special = dateTime.getMinuteOfHour() == 0 && dateTime.getSecondOfMinute() == 0;
                height = buildTimeLabelAndGetWidth(currentTime, format, height, special, labels, specialLabels, style);
            }
            currentTime += timeline.gap;
        }

        TimelineBox timelineBox = new TimelineBox(model, graphWidth, height, style, labels, specialLabels);
        timelineBox.align.y = VerticalAlign.TOP;
        return timelineBox;
    }

    private int buildTimeLabelAndGetWidth(long timestamp, SimpleDateFormat format, int current, boolean special, Map<Long, String> labels, Set<Long> specials, TimelineStyle style) {
        String label = buildTimeLabel(timestamp, format, special, labels, specials);
        int width;
        if (special) {
            width = style.getSpecialText().getWidth(label);
        } else {
            width = style.getSimpleText().getWidth(label);
        }
        return Math.max(width, current);
    }

    protected ValuesBox buildValue(Model model, final ValuesStyle style) {
        final Map<Double, String> labels = new HashMap<Double, String>();
        final int graphHeight = model.getHeight();

        int valueCount = 1;
        double gapHeight = graphHeight;
        while (gapHeight / 2 >= style.getMinimumGap()) {
            gapHeight = gapHeight / 2;
            valueCount = valueCount * 2;
        }

        int valueWidth = 0;
        DecimalFormat format = new DecimalFormat(style.getFormat());
        format.setDecimalFormatSymbols(new DecimalFormatSymbols(new Locale(style.getLocale())));
        for (int index = 0; index < valueCount + 1; index++) {
            double position = (double) index / valueCount;
            double value = (model.getMaxY() - model.getMinY()) * position + model.getMinY();
            String label = buildSimpleValueLabel(value, model.getUnit(), format);
            valueWidth = Math.max(valueWidth, style.getText().getWidth(label));
            labels.put(position, label);
        }

        ValuesBox valuesBox = new ValuesBox(model, valueWidth, graphHeight, style, labels);
        valuesBox.align.x = HorizontalAlign.RIGHT;
        return valuesBox;
    }

    private LayoutBox buildTimeZone(Model model, TimelineStyle style) {
        LayoutBox timeZoneBox = new LayoutBox(model);
        timeZoneBox.customSpacing.y = style.getTimeZoneSpacing();
        timeZoneBox.padding = style.getTimeZonePadding();
        timeZoneBox.align.x = HorizontalAlign.CENTER;
        timeZoneBox.align.y = VerticalAlign.CENTER;

        String timeZone = style.getTimeZone();
        TextBox timeZoneLabelBox = new TextBox("time zone", style.getTimeZoneLabelText());
        timeZoneLabelBox.align.x = HorizontalAlign.CENTER;
        timeZoneBox.addChild(timeZoneLabelBox, 0, 0);

        TextBox timeZoneNameBox = new TextBox(timeZone, style.getTimeZoneNameText());
        timeZoneNameBox.align.x = HorizontalAlign.CENTER;
        timeZoneBox.addChild(timeZoneNameBox, 0, 1);
        return timeZoneBox;
    }

    protected LayoutBox buildLegend(Model model, LegendStyle style, Color defaultItemColor) {
        LayoutBox legendLayoutBox = new LayoutBox(model);
        legendLayoutBox.customSpacing.x = style.getHorizontalSpacing();
        legendLayoutBox.customSpacing.y = style.getVerticalSpacing();
        DecimalFormat format = new DecimalFormat(style.getFormat());
        format.setDecimalFormatSymbols(new DecimalFormatSymbols(new Locale(style.getLocale())));
        legendLayoutBox.addChild(new TextBox("last", style.getText()), 2, -1);
        legendLayoutBox.addChild(new TextBox("min", style.getText()), 3, -1);
        legendLayoutBox.addChild(new TextBox("avg", style.getText()), 4, -1);
        legendLayoutBox.addChild(new TextBox("max", style.getText()), 5, -1);

        int index = 0;
        int colorLayoutBoxSize = style.getText().getAscent();
        for (Item item : model.getItems()) {
            LayoutBox nameLayoutBox = new LayoutBox(model);
            nameLayoutBox.spacing = style.getColorBoxSpacing();
            LayoutBox colorLayoutBox = new LayoutBox(model, colorLayoutBoxSize, colorLayoutBoxSize);
            colorLayoutBox.fill = item.getColor();
            if (colorLayoutBox.fill == null) {
                colorLayoutBox.fill = defaultItemColor;
            }
            colorLayoutBox.border = style.getColorBoxBorder();
            colorLayoutBox.align.x = HorizontalAlign.CENTER;
            colorLayoutBox.align.y = VerticalAlign.CENTER;
            nameLayoutBox.addChild(colorLayoutBox, 0, 0);
            nameLayoutBox.addChild(new TextBox(item.getName(), style.getText()), 1, 0);

            legendLayoutBox.addChild(nameLayoutBox, 0, index);
            legendLayoutBox.addChild(new TextBox("[" + item.getFunction() + "]", style.getText()), 1, index);

            List<Unit> valueModel = UnitsUtils.buildPreciseModel(item.getMin(), item.getMax(), style.getPrecision(), model.getUnit());

            String lastValue = buildPreciseValueLabel(item.getLast(), valueModel, model.getUnit(), format, style.getPrecision());
            String minValue = buildPreciseValueLabel(item.getMin(), valueModel, model.getUnit(), format, style.getPrecision());
            String avgValue = buildPreciseValueLabel(item.getAvg(), valueModel, model.getUnit(), format, style.getPrecision());
            String maxValue = buildPreciseValueLabel(item.getMax(), valueModel, model.getUnit(), format, style.getPrecision());

            legendLayoutBox.addChild(new TextBox(lastValue, style.getText()), 2, index);
            legendLayoutBox.addChild(new TextBox(minValue, style.getText()), 3, index);
            legendLayoutBox.addChild(new TextBox(avgValue, style.getText()), 4, index);
            legendLayoutBox.addChild(new TextBox(maxValue, style.getText()), 5, index);
            index++;
        }

        return legendLayoutBox;
    }

}