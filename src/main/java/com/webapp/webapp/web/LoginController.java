package com.webapp.webapp.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.webapp.data.payloads.request.UserLoginRequestModel;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@RestController
public class LoginController {

    @ApiOperation("User login")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Response Headers", responseHeaders = {
                    @ResponseHeader(name = "authorization", description = "Bearer <JWT value here>"),
                    @ResponseHeader(name = "userId", description = "<Public User Id value here>")
            })
    })
    @PostMapping("/login")
    public void theFakeLogin(@RequestBody UserLoginRequestModel loginRequestModel) {
        throw new IllegalStateException(
                "This method should not be called. This method is implemented by Spring Security");
    }
} 