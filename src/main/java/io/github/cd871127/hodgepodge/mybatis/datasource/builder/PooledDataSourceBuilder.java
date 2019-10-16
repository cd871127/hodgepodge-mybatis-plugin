package io.github.cd871127.hodgepodge.mybatis.datasource.builder;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.datasource.pooled.PooledDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author anthonychen
 */
@Data
@Accessors(chain = true)
public class PooledDataSourceBuilder implements DataSourceBuilder {
    private String driver;
    private String url;
    private String username;
    private String password;

    public DataSource build() {
        return new PooledDataSource(driver, url, username, password);
    }

    @Override
    public DataSource build(Map<String, Object> config) {
        this.setDriver(String.valueOf(config.get("driver"))).setPassword(String.valueOf(config.get("password")))
                .setUrl(String.valueOf(config.get("url"))).setUsername(String.valueOf(config.get("username")));
        return build();
    }
}
