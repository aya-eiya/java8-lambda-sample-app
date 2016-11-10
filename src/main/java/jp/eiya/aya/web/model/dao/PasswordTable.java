package jp.eiya.aya.web.model.dao;

import jp.eiya.aya.web.model.dao.base.DAOModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PasswordTable extends DAOModel {
    public static String FIELD_ID = "_id";
    public static String FIELD_ACCOUNT_ID = "_accountId";
    public static String FIELD_PASSWORD = "_password";
    public static String FIELD_SALT = "_salt";

    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private Integer accountId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String salt;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getAccountId() { return accountId; }
    public void setAccountId(Integer accountId) { if(this.accountId==null) { this.accountId = accountId; } }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getSalt() { return salt; }
    public void setSalt(String salt) { this.salt = salt; }

}
