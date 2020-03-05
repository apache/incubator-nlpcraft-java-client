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

/**
 * Company descriptor.
 *
 * @see NCClient#getCompany()
 */
public interface NCCompany {
    /**
     * Gets company ID.
     *
     * @return Company ID.
     */
    long getId();

    /**
     * Gets name of the company.
     *
     * @return Name of the company.
     */
    String getName();

    /**
     * Gets optional website of the company.
     *
     * @return Optional website of the company. Can be {@code null}.
     */
    String getWebsite();

    /**
     * Gets optional country of the company address. Can be {@code null}.
     *
     * @return Optional country of the company.
     */
    String getCountry();

    /**
     * Gets optional region of the company address. Can be {@code null}.
     *
     * @return Optional region of the company.
     */
    String getRegion();

    /**
     * Gets optional city of the company address. Can be {@code null}.
     *
     * @return Optional city of the company.
     */
    String getCity();

    /**
     * Gets optional street address of the company. Can be {@code null}.
     *
     * @return Optional street address of the company.
     */
    String getAddress();

    /**
     * Gets optional postal code of the company address. Can be {@code null}.
     *
     * @return Optional postal code of the company.
     */
    String getPostalCode();
}
