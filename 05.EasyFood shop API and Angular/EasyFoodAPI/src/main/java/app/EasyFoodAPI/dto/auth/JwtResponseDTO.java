package app.EasyFoodAPI.dto.auth;

public class JwtResponseDTO {
    private String accessToken;
    private String exception;
    private String userRole;
    private String userId;

    public JwtResponseDTO(String accessToken, String exception, String userRole, String userId) {
        this.accessToken = accessToken;
        this.exception = exception;
        this.userRole = userRole;
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
