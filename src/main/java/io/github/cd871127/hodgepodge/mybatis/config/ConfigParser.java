package io.github.cd871127.hodgepodge.mybatis.config;

import java.util.List;
import java.util.Map;

/**
 * @author anthonychen
 */
public interface ConfigParser {


    /**
     * 从配置文件构造MultiDataSource
     *
     * @param configPath 配置文件位置
     * @return MultiDataSource
     */
    List<Map<String, Object>> parse(String configPath);


}
