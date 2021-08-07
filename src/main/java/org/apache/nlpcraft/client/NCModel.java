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
 * Data model descriptor.
 *
 * @see NCClient#getProbes()
 * @see NCProbe#getModels()
 * @see NCModelInfo
 */
public interface NCModel {
    /**
     * Gets unique, <i>immutable</i> ID of this model.
     * <p>
     * Note that <b>model IDs are immutable</b> while name and version
     * can be changed freely. Changing model ID is equal to creating a completely new model.
     * Model IDs (unlike name and version) are not exposed to the end user and only serve a
     * technical purpose. ID's max length is 32 characters.
     *
     * @return Unique, <i>immutable</i> ID of this model.
     */
    String getId();

    /**
     * Gets descriptive name of this model. Name's max length is 64 characters.
     *
     * @return Descriptive name for this model.
     */
    String getName();

    /**
     * Gets the version of this model using semantic versioning. Version's max length is 16 characters.
     *
     * @return A version compatible with (<a href="http://www.semver.org">www.semver.org</a>) specification.
     */
    String getVersion();
}