package io.github.cd871127.hodgepodge.mybatis.config;

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
    HIKARI("");

    DataSourceType(String builderClass) {
        this.builderClass = builderClass;
    }

    @Getter
    private String builderClass;

}
