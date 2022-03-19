package com.logging.aopmdc.common.filter.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CachingRequestWrapper extends HttpServletRequestWrapper {

    public CachingRequestWrapper(HttpServletRequest request) {
        super(request);
    }
}
