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

import org.apache.nlpcraft.examples.alarm.AlarmModel;
import org.apache.nlpcraft.model.NCModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * REST client test. Checks user permissions.
 */
class NCPermissionsTest extends NCTestAdapter {
    /** */
    private static final String MDL_ID = "nlpcraft.alarm.ex";
    
    /** */
    private NCClient client;

    @Override
    Optional<Class<? extends NCModel>> getModelClass() {
        return Optional.of(AlarmModel.class);
    }

    /**
     *
     * @throws Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        super.setUp();
    
        String email = "email1@test.com";
        String pswd = "pswd1";
        
        admCli.addUser(email, pswd, "first1", "last1", null, false, null, null);
    
        client =
            new NCClientBuilder().
                setLogin(email, pswd).
                build();
    }
    
    /**
     *
     * @throws Exception
     */
    @AfterEach
    void tearDown() throws Exception {
        client.close();
        
        super.tearDown();
    }
    
    /**
     *
     * @throws Exception
     */
    @Test
    void test() throws Exception {
        testServerException(() -> client.clearConversation(MDL_ID, admUsrId, null), "NC_ADMIN_REQUIRED");
    
        testServerException(
            () -> client.addUser("x@x.com", "a", "a", "a","a", false, null,null),
            "NC_ADMIN_REQUIRED"
        );
        testServerException(
            () -> client.updateUser(admUsrId, "a", "a", "a", null),
            "NC_ADMIN_REQUIRED"
        );
        testServerException(() -> client.resetUserPassword(admUsrId, "a"), "NC_ADMIN_REQUIRED");
        testServerException(() -> client.updateUserAdmin(admUsrId, true), "NC_ADMIN_REQUIRED");
        testServerException(() -> client.updateUserAdmin(admUsrId, false), "NC_ADMIN_REQUIRED");
        testServerException(() -> client.deleteUser(admUsrId, null), "NC_ADMIN_REQUIRED");

        testServerException(client::getProbes, "NC_ADMIN_REQUIRED");
    }
}
