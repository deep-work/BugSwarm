/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.batch.protocol.models;

import java.util.List;

/**
 * An error that occurred when resizing a pool.
 */
public class ResizeError {
    /**
     * An identifier for the pool resize error. Codes are invariant and are
     * intended to be consumed programmatically.
     */
    private String code;

    /**
     * A message describing the pool resize error, intended to be suitable for
     * display in a user interface.
     */
    private String message;

    /**
     * A list of additional error details related to the pool resize error.
     */
    private List<NameValuePair> values;

    /**
     * Get the code value.
     *
     * @return the code value
     */
    public String code() {
        return this.code;
    }

    /**
     * Set the code value.
     *
     * @param code the code value to set
     * @return the ResizeError object itself.
     */
    public ResizeError setCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * Get the message value.
     *
     * @return the message value
     */
    public String message() {
        return this.message;
    }

    /**
     * Set the message value.
     *
     * @param message the message value to set
     * @return the ResizeError object itself.
     */
    public ResizeError setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Get the values value.
     *
     * @return the values value
     */
    public List<NameValuePair> values() {
        return this.values;
    }

    /**
     * Set the values value.
     *
     * @param values the values value to set
     * @return the ResizeError object itself.
     */
    public ResizeError setValues(List<NameValuePair> values) {
        this.values = values;
        return this;
    }

}
