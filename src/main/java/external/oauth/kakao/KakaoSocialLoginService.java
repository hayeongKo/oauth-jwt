package external.oauth.kakao;

import external.oauth.dto.SocialLoginRequest;
import external.oauth.kakao.request.KakaoInfoClient;
import external.oauth.kakao.response.KakaoInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoSocialLoginService {

    private final KakaoInfoClient kakaoInfoClient;

    public KakaoInfoResponse login(SocialLoginRequest socialLoginRequest) {
        return kakaoInfoClient.kakaoInfo("Bearer " + socialLoginRequest.code(), "application/x-www-form-urlencoded;charset=utf-8");
    }
}
