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
package net.sf.timecharts.core.bean.unit;

/**
 * @author WispY
 */
public class Unit {
    private String fullName;
    private String shortName;
    private Double fixedMin;
    private Double fixedMax;

    private Unit(String fullName, String shortName) {
        this(fullName, shortName, null, null);
    }

    private Unit(String fullName, String shortName, Double fixedMin, Double fixedMax) {
        this.fullName = fullName;
        this.shortName = shortName;
        this.fixedMin = fixedMin;
        this.fixedMax = fixedMax;
    }

    public String getFullName() {
        return fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public Double getFixedMin() {
        return fixedMin;
    }

    public Double getFixedMax() {
        return fixedMax;
    }

}