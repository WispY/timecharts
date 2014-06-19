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
public class ValuesStyle extends AbstractLightStyle {
    private TextStyle text;
    private String format;
    private String locale;
    private int gap;
    private int lift;

    public ValuesStyle() {
        this.text = DEFAULT_VALUES_TEXT;
        this.format = DEFAULT_VALUES_FORMAT;
        this.locale = DEFAULT_VALUES_LOCALE;
        this.gap = DEFAULT_VALUES_MINIMUM_GAP;
        this.lift = DEFAULT_VALUES_LIFT;
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

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public int getLift() {
        return lift;
    }

    public void setLift(int lift) {
        this.lift = lift;
    }
}