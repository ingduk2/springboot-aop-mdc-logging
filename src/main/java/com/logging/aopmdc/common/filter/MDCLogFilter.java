package com.logging.aopmdc.common.filter;

import com.logging.aopmdc.common.constant.LogConst;
import com.logging.aopmdc.common.filter.txid.TxIdGenerator;
import com.logging.aopmdc.common.filter.wrapper.CachingRequestWrapper;
import com.logging.aopmdc.common.filter.wrapper.CachingResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MDCLogFilter extends OncePerRequestFilter {

    private final TxIdGenerator txIdGenerator;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String txId = txIdGenerator.generateTxId();
        MDC.put(LogConst.LOG_ID.getValue(), txId);

        doFilterWrapped(
                new CachingRequestWrapper(request),
                new CachingResponseWrapper(response),
                filterChain,
                txId
        );

        MDC.clear();
    }

    protected void doFilterWrapped(CachingRequestWrapper requestWrapper,
                                   CachingResponseWrapper responseWrapper,
                                   FilterChain filterChain,
                                   String txId) throws ServletException, IOException {
        filterChain.doFilter(requestWrapper, responseWrapper);
        responseWrapper.setHeader(LogConst.LOG_ID.getValue(), txId);
        responseWrapper.copyBodyToResponse();
    }
}
