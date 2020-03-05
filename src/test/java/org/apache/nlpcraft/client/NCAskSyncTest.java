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

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

/**
 * REST client test. Method `ask/sync`.
 */
class NCAskSyncTest extends NCTestAdapter {
    //
    private static final String MDL_ID = "nlpcraft.alarm.ex";
    
    /**
     *
     * @param txt
     * @throws Exception
     */
    private void check(String txt, Consumer<NCResult> resConsumer) throws Exception {
        // Different combinations of input parameters.
        resConsumer.accept(admCli.askSync(MDL_ID, txt, null, true, null, null));
        resConsumer.accept(admCli.askSync(MDL_ID, txt, "{\"a\": 1}", true, null, null));
        resConsumer.accept(admCli.askSync(MDL_ID, txt, "data", false, null, null));
        resConsumer.accept(admCli.askSync(MDL_ID, txt, "data", false, admUsrId, null));
        
        String extId = "extId";
    
        resConsumer.accept(admCli.askSync(MDL_ID, txt, "data", false, null, extId));
    
        long id = get(admCli.getAllUsers(), (u) -> extId.equals(u.getExternalId())).getId();
    
        resConsumer.accept(admCli.askSync(MDL_ID, txt, "data", false, id, extId));
    }
    
    /**
     *
     * @throws Exception
     */
    @Test
    void test() throws Exception {
        // Only latin charset is supported.
        check("El tiempo en España", this::checkError);
    
        check("Ping me in 3 minutes", this::checkOk);
        check("Buzz me in an hour and 15mins", this::checkOk);
        check("Set my alarm for 30s", this::checkOk);
    }
}
