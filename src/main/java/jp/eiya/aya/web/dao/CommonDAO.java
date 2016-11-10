package jp.eiya.aya.web.dao;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import jp.eiya.aya.web.dao.base.AbstractDAO;
import jp.eiya.aya.web.dao.manage.Java8LambdaSampleEntityManagerFactoryBean;
import jp.eiya.aya.web.model.dao.Account;
import jp.eiya.aya.web.model.dao.AccountInfo;
import jp.eiya.aya.web.model.dao.PasswordTable;
import jp.eiya.aya.web.model.dao.PaymentStatus;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Named("myDAO")
public class CommonDAO extends AbstractDAO {
    @Inject
    private EntityManager java8LambdaSampleEntityManager;

    public EntityManager getEntityManager() {
        return java8LambdaSampleEntityManager;
    }

    private static <T> Optional<T> onlyOneOf(List<T> expectedSizeOneList){
        if(CollectionUtils.isEmpty(expectedSizeOneList) || expectedSizeOneList.size() > 1 || expectedSizeOneList.get(0) == null){
            return Optional.empty();
        }
        return Optional.of(expectedSizeOneList.get(0));
    }

    /* <Account start> */
    protected List<Account> findAccounts(String query, Map<String,Object> params){
        return find(Account.class,query,params);
    }

    public Optional<Account> findAccount(int accountId) {
        List<Account> accounts = findAccounts("c._id = $_id", ImmutableMap.of("_id",accountId));
        return onlyOneOf(accounts);
    }

    public Optional<Account> findAccount(String accountName) {
        List<Account> accounts = findAccounts(
                "c."+Account.FIELD_ACCOUNT_NAME+" = $"+Account.FIELD_ACCOUNT_NAME,
                ImmutableMap.of(Account.FIELD_ACCOUNT_NAME,accountName)
        );
        return onlyOneOf(accounts);
    }
    /* </Account end> */


    /* <AccountInfo start> */
    protected List<AccountInfo> findAccountInfos(String query, Map<String,Object> params){
        return find(AccountInfo.class,query,params);
    }

    public Optional<AccountInfo> findAccountInfo(int accountId) {
        List<AccountInfo> accountInfos = findAccountInfos(
                "c."+AccountInfo.FIELD_ACCOUNT_ID+" = $" + AccountInfo.FIELD_ACCOUNT_ID,
                ImmutableMap.of(AccountInfo.FIELD_ACCOUNT_ID,accountId)
        );
        return onlyOneOf(accountInfos);
    }

    public Optional<AccountInfo> findAccountInfo_useFK(String accountName) {
        Optional<Account> account = findAccount(accountName);
        if(account.isPresent()){
            return findAccountInfo(account.get().getId());
        }
        return Optional.empty();
    }
    /* </AccountInfo end> */

    /* <PasswordTable start> */
    protected List<PasswordTable> findPasswordTables(String query,Map<String, Object> params){
        return find(PasswordTable.class, query,params);
    }

    public Optional<PasswordTable> findPasswordTable(int accountId){
        return onlyOneOf(
            findPasswordTables(
                "c." + PasswordTable.FIELD_ACCOUNT_ID + " = $" + PasswordTable.FIELD_ACCOUNT_ID,
                ImmutableMap.of(PasswordTable.FIELD_ACCOUNT_ID,accountId)
            )
        );
    }
    /* </PasswordTable end> */

    /* <PaymentStatus start> */
    protected List<PaymentStatus> findPaymentStatuses(String query, Map<String, Object> params){
        return find(PaymentStatus.class,query,params);
    }

    public List<PaymentStatus> findPaymentStatuses(Integer accountId){
        return findPaymentStatuses(
                "c."+PaymentStatus.FIELD_ACCOUNT_ID+" = $"+PaymentStatus.FIELD_ACCOUNT_ID,
                ImmutableMap.of("accountId",accountId)
        );
    }
    public List<PaymentStatus> findPaymentStatuses_useFK(String accountName){
        Optional<Account> account = findAccount(accountName);
        if(account.isPresent()){
            return findPaymentStatuses(account.get().getId());
        }
        return ImmutableList.of();
    }
    /* </PaymentStatus end> */
}
