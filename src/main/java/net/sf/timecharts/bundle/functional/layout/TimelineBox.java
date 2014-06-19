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


import net.sf.timecharts.bundle.functional.style.TimelineStyle;
import net.sf.timecharts.core.bean.model.Model;
import net.sf.timecharts.core.layout.base.LayoutBox;
import net.sf.timecharts.core.style.TextStyle;
import net.sf.timecharts.core.utils.GraphUtils;

import java.awt.*;
import java.util.Map;
import java.util.Set;

/**
 * @author WispY
 */
public class TimelineBox extends LayoutBox {
    private TimelineStyle style;
    private Map<Long, String> labels;
    private Set<Long> specialLabels;

    public TimelineBox(Model model, int width, int height, TimelineStyle style, Map<Long, String> labels, Set<Long> specialLabels) {
        super(model, width, height);
        this.style = style;
        this.labels = labels;
        this.specialLabels = specialLabels;
    }

    @Override
    public void draw(Graphics2D graphics) {
        int width = model.getWidth();
        graphics.rotate(-Math.PI / 2);
        int simpleYOffset = style.getSimpleText().getAscent() / 2;
        int specialYOffset = style.getSpecialText().getAscent() / 2;
        for (Map.Entry<Long, String> entry : labels.entrySet()) {
            long time = entry.getKey();
            String label = entry.getValue();
            TextStyle textStyle;
            int offset = GraphUtils.getPosition(time, model.getTimeStart(), model.getTimeEnd() - model.getTimeStart(), width);
            if (specialLabels.contains(time)) {
                textStyle = style.getSpecialText();
                offset += specialYOffset;
            } else {
                textStyle = style.getSimpleText();
                offset += simpleYOffset;
            }
            int x = -textStyle.getWidth(label);
            GraphUtils.drawString(label, x, offset, graphics, textStyle);
        }
    }
}