package external.oauth.dto;

import auth.jwt.TokenResponse;

public record SocialLoginResponse(
        TokenResponse tokens
) {
    public static SocialLoginResponse of(TokenResponse tokens) {
        return new SocialLoginResponse(tokens);
    }
}
