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
package net.sf.timecharts.bundle.light.style;

import net.sf.timecharts.core.bean.layout.IFeature;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author WispY
 */
public class AbstractLightStyle implements ILightStyle {
    private static final Set<String> FEATURES;

    static {
        FEATURES = new HashSet<String>();
        FEATURES.add(IFeature.TITLE);
        FEATURES.add(IFeature.CHART);
        FEATURES.add(IFeature.TIMELINE);
        FEATURES.add(IFeature.ITEM);
        FEATURES.add(IFeature.VALUE);
        FEATURES.add(IFeature.BIG_NUMBER);
        FEATURES.add(IFeature.LEGEND);
    }

    @Override
    public String getName() {
        return "tc:light";
    }

    @Override
    public Set<String> getFeatures() {
        return FEATURES;
    }

    public Color stylize(Color color) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        if (hsb[1] <= 0.6f) {
            return color;
        }
        hsb[1] = 0.6f;
        return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }

}