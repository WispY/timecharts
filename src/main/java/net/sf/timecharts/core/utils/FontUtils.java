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

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.io.File;

/**
 * @author WispY
 */
public final class FontUtils {
    public static final String FILE = "file:";
    public static final String CLASSPATH = "classpath:";

    private static final Toolkit TOOLKIT;
    private static final FontRenderContext CONTEXT;
    private static final String REPLACEMENT_FONT = "Sans";

    static {
        TOOLKIT = Toolkit.getDefaultToolkit();
        CONTEXT = new FontRenderContext(null, false, false);
    }

    private FontUtils() {
    }

    public static Font create(String fontId) {
        Font font = FontCache.INSTANCE.get(fontId);
        if (font != null) {
            return font;
        }

        boolean useCache = false;
        if (fontId.startsWith(CLASSPATH)) {
            String fileName = fontId.substring(CLASSPATH.length());
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, FontUtils.class.getResourceAsStream(fileName));
            } catch (Exception exception) {
                font = new Font(REPLACEMENT_FONT, Font.PLAIN, 1);
            }
            useCache = true;
        } else if (fontId.startsWith(FILE)) {
            String fileName = fontId.substring(CLASSPATH.length());
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, new File(fileName));
            } catch (Exception exception) {
                font = new Font(REPLACEMENT_FONT, Font.PLAIN, 1);
            }
            useCache = true;
        } else {
            font = new Font(fontId, Font.PLAIN, 1);
        }

        if (useCache) {
            FontCache.INSTANCE.put(fontId, font);
        }
        return font;
    }

    public static FontMetrics getMetrics(Font font) {
        return TOOLKIT.getFontMetrics(font);
    }

    public static Rectangle getBounds(Font font, String text) {
        GlyphVector glyph = font.createGlyphVector(CONTEXT, text);
        return glyph.getPixelBounds(null, 0, 0);
    }

}