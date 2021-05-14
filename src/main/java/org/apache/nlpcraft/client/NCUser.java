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

import java.util.Map;

/**
 * User record descriptor.
 *
 * @see NCClient#getUser(Long, String)
 * @see NCClient#getAllUsers() 
 */
public interface NCUser {
    /**
     * Gets user ID.
     *
     * @return User ID.
     */
    long getId();

    /**
     * Gets user email.
     *
     * @return Optional user email.
     */
    String getEmail();

    /**
     * Gets user first name.
     *
     * @return Optional user first name.
     */
    String getFirstName();

    /**
     * Gets user last name.
     *
     * @return Optional user last name.
     */
    String getLastName();

    /**
     * Gets optional user avatar URl.
     *
     * @return Optional user avatar URL.
     */
    String getAvatarUrl();

    /**
     * Gets whether or not this user has administrative privileges.
     *
     * @return Whether or not this user has administrative privileges.
     */
    boolean isAdmin();
    
    /**
     * Gets external "on-behalf-of" user ID.
     *
     * @return Optional external "on-behalf-of" user ID.
     */
    String getExternalId();
    
    /**
     * Gets user properties (metadata).
     *
     * @return Optional user properties (metadata).
     */
    Map<String, String> getProperties();
}
