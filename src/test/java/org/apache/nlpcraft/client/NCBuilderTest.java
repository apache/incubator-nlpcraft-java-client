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

import java.io.IOException;

/**
 * REST client builder test.
 */
class NCBuilderTest extends NCTestAdapter {
    /**
     *
     * @throws Exception
     */
    @Test
    void test() throws Exception {
        testException(
            () -> new NCClientBuilder().setLogin(null, "").build(),
            IllegalArgumentException.class
        );
        testException(
            () -> new NCClientBuilder().setLogin("", null).build(),
            IllegalArgumentException.class
        );
        
        testException(() -> new NCClientBuilder().setBaseUrl("?").build(), IOException.class);
        testException(() -> new NCClientBuilder().setBaseUrl("http://invalid.com").build(), NCClientException.class);
    
        testServerException(
            () -> new NCClientBuilder().setLogin("invalid", "invalid").build(),
            "NC_SIGNIN_FAILURE"
        );
    }
}
