package io.github.cd871127.hodgepodge.mybatis.datasource.builder;

import lombok.Getter;


/**
 * @author anthonychen
 */

public enum DataSourceType {
    /**
     * Mybatis默认数据源
     */
    POOL("io.github.cd871127.hodgepodge.mybatis.datasource.builder.PooledDataSourceBuilder"),
    /**
     *
     */
    HIKARI("io.github.cd871127.hodgepodge.mybatis.datasource.builder.HikariDataSourceBuilder");

    DataSourceType(String builderClass) {
        this.builderClass = builderClass;
    }

    @Getter
    private String builderClass;

}
