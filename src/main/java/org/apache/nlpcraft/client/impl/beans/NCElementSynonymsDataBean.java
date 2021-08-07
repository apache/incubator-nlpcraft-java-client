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
import org.apache.nlpcraft.client.NCElementSynonymsData;

import java.util.List;
import java.util.Map;

/**
 * REST bean.
 */
public class NCElementSynonymsDataBean extends NCStatusResponseBean implements NCElementSynonymsData {
    @SerializedName("synonymsCnt")
    private int synonymsCnt;

    @SerializedName("synonymsExpCnt")
    private int synonymsExpCnt;

    @SerializedName("synonymsExpRatePct")
    private long synonymsExpRatePct;

    @SerializedName("synonyms")
    private List<String> synonyms;

    @SerializedName("synonymsExp")
    private List<String> synonymsExp;

    @SerializedName("values")
    private Map<String, List<String>> values;

    @SerializedName("valuesExp")
    private Map<String, List<String>> valuesExp;

    @Override
    public int getSynonymsCnt() {
        return synonymsCnt;
    }

    @Override
    public int getSynonymsExpCnt() {
        return synonymsExpCnt;
    }

    @Override
    public long getSynonymsExpRatePct() {
        return synonymsExpRatePct;
    }

    @Override
    public List<String> getSynonyms() {
        return synonyms;
    }

    @Override
    public List<String> getSynonymsExp() {
        return synonymsExp;
    }

    @Override
    public Map<String, List<String>> getValues() {
        return values;
    }

    @Override
    public Map<String, List<String>> getValuesExp() {
        return valuesExp;
    }
}
