package jp.eiya.aya.web.model.form;

import jp.eiya.aya.web.model.form.base.HtmlForm;
import jp.eiya.aya.web.model.form.base.PasswordRequired;

public class RegisterForm extends HtmlForm implements PasswordRequired {
    private String accountName;

    private String password;
    private String passwordConfirm;

    private String userName;
    private String displayName;

    public String getAccountName() { return accountName; }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null?null:accountName.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) { this.userName = userName == null? null: userName.toLowerCase(); }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void deletePassword() {
        this.setPassword(null);
        this.setPasswordConfirm(null);
    }
}
