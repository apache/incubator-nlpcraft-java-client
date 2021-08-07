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
 * A model element synonyms data descriptor.
 *
 * @see NCClient#getSynonyms(String, String)
 */
public interface NCElementSynonymsData {
    /**
     * Gets number of synonyms (in their unexpanded forms) for the element.
     *
     * @return Number of synonyms (in their unexpanded forms) for the element.
     * @see #getSynonymsExpCnt()
     */
    int getSynonymsCnt();

    /**
     * Gets number of expanded synonyms for the element.
     *
     * @return Number of expanded synonyms for the element.
     * @see #getSynonymsCnt()
     */
    int getSynonymsExpCnt();

    /**
     * Gets the rate of number of expanded synonyms over the non-expanded ones.
     * The return value is always greater than 1.
     *
     * @return Rate of number of expanded synonyms over the non-expanded ones.
     * @see #getSynonymsCnt()
     * @see #getSynonymsExpCnt()
     */
    long getSynonymsExpRatePct();

    /**
     * Gets the list of non-expanded synonyms for this element.
     *
     * @return List of non-expanded synonyms for this element.
     * @see #getSynonymsExp()
     */
    List<String> getSynonyms();

    /**
     * Gets the list of expanded synonyms for this element.
     *
     * @return List of expanded synonyms for this element.
     * @see #getSynonyms()
     */
    List<String> getSynonymsExp();

    /**
     * Get the map of values and their non-expanded synonyms.
     *
     * @return Map of values and their non-expanded synonyms.
     * @see #getValuesExp()
     */
    Map<String, List<String>> getValues();

    /**
     * Get the map of values and their expanded synonyms.
     *
     * @return Map of values and their expanded synonyms.
     * @see #getValues()
     */
    Map<String, List<String>> getValuesExp();
}