package com.softserve.itacademy.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User  {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "user_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "10"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private long id;

    @Column(name = "email")
    @NotNull(message = "User email cannot be null")
    @NotEmpty(message = "User email cannot be empty")
    @Email(message = "Email is not valid")
    private String email;


    @Column(name = "first_name")
    @NotNull(message = "User firstName cannot be null")
    @Pattern(regexp = "^[A-Z]{1}[a-z]{1,12}([-]{1}[A-Z]{1}[a-z]{1,12})*$",
            message = "User firstName should start with a capital letter and also may have a dash")
    private String firstName;


    @Column(name = "last_name")
    @NotNull(message = "User lastName cannot be null")
    @Pattern(regexp = "^[A-Z]{1}[a-z]{1,12}([-]{1}[A-Z]{1}[a-z]{1,12})*$",
            message = "User lastName should start with a capital letter and also may have a dash")
    private String lastName;


    @Column(name = "password")
    @NotNull(message = "User password cannot be null")
    @Size(min = 4, max = 100, message = "User password should be between 4 and 100 symbols")
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @OneToMany(mappedBy = "owner")
    private List<ToDo> todos;

    @ManyToMany
    @JoinTable(name = "todo_collaborator",
    joinColumns = @JoinColumn(name = "collaborator_id"),
    inverseJoinColumns = @JoinColumn(name = "todo_id"))
    private List<ToDo> collectiveTodos;

    public User() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<ToDo> getTodos() {
        return todos;
    }

    public void setTodos(List<ToDo> todos) {
        this.todos = todos;
    }

    public List<ToDo> getCollectiveTodos() {
        return collectiveTodos;
    }

    public void setCollectiveTodos(List<ToDo> collectiveTodos) {
        this.collectiveTodos = collectiveTodos;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", todos=" + todos +
                ", collectiveTodos=" + collectiveTodos +
                '}';
    }
}
