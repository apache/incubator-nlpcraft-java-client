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

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Test model.
 */
public class NCConversationSpecModel extends NCSpecModelAdapter {
    public static final String MDL_ID = NCConversationSpecModel.class.getSimpleName();

    @Override
    public String getId() {
        return MDL_ID;
    }
    @Override
    public Set<NCElement> getElements() {
        return Stream.of(mkElement("test1"), mkElement("test2")).collect(Collectors.toCollection(HashSet::new));
    }

    // 'test1' is mandatory, 'test2' is optional.
    @NCIntent("intent=intentId term~{id == 'test1'} term~{id == 'test2'}?")
    public org.apache.nlpcraft.model.NCResult onTest() {
        return org.apache.nlpcraft.model.NCResult.text("OK");
    }
}
