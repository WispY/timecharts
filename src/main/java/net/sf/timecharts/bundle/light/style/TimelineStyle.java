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
public class TimelineStyle extends AbstractLightStyle {
    private int gap;
    private int spacing;
    private String format;
    private String timeZone;
    private TextStyle simpleText;
    private TextStyle specialText;

    public TimelineStyle() {
        this.gap = DEFAULT_TIMELINE_GAP;
        this.spacing = DEFAULT_TIMELINE_SPACING;
        this.format = DEFAULT_TIMELINE_FORMAT;
        this.timeZone = DEFAULT_TIMELINE_TIME_ZONE;
        this.simpleText = DEFAULT_TIMELINE_SIMPLE_TEXT;
        this.specialText = DEFAULT_TIMELINE_SPECIAL_TEXT;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public TextStyle getSimpleText() {
        return simpleText;
    }

    public void setSimpleText(TextStyle simpleText) {
        this.simpleText = simpleText;
    }

    public TextStyle getSpecialText() {
        return specialText;
    }

    public void setSpecialText(TextStyle specialText) {
        this.specialText = specialText;
    }

    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }
}