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
package net.sf.timecharts.bundle.light.layout;

import net.sf.timecharts.bundle.light.style.BigNumberStyle;
import net.sf.timecharts.core.bean.model.Model;
import net.sf.timecharts.core.layout.base.LayoutBox;
import net.sf.timecharts.core.layout.base.common.TextBox;

import java.awt.*;

/**
 * @author WispY
 */
public class BigNumberBox extends LayoutBox {
    private String label;
    private String value;
    private Color color;
    private Color defaultItemColor;
    private BigNumberStyle style;

    public BigNumberBox(Model model, String label, String value, Color color, Color defaultItemColor, BigNumberStyle style) {
        super(model);
        this.label = label;
        this.value = value;
        this.color = color;
        this.defaultItemColor = defaultItemColor;
        this.style = style;

        this.spacing = style.getInnerSpacing();

        this.addChild(buildStripeBox(), 0, 0);
        this.addChild(new TextBox(label, style.getLabel()), 1, 0);
        this.addChild(new TextBox(value, style.getValue()), 1, 1);
    }

    private LayoutBox buildStripeBox() {
        LayoutBox stripeBox = new LayoutBox(model, style.getStripe(), 5);
        if (color != null) {
            stripeBox.fill = style.stylize(color);
        } else {
            stripeBox.fill = defaultItemColor;
        }
        stripeBox.weight.y = 1.0;
        stripeBox.stretch.y = true;
        stripeBox.span.height = 2;
        return stripeBox;
    }

}