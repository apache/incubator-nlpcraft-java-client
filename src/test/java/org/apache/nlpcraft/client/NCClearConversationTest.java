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

import org.apache.nlpcraft.client.models.NCConversationSpecModel;
import org.apache.nlpcraft.model.NCModel;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * REST client test. Methods `clear/conversation`.
 */
class NCClearConversationTest extends NCTestAdapter {
    @Override
    Optional<Class<? extends NCModel>> getModelClass() {
        return Optional.of(NCConversationSpecModel.class);
    }

    /**
     * @param txt
     * @param resConsumer
     * @throws Exception
     */
    private void check(String txt, Consumer<NCResult> resConsumer) throws Exception {
        resConsumer.accept(admCli.askSync(NCConversationSpecModel.MDL_ID, txt, null, true, null, null));
    }

    private void flow() throws Exception {
        // missed 'test1'
        check("test2", this::checkError);

        check("test1 test2", this::checkOk);

        // 'test1' received from conversation.
        check("test2", this::checkOk);
    }

    /**
     * @throws Exception
     */
    @Test
    void test() throws Exception {
        flow();

        admCli.clearConversation(
            NCConversationSpecModel.MDL_ID,
            // Finds its own ID.
            get(admCli.getAllUsers(), (u) -> NCClientBuilder.DFLT_EMAIL.equals(u.getEmail())).getId(),
            null
        );

        flow();

        admCli.clearConversation(NCConversationSpecModel.MDL_ID, null, null);

        flow();
    }
}
