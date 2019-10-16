package io.github.cd871127.hodgepodge.mybatis.test.mapper;

import io.github.cd871127.hodgepodge.mybatis.annotation.TargetDataSource;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {

    @Insert("insert into t_test(id) value(#{id})")
    @TargetDataSource("d1")
    int insert(String id);

    @Insert("insert into t_test(id) value(#{id})")
    @TargetDataSource("d2")
    int insert2(String id);


}
