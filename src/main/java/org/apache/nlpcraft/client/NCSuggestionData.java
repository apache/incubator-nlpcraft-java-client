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

import java.util.List;
import java.util.Map;

/**
 * Synonym suggestion tool result descriptor.
 *
 * @see <a href="https://nlpcraft.apache.org/tools/syn_tool.html">Synonym suggestion tool</a>.
 * @see NCClient#suggestSynonyms(String, Double)
 */
public interface NCSuggestionData {
    /**
     * Gets model ID.
     *
     * @return model ID.
     */
    String getModelId();

    /**
     * Gets minimal score used.
     *
     * @return Minimal score used.
     */
    double getMinScore();

    /**
     * Gets duration of the operation in milliseconds.
     *
     * @return Duration of the operation in milliseconds.
     */
    long getDurationMs();

    /**
     * Gets timestamp of the operation.
     *
     * @return Timestamp of the operation.
     */
    long getTimestamp();

    /**
     * Gets optional error message.
     *
     * @return Error message or {@code null} if there was no error.
     */
    String getError();

    /**
     * Gets list of synonym suggestions for each model element ID.
     *
     * @return List of synonym suggestions for each model element ID.
     */
    List<Map<String, List<NCSuggestion>>> getSynonyms();

    /**
     * Optional list of warnings.
     *
     * @return List of warnings or {@code null} if there's no warnings.
     */
    List<String> getWarnings();
}
