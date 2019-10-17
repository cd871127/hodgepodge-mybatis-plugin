package io.github.cd871127.hodgepodge.mybatis.test;

import io.github.cd871127.hodgepodge.mybatis.test.mapper.TestMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class TestOne extends PluginTest {
    @Test
    public void test() {
        SqlSession sqlSession = getSqlSession();
        try {
            TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
            testMapper.insert("d1");
//            testMapper.insert2("d2");
        } finally {
            sqlSession.close();
        }
    }
}
