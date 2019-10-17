package io.github.cd871127.hodgepodge.mybatis.transaction;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.managed.ManagedTransaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MultiDataSourceManagedTransaction extends ManagedTransaction {
    public MultiDataSourceManagedTransaction(Connection connection, boolean closeConnection) {
        super(connection, closeConnection);
    }

    public MultiDataSourceManagedTransaction(DataSource ds, TransactionIsolationLevel level, boolean closeConnection) {
        super(ds, level, closeConnection);
    }

    @Override
    public Connection getConnection() throws SQLException {
        super.close();
        Connection con= super.getConnection();
        return con;
    }
}
