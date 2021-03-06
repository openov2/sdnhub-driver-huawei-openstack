/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdnhub.osdriverservice.openstack.client.http;

import java.util.Map;

/**
 * Captures HTTP response status, body and headers.
 * <br>
 *
 * @author
 * @version SDNHUB 0.5 July 31, 2016
 */
public class HttpResult {

    private int status;

    private String body;

    private Map<String, String> respHeaders;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setRespHeaders(Map<String, String> respHeaders) {
        this.respHeaders = respHeaders;
    }

    public Map<String, String> getRespHeaders() {
        return this.respHeaders;
    }

    /**
     * <br>
     *
     * @return
     * @since SDNHUB 0.5
     */
    @Override
    public String toString() {
        return "\nStatus: " + this.getStatus() + "\nBody: " + this.getBody() + "\n";
    }
}
