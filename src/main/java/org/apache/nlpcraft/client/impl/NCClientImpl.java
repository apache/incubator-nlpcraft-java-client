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

package org.apache.nlpcraft.client.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.nlpcraft.client.NCClient;
import org.apache.nlpcraft.client.NCClientException;
import org.apache.nlpcraft.client.NCCompany;
import org.apache.nlpcraft.client.NCFeedback;
import org.apache.nlpcraft.client.NCNewCompany;
import org.apache.nlpcraft.client.NCProbe;
import org.apache.nlpcraft.client.NCResult;
import org.apache.nlpcraft.client.NCUser;
import org.apache.nlpcraft.client.impl.beans.NCAskBean;
import org.apache.nlpcraft.client.impl.beans.NCAskSyncBean;
import org.apache.nlpcraft.client.impl.beans.NCCheckBean;
import org.apache.nlpcraft.client.impl.beans.NCCompanyBean;
import org.apache.nlpcraft.client.impl.beans.NCCompanyTokenResetBean;
import org.apache.nlpcraft.client.impl.beans.NCErrorMessageBean;
import org.apache.nlpcraft.client.impl.beans.NCFeedbackAddBean;
import org.apache.nlpcraft.client.impl.beans.NCFeedbackAllBean;
import org.apache.nlpcraft.client.impl.beans.NCProbesAllBean;
import org.apache.nlpcraft.client.impl.beans.NCRequestStateBean;
import org.apache.nlpcraft.client.impl.beans.NCSigninBean;
import org.apache.nlpcraft.client.impl.beans.NCStatusResponseBean;
import org.apache.nlpcraft.client.impl.beans.NCTokenCreationBean;
import org.apache.nlpcraft.client.impl.beans.NCUserAddBean;
import org.apache.nlpcraft.client.impl.beans.NCUserBean;
import org.apache.nlpcraft.client.impl.beans.NCUsersAllBean;

//
// TODO:
// These import statements need to be fixed once
// the main project is migrated over ASF and released on maven.
// For now - they are pulling/relying on "old" 'org.nlpcraft' artifact.
//
import org.nlpcraft.probe.embedded.NCEmbeddedProbe;
import org.nlpcraft.probe.embedded.NCEmbeddedResult;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Client implementation.
 */
@SuppressWarnings("JavaDoc")
public class NCClientImpl implements NCClient {
    private static final String STATUS_API_OK = "API_OK";
    
    private static final Gson gson =
        new GsonBuilder().registerTypeAdapter(
            NCRequestStateBean.class,
            (JsonDeserializer<NCRequestStateBean>) (e, type, ctx) -> {
                JsonObject o = e.getAsJsonObject();
                NCRequestStateBean b = new NCRequestStateBean();
    
                b.setSrvReqId(o.get("srvReqId").getAsString());
                b.setTxt(o.get("txt").getAsString());
                b.setUsrId(o.get("usrId").getAsLong());
                b.setMdlId(o.get("mdlId").getAsString());
                b.setProbeId(convert(o, "probeId", JsonElement::getAsString));
                b.setResType(convert(o, "resType", JsonElement::getAsString));
                b.setResBody(
                    convert(o, "resBody", (resBody) -> resBody == null ?
                        null :
                        resBody.isJsonObject() ?
                            resBody.getAsJsonObject().toString() :
                            resBody.getAsString())
                );
                b.setStatus(o.get("status").getAsString());
                b.setErrorCode(convert(o, "errorCode", JsonElement::getAsInt));
                b.setError(convert(o, "error", JsonElement::getAsString));
                b.setLogHolder(convert(o, "logHolder", (logHolder) -> logHolder.getAsJsonObject().toString()));
    
                return b;
            }).create();
    
    private static final Logger log = LogManager.getLogger(NCClientImpl.class);
    private static final String AUTH_ERR = "NC_INVALID_ACCESS_TOKEN";
    
    private Supplier<CloseableHttpClient> httpCliGen;
    private RequestConfig reqCfg;
    private String baseUrl;
    private String email;
    private String pwd;
    private Boolean cancelOnExit;
    private Boolean embeddedProbe;
    
    private CloseableHttpClient httpCli;
    private String acsTok;
    private volatile boolean started = false;
    
