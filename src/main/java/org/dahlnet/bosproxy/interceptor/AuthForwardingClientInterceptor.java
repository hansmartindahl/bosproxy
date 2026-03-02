package org.dahlnet.bosproxy.interceptor;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.stub.MetadataUtils;
import jakarta.servlet.http.HttpServletRequest;

public class AuthForwardingClientInterceptor implements ClientInterceptor {

    private static final Metadata.Key<String> AUTHORIZATION_KEY =
        Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {

        // Try to extract the HTTP request context
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            HttpServletRequest request = attrs.getRequest();

            // Skip if it's the login request
            if (request != null && !request.getRequestURI().contains("/login")) {
                String authHeader = request.getHeader("Authorization");

                if (authHeader != null && !authHeader.isEmpty()) {
                    Metadata metadata = new Metadata();
                    metadata.put(AUTHORIZATION_KEY, authHeader);

                    // Attach metadata and return stub
                    ClientInterceptor metadataInterceptor = MetadataUtils.newAttachHeadersInterceptor(metadata);
                    return metadataInterceptor.interceptCall(method, callOptions, next);
                }
            }
        }

        // Proceed without metadata if login or missing context
        return next.newCall(method, callOptions);
    }
}
