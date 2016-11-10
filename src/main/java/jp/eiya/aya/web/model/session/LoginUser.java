package jp.eiya.aya.web.model.session;

import jp.eiya.aya.web.model.session.base.SessionObject;

import java.time.ZonedDateTime;

public class LoginUser extends SessionObject {
    private Integer accountId;
    private ZonedDateTime loginTime;

    public Integer getAccountId() {
        return accountId;
    }
    public void setAccountId(Integer accountId) {
        if(this.accountId == null){
            this.accountId = accountId;
        }
    }

    public ZonedDateTime getLoginTime() { return loginTime; }
    public void setLoginTime(ZonedDateTime loginTime) {
        if(this.loginTime == null) {
            this.loginTime = loginTime;
        }
    }
}
