package com.yetanotherbank.api.core.service;

import com.yetanotherbank.api.core.component.DatabaseConnector;
import com.yetanotherbank.api.core.dao.Dao;
import com.yetanotherbank.api.core.dao.DaoContext;
import com.yetanotherbank.api.core.dao.impl.MtCurrencyCourseDaoExtended;
import com.yetanotherbank.api.core.model.TransactionData;
import com.yetanotherbank.api.core.model.TransactionLog;
import org.jooq.Record2;
import org.jooq.generated.public_.tables.daos.MtAccountDao;
import org.jooq.generated.public_.tables.daos.MtCurrencyCourseDao;
import org.jooq.generated.public_.tables.daos.MtTransactionDao;
import org.jooq.generated.public_.tables.daos.MtTransactionLogDao;
import org.jooq.generated.public_.tables.pojos.MtAccount;
import org.jooq.generated.public_.tables.pojos.MtCurrencyCourse;
import org.jooq.generated.public_.tables.pojos.MtTransaction;
import org.jooq.generated.public_.tables.pojos.MtTransactionLog;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.UUID;

@Singleton
public class TransactionServiceBean implements TransactionService {

    private DaoContext<MtAccountDao> accountDaoContext;
    private DaoContext<MtTransactionDao> transactionDaoContext;
    private DaoContext<MtTransactionLogDao> transactionLogDaoContext;
    private DaoContext<MtCurrencyCourseDaoExtended> currencyCourseDaoContext;
    private DatabaseConnector databaseConnector;

    @Inject
    public TransactionServiceBean(
            DatabaseConnector databaseConnector,
            @Dao(MtCurrencyCourseDao.class) DaoContext<MtCurrencyCourseDaoExtended> currencyCourseDaoContext,
            @Dao(MtAccountDao.class) DaoContext<MtAccountDao> accountDaoContext,
            @Dao(MtTransactionDao.class) DaoContext<MtTransactionDao> transactionDaoContext,
            @Dao(MtTransactionLogDao.class) DaoContext<MtTransactionLogDao> transactionLogDaoContext) {
        this.accountDaoContext = accountDaoContext;
        this.currencyCourseDaoContext = currencyCourseDaoContext;
        this.transactionDaoContext = transactionDaoContext;
        this.transactionLogDaoContext = transactionLogDaoContext;
        this.databaseConnector = databaseConnector;
    }

    @Override
    public TransactionData deposit(UUID accountId, BigDecimal amount) {
        return databaseConnector.transaction(conf -> {
            MtAccountDao accountDao = accountDaoContext.apply(dao -> dao);
            MtTransactionDao transactionDao = transactionDaoContext.apply(dao -> dao);
            MtTransactionLogDao transactionLogDao = transactionLogDaoContext.apply(dao -> dao);

            MtAccount account = accountDao.findById(accountId);

            MtTransaction transaction = new MtTransaction(
                    UUID.randomUUID(),
                    account.getUserId()
            );

            transactionDao.insert(transaction);

            MtTransactionLog transactionLog = new MtTransactionLog(
                    transaction.getId(),
                    account.getUserId(),
                    account.getId(),
                    amount
            );

            account.setBallance(account.getBallance().add(transactionLog.getAmount()));

            transactionLogDao.insert(transactionLog);

            accountDao.update(account);

            TransactionData transactionResult = new TransactionData(transaction.getId(), transaction.getUserId());

            transactionResult.addTransactionLog(new TransactionLog(transactionResult,
                    transactionLog.getAccountId(), transactionLog.getAmount()));

            return transactionResult;
        });
    }

    @Override
    public TransactionData withdraw(UUID accountId, BigDecimal amount) {
        return databaseConnector.transaction(conf -> {
            MtAccountDao accountDao = accountDaoContext.apply(dao -> dao);
            MtTransactionDao transactionDao = transactionDaoContext.apply(dao -> dao);
            MtTransactionLogDao transactionLogDao = transactionLogDaoContext.apply(dao -> dao);

            MtAccount account = accountDao.findById(accountId);

            MtTransaction transaction = new MtTransaction(
                    UUID.randomUUID(),
                    account.getUserId()
            );

            transactionDao.insert(transaction);

            MtTransactionLog transactionLog = new MtTransactionLog(
                    transaction.getId(),
                    account.getUserId(),
                    account.getId(),
                    amount
            );

            account.setBallance(account.getBallance().subtract(transactionLog.getAmount()));

            transactionLogDao.insert(transactionLog);

            accountDao.update(account);

            TransactionData transactionResult = new TransactionData(transaction.getId(), transaction.getUserId());

            transactionResult.addTransactionLog(new TransactionLog(transactionResult,
                    transactionLog.getAccountId(), transactionLog.getAmount()));

            return transactionResult;
        });
    }

    @Override
    public TransactionData transferMoney(UUID accountOwnerFrom, UUID accountTo, BigDecimal amount) {
        return databaseConnector.transaction(conf -> {
            MtAccountDao accountDao = accountDaoContext.apply(dao -> dao);
            MtTransactionDao transactionDao = transactionDaoContext.apply(dao -> dao);
            MtTransactionLogDao transactionLogDao = transactionLogDaoContext.apply(dao -> dao);
            MtCurrencyCourseDaoExtended currencyCourseDao = currencyCourseDaoContext.apply(dao -> dao);

            MtAccount from = accountDao.findById(accountOwnerFrom);
            MtAccount to = accountDao.findById(accountTo);

            Record2<String, String> convertationCourseId = currencyCourseDao.compositeKeyRecord(
                    from.getCurrencyCode(), to.getCurrencyCode());
            MtCurrencyCourse currencyCourse = currencyCourseDao.findById(convertationCourseId);

            double convertationMultiple = 1;

            if (currencyCourse != null) {
                convertationMultiple = currencyCourse.getCorrection();
            }

            MtTransaction transaction = new MtTransaction(
                    UUID.randomUUID(),
                    from.getUserId()
            );

            BigDecimal amountTo = amount.multiply(BigDecimal.valueOf(convertationMultiple));

            transactionDao.insert(transaction);

            MtTransactionLog transactionLogFrom = new MtTransactionLog(
                    transaction.getId(),
                    from.getUserId(),
                    from.getId(),
                    amount
            );

            from.setBallance(from.getBallance().subtract(transactionLogFrom.getAmount()));

            MtTransactionLog transactionLogTo = new MtTransactionLog(
                    transaction.getId(),
                    to.getUserId(),
                    to.getId(),
                    amountTo
            );

            to.setBallance(to.getBallance().add(transactionLogTo.getAmount()));

            transactionLogDao.insert(transactionLogFrom);
            transactionLogDao.insert(transactionLogTo);

            accountDao.update(from);
            accountDao.update(to);

            TransactionData transactionResult = new TransactionData(transaction.getId(), transaction.getUserId());

            transactionResult.addTransactionLog(new TransactionLog(transactionResult,
                    transactionLogFrom.getAccountId(), transactionLogFrom.getAmount()));

            transactionResult.addTransactionLog(new TransactionLog(transactionResult,
                    transactionLogTo.getAccountId(), transactionLogTo.getAmount()));

            return transactionResult;
        });
    }
}
