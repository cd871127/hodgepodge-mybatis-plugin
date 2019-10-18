package io.github.cd871127.hodgepodge.mybatis.test.mapper;

import lombok.Data;

@Data
public
class TestVO {
    public TestVO() {
    }

    public TestVO(String id, int name) {
    }

    private String id;
    private int name;
}