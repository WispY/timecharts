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
package net.sf.timecharts.core.model;

/**
 * @author WispY
 */
public class AllAggregator implements IAggregator {
    private IAggregator min = new MinimumAggregator();
    private IAggregator avg = new AverageAggregator();
    private IAggregator max = new MaximumAggregator();

    @Override
    public void add(double value) {
        min.add(value);
        avg.add(value);
        max.add(value);
    }

    @Override
    public double aggregate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void reset() {
        min.reset();
        avg.reset();
        max.reset();
    }

    public double getMinimum() {
        return min.aggregate();
    }

    public double getAverage() {
        return avg.aggregate();
    }

    public double getMaximum() {
        return max.aggregate();
    }
}