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
package net.sf.timecharts.core.context;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author WispY
 */
public enum Periods {
    INSTANCE;

    private Set<Long> periods = new TreeSet<Long>();

    public void register(long period) {
        periods.add(period);
    }

    public Set<Long> getPeriods() {
        return periods;
    }
}