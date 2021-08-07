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
import org.apache.nlpcraft.client.NCElement;
import org.apache.nlpcraft.client.NCValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST bean.
 */
public class NCElementBean implements NCElement {
    @SerializedName("id") private String id;
    @SerializedName("groups") private List<String> groups;
    @SerializedName("metadata") private Map<String, Object> metadata;
    @SerializedName("description") private String description;
    @SerializedName("values") private List<NCValueBean> values;
    @SerializedName("parentId") private String parentId;
    @SerializedName("synonyms") private List<String> synonyms;
    // java.lang.Boolean but not boolean
    @SerializedName("permutateSynonyms") private Boolean permutateSynonyms;
    // java.lang.Boolean but not boolean
    @SerializedName("sparse") private Boolean sparse;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<String> getGroups() {
        return groups;
    }

    @Override
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<NCValue> getValues() {
        return values != null ? new ArrayList<>(values) : null;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    @Override
    public List<String> getSynonyms() {
        return synonyms;
    }

    @Override
    public Optional<Boolean> isPermutateSynonyms() {
        return Optional.ofNullable(permutateSynonyms);
    }

    @Override
    public Optional<Boolean> isSparse() {
        return Optional.ofNullable(sparse);
    }
}