    private final Map<String, NCEmbeddedResult> embeddedResMap = new ConcurrentHashMap<>();
    private final Object mux = new Object();
    
    private static<T> T convert(JsonObject o, String name, Function<JsonElement, T> converter) {
        JsonElement e = o.get(name);
    
        return e != null ? converter.apply(e) : null;
    }

    @Override
    public String getClientUserEmail() {
        return email;
    }

    @Override
    public String getClientUserPassword() {
        return pwd;
    }

    @Override
    public boolean isClientEmbeddedMode() {
        return embeddedProbe;
    }

    @Override
    public boolean isClientCancelOnExit() {
        return cancelOnExit;
    }

    @Override
    public String getClientBaseUrl() {
        return baseUrl;
    }

    /**
     *
     * @return
     */
    public Supplier<CloseableHttpClient> getClientSupplier() {
        return httpCliGen;
    }
    
    /**
     *
     * @param httpCliGen
     */
    public void setClientSupplier(Supplier<CloseableHttpClient> httpCliGen) {
        this.httpCliGen = httpCliGen;
    }
    
    /**
     *
     * @return
     */
    public RequestConfig getRequestConfig() {
        return reqCfg;
    }
    
    /**
     *
     * @param reqCfg
     */
    public void setRequestConfig(RequestConfig reqCfg) {
        this.reqCfg = reqCfg;
    }
    
    /**
     *
     * @return
     */
    public String getBaseUrl() {
        return baseUrl;
    }
    
    /**
     *
     * @param baseUrl
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 
     * @param embeddedProbe
     */
    public void setEmbeddedProbe(Boolean embeddedProbe) {
        this.embeddedProbe = embeddedProbe;
    }

