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

package org.openo.sdnhub.osdriverservice.util;

import java.util.Map;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;

public class ESRutil {

    private static final String URL = "/openoapi/extsys/v1/vims/";

    /**
     * Queries ESR for controller details and returns contoller's properties as map.
     * <br/>
     *
     * @param ctrlUuid
     * @return
     * @throws ServiceException
     * @since  SDNHUB 0.5
     */
    public static Map<String,Object> getControllerDetails(String ctrlUuid) throws ServiceException {
        String esrurl = URL + ctrlUuid;
        final RestfulParametes restfulParametes = new RestfulParametes();
        RestfulResponse response = RestfulProxy.get(esrurl, restfulParametes);

        return (Map)JsonUtil.fromJson(response.getResponseContent(), Map.class);

    }
}
