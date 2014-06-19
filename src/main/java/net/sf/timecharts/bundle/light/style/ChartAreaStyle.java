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

import java.awt.*;

/**
 * @author WispY
 */
public class ChartAreaStyle extends AbstractLightStyle {
    private Color grid;
    private int minimumGrid;
    private int valuesSpacing;
    private int boxSpacing;
    private double spreadAlpha;
    private int dotSize;
    private int dotGap;
    private Color maxDot;
    private int maxDotSize;

    private TimelineStyle timelineStyle;
    private ValuesStyle valuesStyle;

    public ChartAreaStyle() {
        this.grid = DEFAULT_GRID_COLOR;
        this.minimumGrid = DEFAULT_GRID_MINIMUM_GRID;
        this.valuesSpacing = DEFAULT_GRID_VALUES_SPACING;
        this.boxSpacing = DEFAULT_GRID_BOX_SPACING;
        this.spreadAlpha = DEFAULT_ITEM_SPREAD_ALPHA;
        this.dotSize = DEFAULT_ITEM_DOT_SIZE;
        this.dotGap = DEFAULT_ITEM_DOT_GAP;
        this.maxDot = DEFAULT_ITEM_MAX_DOT;
        this.maxDotSize = DEFAULT_ITEM_MAX_DOT_SIZE;

        this.timelineStyle = new TimelineStyle();
        this.valuesStyle = new ValuesStyle();
    }

    public Color getGrid() {
        return grid;
    }

    public void setGrid(Color grid) {
        this.grid = grid;
    }

    public int getMinimumGrid() {
        return minimumGrid;
    }

    public void setMinimumGrid(int minimumGrid) {
        this.minimumGrid = minimumGrid;
    }

    public int getValuesSpacing() {
        return valuesSpacing;
    }

    public void setValuesSpacing(int valuesSpacing) {
        this.valuesSpacing = valuesSpacing;
    }

    public int getBoxSpacing() {
        return boxSpacing;
    }

    public void setBoxSpacing(int boxSpacing) {
        this.boxSpacing = boxSpacing;
    }

    public TimelineStyle getTimelineStyle() {
        return timelineStyle;
    }

    public void setTimelineStyle(TimelineStyle timelineStyle) {
        this.timelineStyle = timelineStyle;
    }

    public ValuesStyle getValuesStyle() {
        return valuesStyle;
    }

    public void setValuesStyle(ValuesStyle valuesStyle) {
        this.valuesStyle = valuesStyle;
    }

    public double getSpreadAlpha() {
        return spreadAlpha;
    }

    public void setSpreadAlpha(double spreadAlpha) {
        this.spreadAlpha = spreadAlpha;
    }

    public int getDotSize() {
        return dotSize;
    }

    public void setDotSize(int dotSize) {
        this.dotSize = dotSize;
    }

    public int getDotGap() {
        return dotGap;
    }

    public void setDotGap(int dotGap) {
        this.dotGap = dotGap;
    }

    public Color getMaxDot() {
        return maxDot;
    }

    public void setMaxDot(Color maxDot) {
        this.maxDot = maxDot;
    }

    public int getMaxDotSize() {
        return maxDotSize;
    }

    public void setMaxDotSize(int maxDotSize) {
        this.maxDotSize = maxDotSize;
    }
}