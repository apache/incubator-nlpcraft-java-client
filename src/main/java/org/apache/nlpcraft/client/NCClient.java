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

import java.io.*;
import java.util.*;

/**
 * Java client API. Java REST client provides native and easy-to-use wrapper for NLPCraft
 * <a target=_ href="https://nlpcraft.apache.org/using-rest.html">REST APIs</a> for any JVM-based languages
 * like Java, Scala, Kotlin or Groovy. Note that NLPCraft REST APIs allow to submit the request to existing
 * deployed data model and perform other related, auxiliary operations. To create data models you need to
 * use main <a target=_ href="https://nlpcraft.apache.org/apis/latest/index.html">NLPCraft APIs</a>.
 * <p>
 * <b>External User ID</b><br>
 * Several methods on Java client accept external "on-behalf-of" user ID (<code>usrExtId</code>) additionally to the regular
 * user ID (<code>usrID</code>). In these methods zero, one or both IDs should be provided. If none are provided
 * the ID of the currently signed in user will be used, and if both are provided they should point to the same user.
 * External user ID allows to use user identification from the external systems without a need to import all the
 * existing users into NLPCraft in the first place.
 * <p>
 * Typical usage pattern for integrating NLPCraft into existing
 * system is to create a single administrative NLPCraft user, sign in with that account and then use external
 * "on-behalf-of" user IDs in all the requests. This way you get the same functionality as if using ordinary user IDs
 * but without a need to migrate and synchronize all user accounts from the existing system to NLPCraft.
 * <p>
 * <b>Usage</b><br>
 * Java client usage is straightforward - create client instance using {@link NCClientBuilder} and use this
 * instance for all API calls:
 * <pre class="brush: java">
 *     // Get client instance with all defaults.
 *     NCClient cli = new NCClientBuilder().build();
 *
 *     // Perform any necessary calls...
 *     NCResult res = cli.askSync("my.model.id", txt);
 *
 *     // Close client &amp; sign out at the end.
 *     cli.close();
 * </pre>
 * 
 * @see NCClientBuilder
 */
public interface NCClient {
    /**
     * Gets current signed in user email for this client.
     *
     * @return Current signed in user email for this client.
     * @see NCClientBuilder
     */
    String getClientUserEmail();

    /**
     * Gets current signed in user password for this client.
     *
     * @return Current signed in user password for this client.
     * @see NCClientBuilder
     */
    String getClientUserPassword();

    /**
     * Gets whether or not this client is configured with embedded probe.
     *
     * @return Whether or not this client is configured with embedded probe.
     * @see NCClientBuilder
     */
    boolean isClientEmbeddedMode();

    /**
     * Gets whether or not this client is configured with cancel-on-exit logic.
     * 
     * @return Whether or not this client is configured with cancel-on-exit logic
     * @see NCClientBuilder
     */
    boolean isClientCancelOnExit();

    /**
     * Gets base URL this client is configured with.
     *
     * @return Base URL this client is configured with.
     * @see NCClientBuilder
     */
    String getClientBaseUrl();

    /**
     * Clears conversation context for the given model and the specified user.
     * <p>
     * <b>User IDs</b><br>
     * This method allows multiple ways of specifying the ID of the user. If neither <code>usrId</code>
     * or <code>usrExtId</code> are provided (both are <code>null</code>) then the currently signed in user ID
     * of this client instance will be used by default. If both user IDs are provided they must identify the same
     * user in NLPCraft. If only external "on-behalf-of" <code>usrExtId</code> parameter is provided and such user
     * doesn't yet exist in NLPCraft - it will be automatically created. Note that only admin users can specify
     * user ID other than their own (as either <code>usrId</code> or <code>usrExtId</code>).
     *
     * @param mdlId Model ID.
     * @param usrId Optional user ID.
     * @param usrExtId Optional external "on-behalf-of" user ID.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     * @see #clearDialog(String, Long, String)
     */
    void clearConversation(String mdlId, Long usrId, String usrExtId) throws NCClientException, IOException;

