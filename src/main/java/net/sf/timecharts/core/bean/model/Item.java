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
package net.sf.timecharts.core.bean.model;

import java.awt.*;
import java.util.List;

/**
 * @author WispY
 */
public class Item {
    private String name;
    private double last;
    private double min;
    private double avg;
    private double max;
    private boolean unchanged;
    private boolean empty;
    private Color color;
    private List<List<Value>> valueGroups;
    private String function;
    private long granularity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public boolean isUnchanged() {
        return unchanged;
    }

    public void setUnchanged(boolean unchanged) {
        this.unchanged = unchanged;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<List<Value>> getValueGroups() {
        return valueGroups;
    }

    public void setValueGroups(List<List<Value>> valueGroups) {
        this.valueGroups = valueGroups;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public long getGranularity() {
        return granularity;
    }

    public void setGranularity(long granularity) {
        this.granularity = granularity;
    }
}