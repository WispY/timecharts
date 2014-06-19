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

import net.sf.timecharts.core.style.IStyle;
import net.sf.timecharts.core.style.TextStyle;

import java.awt.*;

/**
 * @author WispY
 */
public interface IFunctionalStyle extends IStyle {
    // common
    String DEFAULT_FONT = "Verdana";
    Color DEFAULT_ITEM_COLOR = new Color(40, 98, 161);
    Color DEFAULT_BOTTOM_COLOR = new Color(35, 161, 30);
    Color DEFAULT_TOP_COLOR = new Color(223, 133, 42);

    // graph
    Color DEFAULT_GRAPH_BACKGROUND = null;
    Color DEFAULT_GRAPH_BORDER = null;
    int DEFAULT_GRAPH_SPACING = 10;
    int DEFAULT_GRAPH_PADDING = 15;
    TextStyle DEFAULT_GRAPH_LABEL = new TextStyle(DEFAULT_FONT, Font.PLAIN, 20, 0, 0, 0);
    int DEFAULT_GRAPH_BODY_SPACING = 7;

    // grid body
    Color DEFAULT_GRID_BACKGROUND = new Color(255, 255, 255);
    Color DEFAULT_GRID_GRID = new Color(209, 209, 209);
    Color DEFAULT_GRID_AXIS = new Color(0, 0, 0);
    int DEFAULT_GRID_ARROW_SIZE = 3;
    int DEFAULT_GRID_SUBDIVISION = 2;
    int DEFAULT_GRID_MINIMUM_GRID = 15;

    // timeline
    int DEFAULT_TIMELINE_MINIMUM_GAP = 30;
    int DEFAULT_TIMELINE_MINIMUM_ENDS_GAP = 10;
    String DEFAULT_TIMELINE_FORMAT = "HH:mm";
    String DEFAULT_TIMELINE_TIME_ZONE = "UTC";
    String DEFAULT_TIMELINE_ENDS_FORMAT = "HH:mm:ss";
    TextStyle DEFAULT_TIMELINE_SIMPLE_TEXT = new TextStyle(DEFAULT_FONT, Font.PLAIN, 10, 0, 0, 0);
    TextStyle DEFAULT_TIMELINE_SPECIAL_TEXT = new TextStyle(DEFAULT_FONT, Font.PLAIN, 12, 0, 82, 186);
    // time zone
    int DEFAULT_TIMELINE_TIMEZONE_SPACING = 2;
    int DEFAULT_TIMELINE_TIMEZONE_PADDING = 5;
    TextStyle DEFAULT_TIMELINE_TIMEZONE_LABEL_TEXT = new TextStyle(DEFAULT_FONT, Font.PLAIN, 10, 0, 0, 0);
    TextStyle DEFAULT_TIMELINE_TIMEZONE_NAME_TEXT = new TextStyle(DEFAULT_FONT, Font.PLAIN, 24, 0, 82, 186);

    // values
    TextStyle DEFAULT_VALUES_TEXT = new TextStyle(DEFAULT_FONT, Font.PLAIN, 10, 0, 0, 0);
    String DEFAULT_VALUES_FORMAT = "#.##";
    String DEFAULT_VALUES_LOCALE = "en";
    int DEFAULT_VALUES_MINIMUM_GAP = 30;

    // legend
    int DEFAULT_LEGEND_HORIZONTAL_SPACING = 20;
    int DEFAULT_LEGEND_VERTICAL_SPACING = 5;
    TextStyle DEFAULT_LEGEND_TEXT = new TextStyle(DEFAULT_FONT, Font.PLAIN, 12, 0, 0, 0);
    String DEFAULT_LEGEND_FORMAT = "#.##";
    String DEFAULT_LEGEND_LOCALE = "en";
    double DEFAULT_LEGEND_PRECISION = 0.01;
    Color DEFAULT_LEGEND_COLOR_BOX_BORDER = new Color(0, 0, 0);
    int DEFAULT_LEGEND_COLOR_BOX_SIZE = 10;
    int DEFAULT_LEGEND_COLOR_BOX_SPACING = 5;
}