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

/**
 * @author WispY
 */
public class BigNumberStyle extends AbstractLightStyle {
    private TextStyle label;
    private TextStyle value;
    private int stripe;
    private int spacing;
    private int innerSpacing;
    private String format;
    private String locale;

    public BigNumberStyle() {
        this.label = DEFAULT_BIG_NUMBER_LABEL;
        this.value = DEFAULT_BIG_NUMBER_VALUE;
        this.stripe = DEFAULT_BIG_NUMBER_STRIPE;
        this.spacing = DEFAULT_BIG_NUMBER_SPACING;
        this.innerSpacing = DEFAULT_BIG_NUMBER_INNER_SPACING;
        this.format = DEFAULT_BIG_NUMBER_FORMAT;
        this.locale = DEFAULT_BIG_NUMBER_LOCALE;
    }

    public TextStyle getLabel() {
        return label;
    }

    public void setLabel(TextStyle label) {
        this.label = label;
    }

    public TextStyle getValue() {
        return value;
    }

    public void setValue(TextStyle value) {
        this.value = value;
    }

    public int getStripe() {
        return stripe;
    }

    public void setStripe(int stripe) {
        this.stripe = stripe;
    }

    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public int getInnerSpacing() {
        return innerSpacing;
    }

    public void setInnerSpacing(int innerSpacing) {
        this.innerSpacing = innerSpacing;
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
}