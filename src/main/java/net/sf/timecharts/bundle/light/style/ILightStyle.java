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

import net.sf.timecharts.core.style.IStyle;
import net.sf.timecharts.core.style.TextStyle;

import java.awt.*;

import static java.awt.Font.BOLD;
import static java.awt.Font.PLAIN;

/**
 * @author WispY
 */
public interface ILightStyle extends IStyle {
    Color PICKER = new Color(230, 154, 23);
    Color LIGHT = new Color(209, 209, 209);
    Color DARK = new Color(102, 102, 102);
    Color EMPHASIS = new Color(80, 119, 161);
    String LOCALE = "en";
    String BASE = "Ag";
    String COMPACT_BASE = "A";
    int FONT_SIZE = 13;

    // common
    String DEFAULT_FONT = "classpath:/fonts/opensans-regular.ttf";
    Color DEFAULT_ITEM_COLOR = new Color(40, 98, 161);

    // graph
    Color DEFAULT_CHART_BACKGROUND = null;
    Color DEFAULT_CHART_BORDER = null;
    int DEFAULT_CHART_SPACING = 20;
    int DEFAULT_CHART_PADDING = 10;
    TextStyle DEFAULT_CHART_LABEL = new TextStyle(DEFAULT_FONT, PLAIN, 20, DARK).with(BASE);

    // grid body
    Color DEFAULT_GRID_COLOR = LIGHT;
    int DEFAULT_GRID_VALUES_SPACING = 15;
    int DEFAULT_GRID_BOX_SPACING = 20;
    int DEFAULT_GRID_MINIMUM_GRID = 45;

    // items
    double DEFAULT_ITEM_SPREAD_ALPHA = 0.3;
    int DEFAULT_ITEM_DOT_SIZE = 4;
    int DEFAULT_ITEM_DOT_GAP = 50;
    int DEFAULT_ITEM_MAX_DOT_SIZE = 6;
    Color DEFAULT_ITEM_MAX_DOT = new Color(230, 225, 100);

    // timeline
    int DEFAULT_TIMELINE_GAP = 100;
    int DEFAULT_TIMELINE_SPACING = 3;
    String DEFAULT_TIMELINE_FORMAT = "HH:mm";
    String DEFAULT_TIMELINE_TIME_ZONE = "UTC";
    TextStyle DEFAULT_TIMELINE_SIMPLE_TEXT = new TextStyle(DEFAULT_FONT, PLAIN, FONT_SIZE, DARK);
    TextStyle DEFAULT_TIMELINE_SPECIAL_TEXT = new TextStyle(DEFAULT_FONT, BOLD, FONT_SIZE, EMPHASIS);

    // values
    TextStyle DEFAULT_VALUES_TEXT = new TextStyle(DEFAULT_FONT, PLAIN, FONT_SIZE, DARK).with(BASE);
    String DEFAULT_VALUES_FORMAT = "#.#";
    String DEFAULT_VALUES_LOCALE = LOCALE;
    int DEFAULT_VALUES_MINIMUM_GAP = 50;
    int DEFAULT_VALUES_LIFT = 3;

    // legend
    TextStyle DEFAULT_LEGEND_HEADER = new TextStyle(DEFAULT_FONT, BOLD, FONT_SIZE, DARK).with(COMPACT_BASE);
    TextStyle DEFAULT_LEGEND_BODY = new TextStyle(DEFAULT_FONT, PLAIN, FONT_SIZE, DARK);
    Color DEFAULT_LEGEND_BORDER = LIGHT;
    int DEFAULT_LEGEND_SPACING = 7;
    String DEFAULT_LEGEND_FORMAT = "#.##";
    String DEFAULT_LEGEND_LOCALE = LOCALE;
    double DEFAULT_LEGEND_PRECISION = 0.01;
    int DEFAULT_LEGEND_COLOR_CIRCLE_SIZE = 10;
    String DEFAULT_LEGEND_EMPTY_VALUE = "--";

    // big numbers
    TextStyle DEFAULT_BIG_NUMBER_LABEL = new TextStyle(DEFAULT_FONT, BOLD, FONT_SIZE, DARK).with(COMPACT_BASE);
    TextStyle DEFAULT_BIG_NUMBER_VALUE = new TextStyle(DEFAULT_FONT, BOLD, 32, DARK).with(COMPACT_BASE);
    int DEFAULT_BIG_NUMBER_STRIPE = 6;
    int DEFAULT_BIG_NUMBER_SPACING = 20;
    int DEFAULT_BIG_NUMBER_INNER_SPACING = 6;
    String DEFAULT_BIG_NUMBER_FORMAT = "#.#";
    String DEFAULT_BIG_NUMBER_LOCALE = LOCALE;

}