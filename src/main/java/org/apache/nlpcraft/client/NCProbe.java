/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.nlpcraft.client;

import java.util.Set;

/**
 * Data probe descriptor.
 *
 * @see NCClient#getProbes()
 */
public interface NCProbe {
    /**
     * Gets probe ID.
     *
     * @return Probe ID.
     */
    String getId();

    /**
     * Gets probe token. Both REST server and the probe should be configured with the same token
     * to be able to connect. Note that probe token should be kept secure.
     *
     * @return Probe token.
     */
    String getProbeToken();

    /**
     * Gets internal globally unique ID of the probe runtime instance.
     *
     * @return Gets probe GUID.
     */
    String getProbeGuid();

    /**
     * Probe API version.
     *
     * @return Gets probe API version.
     */
    String getProbeApiVersion();

    /**
     * Gets release date of the probe API.
     *
     * @return Release date of the probe API.
     */
    String getProbeApiDate();

    /**
     * Gets OS version for the probe's runtime.
     *
     * @return OS version for the probe's runtime.
     */
    String getOsVersion();

    /**
     * Gets OS name for the probe's runtime.
     *
     * @return OS name for the probe's runtime.
     */
    String getOsName();

    /**
     * Gets OS architecture for the probe's runtime.
     *
     * @return OS architecture for the probe's runtime.
     */
    String getOsArchitecture();

    /**
     * Gets probe start time in UTC timezone.
     *
     * @return Probe start time in UTC timezone.
     */
    long getStartTimestamp();

    /**
     * Gets timezone ID for the probe's runtime.
     *
     * @return Timezone ID for the probe's runtime.
     */
    String getTimezoneId();

    /**
     * Gets timezone official abbreviation for the probe's runtime.
     *
     * @return Timezone official abbreviation for the probe's runtime.
     */
    String getTimezoneAbbreviation();

    /**
     * Gets timezone name for the probe's runtime.
     *
     * @return Timezone name for the probe's runtime.
     */
    String getTimezoneName();

    /**
     * Gets the system user name for the probe's runtime.
     *
     * @return System user name for the probe's runtime.
     */
    String getUserName();

    /**
     * Gets the Java/JVM version for the probe's runtime.
     *
     * @return Java/JVM version for the probe's runtime.
     */
    String getJavaVersion();

    /**
     * Gets the Java/JVM vendor name for the probe's runtime.
     *
     * @return Java/JVM vendor name for the probe's runtime.
     */
    String getJavaVendor();

    /**
     * Gets the hostname for the probe's runtime.
     *
     * @return Hostname for the probe's runtime.
     */
    String getHostName();

    /**
     * Gets the host IP address for the probe's runtime.
     *
     * @return Host IP address for the probe's runtime.
     */
    String getHostAddress();

    /**
     * Gets MAC address for the probe's runtime.
     *
     * @return MAC address for the probe's runtime.
     */
    String getMacAddress();

    /**
     * Gets a non-empty list of models deployed by this probe.
     *
     * @return A non-empty list of models deployed by this probe.
     */
    Set<NCModel> getModels();
}
