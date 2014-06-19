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

import java.awt.*;

/**
 * @author WispY
 */
public class GraphGridStyle extends AbstractFunctionalStyle {
    private Color background;
    private Color grid;
    private Color axis;
    private int arrowSize;
    private int subdivision;
    private int minimumGrid;

    public GraphGridStyle() {
        this.background = DEFAULT_GRID_BACKGROUND;
        this.grid = DEFAULT_GRID_GRID;
        this.axis = DEFAULT_GRID_AXIS;
        this.arrowSize = DEFAULT_GRID_ARROW_SIZE;
        this.subdivision = DEFAULT_GRID_SUBDIVISION;
        this.minimumGrid = DEFAULT_GRID_MINIMUM_GRID;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Color getGrid() {
        return grid;
    }

    public void setGrid(Color grid) {
        this.grid = grid;
    }

    public Color getAxis() {
        return axis;
    }

    public void setAxis(Color axis) {
        this.axis = axis;
    }

    public int getArrowSize() {
        return arrowSize;
    }

    public void setArrowSize(int arrowSize) {
        this.arrowSize = arrowSize;
    }

    public int getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(int subdivision) {
        this.subdivision = subdivision;
    }

    public int getMinimumGrid() {
        return minimumGrid;
    }

    public void setMinimumGrid(int minimumGrid) {
        this.minimumGrid = minimumGrid;
    }
}