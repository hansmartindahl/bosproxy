package org.dahlnet.bosproxy.controller;

import braiins.bos.v1.Authentication;
import braiins.bos.v1.AuthenticationServiceGrpc.AuthenticationServiceBlockingStub;

import org.dahlnet.bosproxy.dto.LoginRequestDTO;
import org.dahlnet.bosproxy.dto.LoginResponseDTO;
import org.dahlnet.bosproxy.dto.SetPasswordRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationServiceBlockingStub authenticationStub;

    public AuthenticationController(AuthenticationServiceBlockingStub authenticationStub) {
        this.authenticationStub = authenticationStub;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        Authentication.LoginRequest grpcRequest = Authentication.LoginRequest.newBuilder()
                .setUsername(loginRequest.username())
                .setPassword(loginRequest.password())
                .build();
        Authentication.LoginResponse grpcResponse = authenticationStub.login(grpcRequest);
        return ResponseEntity.ok(new LoginResponseDTO(
                grpcResponse.getToken(),
                grpcResponse.getTimeoutS()
        ));
    }

    @PostMapping("/set-password")
    public ResponseEntity<Void> setPassword(@RequestBody SetPasswordRequestDTO body) {
        Authentication.SetPasswordRequest.Builder builder = Authentication.SetPasswordRequest.newBuilder();
        if (body.password() != null && !body.password().isEmpty()) {
            builder.setPassword(body.password());
        }
        authenticationStub.setPassword(builder.build());
        return ResponseEntity.ok().build();
    }
}

