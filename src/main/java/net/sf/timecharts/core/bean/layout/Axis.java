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

/**
 * @author WispY
 */
public class Axis<T> implements IAccessible<T> {
    public T x;
    public T y;

    public Axis(T defaultValue) {
        this.x = defaultValue;
        this.y = defaultValue;
    }

    public T get(Accessor accessor) {
        switch (accessor) {
            case HORIZONTAL:
                return x;
            case VERTICAL:
                return y;
            default:
                return null;
        }
    }

    public void set(Accessor accessor, T value) {
        switch (accessor) {
            case HORIZONTAL:
                x = value;
                return;
            case VERTICAL:
                y = value;
                return;
            default:
        }
    }

}