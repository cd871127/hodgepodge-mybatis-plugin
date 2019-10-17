package io.github.cd871127.hodgepodge.mybatis.sharding.stragtegy;

/**
 * @author anthonychen
 */
public class DefaultHashShardingStrategy implements ShardingStrategy {
    @Override
    public String sharding(String sql, String table, String[] cols) {
        sql = sql.replaceAll(table, "t_test_01");
        return sql;
    }
}
