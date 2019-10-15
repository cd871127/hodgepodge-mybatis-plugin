package io.github.cd871127.hodgepodge.mybatis.datasource;

public class MultiDataSource extends AbstractMultiDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }
}
