package io.github.cd871127.hodgepodge.mybatis.datasource.builder;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author anthonychen
 */
public interface DataSourceBuilder {

    /**
     * @param config
     * @return
     */
    DataSource build(Map<String, Object> config);
}
