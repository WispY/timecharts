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
package net.sf.timecharts.core.style;

import net.sf.timecharts.core.utils.FontUtils;

import java.awt.*;

/**
 * @author WispY
 */
public class TextStyle {
    private Font font;
    private Color color;
    private int height;
    private int ascent;
    private FontMetrics metrics;

    public TextStyle(String fontId, int fontStyle, int fontSize, int red, int green, int blue) {
        this(fontId, fontStyle, fontSize, new Color(red, green, blue));
    }

    public TextStyle(String fontId, int fontStyle, int fontSize, Color color) {
        this.color = color;

        this.font = FontUtils.create(fontId).deriveFont(fontStyle, fontSize);
        this.metrics = FontUtils.getMetrics(this.font);
        this.height = this.metrics.getAscent() + this.metrics.getDescent();
        this.ascent = this.metrics.getAscent();
    }

    public TextStyle with(String base) {
        if (base == null || base.isEmpty()) {
            return this;
        }
        Rectangle bounds = FontUtils.getBounds(this.font, base);
        this.height = bounds.height;
        this.ascent = -bounds.y;
        return this;
    }

    public Font getFont() {
        return font;
    }

    public Color getColor() {
        return color;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth(String text) {
        return metrics.stringWidth(text);
    }

    public int getAscent() {
        return ascent;
    }

}