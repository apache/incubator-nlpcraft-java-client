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

import org.apache.nlpcraft.client.NCResult;

import java.util.Map;

/**
 * REST bean.
 */
public class NCRequestStateBean implements NCResult {
    private String srvReqId;
    private String txt;
    private long usrId;
    private String mdlId;
    private String probeId;
    private String resType;
    private String resBody;
    private String status;
    private Integer errorCode;
    private String error;
    private String logHolder;
    private Map<String, Object> resMeta;

    @Override
    public String getServerRequestId() {
        return srvReqId;
    }

    @Override
    public String getText() {
        return txt;
    }

    @Override
    public long getUserId() {
        return usrId;
    }

    @Override
    public String getModelId() {
        return mdlId;
    }

    @Override
    public String getProbeId() {
        return probeId;
    }

    public void setProbeId(String probeId) {
        this.probeId = probeId;
    }

    @Override
    public String getResultType() {
        return resType;
    }

    public void setResultType(String resType) {
        this.resType = resType;
    }

    @Override
    public String getResultBody() {
        return resBody;
    }

    public void setResultBody(String resBody) {
        this.resBody = resBody;
    }

    @Override
    public boolean isReady() {
        return "QRY_READY".equals(status);
    }

    @Override
    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getErrorMessage() {
        return error;
    }

    @Override
    public String getLogHolder() {
        return logHolder;
    }

    public void setLogHolder(String logHolder) {
        this.logHolder = logHolder;
    }

    @Override
    public Map<String, Object> getResultMeta() {
        return resMeta;
    }

    public void setResultMeta(Map<String, Object> resMeta) {
        this.resMeta = resMeta;
    }

    public void setSrvReqId(String srvReqId) {
        this.srvReqId = srvReqId;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public void setUsrId(long usrId) {
        this.usrId = usrId;
    }

    public void setMdlId(String mdlId) {
        this.mdlId = mdlId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setError(String error) {
        this.error = error;
    }
}
