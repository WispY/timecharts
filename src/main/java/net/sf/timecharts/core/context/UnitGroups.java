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

import net.sf.timecharts.core.bean.unit.Unit;
import net.sf.timecharts.core.bean.unit.UnitGroup;

import java.util.HashSet;
import java.util.Set;

/**
 * @author WispY
 */
public enum UnitGroups {
    INSTANCE;

    private Set<UnitGroup> groups = new HashSet<UnitGroup>();

    public void register(UnitGroup group) {
        groups.add(group);
    }

    public Set<UnitGroup> getUnitGroups() {
        return groups;
    }

    public UnitGroup getGroupFor(Unit unit) {
        if (unit == null) {
            return null;
        }
        for (UnitGroup group : groups) {
            if (group.getUnits().contains(unit)) {
                return group;
            }
        }
        return null;
    }
}