    /**
     * Clears the dialog flow for the given model ID and specified user.
     * <p>
     * <b>User IDs</b><br>
     * This method allows multiple ways of specifying the ID of the user. If neither <code>usrId</code>
     * or <code>usrExtId</code> are provided (both are <code>null</code>) then the currently signed in user ID
     * of this client instance will be used by default. If both user IDs are provided they must identify the same
     * user in NLPCraft. If only external "on-behalf-of" <code>usrExtId</code> parameter is provided and such user
     * doesn't yet exist in NLPCraft - it will be automatically created. Note that only admin users can specify
     * user ID other than their own (as either <code>usrId</code> or <code>usrExtId</code>).
     *
     * @param mdlId Model ID.
     * @param usrId Optional user ID.
     * @param usrExtId Optional external "on-behalf-of" user ID.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     * @see #clearConversation(String, Long, String) 
     */
    void clearDialog(String mdlId, Long usrId, String usrExtId) throws NCClientException, IOException;

    /**
     * Adds feedback for given request.
     * <p>
     * <b>User IDs</b><br>
     * This method allows multiple ways of specifying the ID of the user. If neither <code>usrId</code>
     * or <code>usrExtId</code> are provided (both are <code>null</code>) then the currently signed in user ID
     * of this client instance will be used by default. If both user IDs are provided they must identify the same
     * user in NLPCraft. If only external "on-behalf-of" <code>usrExtId</code> parameter is provided and such user
     * doesn't yet exist in NLPCraft - it will be automatically created. Note that only admin users can specify
     * user ID other than their own (as either <code>usrId</code> or <code>usrExtId</code>).
     *
     * @param srvReqId ID of the request to add feedback for.
     * @param score Feedback score, between <code>0</code> and <code>1</code> inclusive. Higher score indicates better result.
     * @param comment Optional feedback comment.
     * @param usrId Optional user ID.
     * @param usrExtId Optional external "on-behalf-of" user ID.
     * @return ID of the newly added feedback record.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     */
    long addFeedback(String srvReqId, double score, String comment, Long usrId, String usrExtId) throws NCClientException, IOException;

    /**
     * Deletes feedback record.
     *
     * @param id Optional ID of the feedback record to delete. if {@code} - all feedback
     *      records for the current user will be deleted.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     */
    void deleteFeedback(Long id) throws NCClientException, IOException;

    /**
     * Gets all feedback records for given request ID and user. If request ID is not provided all feedback records
     * for the specified user will be returned.
     * <p>
     * <b>User IDs</b><br>
     * This method allows multiple ways of specifying the ID of the user. If neither <code>usrId</code>
     * or <code>usrExtId</code> are provided (both are <code>null</code>) then the currently signed in user ID
     * of this client instance will be used by default. If both user IDs are provided they must identify the same
     * user in NLPCraft. If only external "on-behalf-of" <code>usrExtId</code> parameter is provided and such user
     * doesn't yet exist in NLPCraft - it will be automatically created. Note that only admin users can specify
     * user ID other than their own (as either <code>usrId</code> or <code>usrExtId</code>).
     *
     * @param srvReqId Optional request ID.
     * @param usrId Optional user ID.
     * @param usrExtId Optional external "on-behalf-of" user ID.
     * @return List of feedback records.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     */
    List<NCFeedback> getAllFeedback(String srvReqId, Long usrId, String usrExtId) throws NCClientException, IOException;

    /**
     * Adds new user to the company of the currently signed in user. Current signed in user must have
     * administrative privileges.
     * <p>
     * <b>User IDs</b><br>
     * This method accepts optional external "on-behalf-of" user ID. If this ID is provided than this method
     * will update the existing user record located by that ID instead of creating a new user record.
     *
     * @param email New user email.
     * @param passwd New user password. Note that NLPCraft doesn't store passwords and therefore cannot
     *      retrieve them later.
     * @param firstName New user first name.
     * @param lastName New user last name.
     * @param avatarUrl Optional new user avatar URL. Can be {@code null}.
     * @param isAdmin Whether or not the new user will have administrative privileges.
     * @param properties Map of additional user-defined user properties.
     * @param extId Optional external "on-behalf-of" user ID. Can be {@code null}.
     * @return ID of the newly created user.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     */
    long addUser(
        String email,
        String passwd,
        String firstName,
        String lastName,
        String avatarUrl,
        boolean isAdmin,
        Map<String, String> properties,
        String extId) throws NCClientException, IOException;

