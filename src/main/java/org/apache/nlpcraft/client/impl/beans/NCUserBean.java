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
import org.apache.nlpcraft.client.NCUser;

import java.util.Map;

/**
 * REST bean.
 */
public class NCUserBean extends NCStatusResponseBean implements NCUser {
    @SerializedName("id")
    private long id;
    @SerializedName("usrExtId")
    private String usrExtId;
    @SerializedName("email")
    private String email;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("avatarUrl")
    private String avatarUrl;
    @SerializedName("isAdmin")
    private boolean isAdmin;
    @SerializedName("properties")
    private Map<String, Object> properties;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @Override
    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public String getExternalId() {
        return usrExtId;
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }
}
