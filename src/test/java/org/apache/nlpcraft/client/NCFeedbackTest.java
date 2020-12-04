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

import org.apache.nlpcraft.client.models.NCCommonSpecModel;
import org.apache.nlpcraft.model.NCModel;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.apache.nlpcraft.client.models.NCCommonSpecModel.MDL_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * REST client test. Methods `ask`.
 */
class NCFeedbackTest extends NCTestAdapter {
    @Override
    Optional<Class<? extends NCModel>> getModelClass() {
        return Optional.of(NCCommonSpecModel.class);
    }

    /**
     *
     * @param usrId
     * @param usrExtId
     * @throws Exception
     */
    private void test0(Long usrId, String usrExtId) throws Exception {
        // Adds request.
        String srvReqId = admCli.ask(MDL_ID, "test", null, false, usrId, usrExtId);
    
        // Creates 2 feedback.
        long fId1 = admCli.addFeedback(srvReqId, 0.5, null, usrId, usrExtId);
        long fId2 = admCli.addFeedback(srvReqId, 0.1, "Some comment", usrId, usrExtId);
    
        // Check get methods (with filter by server request ID and without).
        assertEquals(2, admCli.getAllFeedback(srvReqId, usrId, usrExtId).size());
        assertEquals(2, admCli.getAllFeedback(null, usrId, usrExtId).size());
    
        // Deletes both feedback by their IDs.
        admCli.deleteFeedback(fId1);
        admCli.deleteFeedback(fId2);
    
        // Checks it.
        assertEquals(0, admCli.getAllFeedback(null, usrId, usrExtId).size());
    
        // Creates 2 feedback.
        admCli.addFeedback(srvReqId, 0.5, null, usrId, usrExtId);
        admCli.addFeedback(srvReqId, 0.1, "Some comment", usrId, usrExtId);
    
        // Checks their existing.
        assertEquals(2, admCli.getAllFeedback(null, usrId, usrExtId).size());
    
        // Deletes all feedback.
        admCli.deleteFeedback(null);
    
        // Checks that there aren't feedback.
        assertEquals(0, admCli.getAllFeedback(null, usrId, usrExtId).size());
    }
    
    /**
     *
     * @throws Exception
     */
    @Test
    void test() throws Exception {
        test0(null, null);
        test0(admUsrId, null);
    
        String extId = "some ext1";
        
        test0(null, extId);
        test0(get(admCli.getAllUsers(), (u) -> extId.equals(u.getExternalId())).getId(), extId);
    }
}
