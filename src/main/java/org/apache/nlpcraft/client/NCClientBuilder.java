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

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.nlpcraft.client.impl.NCClientImpl;

import java.io.IOException;
import java.util.function.Supplier;

/**
 * Builder for the {@link NCClient} instances.
 * <p>
 * <b>Usage</b><br>
 * Client builder usage is straightforward:
 * <pre class="brush: java, highlight=[2,3,4,5]">
 *     // Get client instance.
 *     NCClient cli = new NCClientBuilder()
 *         .setLogin("admin@admin.com", "admin"),
 *         .setBaseUrl("http://localhost:8081/api/v1/")
 *         .build();
 *
 *     // Perform any necessary calls...
 *     NCResult res = cli.askSync("my.model.id", txt);
 *
 *     // Close client & sign out at the end.
 *     cli.close();
 * </pre>
 */
public class NCClientBuilder {
    /** Default public REST API URL (endpoint). */
    public static final String DFLT_BASEURL = "http://localhost:8081/api/v1/";
    /** Default user login email. */
    public static final String DFLT_EMAIL = "admin@admin.com";
    /** Default user login password. */
    public static final String DFLT_PWD = "admin";
    /** Default cancel on exit flag. */
    public static final boolean DFLT_CANCEL_ON_EXIT = true;
    /** Default embedded probe mode. */
    public static final boolean DFLT_EMBEDDED_PROBE = false;
    
    private final NCClientImpl impl = new NCClientImpl();
    
    /**
     * Creates new client builder with all default settings.
     */
    public NCClientBuilder() {
        // No-op.
    }
    
    /**
     * Sets the custom request config for this builder.
     *
     * @param reqCfg Custom request configuration.
     * @return Newly created client builder.
     */
    public NCClientBuilder setRequestConfig(RequestConfig reqCfg) {
        impl.setRequestConfig(reqCfg);

        return this;
    }
    
    /**
     * Sets custom HTTP client for this builder.
     *
     * @param httpCliSup Custom HTTP client to use.
     * @return Current client builder.
     */
    public NCClientBuilder setClientSupplier(Supplier<CloseableHttpClient> httpCliSup) {
        impl.setClientSupplier(httpCliSup);
        
        return this;
    }
    
    /**
     * Sets custom base URL of the REST server for this builder.
     *
     * @param baseUrl Base URL of the REST server to use.
     * @return Current client builder.
     * @see #DFLT_BASEURL
     */
    public NCClientBuilder setBaseUrl(String baseUrl) {
        impl.setBaseUrl(baseUrl);
    
        return this;
    }
    
    /**
     * Sets custom login user account.
     *
     * @param email User email.
     * @param pwd User password.
     * @return Current client builder.
     * @see #DFLT_EMAIL
     * @see #DFLT_PWD
     */
    public NCClientBuilder setLogin(String email, String pwd) {
        impl.setEmail(email);
        impl.setPassword(pwd);
    
        return this;
    }
    
    /**
     * Sets embedded probe mode flag. If set to {@code true} the implementation
     * will expect that the embedded probe is running in the local JVM and will receive
     * query result via local callbacks. See <code>NCEmbeddedProbe</code> class for more details.
     *
     * @param embeddedMode Embedded probe mode.
     * @return Current client builder.
     * @see #DFLT_EMBEDDED_PROBE
     */
    public NCClientBuilder setEmbeddedProbe(boolean embeddedMode) {
        impl.setEmbeddedProbe(embeddedMode);

        return this;
    }
    
    /**
     * Builds a client instance with configured settings.
     *
     * @return Newly created instance of {@link NCClient}.
     * @throws IOException Thrown in case of any I/O errors.
     * @throws NCClientException Thrown in case of any NLPCraft-specific errors.
     */
    public NCClient build() throws IOException, NCClientException {
        if (impl.getEmail() == null && impl.getPassword() != null ||
            impl.getEmail() != null && impl.getPassword() == null
        )
            throw new IllegalArgumentException("Both email and password should be null or not null");
        
        if (impl.getBaseUrl() == null)
            impl.setBaseUrl(DFLT_BASEURL);
        
        if (impl.getClientSupplier() == null)
            impl.setClientSupplier(HttpClients::createDefault);
        
        if (impl.getEmail() == null)
            impl.setEmail(DFLT_EMAIL);
    
        if (impl.getPassword() == null)
            impl.setPassword(DFLT_PWD);
        
        if (impl.isCancelOnExit() == null)
            impl.setCancelOnExit(DFLT_CANCEL_ON_EXIT);

        if (impl.isEmbeddedProbe() == null)
            impl.setEmbeddedProbe(DFLT_EMBEDDED_PROBE);

        impl.initialize();
        
        return impl;
    }
}
