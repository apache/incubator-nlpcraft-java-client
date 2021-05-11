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

import java.util.*;

/**
 * Request result descriptor.
 * 
 * @see NCClient#askSync(String, String, Map, boolean, Long, String)
 * @see NCClient#askSync(String, String)
 * @see NCClient#check(Set, Integer, Long, String)
 */
public interface NCResult {
    /**
     * Gets ID of the server request ID.
     *
     * @return ID of the server request ID.
     */
    String getServerRequestId();

    /**
     * Gets the original text of the request.
     *
     * @return Original text of the request.
     */
    String getText();

    /**
     * Gets ID of the user submitted the request. In other words, this is ID of the user on behalf of which
     * the request was submitted.
     *
     * @return ID of the user submitted the request.
     */
    long getUserId();

    /**
     * Gets ID of the model that request was submitted with.
     *
     * @return ID of the model that request was submitted with.
     */
    String getModelId();

    /**
     * Gets ID of the probe the request was processed by.
     *
     * @return ID of the probe the request was processed by.
     */
    String getProbeId();

    /**
     * Gets ready status of the request. Note that results obtained from {@link NCClient#check(Set, Integer, Long, String)}
     * method can be in not-ready state indicating that they are submitted but still being processed. Results returned
     * from {@link NCClient#askSync(String, String)} or {@link NCClient#askSync(String, String, Map, boolean, Long, String)}
     * methods are always ready.
     *
     * @return Ready status of the request.
     */
    boolean isReady();

    /**
     * Gets the result type. See <code>NCQueryResult</code> class for details.
     *
     * @return Query result type.
     */
    String getResultType();

    /**
     * Gets the result body as stringified JSON object. See <code>NCQueryResult</code> class for details.
     *
     * @return Query result body.
     */
    String getResultBody();

    /**
     * Gets optional error code. See <code>/check</code> operation in
     * <a href="https://github.com/apache/incubator-nlpcraft/blob/master/openapi/nlpcraft_swagger.yml" target=_>openapi/nlpcraft_swagger.yml</a> file
     * for more details. Note that error code and error message are only present if query result is unsuccessful.
     *
     * @return Optional error code. Can be {@code null}.
     */
    Integer getErrorCode();

    /**
     * Gets optional error message. Note that error code and error message are only present if query
     * result is unsuccessful.
     *
     * @return Optional error message. Can be {@code null}.
     */
    String getErrorMessage();

    /**
     * Gets optional processing log holder. When not {@code null} this string contains JSON object representing
     * processing log.
     *
     * @return Optional processing log. Can be {@code null}.
     */
    String getLogHolder();
}
