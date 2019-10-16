package io.github.cd871127.hodgepodge.mybatis.datasource;

import io.github.cd871127.hodgepodge.mybatis.config.ConfigParser;
import io.github.cd871127.hodgepodge.mybatis.config.DataSourceType;
import io.github.cd871127.hodgepodge.mybatis.config.YamlConfigParser;
import io.github.cd871127.hodgepodge.mybatis.datasource.builder.DataSourceBuilder;
import io.github.cd871127.hodgepodge.mybatis.datasource.builder.PooledDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author anthonychen
 */
@Slf4j
public class MultiDataSourceFactory implements DataSourceFactory {

    private final String BUILDER_CLASS = "builderClass";
    private final String DATA_SOURCE_TYPE = "dataSourceType";
    private final String DEFAULT_DATA_SOURCE = "defaultDataSource";

    @Override
    public void setProperties(Properties properties) {
        String configPath = properties.getProperty("datasourceConfig");
        if (StringUtils.isEmpty(configPath)) {
            throw new IllegalArgumentException("Properties datasourceConfig can't be empty!");
        }

        ConfigParser configParser = null;
        if (configPath.endsWith(".yml")) {
            configParser = new YamlConfigParser();
        }
        if (configParser == null) {
            throw new IllegalArgumentException("Datasource config no found!");
        }

        List<Map<String, Object>> configList = configParser.parse(configPath);
        if (CollectionUtils.isEmpty(configList)) {
            throw new IllegalArgumentException("Datasource config no found!");
        }
        setMultiDataSource(configList);
    }

    @Override
    public DataSource getDataSource() {
        return MultiDataSource.getInstance();
    }

    private void setMultiDataSource(List<Map<String, Object>> configList) {
        MultiDataSource multiDataSource = MultiDataSource.getInstance();
        Map<Object, DataSource> targetDataSources = new HashMap<>(configList.size());
        DataSource defaultTargetDataSource = null;
        for (Map<String, Object> configMap : configList) {
            try {
                DataSource dataSource = map2DataSource(configMap);
                targetDataSources.put(configMap.get("name"), dataSource);
                if (configMap.containsKey(DEFAULT_DATA_SOURCE) && "true".equalsIgnoreCase(configMap.get(DEFAULT_DATA_SOURCE).toString())) {
                    if (defaultTargetDataSource != null) {
                        throw new IllegalArgumentException("Too many default Data Source");
                    }
                    defaultTargetDataSource = dataSource;
                    log.info("defaultDataSource: {}", configMap.get("name"));
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        if (targetDataSources.isEmpty()) {
            throw new IllegalStateException("No data source found");
        }
        if (defaultTargetDataSource == null) {
            defaultTargetDataSource = targetDataSources.values().iterator().next();
        }
        multiDataSource.setTargetDataSources(targetDataSources);
        multiDataSource.setDefaultTargetDataSource(defaultTargetDataSource);
        log.info("data sources: {}",targetDataSources.keySet());
    }

    /**
     * 单个数据源解析
     *
     * @param configMap 配置文件的Map
     * @return DataSource
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private DataSource map2DataSource(Map<String, Object> configMap) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        DataSourceBuilder builder;
        if (configMap.containsKey(BUILDER_CLASS)) {
            builder = (DataSourceBuilder) Class.forName(configMap.get(BUILDER_CLASS).toString()).newInstance();
        } else if (configMap.containsKey(DATA_SOURCE_TYPE)) {
            builder = (DataSourceBuilder) Class.forName(DataSourceType.valueOf(configMap.get(DATA_SOURCE_TYPE).toString()).getBuilderClass()).newInstance();
        } else {
            builder = new PooledDataSourceBuilder();
        }

        return builder.build(configMap);
    }
}
