package jp.eiya.aya.web.model.dao;

import jp.eiya.aya.web.model.dao.base.DAOModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Entity
public class AccountInfo extends DAOModel{
    public static String FIELD_ID = "id";
    public static String FIELD_USER_NAME = "userName";
    public static String FIELD_ACCOUNT_ID = "accountId";
    public static String FIELD_DISPLAY_NAME = "displayName";
    public static String FIELD_BIRTH_DAY = "birthDay";

    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private Integer accountId;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String displayName;

    private LocalDate birthDay;

    @NotNull
    public Integer getId(){
        return id;
    }
    public void setId(Integer id) { this.id = id; }

    @NotNull
    public Integer getAccountId(){
        return accountId;
    }
    public void setAccountId(Integer accountId) { if(this.accountId==null){ this.accountId = accountId; } }

    @NotNull
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    @NotNull
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public LocalDate getBirthDay(){
        return birthDay;
    }
    public void setBirthDay(LocalDate birthDay) { this.birthDay = birthDay; }

    /* computed values */
    public boolean isCompleted_computed() {
        return getId() != null &&
                getAccountId() != null &&
                getUserName() != null &&
                getDisplayName() != null &&
                getBirthDay() != null;
    }

    public Optional<Integer> getAge_computed() {
        if(getBirthDay() == null){ return Optional.empty(); }
        return Optional.of(Period.between(getBirthDay(),LocalDate.now()).getYears());
    }

    public void setBirthDay(String birthDayStr) {
        setBirthDay(LocalDate.parse(birthDayStr, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
