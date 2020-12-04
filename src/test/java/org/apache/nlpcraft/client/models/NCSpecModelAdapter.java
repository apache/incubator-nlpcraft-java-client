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
import org.apache.nlpcraft.model.NCModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Test model adapter.
 */
abstract class NCSpecModelAdapter implements NCModel {
    @Override
    public String getName() {
        return getId();
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    protected NCElement mkElement(String id, String... syns) {
        return new NCElement() {
            @Override
            public String getId() {
                return id;
            }

            @Override
            public List<String> getSynonyms() {
                List<String> l = new ArrayList<>();

                l.add(id);
                l.addAll(Arrays.asList(syns));

                return l;
            }
        };
    }
}
