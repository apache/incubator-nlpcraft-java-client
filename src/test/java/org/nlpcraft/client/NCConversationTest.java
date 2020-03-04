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

package org.nlpcraft.client;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

/**
 * REST client test. Methods `clear/conversation`.
 */
class NCConversationTest extends NCTestAdapter {
    /** */
    private static final String MDL_ID = "nlpcraft.weather.ex";
    
    /**
     *
     * @param txt
     * @param resConsumer
     * @throws Exception
     */
    private void check(String txt, Consumer<NCResult> resConsumer) throws Exception {
        resConsumer.accept(admCli.askSync(MDL_ID, txt, null, true, null, null));
    }
    
    /**
     *
     * @throws Exception
     */
    @Test
    void test1() throws Exception {
        check("What's the weather in Moscow?", this::checkOk);
        
        // Should be answered with conversation.
        check("Moscow", this::checkOk);
        
        admCli.clearConversation(MDL_ID, null, null);
        
        // Cannot be answered without conversation.
        check("Moscow", this::checkError);
    }
    
    /**
     *
     * @throws Exception
     */
    @Test
    void test2() throws Exception {
        check("What's the weather in Moscow?", this::checkOk);
        
        // Should be answered with conversation.
        check("Moscow", this::checkOk);
        
        admCli.clearConversation(MDL_ID,
            // Finds its own ID.
            get(admCli.getAllUsers(), (u) -> NCClientBuilder.DFLT_EMAIL.equals(u.getEmail())).getId(), null);
        
        // Cannot be answered without conversation.
        check("Moscow", this::checkError);
    }
}
