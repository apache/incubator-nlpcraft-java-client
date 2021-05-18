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
import org.apache.nlpcraft.client.NCCompany;

import java.util.Map;

/**
 * REST bean.
 */
public class NCCompanyBean extends NCStatusResponseBean implements NCCompany {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("website")
    private String website;
    @SerializedName("country")
    private String country;
    @SerializedName("region")
    private String region;
    @SerializedName("city")
    private String city;
    @SerializedName("address")
    private String address;
    @SerializedName("postalCode")
    private String postalCode;
    @SerializedName("properties")
    private Map<String, Object> properties;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getWebsite() {
        return website;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public String getRegion() {
        return region;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }
}
