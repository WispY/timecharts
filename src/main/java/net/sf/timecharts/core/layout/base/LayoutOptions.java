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

import net.sf.timecharts.core.style.IStyle;

import java.util.Set;

/**
 * @author WispY
 */
public class LayoutOptions {
    private Set<String> disabledFeatures;
    private IStyle style;
    private ITimeResolver timeResolver;
    private Class<? extends IStyle> styleType;
    private boolean debug;

    public Set<String> getDisabledFeatures() {
        return disabledFeatures;
    }

    public void setDisabledFeatures(Set<String> disabledFeatures) {
        this.disabledFeatures = disabledFeatures;
    }

    public IStyle getStyle() {
        return style;
    }

    public void setStyle(IStyle style) {
        this.style = style;
    }

    public Class<? extends IStyle> getStyleType() {
        return styleType;
    }

    public ITimeResolver getTimeResolver() {
        return timeResolver;
    }

    public void setTimeResolver(ITimeResolver timeResolver) {
        this.timeResolver = timeResolver;
    }

    public void setStyleType(Class<? extends IStyle> styleType) {
        this.styleType = styleType;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}