    /**
     * Updates given user. Current signed in user must have administrative privileges.
     *
     * @param id User ID.
     * @param firstName Mandatory user first name.
     * @param lastName Mandatory user last name.
     * @param avatarUrl Optional user avatar URL. Can be {@code null}.
     * @param properties Optional user properties. Can be {@code null} or empty.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     */
    void updateUser(
        long id,
        String firstName,
        String lastName,
        String avatarUrl,
        Map<String, String> properties) throws NCClientException, IOException;

    /**
     * Resets password for the given user. Note that NLPCraft doesn't store clear text passwords and therefore
     * passwords cannot be retrieved - they can only be reset. Current signed in user must have
     * administrative privileges.
     *
     * @param id ID of the user for which to reset the password.
     * @param newPasswd New password.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     */
    void resetUserPassword(Long id, String newPasswd) throws NCClientException, IOException;

    /**
     * Grants or denies given user administrative privileges. Current signed in user must have
     * administrative privileges.
     * 
     * @param id ID of the user for which to change administrative privileges.
     * @param isAdmin Administrative privileges flag.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     */
    void updateUserAdmin(Long id, boolean isAdmin) throws NCClientException, IOException;

    /**
     * Deletes given user. Note that you cannot delete the last admin in the company.
     * Current signed in user must have administrative privileges.
     * <p>
     * <b>User IDs</b><br>
     * This method allows multiple ways of specifying the ID of the user. If neither <code>usrId</code>
     * or <code>usrExtId</code> are provided (both are <code>null</code>) then the currently signed in user ID
     * of this client instance will be used by default. If both user IDs are provided they must identify the same
     * user in NLPCraft. If only external "on-behalf-of" <code>usrExtId</code> parameter is provided and such user
     * doesn't yet exist in NLPCraft - it will be automatically created. Note that only admin
     * users can specify user ID other than their own (as either <code>usrId</code> or <code>usrExtId</code>).
     *
     * @param usrId Optional user ID.
     * @param usrExtId Optional external "on-behalf-of" user ID.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     */
    void deleteUser(Long usrId, String usrExtId) throws NCClientException, IOException;

    /**
     * Gets user record. Note that only users with administrative privileges can get user records for
     * other users in the company.
     * <p>
     * <b>User IDs</b><br>
     * This method allows multiple ways of specifying the ID of the user. If neither <code>usrId</code>
     * or <code>usrExtId</code> are provided (both are <code>null</code>) then the currently signed in user ID
     * of this client instance will be used by default. If both user IDs are provided they must identify the same
     * user in NLPCraft. If only external "on-behalf-of" <code>usrExtId</code> parameter is provided and such user
     * doesn't yet exist in NLPCraft - it will be automatically created. Note that only admin
     * users can specify user ID other than their own (as either <code>usrId</code> or <code>usrExtId</code>).
     *
     * @param usrId Optional user ID.
     * @param usrExtId Optional external "on-behalf-of" user ID.
     * @return User record.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     */
    NCUser getUser(Long usrId, String usrExtId) throws NCClientException, IOException;

    /**
     * Gets all user records for the current signed in user company. Current signed in user must
     * have administrative privileges.
     *
     * @return List of user records.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     */
    List<NCUser> getAllUsers() throws NCClientException, IOException;

    /**
     * Gets all active (connected to the REST server) probes for the current signed in user company.
     * Current signed in user must have administrative privileges.
     * 
     * @return List of active probes.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     */
    List<NCProbe> getProbes() throws NCClientException, IOException;

