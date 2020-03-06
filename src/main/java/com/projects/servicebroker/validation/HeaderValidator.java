package com.projects.servicebroker.validation;

import com.projects.servicebroker.exception.ApiVersionNotFoundException;
import com.projects.servicebroker.exception.ApiVersionNotSupportedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class HeaderValidator extends HandlerInterceptorAdapter {
    private static final String API_VERSON_HEADER = "X-Broker-API-Version";
    @Value("${API_VERSION}")
    private String apiVersion;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String version = request.getHeader(API_VERSON_HEADER);
        if(version == null) {
            throw new ApiVersionNotFoundException("Api version header is required");
        }
        if(!version.equals(apiVersion)) {
            throw new ApiVersionNotSupportedException("Api version not supported. Please use version 2.15");
        }
        return true;
    }
}
