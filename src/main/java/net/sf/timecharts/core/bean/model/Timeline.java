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

import net.sf.timecharts.core.context.Periods;

/**
 * @author WispY
 */
public class Timeline {
    public long start;
    public long end;

    public long offset;
    public long gap;

    public Timeline() {
    }

    public Timeline(long start, long end, int width, int minGapWidth) {
        this.end = end;
        this.start = start;
        long timeWidth = end - start;
        int gapCount = width / minGapWidth;
        long gapTimeWidth = timeWidth / gapCount;

        Long lowerPeriod = null;
        Long upperPeriod = null;
        for (Long period : Periods.INSTANCE.getPeriods()) {
            upperPeriod = period;
            if (gapTimeWidth > period) {
                lowerPeriod = period;
            } else {
                break;
            }
        }
        if (upperPeriod == null) {
            throw new IllegalArgumentException("Could not build timeline, no periods registered in context");
        }
        if (lowerPeriod == null) {
            this.gap = upperPeriod;
        } else if (lowerPeriod.equals(upperPeriod)) {
            int factor = 1;
            while (lowerPeriod * factor < gapTimeWidth) {
                factor++;
            }
            this.gap = lowerPeriod * factor;
        } else {
            int resultFactor = 1;
            int currentFactor = 1;
            while (lowerPeriod * resultFactor < gapTimeWidth) {
                currentFactor++;
                if (upperPeriod < lowerPeriod * currentFactor) {
                    resultFactor = currentFactor;
                    break;
                }
                if (upperPeriod % (lowerPeriod * currentFactor) == 0) {
                    resultFactor = currentFactor;
                }
            }
            this.gap = resultFactor * lowerPeriod;
        }

        if (this.start % this.gap != 0) {
            this.offset = this.gap - this.start % this.gap;
        }
    }
}