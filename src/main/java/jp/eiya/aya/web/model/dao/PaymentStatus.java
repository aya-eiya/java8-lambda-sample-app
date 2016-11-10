package jp.eiya.aya.web.model.dao;

import jp.eiya.aya.web.model.dao.base.DAOModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PaymentStatus extends DAOModel{
    public static final String FIELD_ACCOUNT_ID = "";
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private Integer accountId;

    public Integer getAccountId() { return accountId; }
    public void setAccountId(Integer accountId) { if(this.accountId==null) { this.accountId = accountId; } }

    public boolean hasPaidTicket_computed() {
        return false;
    }
}
