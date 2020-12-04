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

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.apache.nlpcraft.client.models.NCCommonSpecModel.MDL_ID;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * REST client test. Method `ask`.
 */
class NCAskTest extends NCTestAdapter {
    /** */
    private static final int MAX_TEST_TIME = 20000;

    @Override
    Optional<Class<? extends NCModel>> getModelClass() {
        return Optional.of(NCCommonSpecModel.class);
    }

    /**
     *
     * @param txt
     * @param resConsumer
     * @throws Exception
     */
    private void test0(String txt, Consumer<NCResult> resConsumer) throws Exception {
        String srvReqId = admCli.ask(MDL_ID, txt, null, true, null, null);
        
        try {
            long max = System.currentTimeMillis() + MAX_TEST_TIME;
            
            while (System.currentTimeMillis() < max) {
                List<NCResult> states = admCli.check(null, null, null, null);
                
                Optional<NCResult> resOpt = getOpt(states, (s) -> srvReqId.equals(s.getServerRequestId()));
                
                if (resOpt.isPresent()) {
                    NCResult res = resOpt.get();
                    
                    if (res.getErrorMessage() != null || res.getResultBody() != null) {
                        resConsumer.accept(res);
                        
                        return;
                    }
                }
                
                Thread.sleep(500);
            }
            
            fail("Test timeout exception");
        }
        finally {
            admCli.cancel(null, null, null);
        }
    }
    
    /**
     *
     * @throws Exception
     */
    @Test
    void test() throws Exception {
        test0("test", this::checkOk);
        test0("El tiempo en España", this::checkError);
    }
}
