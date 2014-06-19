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

/**
 * @author WispY
 */
public class TimelineStyle extends AbstractFunctionalStyle {
    private int minimumGap;
    private int minimumEndsGap;
    private String format;
    private String endsFormat;
    private TextStyle simpleText;
    private TextStyle specialText;

    private String timeZone;
    private int timeZoneSpacing;
    private int timeZonePadding;
    private TextStyle timeZoneLabelText;
    private TextStyle timeZoneNameText;

    public TimelineStyle() {
        this.minimumGap = DEFAULT_TIMELINE_MINIMUM_GAP;
        this.minimumEndsGap = DEFAULT_TIMELINE_MINIMUM_ENDS_GAP;
        this.format = DEFAULT_TIMELINE_FORMAT;
        this.timeZone = DEFAULT_TIMELINE_TIME_ZONE;
        this.endsFormat = DEFAULT_TIMELINE_ENDS_FORMAT;
        this.simpleText = DEFAULT_TIMELINE_SIMPLE_TEXT;
        this.specialText = DEFAULT_TIMELINE_SPECIAL_TEXT;

        this.timeZoneSpacing = DEFAULT_TIMELINE_TIMEZONE_SPACING;
        this.timeZonePadding = DEFAULT_TIMELINE_TIMEZONE_PADDING;
        this.timeZoneLabelText = DEFAULT_TIMELINE_TIMEZONE_LABEL_TEXT;
        this.timeZoneNameText = DEFAULT_TIMELINE_TIMEZONE_NAME_TEXT;
    }

    public int getMinimumGap() {
        return minimumGap;
    }

    public void setMinimumGap(int minimumGap) {
        this.minimumGap = minimumGap;
    }

    public int getMinimumEndsGap() {
        return minimumEndsGap;
    }

    public void setMinimumEndsGap(int minimumEndsGap) {
        this.minimumEndsGap = minimumEndsGap;
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

    public String getEndsFormat() {
        return endsFormat;
    }

    public void setEndsFormat(String endsFormat) {
        this.endsFormat = endsFormat;
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

    public int getTimeZoneSpacing() {
        return timeZoneSpacing;
    }

    public void setTimeZoneSpacing(int timeZoneSpacing) {
        this.timeZoneSpacing = timeZoneSpacing;
    }

    public TextStyle getTimeZoneLabelText() {
        return timeZoneLabelText;
    }

    public void setTimeZoneLabelText(TextStyle timeZoneLabelText) {
        this.timeZoneLabelText = timeZoneLabelText;
    }

    public TextStyle getTimeZoneNameText() {
        return timeZoneNameText;
    }

    public void setTimeZoneNameText(TextStyle timeZoneNameText) {
        this.timeZoneNameText = timeZoneNameText;
    }

    public int getTimeZonePadding() {
        return timeZonePadding;
    }

    public void setTimeZonePadding(int timeZonePadding) {
        this.timeZonePadding = timeZonePadding;
    }
}