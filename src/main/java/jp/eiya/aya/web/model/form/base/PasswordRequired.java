package jp.eiya.aya.web.model.form.base;

public interface PasswordRequired {
    String getPassword();
    void setPassword(String password);

    default void deletePassword(){
        setPassword(null);
    }
}
