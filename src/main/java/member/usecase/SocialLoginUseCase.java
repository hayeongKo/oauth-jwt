package member.usecase;

import auth.jwt.JwtTokenProvider;
import auth.jwt.TokenResponse;
import external.oauth.dto.SocialLoginRequest;
import external.oauth.dto.SocialLoginResponse;
import external.oauth.google.GoogleSocialLoginService;
import external.oauth.google.response.GoogleInfoResponse;
import external.oauth.kakao.KakaoSocialLoginService;
import external.oauth.kakao.response.KakaoInfoResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import member.entity.MemberEntity;
import member.entity.SocialPlatform;
import member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocialLoginUseCase {

    private final GoogleSocialLoginService googleSocialLoginService;
    private final KakaoSocialLoginService kakaoSocialLoginService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @Transactional
    public SocialLoginResponse login(SocialLoginRequest socialLoginRequest) {
        MemberEntity memberEntity = null;
        String socialPlatFormRequest = socialLoginRequest.socialPlatform();
        if (socialLoginRequest.equals(SocialPlatform.GOOGLE.getSocialPlatform())) {
            GoogleInfoResponse googleInfoResponse = googleSocialLoginService.login(socialLoginRequest);
            if (memberService.isExistsBySub(googleInfoResponse.id())) {
                memberEntity = memberService.findBySub(googleInfoResponse.id());
            } else {
                memberEntity = MemberEntity.builder()
                        .name(googleInfoResponse.name())
                        .email(googleInfoResponse.email())
                        .sub(googleInfoResponse.id())
                        .socialPlatform(SocialPlatform.valueOf(socialLoginRequest.socialPlatform()))
                        .build();
                memberService.saveMember(memberEntity);
            }
        } else if (socialLoginRequest.equals(SocialPlatform.KAKAO.getSocialPlatform())) {
            KakaoInfoResponse kakaoInfoResponse = kakaoSocialLoginService.login(socialLoginRequest);
            if (memberService.isExistsBySub(kakaoInfoResponse.id())) {
                memberEntity = memberService.findBySub(kakaoInfoResponse.id());
            } else {
                memberEntity = MemberEntity.builder()
                        .name(kakaoInfoResponse.kakaoAccount().get("name").toString())
                        .email(kakaoInfoResponse.kakaoAccount().get("email").toString())
                        .sub(kakaoInfoResponse.id())
                        .socialPlatform(SocialPlatform.valueOf(socialLoginRequest.socialPlatform()))
                        .build();
                memberService.saveMember(memberEntity);
            }
        }

        try {
            return SocialLoginResponse.of(signUp(memberEntity.getId()));
        } catch (NullPointerException e) {
            throw new EntityNotFoundException("회원이 존재하지 않습니다.");
        }
    }

    public TokenResponse signUp(Long memberId) {
        return TokenResponse.of(jwtTokenProvider.generateAccessToken(memberId), jwtTokenProvider.generateRefreshToken(memberId));
    }
}
