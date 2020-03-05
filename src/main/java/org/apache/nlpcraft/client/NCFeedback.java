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
 * Feedback record descriptor.
 * 
 * @see NCClient#getAllFeedback(String, Long, String) 
 */
public interface NCFeedback {
    /**
     * Gets ID of the feedback record.
     *
     * @return ID of the feedback record.
     */
    long getId();

    /**
     * Gets ID of the server request this feedback record is associated with.
     * 
     * @return ID of the server request this feedback record is associated with.
     */
    String getServerRequestId();

    /**
     * Gets ID of the user that created this feedback record.
     *
     * @return ID of the user that created this feedback record.
     */
    long getUserId();

    /**
     * Gets <code>[0, 1]</code> score associated with this feedback record.
     *
     * @return <code>[0, 1]</code> score associated with this feedback record.
     */
    double getScore();

    /**
     * Gets optional comment associated with this feedback record.
     *
     * @return Optional comment associated with this feedback record.
     */
    String getComment();

    /**
     * Gets timestamp of when this feedback record was created.
     *
     * @return Timestamp of when this feedback record was created.
     */
    long getCreateTimestamp();
}
