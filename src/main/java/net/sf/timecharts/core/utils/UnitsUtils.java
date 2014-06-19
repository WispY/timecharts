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

import net.sf.timecharts.core.bean.model.Pair;
import net.sf.timecharts.core.bean.unit.Unit;
import net.sf.timecharts.core.bean.unit.UnitGroup;
import net.sf.timecharts.core.context.UnitGroups;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author WispY
 */
public final class UnitsUtils {
    private static final double DEFAULT_CONVERSION_FACTOR = 1.0;

    private UnitsUtils() {
    }

    public static int countUpgradeSteps(double value, Unit unit) {
        return countUpgradeSteps(value, unit, DEFAULT_CONVERSION_FACTOR);
    }

    public static int countUpgradeSteps(double value, Unit unit, double conversionFactor) {
        UnitGroup group = UnitGroups.INSTANCE.getGroupFor(unit);
        if (group == null) {
            return 0;
        }

        int unitIndex = group.getUnits().indexOf(unit);
        int groupSize = group.getUnits().size();
        double rate = group.getConversionRate();

        double convertedValue = value;
        int upgradeSteps = 0;
        int maxUpgradeStepsPossible = groupSize - unitIndex - 1;
        while (convertedValue > rate * conversionFactor && upgradeSteps < maxUpgradeStepsPossible) {
            upgradeSteps++;
            convertedValue /= rate;
        }

        return upgradeSteps;
    }

    public static Unit upgradeMetricUnit(Unit unit, int steps) {
        UnitGroup group = UnitGroups.INSTANCE.getGroupFor(unit);
        if (group == null) {
            return unit;
        }
        int unitIndex = group.getUnits().indexOf(unit);
        int groupSize = group.getUnits().size();
        if (unitIndex + steps >= groupSize) {
            throw new IllegalArgumentException("Upgrade steps number sets unit out of group bounds!");
        }
        return group.getUnits().get(unitIndex + steps);
    }

    public static double upgradeValue(double value, int steps, Unit unit) {
        UnitGroup group = UnitGroups.INSTANCE.getGroupFor(unit);
        if (group == null) {
            return value;
        }
        return value / Math.pow(group.getConversionRate(), steps);
    }

    public static double convertValue(double value, Unit from, Unit to) {
        if (from == to) {
            return value;
        }

        UnitGroup group = UnitGroups.INSTANCE.getGroupFor(from);
        if (UnitGroups.INSTANCE.getGroupFor(to) != group) {
            throw new IllegalArgumentException("Units belong to different groups: " + from + ", " + to);
        }

        double resultValue = value;
        int operation = 0;

        loop:
        for (Unit unit : group.getUnits()) {
            switch (operation) {
                case 0: /* no operation */
                    if (unit == from) {
                        operation = -1;
                    } else if (unit == to) {
                        operation = 1;
                    }
                    break;
                case -1: /* division, unit is upgrading */
                    if (unit == to) {
                        break loop;
                    }
                    break;
                case 1: /* multiplication, unit is downgrading */
                    if (unit == from) {
                        break loop;
                    }
                    break;
            }
            resultValue = resultValue * Math.pow(group.getConversionRate(), operation);
        }

        return resultValue;
    }

    public static List<Unit> buildPreciseModel(double minValue, double maxValue, double precision, Unit unit) {
        if (unit == null) {
            return null;
        }
        if (Math.abs(maxValue - minValue) < precision) {
            return null;
        }

        UnitGroup unitGroup = UnitGroups.INSTANCE.getGroupFor(unit);
        if (unitGroup == null) {
            return null;
        }

        List<Unit> model = new LinkedList<Unit>();
        double difference = maxValue - minValue;
        int differenceSteps = countUpgradeSteps(difference, unit, precision);
        Unit lowestDifferenceUnit = upgradeMetricUnit(unit, differenceSteps);
        boolean startAdding = false;
        for (Unit metricUnit : unitGroup.getUnits()) {
            if (metricUnit == lowestDifferenceUnit) {
                startAdding = true;
            }
            if (startAdding) {
                model.add(metricUnit);
            }
        }
        Collections.reverse(model);
        return model;
    }

    public static List<Pair<Unit, Double>> fillModel(List<Unit> model, double value, Unit baseUnit) {
        List<Pair<Unit, Double>> filledModel = new LinkedList<Pair<Unit, Double>>();
        double currentValue = value;

        for (Iterator<Unit> iterator = model.iterator(); iterator.hasNext(); ) {
            Unit modelUnit = iterator.next();
            Pair<Unit, Double> pair = new Pair<Unit, Double>();
            pair.setLeft(modelUnit);

            double modelValue = convertValue(currentValue, baseUnit, modelUnit);
            if (iterator.hasNext()) {
                modelValue = Math.floor(modelValue);
            }
            pair.setRight(modelValue);
            currentValue = currentValue - convertValue(modelValue, modelUnit, baseUnit);
            filledModel.add(pair);
        }

        return filledModel;
    }

}