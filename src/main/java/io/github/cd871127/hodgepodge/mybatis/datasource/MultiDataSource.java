package io.github.cd871127.hodgepodge.mybatis.datasource;

/**
 * @author anthonychen
 */
public class MultiDataSource extends AbstractMultiDataSource {


    private static MultiDataSource multiDataSource;

    static {
        multiDataSource = new MultiDataSource();
    }

    private MultiDataSource() {
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }

    public static MultiDataSource getInstance() {
        return multiDataSource;
    }
}
