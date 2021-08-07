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

package org.apache.nlpcraft.client.models;

import org.apache.nlpcraft.model.NCElement;
import org.apache.nlpcraft.model.NCIntent;
import org.apache.nlpcraft.model.NCIntentSample;
import org.apache.nlpcraft.model.NCValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Test model for requests: `model/sugsyn`, `model/syns`,`model/info`.
 */
public class NCRestSpecModel extends NCSpecModelAdapter {
    public static final String MDL_ID = NCRestSpecModel.class.getSimpleName();
    public static final String VAL_ELEM_ID1 = "valTest1";
    public static final String VAL_ELEM_ID2 = "valTest2";

    private static Set<String> mkSet(String ... data) {
        return new HashSet<>(Arrays.asList(data));
    }

    @Override
    public String getDescription() {
        return "some desc";
    }

    @Override
    public String getOrigin() {
        return "some origin";
    }

    @Override
    public int getMaxUnknownWords() {
        return 101;
    }

    @Override
    public int getMaxFreeWords() {
        return 22;
    }

    @Override
    public int getMaxSuspiciousWords() {
        return 23;
    }

    @Override
    public int getMinWords() {
        return 2;
    }

    @Override
    public int getMaxWords() {
        return 100;
    }

    @Override
    public int getMinTokens() {
        return 2;
    }

    @Override
    public int getMaxTokens() {
        return 12;
    }

    @Override
    public int getMinNonStopwords() {
        return 3;
    }

    @Override
    public boolean isNonEnglishAllowed() {
        return true;
    }

    @Override
    public boolean isNotLatinCharsetAllowed() {
        return true;
    }

    @Override
    public boolean isSwearWordsAllowed() {
        return true;
    }

    @Override
    public boolean isNoNounsAllowed() {
        return true;
    }

    @Override
    public boolean isPermutateSynonyms() {
        return true;
    }

    @Override
    public boolean isDupSynonymsAllowed() {
        return true;
    }

    @Override
    public int getMaxTotalSynonyms() {
        return 10101;
    }

    @Override
    public boolean isNoUserTokensAllowed() {
        return true;
    }

    @Override
    public boolean isSparse() {
        return true;
    }

    @Override
    public Map<String, Object> getMetadata() {
        Map<String, Object> m = new HashMap<>();

        m.put("k1", 12.12);
        m.put("k2", "word");

        return m;
    }

    @Override
    public Set<String> getAdditionalStopWords() {
        return mkSet("a", "b");
    }

    @Override
    public Set<String> getExcludedStopWords() {
        return mkSet("x", "y");
    }

    @Override
    public Set<String> getSuspiciousWords() {
        return mkSet("f", "h", "h");
    }

    @Override
    public Map<String, String> getMacros() {
        Map<String, String> m = new HashMap<>();

        m.put("<A>", "{a|_}");

        return m;
    }

    @Override
    public Set<String> getEnabledBuiltInTokens() {
        return mkSet("nlpcraft:limit", "nlpcraft:num");
    }

    @Override
    public Set<String> getAbstractTokens() {
        return mkSet(VAL_ELEM_ID1);
    }

    @Override
    public int getMaxElementSynonyms() {
        return 123;
    }

    @Override
    public boolean isMaxSynonymsThresholdError() {
        return true;
    }

    @Override
    public long getConversationTimeout() {
        return 12345;
    }

    @Override
    public int getConversationDepth() {
        return 3;
    }

    @Override
    public Map<String, Set<String>> getRestrictedCombinations() {
        Map<String, Set<String>> m = new HashMap<>();

        m.put("nlpcraft:limit", mkSet("valTest"));

        return m;
    }

    @Override
    public String getId() {
        return MDL_ID;
    }

    @Override
    public Set<NCElement> getElements() {
        Set<NCElement> set = new HashSet<>();

        set.add(mkElement( "test"));

        Map<String, List<String>> vals = new HashMap<>();

        vals.put("v1", List.of("v11", "v12"));
        vals.put("v2", List.of("v21", "v22"));

        set.add(
            new NCElement() {
                @Override
                public String getId() {
                    return VAL_ELEM_ID1;
                }

                @Override
                public Map<String, Object> getMetadata() {
                    Map<String, Object> m = new HashMap<>();

                    m.put("k1", 12.12);
                    m.put("k2", "word");

                    return m;
                }

                @Override
                public String getDescription() {
                    return "Some description";
                }

                @Override
                public List<NCValue> getValues() {
                    return List.of(
                        new NCValue() {
                            @Override
                            public String getName() {
                                return "name1";
                            }

                            @Override
                            public List<String> getSynonyms() {
                                return List.of("v11", "v12");
                            }
                        },
                        new NCValue() {
                            @Override
                            public String getName() {
                                return "name2";
                            }

                            @Override
                            public List<String> getSynonyms() {
                                return List.of("v21", "v22");
                            }
                        }
                    );
                }

                @Override
                public List<String> getSynonyms() {
                    return List.of("vt1", "vt2");
                }

                @Override
                public Optional<Boolean> isPermutateSynonyms() {
                    return Optional.empty();
                }

                @Override
                public Optional<Boolean> isSparse() {
                    return Optional.of(true);
                }
            }
        );

        set.add(
            new NCElement() {
                @Override
                public String getId() {
                    return VAL_ELEM_ID2;
                }

                @Override
                public Optional<Boolean> isPermutateSynonyms() {
                    return Optional.of(true);
                }

                @Override
                public Optional<Boolean> isSparse() {
                    return Optional.of(false);
                }


                @Override
                public String getParentId() {
                    return "valTest1";
                }
            }
        );

        return set;
    }

    @NCIntentSample({"Tests passed well"})
    @NCIntent("intent=intentId term~{tok_id() == 'test'}")
    public org.apache.nlpcraft.model.NCResult onTest() {
        return org.apache.nlpcraft.model.NCResult.text("OK");
    }
}
