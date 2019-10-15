package io.github.cd871127.hodgepodge.mybatis.test.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {

    @Insert("insert into t_test(id) value(#{id})")
    int insert(String id);


}
