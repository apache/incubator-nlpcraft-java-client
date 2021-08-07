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
import org.apache.nlpcraft.model.NCModel;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.nlpcraft.client.models.NCRestSpecModel.MDL_ID;
import static org.apache.nlpcraft.client.models.NCRestSpecModel.VAL_ELEM_ID1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * REST client test. Methods `model/syns`,`model/info`.
 */
class NCModelTest1 extends NCTestAdapter {
    @Override
    Optional<Class<? extends NCModel>> getModelClass() {
        return Optional.of(NCRestSpecModel.class);
    }

    @Test
    void testElementSynonyms() throws Exception {
        NCElementSynonymsData data = admCli.getSynonyms(MDL_ID, VAL_ELEM_ID1);

        assertTrue(data.getSynonymsCnt() > 0);
        assertTrue(data.getSynonymsExpCnt() > 0);
        assertTrue(data.getSynonymsExpRatePct() > 0);

        assertTrue(data.getSynonyms() != null && !data.getSynonyms().isEmpty());
        assertTrue(data.getSynonymsExp() != null && !data.getSynonymsExp().isEmpty());
        assertTrue(data.getValues() != null && !data.getValues().isEmpty());
        assertTrue(data.getValuesExp() != null && !data.getValuesExp().isEmpty());
    }

