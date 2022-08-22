package app.EasyFoodAPI.models;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "people_test")
public class PersonTest {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    @Size(min = 2, max = 36, message = "Username must be between 2 and 36 symbols")
    private String username;

    @Column(name = "password")
    @Size(min = 4, max = 36, message = "Password must be between 4 and 36 symbols")
    private String password;

    @Column(name = "year_of_birth")
    private int yearOfBirth;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
