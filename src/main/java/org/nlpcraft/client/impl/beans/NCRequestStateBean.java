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

package org.nlpcraft.client.impl.beans;

import org.nlpcraft.client.NCResult;

/**
 * REST bean.
 * TODO: doc
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
    
    @Override
    public String getResultType() {
        return resType;
    }
    
    @Override
    public String getResultBody() {
        return resBody;
    }
    
    @Override
    public boolean isReady() {
        return "QRY_READY".equals(status);
    }
    
    @Override
    public Integer getErrorCode() {
        return errorCode;
    }
    
    @Override
    public String getErrorMessage() {
        return error;
    }
    
    @Override
    public String getLogHolder() {
        return logHolder;
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
    
    public void setProbeId(String probeId) {
        this.probeId = probeId;
    }
    
    public void setResType(String resType) {
        this.resType = resType;
    }
    
    public void setResBody(String resBody) {
        this.resBody = resBody;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    public void setLogHolder(String logHolder) {
        this.logHolder = logHolder;
    }
}
