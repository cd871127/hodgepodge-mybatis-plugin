package io.github.cd871127.hodgepodge.mybatis.test;

import io.github.cd871127.hodgepodge.mybatis.test.mapper.TestMapper;
import io.github.cd871127.hodgepodge.mybatis.test.mapper.TestVO;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class TestOne extends PluginTest {
    @Test
    public void test() {
        SqlSession sqlSession = getSqlSession();
        try {
            TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
//            testMapper.insert("c",555);
            testMapper.insert2(new TestVO("ddd",333));
        } finally {
            sqlSession.close();
        }
    }
}
