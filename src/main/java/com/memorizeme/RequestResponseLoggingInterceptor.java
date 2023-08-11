package com.memorizeme;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.BufferedReader;
import java.io.IOException;

@Component
public class RequestResponseLoggingInterceptor implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        logRequest(httpServletRequest);
        logResponse(httpServletResponse, httpServletRequest);
    }

    private void logRequest(HttpServletRequest request) throws IOException, IOException {
        StringBuilder requestLog = new StringBuilder();
        requestLog.append("Request URI: ").append(request.getRequestURI()).append("\n");
        requestLog.append("Method: ").append(request.getMethod()).append("\n");
        requestLog.append("Headers: ").append(request.getHeaderNames()).append("\n");

        // Lê o corpo da solicitação
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestLog.append("Request Body: ").append(line).append("\n");
            }
        }

        System.out.println(requestLog.toString());
    }

    private void logResponse(HttpServletResponse response, HttpServletRequest request) {
        StringBuilder responseLog = new StringBuilder();
        responseLog.append("Request URI: ").append(request.getRequestURI()).append("\n");
        responseLog.append("Response Status: ").append(response.getStatus()).append("\n");
        responseLog.append("Headers: ").append(response.getHeaderNames()).append("\n");

        System.out.println(responseLog.toString());
    }


}
