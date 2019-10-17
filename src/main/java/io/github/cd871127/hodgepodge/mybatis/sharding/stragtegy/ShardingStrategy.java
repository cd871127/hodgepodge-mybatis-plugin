package io.github.cd871127.hodgepodge.mybatis.sharding.stragtegy;

/**
 * @author anthonychen
 */
public interface ShardingStrategy {
    String sharding(String sql,String table, String[] cols);
}
