package io.github.cd871127.hodgepodge.mybatis.test.mapper;

import io.github.cd871127.hodgepodge.mybatis.annotation.Sharding;
import io.github.cd871127.hodgepodge.mybatis.annotation.Table;
import io.github.cd871127.hodgepodge.mybatis.annotation.TargetDataSource;
import io.github.cd871127.hodgepodge.mybatis.sharding.stragtegy.DefaultHashShardingStrategy;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestMapper {

    @Insert("insert into t_test(id,name) value(#{id},#{name})")
    @TargetDataSource("d1")
    @Sharding(tables = {@Table(cols = {"id"}, strategyClass = DefaultHashShardingStrategy.class, tableName = "t_test")})
    int insert(@Param("id") String pId, @Param("name") int name);

    @Insert("insert into t_test(id,name) value(#{id},#{name})")
    @Sharding(tables = {@Table(cols = {"id"}, strategyClass = DefaultHashShardingStrategy.class, tableName = "t_test")})
    @TargetDataSource("d1")
    int insert2(TestVO testVO);


}


