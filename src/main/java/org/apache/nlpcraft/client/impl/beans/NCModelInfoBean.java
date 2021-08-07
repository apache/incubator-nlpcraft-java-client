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
import org.apache.nlpcraft.client.NCModelInfo;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * REST bean.
 */
public class NCModelInfoBean implements NCModelInfo {
    @SerializedName("id") private String id;
    @SerializedName("name") private String name;
    @SerializedName("version") private String version;
    @SerializedName("description") private String description;
    @SerializedName("origin") private String origin;
    @SerializedName("maxUnknownWords") private int maxUnknownWords;
    @SerializedName("maxFreeWords") private int maxFreeWords;
    @SerializedName("maxSuspiciousWords") private int maxSuspiciousWords;
    @SerializedName("minWords") private int minWords;
    @SerializedName("maxWords") private int maxWords;
    @SerializedName("minTokens") private int minTokens;
    @SerializedName("maxTokens") private int maxTokens;
    @SerializedName("minNonStopwords") private int minNonStopwords;
    @SerializedName("nonEnglishAllowed") private boolean nonEnglishAllowed;
    @SerializedName("notLatinCharsetAllowed") private boolean notLatinCharsetAllowed;
    @SerializedName("swearWordsAllowed") private boolean swearWordsAllowed;
    @SerializedName("noNounsAllowed") private boolean noNounsAllowed;
    @SerializedName("permutateSynonyms") private boolean permutateSynonyms;
    @SerializedName("dupSynonymsAllowed") private boolean dupSynonymsAllowed;
    @SerializedName("maxTotalSynonyms") private int maxTotalSynonyms;
    @SerializedName("noUserTokensAllowed") private boolean noUserTokensAllowed;
    @SerializedName("sparse") private boolean sparse;
    @SerializedName("metadata") private Map<String, Object> metadata;
    @SerializedName("additionalStopWords") private Set<String> additionalStopWords;
    @SerializedName("excludedStopWords") private Set<String> excludedStopWords;
    @SerializedName("suspiciousWords") private Set<String> suspiciousWords;
    @SerializedName("macros") private Map<String, String> macros;
    @SerializedName("elements") private Set<NCElementBean> elements;
    @SerializedName("enabledBuiltInTokens") private Set<String> enabledBuiltInTokens;
    @SerializedName("abstractTokens") private Set<String> abstractTokens;
    @SerializedName("maxElementSynonyms") private int maxElementSynonyms;
    @SerializedName("maxSynonymsThresholdError") private boolean maxSynonymsThresholdError;
    @SerializedName("conversationTimeout") private long conversationTimeout;
    @SerializedName("conversationDepth") private int conversationDepth;
    @SerializedName("restrictedCombinations") private Map<String, Set<String>> restrictedCombinations;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getOrigin() {
        return origin;
    }

    @Override
    public int getMaxUnknownWords() {
        return maxUnknownWords;
    }

    @Override
    public int getMaxFreeWords() {
        return maxFreeWords;
    }

    @Override
    public int getMaxSuspiciousWords() {
        return maxSuspiciousWords;
    }

    @Override
    public int getMinWords() {
        return minWords;
    }

    @Override
    public int getMaxWords() {
        return maxWords;
    }

    @Override
    public int getMinTokens() {
        return minTokens;
    }

    @Override
    public int getMaxTokens() {
        return maxTokens;
    }

    @Override
    public int getMinNonStopwords() {
        return minNonStopwords;
    }

    @Override
    public boolean isNonEnglishAllowed() {
        return nonEnglishAllowed;
    }

    @Override
    public boolean isNotLatinCharsetAllowed() {
        return notLatinCharsetAllowed;
    }

    @Override
    public boolean isSwearWordsAllowed() {
        return swearWordsAllowed;
    }

    @Override
    public boolean isNoNounsAllowed() {
        return noNounsAllowed;
    }

    @Override
    public boolean isPermutateSynonyms() {
        return permutateSynonyms;
    }

    @Override
    public boolean isDupSynonymsAllowed() {
        return dupSynonymsAllowed;
    }

    @Override
    public int getMaxTotalSynonyms() {
        return maxTotalSynonyms;
    }

    @Override
    public boolean isNoUserTokensAllowed() {
        return noUserTokensAllowed;
    }

    @Override
    public boolean isSparse() {
        return sparse;
    }

    @Override
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    @Override
    public Set<String> getAdditionalStopWords() {
        return additionalStopWords;
    }

    @Override
    public Set<String> getExcludedStopWords() {
        return excludedStopWords;
    }

    @Override
    public Set<String> getSuspiciousWords() {
        return suspiciousWords;
    }

    @Override
    public Map<String, String> getMacros() {
        return macros;
    }

    @Override
    public Set<NCElement> getElements() {
        return elements != null ? new HashSet<>(elements) : null;
    }

    @Override
    public Set<String> getEnabledBuiltInTokens() {
        return enabledBuiltInTokens;
    }

    @Override
    public Set<String> getAbstractTokens() {
        return abstractTokens;
    }

    @Override
    public int getMaxElementSynonyms() {
        return maxElementSynonyms;
    }

    @Override
    public boolean isMaxSynonymsThresholdError() {
        return maxSynonymsThresholdError;
    }

    @Override
    public long getConversationTimeout() {
        return conversationTimeout;
    }

    @Override
    public int getConversationDepth() {
        return conversationDepth;
    }

    @Override
    public Map<String, Set<String>> getRestrictedCombinations() {
        return restrictedCombinations;
    }
}
