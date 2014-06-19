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
package net.sf.timecharts.core.utils;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author WispY
 */
public enum FontCache {
    INSTANCE;

    public static final int CACHE_SIZE = 20;

    private Map<String, Font> cache;

    private FontCache() {
        cache = new LinkedHashMap<String, Font>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Font> eldest) {
                return size() > CACHE_SIZE;
            }
        };
    }

    public Font get(String fontId) {
        return cache.get(fontId);
    }

    public void put(String fontId, Font font) {
        cache.put(fontId, font);
    }
}