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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.apache.nlpcraft.client.models.NCCommonSpecModel.MDL_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * REST client test. Methods `user/*`.
 */
class NCUserTest extends NCTestAdapter {
    @Override
    Optional<Class<? extends NCModel>> getModelClass() {
        return Optional.of(NCCommonSpecModel.class);
    }

    /**
     *
     * @param u
     * @param email
     * @param firstName
     * @param lastName
     * @param avatar
     * @param isAdmin
     * @param extId
     * @param props
     */
    private static void check(
        NCUser u,
        String email,
        String firstName,
        String lastName,
        String avatar,
        boolean isAdmin,
        String extId,
        Map<String, String> props
    ) {
        assertEquals(u.getEmail(), email);
        assertEquals(u.getFirstName(), firstName);
        assertEquals(u.getLastName(), lastName);
        assertEquals(u.getAvatarUrl(), avatar);
        assertEquals(u.isAdmin(), isAdmin);
        assertEquals(u.getExternalId(), extId);
        assertEquals(u.getProperties(), props);
    }
    
    /**
     *
     * @param u1
     * @param u2
     */
    private static void check(NCUser u1, NCUser u2) {
        assertEquals(u1.getId(), u2.getId());
        
        check(u1, u2.getEmail(), u2.getFirstName(), u2.getLastName(), u2.getAvatarUrl(), u2.isAdmin(), u2.getExternalId(), u2.getProperties());
    }
    
    /**
     *
     * @throws Exception
     */
    @Test
    void test() throws Exception {
        long usrId1 =
            admCli.addUser(
                "email1@test.com", "pswd1", "first1", "last1", null, true, null, null
            );
    
        List<NCUser> users = admCli.getAllUsers();
        
        NCUser user = get(users, (u) -> usrId1 == u.getId());
        
        check(user, "email1@test.com", "first1", "last1", null, true, null, null);
        
        admCli.resetUserPassword(usrId1, "pswd2");
        admCli.updateUserAdmin(usrId1, false);
        admCli.updateUser(usrId1, "first2", "last2", "av2", null);
    
        users = admCli.getAllUsers();
    
        user = get(users, (u) -> usrId1 == u.getId());
        
        check(user, "email1@test.com", "first2", "last2", "av2", false, null, null);
    
        admCli.deleteUser(usrId1, null);
    
        users = admCli.getAllUsers();
    
        assertFalse(getOpt(users, (u) -> usrId1 == u.getId()).isPresent());

        Map<String, String> props = new HashMap<>();

        props.put("k1", "v1");
    
        long usrId2 =
            admCli.addUser(
                "email1@test.com", "pswd1", "first1", "last1", "av1", false, props, null
            );
    
        users = admCli.getAllUsers();
    
        user = get(users, (u) -> usrId2 == u.getId());
    
        check(user, "email1@test.com", "first1", "last1", "av1", false, null, props);
    
        admCli.deleteUser(usrId2, null);
    
        users = admCli.getAllUsers();
    
        assertFalse(getOpt(users, (u) -> usrId2 == u.getId()).isPresent());
    }
    
    /**
     *
     * @throws Exception
     */
    @Test
    void testInvalidOperations() throws Exception {
        // Unknown user
        testServerException(() -> admCli.deleteUser(-1L, null), "NC_INVALID_FIELD");
    
        List<NCUser> users = admCli.getAllUsers();
    
        testServerException(
            () -> {
                for (NCUser user : users) {
                    admCli.deleteUser(user.getId(), null);
                }
            },
            "NC_INVALID_OPERATION"
        );
    
        testServerException(
            () -> {
                for (NCUser user : users) {
                    admCli.updateUserAdmin(user.getId(), false);
                }
            },
            "NC_INVALID_OPERATION"
        );
    }
    
    /**
     *
     * @throws Exception
     */
    @Test
    void testGet() throws Exception {
        NCUser adminUser = get(admCli.getAllUsers(), (u) -> u.getId() == admUsrId);
    
        check(adminUser, admCli.getUser(null, null));
        check(adminUser, admCli.getUser(admUsrId, null));
    
        String extId = "extId";
    
        // Implicitly creates user with external ID.
        admCli.askSync(MDL_ID, "test", null, false, null, extId);
    
        NCUser newUser = get(admCli.getAllUsers(), (u) -> extId.equals(u.getExternalId()));
    
        check(newUser, admCli.getUser(null, newUser.getExternalId()));
        check(newUser, admCli.getUser(newUser.getId(), newUser.getExternalId()));
        check(newUser, admCli.getUser(newUser.getId(), null));
        
        testServerException(
            () -> check(newUser, admCli.getUser(newUser.getId(), "Wrong ID")),
            "NC_INVALID_FIELD"
        );
    }
    
    /**
     *
     * @throws Exception
     */
    @Test
    void testExternalId() throws Exception {
        String extId = "extId";
    
        // Implicitly creates user with external ID.
        admCli.askSync(MDL_ID, "test", null, false, null, extId);
    
        // Updates existing user with given external ID.
        admCli.addUser(
            "e1@test.com",
            "p",
            "f",
            "l",
            null,
            true,
            null,
            extId
        );
    
        // Tries to update user with invalid external ID.
        testServerException(
            () ->
                admCli.addUser(
                    "e1@test.com",
                    "p",
                    "f",
                    "l",
                    null,
                    true,
                    null,
                    "non-existed external ID"
                ),
            "NC_ERROR"
        );
    }
}
