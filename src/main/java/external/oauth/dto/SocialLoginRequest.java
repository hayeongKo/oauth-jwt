package external.oauth.dto;

public record SocialLoginRequest(
        String socialPlatform,
        String name,
        String code
) {
    public static SocialLoginRequest of(String socialPlatform, String name, String code) {
        return new SocialLoginRequest(socialPlatform, name, code);
    }
}
