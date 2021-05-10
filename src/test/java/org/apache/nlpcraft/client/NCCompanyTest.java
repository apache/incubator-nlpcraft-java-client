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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * REST client test. Methods `company/*`.
 */
class NCCompanyTest extends NCTestAdapter {
    /**
     *
     * @param comp
     * @param name
     * @param website
     * @param country
     * @param region
     * @param city
     * @param address
     * @param postalCode
     * @param props
     */
    private static void check(
        NCCompany comp,
        String name,
        String website,
        String country,
        String region,
        String city,
        String address,
        String postalCode,
        Map<String, String> props
    ) {
        assertEquals(comp.getName(), name);
        assertEquals(comp.getWebsite(), website);
        assertEquals(comp.getCountry(), country);
        assertEquals(comp.getRegion(), region);
        assertEquals(comp.getCity(), city);
        assertEquals(comp.getAddress(), address);
        assertEquals(comp.getPostalCode(), postalCode);
        assertEquals(comp.getProperties(), props);
    }
    
    /**
     *
     * @param name
     * @param props
     * @param other
     * @throws Exception
     */
    private void test0(String name, Map<String, String> props, String other) throws Exception {
        String adminEmail = "test@" + UUID.randomUUID() + ".com";
        String adminPwd = "test";
    
        // Creates the company.
        NCNewCompany data =
            admCli.addCompany(
                name,
                other,
                other,
                other,
                other,
                other,
                other, adminEmail, adminPwd,
                "test",
                "test",
                null,
                props
            );
    
        // Prepares client for this company.
        NCClient newCompClient = new NCClientBuilder().setLogin(adminEmail, adminPwd).build();
        
        try {
            // Makes sure that admin existed.
            get(newCompClient.getAllUsers(), (u) -> data.getAdminUserId() == u.getId());
    
            // Checks created company fields.
            check(newCompClient.getCompany(), name, other, other, other, other, other, other, props);
    
            name = "new " + name;
            other = "new " + other;

            if (props != null) {
                props.put("new", "new");
            }
    
            // Updates fields.
            newCompClient.updateCompany(name, other, other, other, other, other, other, props);
    
            // Checks updates company fields.
            check(newCompClient.getCompany(), name, other, other, other, other, other, other, props);
    
            // Updates fields.
            newCompClient.updateCompany(
                name, null, null, null, null, null, null, null
            );
    
            // Checks updates company fields.
            check(
                newCompClient.getCompany(), name, null, null, null, null, null, null, null
            );
    
            // Checks method.
            newCompClient.resetCompanyToken();
        }
        finally {
            // Deletes company.
            newCompClient.deleteCompany();
        }
    }
    
    /**
     *
     * @throws Exception
     */
    @Test
    void test() throws Exception {
        Map<String, String> props = new HashMap<>();

        props.put("k1", "v1");

        test0("company", props, "company");
        test0("company", null, null);
    }
}
