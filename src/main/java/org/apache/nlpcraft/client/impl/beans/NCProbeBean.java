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

package org.apache.nlpcraft.client.impl.beans;

import com.google.gson.annotations.SerializedName;
import org.apache.nlpcraft.client.NCModel;
import org.apache.nlpcraft.client.NCProbe;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * REST bean.
 */
public class NCProbeBean implements NCProbe {
    @SerializedName("probeId") private String id;
    @SerializedName("probeToken") private String probeToken;
    @SerializedName("probeGuid") private String probeGuid;
    @SerializedName("probeApiVersion") private String probeApiVersion;
    @SerializedName("probeApiDate") private String probeApiDate;
    @SerializedName("osVersion") private String osVersion;
    @SerializedName("osName") private String osName;
    @SerializedName("osArch") private String osArch;
    @SerializedName("startTstamp") private long startTstamp;
    @SerializedName("tmzId") private String tmzId;
    @SerializedName("tmzAbbr") private String tmzAbbr;
    @SerializedName("tmzName") private String tmzName;
    @SerializedName("userName") private String userName;
    @SerializedName("javaVersion") private String javaVersion;
    @SerializedName("javaVendor") private String javaVendor;
    @SerializedName("hostName") private String hostName;
    @SerializedName("hostAddr") private String hostAddr;
    @SerializedName("macAddr") private String macAddr;
    @SerializedName("models") private Set<NCModelBean> models;
    
    @Override public String getProbeToken() { return probeToken; }
    @Override public String getId() { return id; }
    @Override public String getProbeGuid() { return probeGuid; }
    @Override public String getProbeApiVersion() { return probeApiVersion; }
    @Override public String getProbeApiDate() { return probeApiDate; }
    @Override public String getOsVersion() { return osVersion; }
    @Override public String getOsName() { return osName; }
    @Override public String getOsArchitecture() { return osArch; }
    @Override public long getStartTimestamp() { return startTstamp; }
    @Override public String getTimezoneId() { return tmzId; }
    @Override public String getTimezoneAbbreviation() { return tmzAbbr; }
    @Override public String getTimezoneName() { return tmzName; }
    @Override public String getUserName() { return userName; }
    @Override public String getJavaVersion() { return javaVersion; }
    @Override public String getJavaVendor() { return javaVendor; }
    @Override public String getHostName() { return hostName; }
    @Override public String getHostAddress() { return hostAddr; }
    @Override public String getMacAddress() { return macAddr; }
    @Override public Set<NCModel> getModels() { return models != null ? new HashSet<>(models) : Collections.emptySet(); }
}
