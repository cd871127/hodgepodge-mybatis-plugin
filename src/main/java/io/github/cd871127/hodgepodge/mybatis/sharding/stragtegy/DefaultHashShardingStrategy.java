package io.github.cd871127.hodgepodge.mybatis.sharding.stragtegy;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author anthonychen
 */
public class DefaultHashShardingStrategy implements ShardingStrategy {
    private final int tableNum = 2;

    @Override
    public String sharding(String sql, String table, String[] cols, Map<String, Object> paraMap) {
        int hash = 0;
        for (String col : cols) {
            int tmp = col.hashCode() % 2;
            hash = tmp % 2;
        }
        ++hash;
        String shardingTableName = table + "_" + StringUtils.leftPad(String.valueOf(hash), 2, '0');

        sql = sql.replaceAll(table, shardingTableName);
        return sql;
    }
}
