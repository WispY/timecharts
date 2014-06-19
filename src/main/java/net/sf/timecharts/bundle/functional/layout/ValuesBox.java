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
package net.sf.timecharts.bundle.functional.layout;

import net.sf.timecharts.bundle.functional.style.ValuesStyle;
import net.sf.timecharts.core.bean.model.Model;
import net.sf.timecharts.core.layout.base.LayoutBox;

import java.awt.*;
import java.util.Map;

import static net.sf.timecharts.core.utils.GraphUtils.drawString;

/**
 * @author WispY
 */
public class ValuesBox extends LayoutBox {
    protected ValuesStyle style;
    protected Map<Double, String> labels;

    public ValuesBox(Model model, int width, int height, ValuesStyle style, Map<Double, String> labels) {
        super(model, width, height);
        this.style = style;
        this.labels = labels;
    }

    @Override
    public void draw(Graphics2D graphics) {
        int yOffset = style.getText().getAscent() / 2;
        for (Map.Entry<Double, String> entry : labels.entrySet()) {
            Double position = entry.getKey();
            String label = entry.getValue();

            int x = size.width - style.getText().getWidth(label);
            int y = size.height - (int) (position * size.height) + yOffset;

            drawString(label, x, y, graphics, style.getText());
        }
    }
}