    /**
     * @throws Exception
     */
    @Test
    void testModelInfo() throws Exception {
        NCRestSpecModel probeMdl = new NCRestSpecModel();

        // Checked model values.
        assert probeMdl.getElements().stream().anyMatch(p -> p.getValues() != null && !p.getValues().isEmpty());

        NCModelInfo restMdl = admCli.getModelInfo(MDL_ID);

        assertEquals(probeMdl.getId(), restMdl.getId());
        assertEquals(probeMdl.getName(), restMdl.getName());
        assertEquals(probeMdl.getDescription(), restMdl.getDescription());
        assertEquals(probeMdl.getVersion(), restMdl.getVersion());
        assertEquals(probeMdl.getOrigin(), restMdl.getOrigin());
        assertEquals(probeMdl.getMaxUnknownWords(), restMdl.getMaxUnknownWords());
        assertEquals(probeMdl.getMaxFreeWords(), restMdl.getMaxFreeWords());
        assertEquals(probeMdl.getMaxSuspiciousWords(), restMdl.getMaxSuspiciousWords());
        assertEquals(probeMdl.getMinWords(), restMdl.getMinWords());
        assertEquals(probeMdl.getMaxWords(), restMdl.getMaxWords());
        assertEquals(probeMdl.getMinTokens(), restMdl.getMinTokens());
        assertEquals(probeMdl.getMaxTokens(), restMdl.getMaxTokens());
        assertEquals(probeMdl.getMinNonStopwords(), restMdl.getMinNonStopwords());
        assertEquals(probeMdl.isNonEnglishAllowed(), restMdl.isNonEnglishAllowed());
        assertEquals(probeMdl.isNotLatinCharsetAllowed(), restMdl.isNotLatinCharsetAllowed());
        assertEquals(probeMdl.isSwearWordsAllowed(), restMdl.isSwearWordsAllowed());
        assertEquals(probeMdl.isNoNounsAllowed(), restMdl.isNoNounsAllowed());
        assertEquals(probeMdl.isPermutateSynonyms(), restMdl.isPermutateSynonyms());
        assertEquals(probeMdl.isDupSynonymsAllowed(), restMdl.isDupSynonymsAllowed());
        assertEquals(probeMdl.getMaxTotalSynonyms(), restMdl.getMaxTotalSynonyms());
        assertEquals(probeMdl.isNoUserTokensAllowed(), restMdl.isNoUserTokensAllowed());
        assertEquals(probeMdl.isSparse(), restMdl.isSparse());
        assertEquals(probeMdl.getMetadata(), restMdl.getMetadata());
        assertEquals(probeMdl.getAdditionalStopWords(), restMdl.getAdditionalStopWords());
        assertEquals(probeMdl.getExcludedStopWords(), restMdl.getExcludedStopWords());
        assertEquals(probeMdl.getSuspiciousWords(), restMdl.getSuspiciousWords());
        assertEquals(probeMdl.getMacros(), restMdl.getMacros());
        assertEquals(probeMdl.getEnabledBuiltInTokens(), restMdl.getEnabledBuiltInTokens());
        assertEquals(probeMdl.getAbstractTokens(), restMdl.getAbstractTokens());
        assertEquals(probeMdl.getMaxElementSynonyms(), restMdl.getMaxElementSynonyms());
        assertEquals(probeMdl.isMaxSynonymsThresholdError(), restMdl.isMaxSynonymsThresholdError());
        assertEquals(probeMdl.getConversationTimeout(), restMdl.getConversationTimeout());
        assertEquals(probeMdl.getConversationDepth(), restMdl.getConversationDepth());
        assertEquals(probeMdl.getRestrictedCombinations(), restMdl.getRestrictedCombinations());

        Set<org.apache.nlpcraft.model.NCElement> elems1 = probeMdl.getElements();
        Set<org.apache.nlpcraft.client.NCElement> elems2 = restMdl.getElements();

        assertEquals(elems1.size(), elems2.size());

        int elemsCnt = elems1.size();

        List<org.apache.nlpcraft.model.NCElement> elemList1 =
            elems1.stream().sorted(Comparator.comparing(org.apache.nlpcraft.model.NCElement::getId)).collect(Collectors.toList());

        List<org.apache.nlpcraft.client.NCElement> elemList2 =
            elems2.stream().sorted(Comparator.comparing(org.apache.nlpcraft.client.NCElement::getId)).collect(Collectors.toList());

        for (int i = 0; i < elemsCnt; i++) {
            org.apache.nlpcraft.model.NCElement elemMdl = elemList1.get(i);
            org.apache.nlpcraft.client.NCElement elemCli = elemList2.get(i);

            assertEquals(elemMdl.getId(), elemCli.getId());
            assertEquals(elemMdl.getGroups(), elemCli.getGroups());
            assertEquals(elemMdl.getMetadata(), elemCli.getMetadata());
            assertEquals(elemMdl.getDescription(), elemCli.getDescription());
            assertEquals(elemMdl.getParentId(), elemCli.getParentId());
            assertEquals(elemMdl.getSynonyms(), elemCli.getSynonyms());
            // TODO: comment because main nlpcraft release 0.9.0 contains errors. Uncomment after fix in new release.
            //assertEquals(elemMdl.isPermutateSynonyms(), elemCli.isPermutateSynonyms());
            // TODO: comment because main nlpcraft release 0.9.0 contains errors. Uncomment after fix in new release.
            //assertEquals(elemMdl.isSparse(), elemCli.isSparse());

            List<org.apache.nlpcraft.model.NCValue> vals1 = elemMdl.getValues();
            List<org.apache.nlpcraft.client.NCValue> vals2 = elemCli.getValues();

            int valsCnt1 = vals1 == null ? 0 : vals1.size();
            int valsCnt2 = vals2 == null ? 0 : vals2.size();

            assertEquals(valsCnt1, valsCnt2);

            for (int j = 0; j < valsCnt1; j++) {
                assert vals2 != null;

                org.apache.nlpcraft.model.NCValue valMdl = vals1.get(i);
                org.apache.nlpcraft.client.NCValue valCli = vals2.get(i);

                System.out.println("Checked value: " + valMdl.getName());

                assertEquals(valMdl.getName(), valCli.getName());
                // TODO: comment because main nlpcraft release 0.9.0 contains errors. Uncomment after fix in new release.
                //assertEquals(valMdl.getSynonyms(), valCli.getSynonyms());
            }
        }
    }
}
