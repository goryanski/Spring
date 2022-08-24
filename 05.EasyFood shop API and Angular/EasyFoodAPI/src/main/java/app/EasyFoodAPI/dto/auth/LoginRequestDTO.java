package app.EasyFoodAPI.dto.auth;

import javax.validation.constraints.Size;

public class LoginRequestDTO {
    @Size(min = 2, max = 36, message = "Username must be between 2 and 36 symbols")
    private String username;

    @Size(min = 4, max = 36, message = "Password must be between 4 and 36 symbols")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
