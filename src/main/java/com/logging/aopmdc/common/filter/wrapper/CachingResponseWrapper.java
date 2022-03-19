package com.logging.aopmdc.common.filter.wrapper;

import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletResponse;

public class CachingResponseWrapper extends ContentCachingResponseWrapper {

    public CachingResponseWrapper(HttpServletResponse response) {
        super(response);
    }
}
