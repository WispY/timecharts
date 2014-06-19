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
package net.sf.timecharts.core.layout.manager;

import net.sf.timecharts.core.bean.layout.Accessor;
import net.sf.timecharts.core.layout.base.LayoutBox;

import java.util.LinkedList;
import java.util.List;

import static net.sf.timecharts.core.utils.GraphUtils.getMax;
import static net.sf.timecharts.core.utils.GraphUtils.getMin;

/**
 * @author WispY
 */
public class LayoutManager {
    public void layoutSizeAndPosition(LayoutBox box, Accessor accessor) {
        Integer forcedSize = box.forcedSize.get(accessor);
        if (forcedSize != null) {
            box.stretched.set(accessor, true);
            box.size.set(accessor, forcedSize);
        }

        if (box.stretched.get(accessor)) {
            layoutFromChildren(box, accessor);
        } else if (box.size.get(accessor) == 0 && !box.children.isEmpty()) {
            layoutFromChildren(box, accessor);
        }
    }

    public void layoutFromChildren(LayoutBox box, Accessor accessor) {
        Integer minGrid = null;
        Integer maxGrid = null;

        for (LayoutBox child : box.children) {
            minGrid = getMin(minGrid, child.grid.get(accessor));
            maxGrid = getMax(maxGrid, child.grid.get(accessor) + child.span.get(accessor));
        }
        int count = minGrid == null ? 0 : maxGrid - minGrid;

        int[] sizes = new int[count];
        List<LayoutBox> spannedChildren = null;
        List<LayoutBox> stretchedChildren = null;
        // fill sizes of single-cell children
        for (LayoutBox child : box.children) {
            layoutSizeAndPosition(child, accessor);

            if (child.stretch.get(accessor)) {
                if (stretchedChildren == null) {
                    stretchedChildren = new LinkedList<LayoutBox>();
                }
                stretchedChildren.add(child);
            }
            Integer spanCount = child.span.get(accessor);
            if (spanCount == 1) {
                int index = child.grid.get(accessor) - minGrid;
                sizes[index] = Math.max(sizes[index], child.size.get(accessor));
            } else if (spanCount > 1) {
                if (spannedChildren == null) {
                    spannedChildren = new LinkedList<LayoutBox>();
                }
                spannedChildren.add(child);
            }
        }
        // wide up sizes for multiple-cell children
        if (spannedChildren != null) {
            for (LayoutBox child : box.children) {
                int spanCount = child.span.get(accessor);
                if (child.stretch.get(accessor) || spanCount < 2) {
                    continue;
                }

                int spanSize = getChildSpannedSize(box, child, sizes, accessor, minGrid);
                int startingIndex = child.grid.get(accessor) - minGrid;

                Integer childSize = child.size.get(accessor);
                if (childSize > spanSize) {
                    int addition = (int) Math.ceil((childSize - spanSize) / spanCount);
                    for (int offset = 0; offset < spanCount; offset++) {
                        sizes[startingIndex + offset] += addition;
                    }
                }
            }
        }

        // calculate size from children
        int totalSize = box.padding * 2;
        boolean first = true;
        for (int size : sizes) {
            totalSize += size;
            if (first) {
                first = false;
            } else {
                totalSize += box.spacing + box.customSpacing.get(accessor);
            }
        }
        // set parent size if not stretching, otherwise calculate stretch with weights
        if (!box.stretched.get(accessor)) {
            box.size.set(accessor, totalSize);
        } else {
            double[] weights = new double[count];
            // get weights
            for (LayoutBox child : box.children) {
                // ignore spanned children weights
                if (child.span.get(accessor) != 1) {
                    continue;
                }
                int index = child.grid.get(accessor) - minGrid;
                weights[index] = Math.max(weights[index], child.weight.get(accessor));
            }
            // get weight normalization - sum of weights
            double weightSum = 0.0;
            for (double weight : weights) {
                weightSum += weight;
            }
            // no stretch with zero weights
            if (weightSum > 0.0) {
                // addition can be negative - it will narrow the layout
                int addition = box.size.get(accessor) - totalSize;
                // increase/decrease sizes using weights
                for (int index = 0; index < sizes.length; index++) {
                    sizes[index] = sizes[index] + (int) (addition * weights[index] / weightSum);
                }
            }
        }

        // calculate stretched children
        if (stretchedChildren != null) {
            for (LayoutBox child : box.children) {
                if (!child.stretch.get(accessor)) {
                    continue;
                }

                int spanSize = getChildSpannedSize(box, child, sizes, accessor, minGrid);
                if (child.size.get(accessor) < spanSize) {
                    child.stretched.set(accessor, true);
                    child.size.set(accessor, spanSize);
                    layoutSizeAndPosition(child, accessor);
                }
            }
        }

        // position and align children
        for (LayoutBox child : box.children) {
            int index = child.grid.get(accessor) - minGrid;
            int spanSize = getChildSpannedSize(box, child, sizes, accessor, minGrid);
            int offset = box.padding + index * (box.spacing + box.customSpacing.get(accessor));
            for (int offsetIndex = 0; offsetIndex < index; offsetIndex++) {
                offset += sizes[offsetIndex];
            }
            align(child, accessor, offset, spanSize);
        }
    }

    public int getChildSpannedSize(LayoutBox parent, LayoutBox child, int[] sizes, Accessor accessor, int minGrid) {
        boolean ignoreSpacing = true;
        int spanSize = 0;
        int spanCount = child.span.get(accessor);
        int startingIndex = child.grid.get(accessor) - minGrid;
        for (int offset = 0; offset < spanCount; offset++) {
            if (ignoreSpacing) {
                ignoreSpacing = false;
            } else {
                spanSize += parent.spacing + parent.customSpacing.get(accessor);
            }
            spanSize += sizes[startingIndex + offset];
        }
        return spanSize;
    }

    public void align(LayoutBox box, Accessor accessor, int offset, int maximumSize) {
        switch (box.align.get(accessor)) {
            default:
                // fall through
            case 0:
                box.position.set(accessor, offset);
                break;
            case 1:
                box.position.set(accessor, offset + maximumSize / 2 - box.size.get(accessor) / 2);
                break;
            case 2:
                box.position.set(accessor, offset + maximumSize - box.size.get(accessor));
                break;
        }
    }
}