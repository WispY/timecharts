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

import net.sf.timecharts.core.bean.layout.Layout;
import net.sf.timecharts.core.bean.model.Model;
import net.sf.timecharts.core.layout.base.LayoutOptions;
import net.sf.timecharts.core.style.IStyle;

/**
 * @author WispY
 */
public interface ILayoutBuilder<S extends IStyle> {

    Class<S> getProducedStyle();

    S getDefaultStyle();

    Layout buildLayout(Model model, LayoutOptions options);

}