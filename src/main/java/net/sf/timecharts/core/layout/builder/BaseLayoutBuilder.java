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
package net.sf.timecharts.core.layout.builder;

import net.sf.timecharts.core.bean.model.Pair;
import net.sf.timecharts.core.bean.unit.Unit;
import net.sf.timecharts.core.layout.base.LayoutBox;
import net.sf.timecharts.core.layout.base.LayoutOptions;
import net.sf.timecharts.core.style.IStyle;
import net.sf.timecharts.core.utils.UnitsUtils;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author WispY
 */
public abstract class BaseLayoutBuilder<S extends IStyle> implements ILayoutBuilder<S> {
    protected Class<S> styleType;

    public BaseLayoutBuilder(Class<S> styleType) {
        this.styleType = styleType;
    }

    @Override
    public Class<S> getProducedStyle() {
        return styleType;
    }

    protected boolean isDisabled(LayoutOptions options, String feature) {
        Set<String> disabled = options.getDisabledFeatures();
        return disabled != null && disabled.contains(feature);
    }

    protected String buildSimpleValueLabel(double value, Unit unit, DecimalFormat format) {
        String[] parts = buildSimpleValueLabelParts(value, unit, format);
        if (parts[1] == null) {
            return parts[0];
        }
        return parts[0] + " " + parts[1];
    }

    protected String[] buildSimpleValueLabelParts(double value, Unit unit, DecimalFormat format) {
        String[] parts = new String[2];
        if (unit == null) {
            parts[0] = format.format(value);
            return parts;
        }

        int steps = UnitsUtils.countUpgradeSteps(value, unit);
        Unit upgradedUnit = UnitsUtils.upgradeMetricUnit(unit, steps);
        double upgradedValue = UnitsUtils.upgradeValue(value, steps, unit);
        parts[0] = format.format(upgradedValue);
        parts[1] = upgradedUnit.getShortName();
        return parts;
    }

    protected String buildPreciseValueLabel(double value, List<Unit> model, Unit unit, DecimalFormat format, double precision) {
        if (unit == null) {
            return format.format(value);
        }
        if (model == null) {
            return buildSimpleValueLabel(value, unit, format);
        }

        StringBuilder builder = new StringBuilder(20);
        String prefix = "";
        for (Pair<Unit, Double> pair : UnitsUtils.fillModel(model, value, unit)) {
            Double currentValue = pair.getRight();
            if (Math.abs(currentValue) < precision) {
                continue;
            }

            Unit currentUnit = pair.getLeft();
            builder.append(prefix).append(format.format(currentValue)).append(" ").append(currentUnit.getShortName());
            prefix = " ";
        }

        if (builder.length() == 0) {
            return buildSimpleValueLabel(value, unit, format);
        }

        return builder.toString();
    }

    protected String buildTimeLabel(long timestamp, SimpleDateFormat format, boolean special, Map<Long, String> labels, Set<Long> specials) {
        String label = format.format(new Date(timestamp));
        labels.put(timestamp, label);
        if (special) {
            specials.add(timestamp);
        }
        return label;
    }

    protected void addColors(LayoutBox layout, float hue) {
        if (layout.fill == null) {
            layout.fill = Color.getHSBColor(hue, 0.2f, 1.0f);
        }
        if (layout.border == null) {
            layout.border = Color.getHSBColor(hue, 0.5f, 0.5f);
        }
        for (LayoutBox child : layout.children) {
            addColors(child, (hue + 0.2f) % 1.0f);
        }
    }

}