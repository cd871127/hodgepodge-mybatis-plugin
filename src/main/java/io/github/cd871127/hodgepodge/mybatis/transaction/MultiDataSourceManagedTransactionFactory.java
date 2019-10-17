package io.github.cd871127.hodgepodge.mybatis.transaction;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;

public class MultiDataSourceManagedTransactionFactory extends ManagedTransactionFactory {

    private boolean closeConnection = false;

    @Override
    public Transaction newTransaction(Connection conn) {
        return new MultiDataSourceManagedTransaction(conn, closeConnection);
    }

    @Override
    public Transaction newTransaction(DataSource ds, TransactionIsolationLevel level, boolean autoCommit) {
        // Silently ignores autocommit and isolation level, as managed transactions are entirely
        // controlled by an external manager.  It's silently ignored so that
        // code remains portable between managed and unmanaged configurations.
        return new MultiDataSourceManagedTransaction(ds, level, closeConnection);
    }
}
