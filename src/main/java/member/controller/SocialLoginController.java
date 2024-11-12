package member.controller;

import external.oauth.dto.SocialLoginRequest;
import external.oauth.dto.SocialLoginResponse;
import lombok.RequiredArgsConstructor;
import member.usecase.SocialLoginUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth/login")
public class SocialLoginController {
    private final SocialLoginUseCase socialLoginUseCase;
    @PostMapping
    public ResponseEntity<SocialLoginResponse> login(@RequestBody SocialLoginRequest socialLoginRequest) {
        return ResponseEntity.ok(socialLoginUseCase.login(socialLoginRequest));
    }
}
