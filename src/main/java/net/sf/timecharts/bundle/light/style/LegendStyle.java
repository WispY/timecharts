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
public class LegendStyle extends AbstractLightStyle {
    private TextStyle header;
    private TextStyle body;
    private Color border;
    private int spacing;
    private String format;
    private String locale;
    private double precision;
    private int circle;
    private String emptyValue;

    public LegendStyle() {
        this.header = DEFAULT_LEGEND_HEADER;
        this.body = DEFAULT_LEGEND_BODY;
        this.border = DEFAULT_LEGEND_BORDER;
        this.spacing = DEFAULT_LEGEND_SPACING;
        this.format = DEFAULT_LEGEND_FORMAT;
        this.locale = DEFAULT_LEGEND_LOCALE;
        this.precision = DEFAULT_LEGEND_PRECISION;
        this.circle = DEFAULT_LEGEND_COLOR_CIRCLE_SIZE;
        this.emptyValue = DEFAULT_LEGEND_EMPTY_VALUE;
    }

    public TextStyle getHeader() {
        return header;
    }

    public void setHeader(TextStyle header) {
        this.header = header;
    }

    public TextStyle getBody() {
        return body;
    }

    public void setBody(TextStyle body) {
        this.body = body;
    }

    public Color getBorder() {
        return border;
    }

    public void setBorder(Color border) {
        this.border = border;
    }

    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
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

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public int getCircle() {
        return circle;
    }

    public void setCircle(int circle) {
        this.circle = circle;
    }

    public String getEmptyValue() {
        return emptyValue;
    }

    public void setEmptyValue(String emptyValue) {
        this.emptyValue = emptyValue;
    }
}