    /**
     *
     * @return
     */
    public Boolean isEmbeddedProbe() {
        return embeddedProbe;
    }
    
    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }
    
    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     *
     * @return
     */
    public String getPassword() {
        return pwd;
    }
    
    /**
     *
     * @param pwd
     */
    public void setPassword(String pwd) {
        this.pwd = pwd;
    }
    
    /**
     *
     * @return
     */
    public Boolean isCancelOnExit() {
        return cancelOnExit;
    }
    
    /**
     *
     * @param cancelOnExit
     */
    public void setCancelOnExit(Boolean cancelOnExit) {
        this.cancelOnExit = cancelOnExit;
    }
    
    /**
     *
     * @throws IOException
     * @throws NCClientException
     */
    public void initialize() throws IOException, NCClientException {
        httpCli = httpCliGen.get();
    
        if (reqCfg == null)
            reqCfg = RequestConfig.DEFAULT;
    
        if (embeddedProbe) {
            Consumer<NCEmbeddedResult> embeddedCb = (NCEmbeddedResult res) -> {
                embeddedResMap.put(res.getServerRequestId(), res);
        
                synchronized (mux) {
                    mux.notifyAll();
                }
            };
        
            NCEmbeddedProbe.registerCallback(embeddedCb);
        }
        
        acsTok = restSignin();
        
        started = true;
    }
    
    /**
     *
     * @param r
     * @throws NCClientException
     */
    private static void checkStatus(NCStatusResponseBean r) throws NCClientException {
        checkStatus(r.getStatus());
    }
    
    /**
     *
     * @param status
     */
    private static void checkStatus(String status) {
        if (!status.equals(STATUS_API_OK))
            throw new NCClientException(String.format("Unexpected message status: %s", status));
    }
    
    /**
     *
     * @param js
     * @param type
     * @param <T>
     * @return
     * @throws NCClientException
     */
    private <T extends NCStatusResponseBean> T checkAndExtract(String js, Type type) throws NCClientException {
        T t = gson.fromJson(js, type);
        
        checkStatus(t.getStatus());
        
        return t;
    }
    
    /**
     *
     * @param url
     * @param ps
     * @return
     * @throws NCClientException
     * @throws IOException
     * @throws IllegalStateException
     */
    @SafeVarargs
    private final String post(String url, Pair<String, Object>... ps) throws NCClientException, IOException {
        if (!started)
            throw new IllegalStateException("Client is not initialized.");
        
        List<Pair<String, ?>> psList =
            Arrays.stream(ps).filter(p -> p != null && p.getRight() != null).collect(Collectors.toList());
    
        try {
            return postPlain(url, psList);
        }
        catch (NCClientException e) {
            if (!AUTH_ERR.equals(e.getServerCode()))
                throw e;
            
            try {
                log.debug("Reconnect attempt because token is invalid.");
    
                acsTok = restSignin();
    
                log.debug("Reconnected OK.");
    
                // Replaces token.
                return postPlain(url, psList.stream().
                    map(p -> p.getLeft().equals("acsTok") ? Pair.of("acsTok", acsTok) : p).
                    collect(Collectors.toList()));
            }
            catch (NCClientException e1) {
                throw e;
            }
        }
    }
    
    /**
     *
     * @param url
     * @param ps
     * @return
     * @throws NCClientException
     * @throws IOException
     */
    private String postPlain(String url, List<Pair<String, ?>> ps) throws NCClientException, IOException {
        HttpPost post = new HttpPost(baseUrl + url);
        
        try {
            post.setConfig(reqCfg);
    
            StringEntity entity = new StringEntity(
                gson.toJson(ps.stream().collect(Collectors.toMap(Pair::getKey, Pair::getValue))),
                "UTF-8"
            );
            
            post.setHeader("Content-Type", "application/json");
            post.setEntity(entity);
            
            ResponseHandler<String> h = resp -> {
                int code = resp.getStatusLine().getStatusCode();
                
                HttpEntity e = resp.getEntity();
                
                String js = e != null ? EntityUtils.toString(e) : null;
    
                if (js == null)
                    throw new NCClientException(String.format("Unexpected empty response [code=%d]", code));
    
                if (code == 200)
                    return js;
    
                NCErrorMessageBean err;
                
                try {
                     err = gson.fromJson(js, NCErrorMessageBean.class);
                }
                catch (Exception e1) {
                    throw new NCClientException(String.format("Unexpected server error [code=%d]", code));
                }
    
                throw new NCClientException(err.getMessage(), err.getCode());
            };
            
            return httpCli.execute(post, h);
        }
        finally {
            post.releaseConnection();
        }
    }
    
    /**
     * @param v
     * @param name
     * @throws IllegalArgumentException
     */
    private void notNull(String v, String name) throws IllegalArgumentException {
        if (v == null || v.trim().isEmpty())
            throw new IllegalArgumentException(String.format("Parameter cannot be null or empty: '%s'", name));
    }
    
    /**
     * @param v
     * @param name
     * @throws IllegalArgumentException
     */
    private void notNull(Object v, String name) throws IllegalArgumentException {
        if (v == null)
            throw new IllegalArgumentException(String.format("Parameter cannot be null: '%s'", name));
    }
    
    /**
     *
     * @return
     * @throws IOException
     * @throws NCClientException
     */
    private String restSignin() throws IOException, NCClientException {
        NCSigninBean b =
            checkAndExtract(
                postPlain(
                    "/signin",
                    Arrays.asList(
                        Pair.of("email", email),
                        Pair.of("passwd", pwd)
                    )
                ),
                NCSigninBean.class
            );
        
        return b.getAccessToken();
    }
    
    
    @Override
    public long addUser(
        String email,
        String passwd,
        String firstName,
        String lastName,
        String avatarUrl,
        boolean isAdmin,
        Map<String, String> properties,
        String extId
    ) throws NCClientException, IOException {
        notNull(email, "email");
        notNull(passwd, "passwd");
        notNull(firstName, "firstName");
        notNull(lastName, "lastName");
    
        NCUserAddBean b =
            checkAndExtract(
                post(
                    "user/add",
                    Pair.of("acsTok", acsTok),
                    Pair.of("email", email),
                    Pair.of("passwd", passwd),
                    Pair.of("firstName", firstName),
                    Pair.of("lastName", lastName),
                    Pair.of("isAdmin", isAdmin),
                    Pair.of("avatarUrl", avatarUrl),
                    Pair.of("properties", properties),
                    Pair.of("extId", extId)
                ),
                NCUserAddBean.class
            );
        
        return b.getId();
    }
    
    @Override
    public void deleteUser(Long id, String extId) throws NCClientException, IOException {
        checkStatus(
            gson.fromJson(
                post(
                    "user/delete",
                    Pair.of("acsTok", acsTok),
                    Pair.of("id", id),
                    Pair.of("extId", extId)
                ),
                NCStatusResponseBean.class
            )
        );
    }
    
    @Override
    public void updateUser(
        long id, String firstName, String lastName, String avatarUrl, Map<String, String> properties
    ) throws NCClientException, IOException {
        notNull(firstName, "firstName");
        notNull(lastName, "lastName");
    
        checkStatus(
            gson.fromJson(
                post(
                    "user/update",
                    Pair.of("acsTok", acsTok),
                    Pair.of("id", id),
                    Pair.of("firstName", firstName),
                    Pair.of("lastName", lastName),
                    Pair.of("avatarUrl", avatarUrl),
                    Pair.of("properties", properties)
                ),
                NCStatusResponseBean.class
            )
        );
    }
    
    @Override
    public void updateUserAdmin(Long id, boolean admin) throws NCClientException, IOException {
        checkStatus(
            gson.fromJson(
                post(
                    "user/admin",
                    Pair.of("acsTok", acsTok),
                    Pair.of("id", id),
                    Pair.of("admin", admin)
                ),
                NCStatusResponseBean.class
            )
        );
    }
    
    @Override
    public NCUser getUser(Long id, String extId) throws NCClientException, IOException {
        return
            checkAndExtract(
                post(
                    "user/get",
                    Pair.of("acsTok", acsTok),
                    Pair.of("id", id),
                    Pair.of("extId", extId)
                ),
                NCUserBean.class
            );
    }
    
    @Override
    public void resetUserPassword(Long id, String newPasswd) throws NCClientException, IOException {
        notNull(newPasswd, "newPasswd");
    
        checkStatus(
            gson.fromJson(
                post(
                    "user/passwd/reset",
                    Pair.of("acsTok", acsTok),
                    Pair.of("id", id),
                    Pair.of("newPasswd", newPasswd)
                ),
                NCStatusResponseBean.class
            )
        );
    }
    
    @Override
    public List<NCUser> getAllUsers() throws NCClientException, IOException {
        NCUsersAllBean b =
            checkAndExtract(
                post(
                    "user/all",
                    Pair.of("acsTok", acsTok)
                ),
                NCUsersAllBean.class
            );
        
        return new ArrayList<>(b.getUsers());
    }
    
    @Override
    public List<NCProbe> getProbes() throws NCClientException, IOException {
        NCProbesAllBean b =
            checkAndExtract(
                post(
                    "probe/all",
                    Pair.of("acsTok", acsTok)
                ),
                NCProbesAllBean.class
            );
    
        return new ArrayList<>(b.getProbes());
    }
    
    @Override
    public void clearConversation(String mdlId, Long usrId, String usrExtId) throws NCClientException, IOException {
        notNull(mdlId, "mdlId");
        
        checkStatus(
            gson.fromJson(
                post(
                    "clear/conversation",
                    Pair.of("acsTok", acsTok),
                    Pair.of("mdlId", mdlId),
                    Pair.of("usrId", usrId),
                    Pair.of("usrExtId", usrExtId)
                ),
                NCStatusResponseBean.class
            )
        );
    }
    
    @Override
    public void clearDialog(String mdlId, Long usrId, String usrExtId) throws NCClientException, IOException {
        notNull(mdlId, "mdlId");
        
        checkStatus(
            gson.fromJson(
                post(
                    "clear/dialog",
                    Pair.of("acsTok", acsTok),
                    Pair.of("mdlId", mdlId),
                    Pair.of("usrId", usrId),
                    Pair.of("usrExtId", usrExtId)
                ),
                NCStatusResponseBean.class
            )
        );
    }
    
    @Override
    public void close() throws IOException, NCClientException {
        if (cancelOnExit)
            cancel(null, null, null);
    
        checkStatus(
            gson.fromJson(
                post(
                    "signout",
                    Pair.of("acsTok", acsTok)
                ),
                NCStatusResponseBean.class
            )
        );
    
        started = false;
    }
    
    @Override
    public String ask(String mdlId, String txt, String data, boolean enableLog, Long usrId, String usrExtId)
        throws NCClientException, IOException {
        notNull(mdlId, "mdlId");
        notNull(txt, "txt");
        
        NCAskBean b =
            checkAndExtract(
                post(
                    "ask",
                    Pair.of("acsTok", acsTok),
                    Pair.of("txt", txt),
                    Pair.of("mdlId", mdlId),
                    Pair.of("data", data),
                    Pair.of("enableLog", enableLog),
                    Pair.of("usrId", usrId),
                    Pair.of("usrExtId", usrExtId)
                ),
                NCAskBean.class
            );
        
        return b.getServerRequestId();
    }
    
    @Override
    public NCResult askSync(
        String mdlId, String txt, String data, boolean enableLog, Long usrId, String usrExtId
    ) throws NCClientException, IOException {
        notNull(mdlId, "mdlId");
        notNull(txt, "txt");
        
        if (embeddedProbe) {
            String srvReqId = ask(mdlId, txt, data, enableLog, usrId, usrExtId);
            int timeout = reqCfg.getSocketTimeout();
            long maxTime = System.currentTimeMillis() + timeout;
    
            while (true) {
                NCEmbeddedResult res = embeddedResMap.get(srvReqId);
        
                if (res != null)
                    return new NCResult() {
                        @Override
                        public String getServerRequestId() {
                            return res.getServerRequestId();
                        }
    
                        @Override
                        public String getText() {
                            return res.getOriginalText();
                        }
    
                        @Override
                        public long getUserId() {
                            return res.getUserId();
                        }
    
                        @Override
                        public String getModelId() {
                            return res.getModelId();
                        }
    
                        @Override
                        public String getProbeId() {
                            return res.getProbeId();
                        }
    
                        @Override
                        public boolean isReady() {
                            return true;
                        }
    
                        @Override
                        public String getResultType() {
                            return res.getType();
                        }
    
                        @Override
                        public String getResultBody() {
                            return res.getBody();
                        }
    
                        @Override
                        public Integer getErrorCode() {
                            return res.getErrorCode() ==  0 ? null : res.getErrorCode();
                        }
    
                        @Override
                        public String getErrorMessage() {
                            return res.getErrorMessage();
                        }
    
                        @Override
                        public String getLogHolder() {
                            return res.getLogHolder();
                        }
                    };
        
                long sleepTime = maxTime - System.currentTimeMillis();
        
                if (sleepTime <= 0)
                    throw new NCClientException(String.format("Request timeout: %d", timeout));
        
                synchronized (mux) {
                    try {
                        mux.wait(sleepTime);
                    }
                    catch (InterruptedException e) {
                        throw new NCClientException("Result wait thread interrupted.", e);
                    }
                }
            }
        }
        
        NCAskSyncBean b =
            checkAndExtract(
                post(
                    "ask/sync",
                    Pair.of("acsTok", acsTok),
                    Pair.of("txt", txt),
                    Pair.of("mdlId", mdlId),
                    Pair.of("data", data),
                    Pair.of("enableLog", enableLog),
                    Pair.of("usrId", usrId),
                    Pair.of("usrExtId", usrExtId)
                ),
                NCAskSyncBean.class
            );
        
        return b.getState();
    }
    
    @Override
    public long addFeedback(String srvReqId, double score, String comment, Long usrId, String usrExtId)
        throws NCClientException, IOException {
        notNull(srvReqId, "srvReqId");
        
        NCFeedbackAddBean b =
            checkAndExtract(
                post(
                    "feedback/add",
                    Pair.of("acsTok", acsTok),
                    Pair.of("srvReqId", srvReqId),
                    Pair.of("score", score),
                    Pair.of("comment", comment),
                    Pair.of("usrId", usrId),
                    Pair.of("usrExtId", usrExtId)
                ),
                NCFeedbackAddBean.class
            );
        
        return b.getId();
    }
    
    @Override
    public void deleteFeedback(Long id) throws NCClientException, IOException {
        checkStatus(
            gson.fromJson(
                post(
                    "feedback/delete",
                    Pair.of("acsTok", acsTok),
                    Pair.of("id", id)
                ),
                NCStatusResponseBean.class
            )
        );
    }
    
    @Override
    public List<NCFeedback> getAllFeedback(String srvReqId, Long usrId, String usrExtId) throws NCClientException, IOException {
        NCFeedbackAllBean b =
            checkAndExtract(
                post(
                    "feedback/all",
                    Pair.of("acsTok", acsTok),
                    Pair.of("srvReqId", srvReqId),
                    Pair.of("usrId", usrId),
                    Pair.of("usrExtId", usrExtId)
                ),
                NCFeedbackAllBean.class
            );
        
        return new ArrayList<>(b.getFeedback());
    }
    
    @Override
    public List<NCResult> check(Set<String> srvReqIds, Integer maxRows, Long usrId, String usrExtId) throws NCClientException, IOException {
        NCCheckBean b =
            checkAndExtract(
                post(
                    "check",
                    Pair.of("acsTok", acsTok),
                    Pair.of("srvReqIds", srvReqIds),
                    Pair.of("maxRows", maxRows),
                    Pair.of("usrId", usrId),
                    Pair.of("usrExtId", usrExtId)
                ),
                NCCheckBean.class
            );
        
        return new ArrayList<>(b.getStates());
    }
    
    @Override
    public void cancel(Set<String> srvReqIds, Long usrId, String usrExtId) throws NCClientException, IOException {
        checkStatus(
            gson.fromJson(
                post(
                    "cancel",
                    Pair.of("acsTok", acsTok),
                    Pair.of("srvReqIds", srvReqIds),
                    Pair.of("usrId", usrId),
                    Pair.of("usrExtId", usrExtId)
                ),
                NCStatusResponseBean.class
            )
        );
    }
    
    @Override
    public NCNewCompany addCompany(String name, String website, String country, String region, String city,
        String address, String postalCode, String adminEmail, String adminPasswd, String adminFirstName,
        String adminLastName, String adminAvatarUrl) throws IOException, NCClientException {
        notNull(name, "name");
        notNull(adminEmail, "adminEmail");
        notNull(adminPasswd, "adminPasswd");
        notNull(adminFirstName, "adminFirstName");
        notNull(adminLastName, "adminLastName");
        
        return
            checkAndExtract(
                post(
                    "company/add",
                    Pair.of("acsTok", acsTok),
                    Pair.of("name", name),
                    Pair.of("website", website),
                    Pair.of("country", country),
                    Pair.of("region", region),
                    Pair.of("city", city),
                    Pair.of("address", address),
                    Pair.of("postalCode", postalCode),
    
                    Pair.of("adminEmail", adminEmail),
                    Pair.of("adminPasswd", adminPasswd),
                    Pair.of("adminFirstName", adminFirstName),
                    Pair.of("adminLastName", adminLastName),
                    Pair.of("postalCode", adminAvatarUrl)
                ),
                NCTokenCreationBean.class
            );
    }
    
    @Override
    public NCCompany getCompany() throws IOException, NCClientException {
        return
            checkAndExtract(
                post(
                    "company/get",
                    Pair.of("acsTok", acsTok)
                ),
                NCCompanyBean.class
            );
    }
    
    @Override
    public void updateCompany(
        String name,
        String website,
        String country,
        String region,
        String city,
        String address,
        String postalCode
    ) throws IOException, NCClientException {
        notNull(name, "name");
    
        checkStatus(
            gson.fromJson(
                post(
                    "company/update",
                    Pair.of("acsTok", acsTok),
                    Pair.of("name", name),
                    Pair.of("website", website),
                    Pair.of("country", country),
                    Pair.of("region", region),
                    Pair.of("city", city),
                    Pair.of("address", address),
                    Pair.of("postalCode", postalCode)
                ),
                NCStatusResponseBean.class
            )
        );
    }
    
    @Override
    public String resetCompanyToken() throws IOException, NCClientException {
        NCCompanyTokenResetBean b =
            checkAndExtract(
                post(
                    "company/token/reset",
                    Pair.of("acsTok", acsTok)
                ),
                NCCompanyTokenResetBean.class
            );
        
        return b.getToken();
    }
    
    @Override
    public void deleteCompany() throws IOException, NCClientException {
        checkStatus(
            gson.fromJson(
                post(
                    "company/delete",
                    Pair.of("acsTok", acsTok)
                ),
                NCStatusResponseBean.class
            )
        );
    }
}
