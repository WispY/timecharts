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
package net.sf.timecharts.core.utils;

import net.sf.timecharts.core.style.TextStyle;

import java.awt.*;

/**
 * @author WispY
 */
public final class GraphUtils {

    private GraphUtils() {
    }

    public static int getMin(Integer min, int value) {
        if (min == null) {
            return value;
        }
        return Math.min(min, value);
    }

    public static int getMax(Integer max, int value) {
        if (max == null) {
            return value;
        }
        return Math.max(max, value);
    }

    public static int getPosition(double value, double startValue, double formerScale, double targetScale) {
        return (int) ((value - startValue) / formerScale * targetScale);
    }

    public static void drawString(String string, int x, int y, Graphics graphics, TextStyle style) {
        graphics.setFont(style.getFont());
        graphics.setColor(style.getColor());
        graphics.drawString(string, x, y);
    }

}