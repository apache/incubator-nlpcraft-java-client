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
import org.apache.nlpcraft.client.NCSuggestionData;
import org.apache.nlpcraft.client.NCSuggestion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * REST bean.
 */
public class NCSuggestionDataBean implements NCSuggestionData {
    @SerializedName("modelId") private String modelId;
    @SerializedName("minScore") private double minScore;
    @SerializedName("durationMs") private long durationMs;
    @SerializedName("timestamp") private long timestamp;
    @SerializedName("error") private String error;
    @SerializedName("suggestions") private List<Map<String, List<NCSuggestionBean>>> suggestions;
    @SerializedName("warnings") private java.util.List<String> warnings;

    @Override public List<String> getWarnings() { return warnings; }
    @Override public String getModelId() {
        return modelId;
    }
    @Override public double getMinScore() {
        return minScore;
    }
    @Override public long getDurationMs() {
        return durationMs;
    }
    @Override public long getTimestamp() { return timestamp; }
    @Override public String getError() { return error; }

    @Override
    public List<Map<String, List<NCSuggestion>>> getSynonyms() {
        return suggestions == null ?
            null :
            suggestions.stream().map(
                p -> p.entrySet().stream().collect(
                    Collectors.toMap(
                        Map.Entry::getKey,
                        x -> (List<NCSuggestion>) new ArrayList<NCSuggestion>(x.getValue())
                    )
                )
            ).collect(Collectors.toList());
    }
}
