package io.github.cd871127.hodgepodge.mybatis.sharding.stragtegy;

import java.util.Map;

/**
 * @author anthonychen
 */
public class DefaultRangeShardingStrategy implements ShardingStrategy {
    @Override
    public String sharding(String sql, String table, String[] cols, Map<String, Object> paraMap) {
        return sql;
    }
}
