/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.batch.protocol.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for ComputeNodeRebootOption.
 */
public enum ComputeNodeRebootOption {
    /** Enum value requeue. */
    REQUEUE("requeue"),

    /** Enum value terminate. */
    TERMINATE("terminate"),

    /** Enum value taskcompletion. */
    TASKCOMPLETION("taskcompletion"),

    /** Enum value retaineddata. */
    RETAINEDDATA("retaineddata");

    /** The actual serialized value for a ComputeNodeRebootOption instance. */
    private String value;

    ComputeNodeRebootOption(String value) {
        this.value = value;
    }

    /**
     * Gets the serialized value for a ComputeNodeRebootOption instance.
     *
     * @return the serialized value.
     */
    @JsonValue
    public String toValue() {
        return this.value;
    }

    /**
     * Parses a serialized value to a ComputeNodeRebootOption instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed ComputeNodeRebootOption object, or null if unable to parse.
     */
    @JsonCreator
    public static ComputeNodeRebootOption fromValue(String value) {
        ComputeNodeRebootOption[] items = ComputeNodeRebootOption.values();
        for (ComputeNodeRebootOption item : items) {
            if (item.toValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return toValue();
    }
}
