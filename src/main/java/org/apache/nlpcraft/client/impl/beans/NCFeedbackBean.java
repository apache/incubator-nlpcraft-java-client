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

package org.apache.nlpcraft.client.impl.beans;

import org.apache.nlpcraft.client.NCFeedback;

/**
 * REST bean.
 */
public class NCFeedbackBean implements NCFeedback {
    private long id;
    private String srvReqId;
    private long usrId;
    private double score;
    private String comment;
    private long createTstamp;

    @Override
    public String getServerRequestId() {
        return srvReqId;
    }

    public void setServerRequestId(String srvReqId) {
        this.srvReqId = srvReqId;
    }

    @Override
    public long getCreateTimestamp() {
        return createTstamp;
    }

    @Override
    public long getUserId() {
        return usrId;
    }

    @Override
    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsrId(long usrId) {
        this.usrId = usrId;
    }

    public void setCreateTstamp(long createTstamp) {
        this.createTstamp = createTstamp;
    }
}
