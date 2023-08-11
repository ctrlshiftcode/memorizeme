package com.memorizeme;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.BufferedReader;
import java.io.IOException;

@Configuration
public class MemorizemeAppConfig {

    @Bean
    // https://www.appsdeveloperblog.com/how-to-enable-actuators-httptrace-in-spring-boot-3
    // This method make a trace for endpoint in actuator url http://localhost:8080/memorizeme/actuator/httpexchanges
    public HttpExchangeRepository httpTraceRepository()
    {
        return new InMemoryHttpExchangeRepository();
    }

//    @Bean
//    // https://www.baeldung.com/spring-http-logging
//    // This method log all requests
//    public CommonsRequestLoggingFilter logFilter() {
//
//        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
//        filter.setIncludeQueryString(true);
//        filter.setIncludePayload(true);
//        filter.setMaxPayloadLength(10000);
//        filter.setIncludeHeaders(false);
//        filter.setAfterMessagePrefix("\n\nREQUEST PAYLOAD\n");
//
//        return filter;
//
//    }


    @Component
    public static class RequestResponseLoggingFilter implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            // Log da solicitação
            logRequest(request);
            return true;
        }

        // Métodos auxiliares para logar a solicitação e resposta
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
            System.out.println("\n\n\n\n\n TERRERE \n\n\n");
            System.out.println(requestLog.toString());
        }

        private void logResponse(HttpServletResponse response, HttpServletRequest request) {
            StringBuilder responseLog = new StringBuilder();
            responseLog.append("Request URI: ").append(request.getRequestURI()).append("\n");
            responseLog.append("Response Status: ").append(response.getStatus()).append("\n");
            responseLog.append("Headers: ").append(response.getHeaderNames()).append("\n");
            // Não é possível capturar o corpo da resposta diretamente com um interceptor,
            // pois o fluxo de saída já foi consumido. Você pode criar um HttpServletResponseWrapper
            // similar à abordagem do filtro para capturar o corpo, se necessário.

            System.out.println(responseLog.toString());
        }
    }
}