    /**
     * Submits request for asynchronous processing. This method will return immediately and you have to 
     * use {@link #check(Set, Integer, Long, String)} method to check its result.
     * <p>
     * <b>User IDs</b><br>
     * This method allows multiple ways of specifying the ID of the user. If neither <code>usrId</code>
     * or <code>usrExtId</code> are provided (both are <code>null</code>) then the currently signed in user ID
     * of this client instance will be used by default. If both user IDs are provided they must identify the same
     * user in NLPCraft. If only external "on-behalf-of" <code>usrExtId</code> parameter is provided and such user
     * doesn't yet exist in NLPCraft - it will be automatically created. Note that only admin
     * users can specify user ID other than their own (as either <code>usrId</code> or <code>usrExtId</code>).
     *
     * @param mdlId ID of the model to submit the request to.
     * @param txt Text to process.
     * @param data Optional JSON data payload.
     * @param enableLog Whether or not to enable processing log collection.
     * @param usrId Optional user ID.
     * @param usrExtId Optional external "on-behalf-of" user ID.
     * @return Server request ID of the submitted request.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     * @see #askSync(String, String, String, boolean, Long, String) 
     * @see #askSync(String, String) 
     * @see #ask(String, String) 
     */
    String ask(String mdlId, String txt, String data, boolean enableLog, Long usrId, String usrExtId) throws NCClientException, IOException;

    /**
     * Submits request for asynchronous processing. This is a shortcut call that is equivalent to:
     * <pre class="brush: java">
     *     ask(mdlId, txt, null, false, null, null);
     * </pre>
     *
     * @param mdlId ID of the model to submit the request to.
     * @param txt Text to process.
     * @return Server request ID of the submitted request.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     */
    default String ask(String mdlId, String txt) throws NCClientException, IOException {
        return ask(mdlId, txt, null, false, null, null);
    }

    /**
     * Submits request for synchronous processing. This method will block and return only when result is available or
     * internal time out expired.
     * <p>
     * <b>User IDs</b><br>
     * This method allows multiple ways of specifying the ID of the user. If neither <code>usrId</code>
     * or <code>usrExtId</code> are provided (both are <code>null</code>) then the currently signed in user ID
     * of this client instance will be used by default. If both user IDs are provided they must identify the same
     * user in NLPCraft. If only external "on-behalf-of" <code>usrExtId</code> parameter is provided and such user
     * doesn't yet exist in NLPCraft - it will be automatically created. Note that only admin
     * users can specify user ID other than their own (as either <code>usrId</code> or <code>usrExtId</code>).
     *
     * @param mdlId ID of the model to submit the request to.
     * @param txt Text to process.
     * @param data Optional JSON data payload.
     * @param enableLog Whether or not to enable processing log collection.
     * @param usrId Optional user ID.
     * @param usrExtId Optional external "on-behalf-of" user ID.
     * @return Query processing result.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     * @see #askSync(String, String)
     * @see #ask(String, String)
     * @see #ask(String, String, String, boolean, Long, String) 
     */
    NCResult askSync(String mdlId, String txt, String data, boolean enableLog, Long usrId, String usrExtId) throws NCClientException, IOException;

    /**
     * Submits request for synchronous processing. This is a shortcut call that is equivalent to:
     * <pre class="brush: java">
     *     askSync(mdlId, txt, null, false, null, null);
     * </pre>
     *
     * @param mdlId ID of the model to submit the request to.
     * @param txt Text to process.
     * @return Query processing result.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     */
    default NCResult askSync(String mdlId, String txt) throws NCClientException, IOException {
        return askSync(mdlId, txt, null, false, null, null);
    }

    /**
     * Gets the status and result (OK or failure) of the previously submitted requests. Request statuses
     * returned sorted by their registration time, starting from newest.
     * <p>
     * <b>User IDs</b><br>
     * This method allows multiple ways of specifying the ID of the user. If neither <code>usrId</code>
     * or <code>usrExtId</code> are provided (both are <code>null</code>) then the currently signed in user ID
     * of this client instance will be used by default. If both user IDs are provided they must identify the same
     * user in NLPCraft. If only external "on-behalf-of" <code>usrExtId</code> parameter is provided and such user
     * doesn't yet exist in NLPCraft - it will be automatically created. Note that only admin
     * users can specify user ID other than their own (as either <code>usrId</code> or <code>usrExtId</code>).
     *
     * @param srvReqIds Optional server request IDs for which to get the statuses. If not provided - statuses for
     *      all currently processed requests for the specified user will be returned. Invalid or unknown
     *      server request IDs will be ignored.
     * @param maxRows Optional maximum number of returned items - by default all statuses will be returned.
     *      Can be {@code null}.
     * @param usrId Optional user ID.
     * @param usrExtId Optional external "on-behalf-of" user ID.
     * @return List of results.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     */
    List<NCResult> check(Set<String> srvReqIds, Integer maxRows, Long usrId, String usrExtId) throws NCClientException, IOException;

