package io.github.cd871127.hodgepodge.mybatis.datasource.builder;

import com.zaxxer.hikari.HikariDataSource;

public class HikariDataSourceBuilder extends AbstractDataSourceBuilder {
    @Override
    protected HikariDataSource init() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(getUrl());
        hikariDataSource.setDriverClassName(getDriver());
        hikariDataSource.setPassword(getPassword());
        hikariDataSource.setUsername(getUsername());
        return hikariDataSource;
    }
}
