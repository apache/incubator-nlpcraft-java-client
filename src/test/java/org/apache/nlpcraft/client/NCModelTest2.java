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

import org.apache.nlpcraft.client.models.NCRestSpecModel;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.nlpcraft.client.models.NCRestSpecModel.MDL_ID;
import static org.junit.jupiter.api.Assertions.*;

/**
 * REST client test. Methods `model/sugsyn`. Required started 'ctxword' server.
 */
class NCModelTest2 extends NCTestAdapter {
    @Override
    Optional<Class<? extends org.apache.nlpcraft.model.NCModel>> getModelClass() {
        return Optional.of(NCRestSpecModel.class);
    }

    /**
     * @param s
     * @param minScore
     * @throws Exception
     */
    private static void check(NCSuggestionData s, double minScore) {
        assertNotNull(s);

        assertNull(s.getError());
        assertNotNull(s.getModelId());
        assertNotNull(s.getSynonyms());
        assertFalse(s.getSynonyms().isEmpty());
        assertFalse(s.getSynonyms().isEmpty());
        assertEquals(minScore, s.getMinScore());

        List<NCSuggestion> allSugs =
            s.getSynonyms().
                stream().
                flatMap(p -> p.values().stream().flatMap(Collection::stream)).
                collect(Collectors.toList());

        assertFalse(allSugs.isEmpty());

        allSugs.forEach(sugg -> {
            assertNotNull(sugg.getSynonym());
            assertTrue(sugg.getScore() >= minScore);
        });
    }

    /**
     * @throws Exception
     */
    @Test
    void testSuggestionSynonym() throws Exception {
        check(admCli.suggestSynonyms(MDL_ID, null), 0);
        check(admCli.suggestSynonyms(MDL_ID, 0.5), 0.5);
    }
}
