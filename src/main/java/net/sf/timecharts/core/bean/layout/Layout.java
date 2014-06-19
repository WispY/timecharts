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
package net.sf.timecharts.core.bean.layout;

import net.sf.timecharts.core.bean.model.Model;
import net.sf.timecharts.core.layout.base.LayoutBox;

import java.util.Map;

/**
 * @author WispY
 */
public class Layout extends LayoutBox {
    private Map<String, LayoutBox> featureBoxes;

    public Layout(Model model) {
        super(model);
    }

    public Map<String, LayoutBox> getFeatureBoxes() {
        return featureBoxes;
    }

    public void setFeatureBoxes(Map<String, LayoutBox> featureBoxes) {
        this.featureBoxes = featureBoxes;
    }
}