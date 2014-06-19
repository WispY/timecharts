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
package net.sf.timecharts;

import net.sf.timecharts.core.bean.layout.Accessor;
import net.sf.timecharts.core.bean.layout.Layout;
import net.sf.timecharts.core.bean.model.Model;
import net.sf.timecharts.core.context.Styles;
import net.sf.timecharts.core.layout.base.LayoutBox;
import net.sf.timecharts.core.layout.base.LayoutOptions;
import net.sf.timecharts.core.layout.builder.ILayoutBuilder;
import net.sf.timecharts.core.layout.manager.LayoutManager;
import net.sf.timecharts.core.style.IStyle;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * @author WispY
 */
public class ImageGenerator {
    private LayoutManager manager;

    public ImageGenerator() {
        manager = new LayoutManager();
    }

    public Layout buildGraphLayout(Model model, LayoutOptions options) {
        ILayoutBuilder layoutBuilder = Styles.INSTANCE.getBuilder(options.getStyleType());
        if (layoutBuilder == null) {
            throw new IllegalArgumentException("Style " + options.getStyleType() + " is not supported");
        }
        IStyle style = options.getStyle();
        if (style == null) {
            style = layoutBuilder.getDefaultStyle();
            options.setStyle(style);
        }
        Layout layout = layoutBuilder.buildLayout(model, options);

        calculateLayout(layout);

        return layout;
    }

    public void calculateLayout(LayoutBox box) {
        for (Accessor accessor : Accessor.values()) {
            manager.layoutSizeAndPosition(box, accessor);
        }
    }

    public void drawOnCanvas(Model model, LayoutBox layout, Graphics2D graphics) {
        if (layout.fill != null) {
            graphics.setColor(layout.fill);
            graphics.fillRect(layout.position.x, layout.position.y, layout.size.width, layout.size.height);
        }
        if (layout.border != null) {
            graphics.setColor(layout.border);
            graphics.drawRect(layout.position.x, layout.position.y, layout.size.width - 1, layout.size.height - 1);
        }

        AffineTransform transform = graphics.getTransform();
        graphics.translate(layout.position.x, layout.position.y);
        layout.draw(graphics);

        for (LayoutBox child : layout.children) {
            drawOnCanvas(model, child, graphics);
        }
        graphics.setTransform(transform);
    }

    public BufferedImage drawImage(Model model, LayoutBox layout) {
        BufferedImage image = new BufferedImage(layout.size.width, layout.size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(new Color(0, 0, 0, 0));
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        drawOnCanvas(model, layout, graphics);

        return image;
    }

}