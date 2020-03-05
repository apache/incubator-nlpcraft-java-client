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
 * Java client exception.
 */
public class NCClientException extends RuntimeException {
    private String code = null;

    /**
     * Creates new exception with given error message.
     *
     * @param msg Error message.
     */
    public NCClientException(String msg) {
        super(msg);
    }

    /**
     * Creates new exception with given error message and server error code.
     *
     * @param msg Error message.
     * @param code REST server error code.
     */
    public NCClientException(String msg, String code) {
        super(msg);

        this.code = code;
    }

    /**
     * Creates new exception with given error message and the cause.
     * 
     * @param message Error message.
     * @param cause Cause of this exception.
     */
    public NCClientException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Gets optional REST server error code.
     * 
     * @return REST server error code or <code>null</code>. If not {@code null} - one of the following
     *      values:
     *      <ul>
     *          <li>NC_INVALID_ACCESS_TOKEN</li>
     *          <li>NC_SIGNIN_FAILURE</li>
     *          <li>NC_NOT_IMPLEMENTED</li>
     *          <li>NC_INVALID_FIELD</li>
     *          <li>NC_ADMIN_REQUIRED</li>
     *          <li>NC_INVALID_OPERATION</li>
     *          <li>NC_ERROR</li>
     *          <li>NC_UNEXPECTED_ERROR</li></li>
     *      </ul>
     *      See <a href="https://github.com/apache/incubator-nlpcraft/blob/master/openapi/nlpcraft_swagger.yml" target=_>openapi/nlpcraft_swagger.yml</a> file for
     *      details.
     */
    public String getServerCode() {
        return code;
    }
}
