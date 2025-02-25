package by.urbans.springproject.bean;

/**
 * храним нечувствительную информацию
 */
// возможно, будет переделан со спринг security?
public interface AuthorizedUser {
    String getLogin();

    String getPhone();
}
