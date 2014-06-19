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
public class LegendStyle extends AbstractFunctionalStyle {
    private int horizontalSpacing;
    private int verticalSpacing;
    private TextStyle text;
    private String format;
    private String locale;
    private double precision;
    private Color colorBoxBorder;
    private int colorBoxSize;
    private int colorBoxSpacing;

    public LegendStyle() {
        this.horizontalSpacing = DEFAULT_LEGEND_HORIZONTAL_SPACING;
        this.verticalSpacing = DEFAULT_LEGEND_VERTICAL_SPACING;
        this.text = DEFAULT_LEGEND_TEXT;
        this.format = DEFAULT_LEGEND_FORMAT;
        this.locale = DEFAULT_LEGEND_LOCALE;
        this.precision = DEFAULT_LEGEND_PRECISION;
        this.colorBoxBorder = DEFAULT_LEGEND_COLOR_BOX_BORDER;
        this.colorBoxSize = DEFAULT_LEGEND_COLOR_BOX_SIZE;
        this.colorBoxSpacing = DEFAULT_LEGEND_COLOR_BOX_SPACING;
    }

    public int getHorizontalSpacing() {
        return horizontalSpacing;
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
    }

    public int getVerticalSpacing() {
        return verticalSpacing;
    }

    public void setVerticalSpacing(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
    }

    public TextStyle getText() {
        return text;
    }

    public void setText(TextStyle text) {
        this.text = text;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Color getColorBoxBorder() {
        return colorBoxBorder;
    }

    public void setColorBoxBorder(Color colorBoxBorder) {
        this.colorBoxBorder = colorBoxBorder;
    }

    public int getColorBoxSize() {
        return colorBoxSize;
    }

    public void setColorBoxSize(int colorBoxSize) {
        this.colorBoxSize = colorBoxSize;
    }

    public int getColorBoxSpacing() {
        return colorBoxSpacing;
    }

    public void setColorBoxSpacing(int colorBoxSpacing) {
        this.colorBoxSpacing = colorBoxSpacing;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }
}