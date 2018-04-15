package com.assignment.utils;

import java.util.List;

/**
 * @author Mahesh R Bhatkande (mahesh.bhatkande@infosys.com)
 * @since 14 Apr, 2018
 */

public class Validation {

    /**
     * Check {@link String} is null or empty.
     *
     * @param value {@link String} value
     * @return boolean {@code true} if value is null or empty else {@code false}
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    /**
     * Check if the List is null
     *
     * @param objectList List of {@link Object}
     * @return boolean {@code true} if value is null else {@code false}
     */
    public static boolean isNotNull(List objectList) {
        return objectList == null;
    }
}