    /**
     * Cancels the previously submitted sentence and removes its result, if any, from the server storage.
     * Must be called when query result is no longer needed (i.e. downloaded by all client apps) to release the server memory.
     * Note that query results will auto-expire on server after a certain period of time. Note also that even
     * when the embedded probe is used the results are still stored on the server and have to be cancelled or
     * otherwise will be timed out.
     * <p>
     * <b>User IDs</b><br>
     * This method allows multiple ways of specifying the ID of the user. If neither <code>usrId</code>
     * or <code>usrExtId</code> are provided (both are <code>null</code>) then the currently signed in user ID
     * of this client instance will be used by default. If both user IDs are provided they must identify the same
     * user in NLPCraft. If only external "on-behalf-of" <code>usrExtId</code> parameter is provided and such user
     * doesn't yet exist in NLPCraft - it will be automatically created. Note that only admin
     * users can specify user ID other than their own (as either <code>usrId</code> or <code>usrExtId</code>).
     *
     * @param srvReqIds Server IDs of the requests to cancel. Optional, all current user requests will be
     *      cancelled by default. Invalid or unknown server request IDs will be ignored.
     * @param usrId Optional user ID.
     * @param usrExtId Optional external "on-behalf-of" user ID.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     */
    void cancel(Set<String> srvReqIds, Long usrId, String usrExtId) throws NCClientException, IOException;

    /**
     * Adds new company with given parameters. Administrative privileges required. Note that to create a new
     * company you also need to supply information for its first administrative user account which will be
     * created in the same time.
     *
     * @param name Company name.
     * @param website Optional company website.
     * @param country Optional company address country.
     * @param region Optional company address region.
     * @param city Optional company address city.
     * @param address Optional company address.
     * @param postalCode Optional company address postal code.
     * @param adminEmail Company administrator email.
     * @param adminPasswd Company administrator password.
     * @param adminFirstName Optional company administrator first name.
     * @param adminLastName Optional company administrator last name.
     * @param adminAvatarUrl Optional company administrator avatar URL.
     * @return New company data.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     */
    NCNewCompany addCompany(
        String name,
        String website,
        String country,
        String region,
        String city,
        String address,
        String postalCode,
        String adminEmail,
        String adminPasswd,
        String adminFirstName,
        String adminLastName,
        String adminAvatarUrl
    ) throws NCClientException, IOException;

    /**
     * Gets the company descriptor for the current signed in user.
     *
     * @return Company descriptor.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     */
    NCCompany getCompany() throws NCClientException, IOException;

    /**
     * Updates company information for the current signed in user. Current signed in user must have
     * administrative privileges. Note that users cannot update or get information about other
     * companies.
     *
     * @param name Company name.
     * @param website Optional company website.
     * @param country Optional company address country.
     * @param region Optional company address region.
     * @param city Optional company address city.
     * @param address Optional company address.
     * @param postalCode Optional company address postal code.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     */
    void updateCompany(
        String name,
        String website,
        String country,
        String region,
        String city,
        String address,
        String postalCode
    ) throws NCClientException, IOException;

    /**
     * Sets and returns new company probe authentication token.
     * Administrative privileges required for resetting company token.
     *
     * @return New company probe token.
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     */
    String resetCompanyToken() throws NCClientException, IOException;

    /**
     * Deletes company and all its users and other associated data. Administrative privileges required.
     *
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     */
    void deleteCompany() throws NCClientException, IOException;
    
    /**
     * Closes the client and signs out from the REST server. Any further calls to this client will result in
     * exception.
     *
     * @throws NCClientException Thrown in case of client-specific errors.
     * @throws IOException Thrown in case of generic I/O errors.
     */
    void close() throws NCClientException, IOException;
}
