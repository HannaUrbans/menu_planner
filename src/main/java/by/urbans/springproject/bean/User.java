package by.urbans.springproject.bean;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

// возможно, будет переделан со спринг security?
@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = "login")})
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements AuthorizedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    /*можно добавить кастомную валидацию unique этот логин уже занят*/

    @Column(name = "login", unique = true, nullable = false, length = 10)
    @NotEmpty(message = "Поле \"логин\" не заполнено")
    @Size(max = 10, message = "Не используйте более 10 символов")
    private String login;

    @Transient
    private String password;

    @Column(name = "hashed_password", nullable = false)
    @NotEmpty(message = "Поле \"пароль\" не заполнено")
    private String hashedPassword;

    @Column(name = "phone", nullable = false, length = 13)
    @Size(min = 13, max = 13, message = "Неверное количество знаков")
    @Pattern(regexp = "[+]\\d{12}", message = "Неверный формат")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "user_role")
    private Role userRole;

    public User() {
    }

    public User(String login, String hashedPassword) {
        this.login = login;
        this.hashedPassword = hashedPassword;
    }

    public User(String login, String hashedPassword, String phone) {
        this.login = login;
        this.hashedPassword = hashedPassword;
        this.phone = phone;
    }

    public User(String login, String hashedPassword, String phone, Role userRole) {
        this.login = login;
        this.hashedPassword = hashedPassword;
        this.phone = phone;
        this.userRole = userRole;
    }

    public User(int id, String login, String hashedPassword, String phone, Role userRole) {
        this.id = id;
        this.login = login;
        this.hashedPassword = hashedPassword;
        this.phone = phone;
        this.userRole = userRole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    public String getPassword() {
        return password;
    }
    
    // методы для паролей
    public void setPassword(String password) {
        if (!password.startsWith("$2a$")) {
            this.hashedPassword = encodePassword(password);
        } else {
            this.hashedPassword = password;
        }
    }

    private String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(login, user.login) && Objects.equals(hashedPassword, user.hashedPassword) && Objects.equals(phone, user.phone) && Objects.equals(userRole, user.userRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, hashedPassword, phone, userRole);
    }

    // выводится в авторах рецепта, но лучше взять user.login, надо переделать!
    @Override
    public String toString() {
        return login;
    }
}
