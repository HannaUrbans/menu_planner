package by.urbans.springproject.bean;

import java.util.Objects;

// возможно, будет переделан со спринг security?
public class Auth {
    private String login;
    private String password;

    public Auth() {
    }

    public Auth(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auth auth = (Auth) o;
        return Objects.equals(login, auth.login) && Objects.equals(password, auth.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
               "login='" + login + '\'' +
               ", password='" + password + '\'' +
               '}';
    }

}
