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
import org.apache.nlpcraft.model.NCResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Test model.
 */
public class NCCommonSpecModel extends NCSpecModelAdapter {
    public static final Map<String, Object> MAP = new HashMap<>();

    static {
        MAP.put("k1", "v1");
        MAP.put("k2", 2.2);

        Map<String, Object> m = new HashMap<>();

        m.put("k11", "v11");
        m.put("k12", 2.22);

        MAP.put("k3", m);
    }

    public static final String MDL_ID = NCCommonSpecModel.class.getSimpleName();

    @Override
    public String getId() {
        return MDL_ID;
    }

    @Override
    public Set<NCElement> getElements() {
        return new HashSet<>(Arrays.asList(mkElement("test"), mkElement("meta")));
    }

    @NCIntent("intent=intentId term~{tok_id() == 'test'}")
    public org.apache.nlpcraft.model.NCResult onTest() {
        return org.apache.nlpcraft.model.NCResult.text("OK");
    }

    @NCIntent("intent=intentMetaId term~{tok_id() == 'meta'}")
    public org.apache.nlpcraft.model.NCResult onMeta() {
        NCResult res = NCResult.text("OK");

        res.getMetadata().putAll(MAP);

        return res;
    }

}
