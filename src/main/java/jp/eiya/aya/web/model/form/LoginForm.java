package jp.eiya.aya.web.model.form;

import jp.eiya.aya.web.model.form.base.HtmlForm;
import jp.eiya.aya.web.model.form.base.PasswordRequired;

public class LoginForm extends HtmlForm implements PasswordRequired {
    private String accountName;
    private String password;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
