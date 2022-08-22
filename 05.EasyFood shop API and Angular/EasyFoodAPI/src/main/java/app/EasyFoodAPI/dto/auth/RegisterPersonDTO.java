package app.EasyFoodAPI.dto.auth;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;


public class RegisterPersonDTO {
    @Size(min = 2, max = 74, message = "FullName must be between 2 and 74 symbols")
    private String fullName;

    @Email(message = "Email must be valid")
    private String email;

    // validate with custom validator. and with regular expression on the client side ("dd/MM/yyyy")
    private String dateOfBirth;

    @Size(min = 2, max = 36, message = "Username must be between 2 and 36 symbols")
    private String username;

    @Size(min = 4, max = 36, message = "Password must be between 4 and 36 symbols")
    private String password;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
