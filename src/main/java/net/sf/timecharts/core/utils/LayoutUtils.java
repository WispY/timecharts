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

/**
 * @author WispY
 */
public class LayoutUtils {
    public static String capitalize(String string) {
        if (string == null) {
            return null;
        } else if (string.length() == 0) {
            return "";
        } else if (Character.isUpperCase(string.charAt(0))) {
            return string;
        } else {
            char[] chars = string.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            return new String(chars);
        }
    }

    public static boolean isNotBlank(CharSequence sequence) {
        return !isBlank(sequence);
    }

    public static boolean isBlank(CharSequence sequence) {
        int length;
        if (sequence == null || (length = sequence.length()) == 0) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(sequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
