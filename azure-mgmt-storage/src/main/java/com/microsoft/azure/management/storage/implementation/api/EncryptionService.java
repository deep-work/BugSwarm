/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.storage.implementation.api;

import org.joda.time.DateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An encrypted service.
 */
public class EncryptionService {
    /**
     * A boolean indicating whether or not the service is encrypted.
     */
    private Boolean enabled;

    /**
     * Gets a time value indicating when was the encryption enabled by the
     * user last time. We return this value only when encryption is enabled.
     * There might be some unencrypted blobs which were written after this
     * time. This time is just to give a rough estimate of when encryption
     * was enabled.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private DateTime lastEnabledTime;

    /**
     * Get the enabled value.
     *
     * @return the enabled value
     */
    public Boolean enabled() {
        return this.enabled;
    }

    /**
     * Set the enabled value.
     *
     * @param enabled the enabled value to set
     * @return the EncryptionService object itself.
     */
    public EncryptionService withEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    /**
     * Get the lastEnabledTime value.
     *
     * @return the lastEnabledTime value
     */
    public DateTime lastEnabledTime() {
        return this.lastEnabledTime;
    }

}
