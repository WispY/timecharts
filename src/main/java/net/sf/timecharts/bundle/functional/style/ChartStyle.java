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
package net.sf.timecharts.bundle.functional.style;

import net.sf.timecharts.core.style.TextStyle;

import java.awt.*;

/**
 * @author WispY
 */
public class ChartStyle extends AbstractFunctionalStyle {
    private Color background;
    private Color border;
    private Color defaultItemColor;
    private Color defaultBottomColor;
    private Color defaultTopColor;
    private int spacing;
    private int padding;
    private String font;
    private TextStyle label;
    private int graphSpacing;

    private GraphGridStyle graphGridStyle;
    private ValuesStyle valuesStyle;
    private TimelineStyle timelineStyle;
    private LegendStyle legendStyle;

    public ChartStyle() {
        this.background = DEFAULT_GRAPH_BACKGROUND;
        this.border = DEFAULT_GRAPH_BORDER;
        this.defaultItemColor = DEFAULT_ITEM_COLOR;
        this.defaultBottomColor = DEFAULT_BOTTOM_COLOR;
        this.defaultTopColor = DEFAULT_TOP_COLOR;
        this.spacing = DEFAULT_GRAPH_SPACING;
        this.padding = DEFAULT_GRAPH_PADDING;
        this.font = DEFAULT_FONT;
        this.label = DEFAULT_GRAPH_LABEL;
        this.graphSpacing = DEFAULT_GRAPH_BODY_SPACING;

        this.graphGridStyle = new GraphGridStyle();
        this.valuesStyle = new ValuesStyle();
        this.timelineStyle = new TimelineStyle();
        this.legendStyle = new LegendStyle();
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

    public int getGraphSpacing() {
        return graphSpacing;
    }

    public void setGraphSpacing(int graphSpacing) {
        this.graphSpacing = graphSpacing;
    }

    public GraphGridStyle getGraphGridStyle() {
        return graphGridStyle;
    }

    public void setGraphGridStyle(GraphGridStyle graphGridStyle) {
        this.graphGridStyle = graphGridStyle;
    }

    public ValuesStyle getValuesStyle() {
        return valuesStyle;
    }

    public void setValuesStyle(ValuesStyle valuesStyle) {
        this.valuesStyle = valuesStyle;
    }

    public TimelineStyle getTimelineStyle() {
        return timelineStyle;
    }

    public void setTimelineStyle(TimelineStyle timelineStyle) {
        this.timelineStyle = timelineStyle;
    }

    public LegendStyle getLegendStyle() {
        return legendStyle;
    }

    public void setLegendStyle(LegendStyle legendStyle) {
        this.legendStyle = legendStyle;
    }

    public Color getDefaultBottomColor() {
        return defaultBottomColor;
    }

    public void setDefaultBottomColor(Color defaultBottomColor) {
        this.defaultBottomColor = defaultBottomColor;
    }

    public Color getDefaultTopColor() {
        return defaultTopColor;
    }

    public void setDefaultTopColor(Color defaultTopColor) {
        this.defaultTopColor = defaultTopColor;
    }
}