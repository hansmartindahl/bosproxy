package org.dahlnet.bosproxy.config;

import org.dahlnet.bosproxy.interceptor.AuthForwardingClientInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import braiins.bos.v1.ActionsServiceGrpc;
import braiins.bos.v1.AuthenticationServiceGrpc;
import braiins.bos.v1.ConfigurationServiceGrpc;
import braiins.bos.v1.CoolingServiceGrpc;
import braiins.bos.v1.LicenseServiceGrpc;
import braiins.bos.v1.MinerServiceGrpc;
import braiins.bos.v1.NetworkServiceGrpc;
import braiins.bos.v1.PerformanceServiceGrpc;
import braiins.bos.v1.PoolServiceGrpc;

@Configuration
public class GrpcClientConfig {

    @Value("${grpc.server.address}")
    private String grpcServerAddress;

    @Value("${grpc.server.port}")
    private int grpcServerPort;

    @Bean
    public ManagedChannel managedChannel() {
        return ManagedChannelBuilder.forAddress(grpcServerAddress, grpcServerPort)
                .usePlaintext()
                .build();
    }

    private AuthForwardingClientInterceptor authInterceptor() {
        return new AuthForwardingClientInterceptor();
    }

    @Bean
    public ActionsServiceGrpc.ActionsServiceBlockingStub actionsServiceStub(ManagedChannel channel) {
        return ActionsServiceGrpc.newBlockingStub(channel)
                .withInterceptors(authInterceptor());
    }

    @Bean
    public AuthenticationServiceGrpc.AuthenticationServiceBlockingStub authenticationServiceStub(ManagedChannel channel) {
        return AuthenticationServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public MinerServiceGrpc.MinerServiceBlockingStub minerServiceStub(ManagedChannel channel) {
        return MinerServiceGrpc.newBlockingStub(channel)
                .withInterceptors(authInterceptor());
    }

    @Bean
    public PoolServiceGrpc.PoolServiceBlockingStub poolServiceStub(ManagedChannel channel) {
        return PoolServiceGrpc.newBlockingStub(channel)
                .withInterceptors(authInterceptor());
    }

    @Bean
    public CoolingServiceGrpc.CoolingServiceBlockingStub coolingServiceStub(ManagedChannel channel) {
        return CoolingServiceGrpc.newBlockingStub(channel)
                .withInterceptors(authInterceptor());
    }

    @Bean
    public PerformanceServiceGrpc.PerformanceServiceBlockingStub performanceServiceStub(ManagedChannel channel) {
        return PerformanceServiceGrpc.newBlockingStub(channel)
                .withInterceptors(authInterceptor());
    }

    @Bean
    public ConfigurationServiceGrpc.ConfigurationServiceBlockingStub configurationServiceStub(ManagedChannel channel) {
        return ConfigurationServiceGrpc.newBlockingStub(channel)
                .withInterceptors(authInterceptor());
    }

    @Bean
    public LicenseServiceGrpc.LicenseServiceBlockingStub licenseServiceStub(ManagedChannel channel) {
        return LicenseServiceGrpc.newBlockingStub(channel)
                .withInterceptors(authInterceptor());
    }

    @Bean
    public NetworkServiceGrpc.NetworkServiceBlockingStub networkServiceStub(ManagedChannel channel) {
        return NetworkServiceGrpc.newBlockingStub(channel)
                .withInterceptors(authInterceptor());
    }

}

