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

import org.apache.nlpcraft.client.models.NCDialogSpecModel;
import org.apache.nlpcraft.model.NCModel;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * REST client test. Methods `clear/dialog`.
 */
class NCClearDialogTest extends NCTestAdapter {
    @Override
    Optional<Class<? extends NCModel>> getModelClass() {
        return Optional.of(NCDialogSpecModel.class);
    }

    /**
     * @param txt
     * @param resConsumer
     * @throws Exception
     */
    private void check(String txt, Consumer<NCResult> resConsumer) throws Exception {
        resConsumer.accept(admCli.askSync(NCDialogSpecModel.MDL_ID, txt, null, true, null, null));
    }

    /**
     *
     * @throws Exception
     */
    private void flow() throws Exception {
        // There isn't `test1` before.
        check("test2", this::checkError);

        // `test1` is always ok.
        check("test1", this::checkOk);

        // There is one `test1` before.
        check("test2", this::checkOk);

        // `test1` is always ok.
        check("test1", this::checkOk);
        check("test1", this::checkOk);

        // There are too much `test1` before.
        check("test2", this::checkError);
    }

    /**
     *
     * @throws Exception
     */
    @Test
    void test() throws Exception {
        flow();

        admCli.clearDialog(NCDialogSpecModel.MDL_ID, null, null);
        admCli.clearConversation(NCDialogSpecModel.MDL_ID, null, null);

        flow();
    }
}
