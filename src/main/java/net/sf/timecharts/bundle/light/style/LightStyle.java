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
package net.sf.timecharts.bundle.light.style;

import net.sf.timecharts.core.style.TextStyle;

import java.awt.*;

/**
 * @author WispY
 */
public class LightStyle extends AbstractLightStyle {
    private Color background;
    private Color border;
    private Color defaultItemColor;
    private int spacing;
    private int padding;
    private String font;
    private TextStyle label;

    private ChartAreaStyle chartAreaStyle;
    private LegendStyle legendStyle;
    private BigNumberStyle bigNumberStyle;

    public LightStyle() {
        this.background = DEFAULT_CHART_BACKGROUND;
        this.border = DEFAULT_CHART_BORDER;
        this.defaultItemColor = DEFAULT_ITEM_COLOR;
        this.spacing = DEFAULT_CHART_SPACING;
        this.padding = DEFAULT_CHART_PADDING;
        this.font = DEFAULT_FONT;
        this.label = DEFAULT_CHART_LABEL;

        this.chartAreaStyle = new ChartAreaStyle();
        this.legendStyle = new LegendStyle();
        this.bigNumberStyle = new BigNumberStyle();
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Color getBorder() {
        return border;
    }

    public void setBorder(Color border) {
        this.border = border;
    }

    public Color getDefaultItemColor() {
        return defaultItemColor;
    }

    public void setDefaultItemColor(Color defaultItemColor) {
        this.defaultItemColor = defaultItemColor;
    }

    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public TextStyle getLabel() {
        return label;
    }

    public void setLabel(TextStyle label) {
        this.label = label;
    }

    public ChartAreaStyle getChartAreaStyle() {
        return chartAreaStyle;
    }

    public void setChartAreaStyle(ChartAreaStyle chartAreaStyle) {
        this.chartAreaStyle = chartAreaStyle;
    }

    public LegendStyle getLegendStyle() {
        return legendStyle;
    }

    public void setLegendStyle(LegendStyle legendStyle) {
        this.legendStyle = legendStyle;
    }

    public BigNumberStyle getBigNumberStyle() {
        return bigNumberStyle;
    }

    public void setBigNumberStyle(BigNumberStyle bigNumberStyle) {
        this.bigNumberStyle = bigNumberStyle;
    }
}