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
package net.sf.timecharts.core.layout.base;

import net.sf.timecharts.core.bean.layout.Axis;
import net.sf.timecharts.core.bean.layout.Size;
import net.sf.timecharts.core.bean.model.Model;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author WispY
 */
public class LayoutBox {
    // calculated size or end-box size
    public Size<Integer> size;
    // forced size (enables stretching inner boxes)
    public Size<Integer> forcedSize;

    // layout
    public Axis<Integer> grid;
    public Size<Integer> span;
    public Axis<Integer> align;
    public Axis<Double> weight;
    public Axis<Boolean> stretch;
    // layout result
    public Axis<Boolean> stretched;

    // relative position
    public Axis<Integer> position;

    // style
    public Color border;
    public Color fill;

    // inner layout
    public List<LayoutBox> children;
    public int padding;
    public int spacing;
    public Axis<Integer> customSpacing;

    public Model model;

    public LayoutBox(Model model) {
        this.model = model;

        size = new Size<Integer>(0);
        forcedSize = new Size<Integer>(null);
        align = new Axis<Integer>(0);
        grid = new Axis<Integer>(0);
        span = new Size<Integer>(1);
        weight = new Axis<Double>(0.0);
        stretch = new Axis<Boolean>(false);
        stretched = new Axis<Boolean>(false);
        position = new Axis<Integer>(0);
        customSpacing = new Axis<Integer>(0);
        children = new LinkedList<LayoutBox>();
    }

    public LayoutBox(Model model, int width, int height) {
        this(model);
        this.size.width = width;
        this.size.height = height;
    }

    public void addChild(LayoutBox box, int gridX, int gridY) {
        children.add(box);
        box.grid.x = gridX;
        box.grid.y = gridY;
    }

    public void draw(Graphics2D graphics) {
    }

}