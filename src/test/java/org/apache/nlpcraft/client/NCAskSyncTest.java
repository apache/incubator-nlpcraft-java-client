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

import org.apache.nlpcraft.client.models.NCCommonSpecModel;
import org.apache.nlpcraft.model.NCModel;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Consumer;

import static org.apache.nlpcraft.client.models.NCCommonSpecModel.MDL_ID;

/**
 * REST client test. Method `ask/sync`.
 */
class NCAskSyncTest extends NCTestAdapter {
    @Override
    Optional<Class<? extends NCModel>> getModelClass() {
        return Optional.of(NCCommonSpecModel.class);
    }

    /**
     *
     * @param txt
     * @throws Exception
     */
    private void check(String txt, Consumer<NCResult> resConsumer) throws Exception {
        // Different combinations of input parameters.
        resConsumer.accept(admCli.askSync(MDL_ID, txt, null, true, null, null));

        resConsumer.accept(admCli.askSync(MDL_ID, txt, MAP, true, null, null));
        resConsumer.accept(admCli.askSync(MDL_ID, txt, MAP, false, null, null));
        resConsumer.accept(admCli.askSync(MDL_ID, txt, MAP, false, admUsrId, null));
        
        String extId = "extId";
    
        resConsumer.accept(admCli.askSync(MDL_ID, txt, MAP, false, null, extId));
    
        long id = get(admCli.getAllUsers(), (u) -> extId.equals(u.getExternalId())).getId();
    
        resConsumer.accept(admCli.askSync(MDL_ID, txt, MAP, false, id, extId));
    }
    
    /**
     *
     * @throws Exception
     */
    @Test
    void test() throws Exception {
        // Only latin charset is supported.
        check("El tiempo en España", this::checkError);

        check("test", res -> checkOk(res, null));
        check("meta", res -> checkOk(res, NCCommonSpecModel.MAP));
    }
}
