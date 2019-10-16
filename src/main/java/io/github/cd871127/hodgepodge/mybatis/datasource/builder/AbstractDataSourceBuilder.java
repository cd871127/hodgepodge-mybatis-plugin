package io.github.cd871127.hodgepodge.mybatis.datasource.builder;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.sql.DataSource;
import java.util.Map;

@Data
@Accessors(chain = true)
public abstract class AbstractDataSourceBuilder implements DataSourceBuilder {
    private String driver;
    private String url;
    private String username;
    private String password;

    protected abstract DataSource init();

    @Override
    public DataSource build(Map<String, Object> config) {
        this.setDriver(String.valueOf(config.get("driver"))).setPassword(String.valueOf(config.get("password")))
                .setUrl(String.valueOf(config.get("url"))).setUsername(String.valueOf(config.get("username")));
        return init();
    }
}
