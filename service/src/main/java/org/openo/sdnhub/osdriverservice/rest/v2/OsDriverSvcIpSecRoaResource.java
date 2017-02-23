/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdnhub.osdriverservice.rest.v2;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdnhub.osdriverservice.nbi.IpSecNbiService;
import org.openo.sdnhub.osdriverservice.nbi.model.SbiNeIpSec;
import org.openo.sdnhub.osdriverservice.sbi.model.OsIpSec;
import org.openo.sdnhub.osdriverservice.util.MigrateModelUtil;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Restful interface class for OS driver.<br>
 *
 * @author
 */
@Service
@Path("/sbi-ipsec/v1")
@Api(value = "IPSec Service")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen")
public class OsDriverSvcIpSecRoaResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(OsDriverSvcIpSecRoaResource.class);

    IpSecNbiService service = new IpSecNbiService();

    /**
     * Create IpSec connection batch.<br>
     *
     * @param request HttpServletRequest Object
     * @param ctrlUuidParam Controller Id Parameter
     * @param dcGwIpSecConnList List of IpSec connections
     * @return ResultRsp object with IpSec connection list data
     * @throws ServiceException when create IpSec connection failed
     * @since SDNHUB 0.5
     */
    @POST
    @Path("/dc-gateway/ipsec-connections")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create IpSec.", notes = "Create IpSec.", response = void.class, tags = {})
    @ApiResponses(value = {
                    @ApiResponse(code = 201, message = "IpSec creation success.", response = ResultRsp.class),
                    @ApiResponse(code = 400, message = "IpSec resource provided in body is missing with required properties.", response = void.class),
                    @ApiResponse(code = 401, message = "Unauthorized.", response = void.class),
                    @ApiResponse(code = 404, message = "Not found.", response = void.class),
                    @ApiResponse(code = 500, message = "internal server error.", response = void.class)})
    @ApiImplicitParams({
        @ApiImplicitParam(name = "dcGwIpSecConnList", value = "List of IpSec to be created.", required = true,  paramType = "body", dataType = "org.openo.sdnhub.osdriverservice.nbi.model.SbiNeIpSec")})
    public ResultRsp<List<SbiNeIpSec>> createIpSec(@Context HttpServletRequest request,
            @HeaderParam("X-Driver-Parameter") String ctrlUuidParam, List<SbiNeIpSec> dcGwIpSecConnList)
            throws ServiceException {
        long enterTime = System.currentTimeMillis();
        String ctrlUuid = ctrlUuidParam.substring(ctrlUuidParam.indexOf('=') + 1);

        for(SbiNeIpSec dcGwIpSecConn : dcGwIpSecConnList) {
            OsIpSec osIpSec = MigrateModelUtil.convert(dcGwIpSecConn);

            osIpSec = this.service.createIpSec(ctrlUuid, osIpSec);
            dcGwIpSecConn.getIkePolicy().setExternalId(osIpSec.getVpnIkePolicy().getId());
            if (dcGwIpSecConn.getIpSecPolicy() != null) {
                dcGwIpSecConn.getIpSecPolicy().setExternalId(osIpSec.getVpnIpSecPolicy().getId());
            }
            dcGwIpSecConn.getIkePolicy().setExternalId(osIpSec.getVpnIkePolicy().getId());
            dcGwIpSecConn.setExternalId(osIpSec.getVpnService().getId());
            dcGwIpSecConn.setExternalIpSecId(osIpSec.getVpnIpSecSiteConnection().getId());

            dcGwIpSecConn.setSourceAddress(osIpSec.getAttributes().getSourceAddress());
            dcGwIpSecConn.setSourceLanCidrs(osIpSec.getAttributes().getSourceLanCidrs());
        }

        LOGGER.info("Exit create method. cost time = " + (System.currentTimeMillis() - enterTime));

        return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS, dcGwIpSecConnList);
    }


    /**
     * Update IpSec connection.<br>
     *
     * @param request HttpServletRequest Object
     * @param ctrlUuidParam Controller Id Parameter
     * @param dcGwIpSecConnList List of IpSec connections
     * @return ResultRsp object with IpSec connection list data
     * @throws ServiceException when create IpSec connection failed
     * @since SDNHUB 0.5
     */
    @PUT
    @Path("/dc-gateway/ipsec-connections/{ipsecconnectionid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create IpSec.", notes = "Create IpSec.", response = void.class, tags = {})
    @ApiResponses(value = {
                    @ApiResponse(code = 201, message = "IpSec creation success.", response = ResultRsp.class),
                    @ApiResponse(code = 400, message = "IpSec resource provided in body is missing with required properties.", response = void.class),
                    @ApiResponse(code = 401, message = "Unauthorized.", response = void.class),
                    @ApiResponse(code = 404, message = "Not found.", response = void.class),
                    @ApiResponse(code = 500, message = "internal server error.", response = void.class)})
    @ApiImplicitParams({
        @ApiImplicitParam(name = "dcGwIpSecConnList", value = "IpSec to be created.", required = true,  paramType = "body", dataType = "org.openo.sdnhub.osdriverservice.nbi.model.SbiNeIpSec")})
    public ResultRsp<String> updateIpSec(@Context HttpServletRequest request,
            @HeaderParam("X-Driver-Parameter") String ctrlUuidParam, @PathParam("ipsecconnectionid") String ipSecConnId, SbiNeIpSec dcGwIpSecConn)
            throws ServiceException {
        String ctrlUuid = ctrlUuidParam.substring(ctrlUuidParam.indexOf('=') + 1);
        OsIpSec osIpSec = MigrateModelUtil.convert(dcGwIpSecConn);
        if (osIpSec.getVpnIpSecSiteConnection().getPeerCidrs() != null) {
            this.service.updateIpSec(ctrlUuid, ipSecConnId, osIpSec);
        }

        return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);
    }


    /**
     * Delete IpSec connection.<br>
     *
     * @param request HttpServletRequest Object
     * @param ctrlUuidParam Controller Id Parameter
     * @param ipSecConnIds IpSec connection ids
     * @return ResultRsp object with deleting result data
     * @throws ServiceException when delete IpSec connection failed
     * @since SDNHUB 0.5
     */
    @POST
    @Path("/dc-gateway/ipsec-connections/batch-delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create IpSec.", notes = "Create IpSec.", response = void.class, tags = {})
    @ApiResponses(value = {
                    @ApiResponse(code = 201, message = "IpSec creation success.", response = ResultRsp.class),
                    @ApiResponse(code = 400, message = "IpSec resource provided in body is missing with required properties.", response = void.class),
                    @ApiResponse(code = 401, message = "Unauthorized.", response = void.class),
                    @ApiResponse(code = 404, message = "Not found.", response = void.class),
                    @ApiResponse(code = 500, message = "internal server error.", response = void.class)})
    @ApiImplicitParams({
        @ApiImplicitParam(name = "dcGwIpSecConnList", value = "List of IpSecs to be created.", required = true,  paramType = "body", dataType = "org.openo.sdnhub.osdriverservice.nbi.model.SbiNeIpSec")})
    public ResultRsp<String> deleteIpSec(@Context HttpServletRequest request,
            @HeaderParam("X-Driver-Parameter") String ctrlUuidParam, List<SbiNeIpSec> dcGwIpSecConnList)
            throws ServiceException {
        long enterTime = System.currentTimeMillis();
        String ctrlUuid = ctrlUuidParam.substring(ctrlUuidParam.indexOf('=') + 1);

        for(SbiNeIpSec dcGwIpSecConn : dcGwIpSecConnList) {
            this.service.deleteIpSec(ctrlUuid, dcGwIpSecConn.getUuid());
        }

        LOGGER.info("Exit delete method. cost time = " + (System.currentTimeMillis() - enterTime));

        return new ResultRsp<>();
    }
}
