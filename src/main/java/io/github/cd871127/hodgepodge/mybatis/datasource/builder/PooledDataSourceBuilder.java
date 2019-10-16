package io.github.cd871127.hodgepodge.mybatis.datasource.builder;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.datasource.pooled.PooledDataSource;

/**
 * @author anthonychen
 */
public class PooledDataSourceBuilder extends AbstractDataSourceBuilder {
    @Override
    protected PooledDataSource init() {
        return new PooledDataSource(getDriver(), getUrl(), getUsername(), getPassword());
    }
}
