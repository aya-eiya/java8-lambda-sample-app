package jp.eiya.aya.web.model.dao;

import jp.eiya.aya.web.model.dao.base.DAOModel;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Account extends DAOModel {
    public static String FIELD_ID = "id";
    public static String FIELD_ACCOUNT_NAME = "accountName";

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String accountName;

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public void setAccountName(String accountName){
        this.accountName = accountName;
    }

    @Email
    public String getAccountName(){
        return accountName;
    }
}
