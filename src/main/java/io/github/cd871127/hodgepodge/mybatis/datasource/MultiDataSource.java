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
        return getCurrentDataSource();
    }

    public static MultiDataSource getInstance() {
        return multiDataSource;
    }


    private static ThreadLocal<String> currentDataSource = new ThreadLocal<>();

    public void setCurrentDataSource(String dataSourceName) {
        currentDataSource.set(dataSourceName);
    }

    public String getCurrentDataSource() {
        return currentDataSource.get();
    }

    public String clear() {
        String dataSourceName = getCurrentDataSource();
        currentDataSource.remove();
        return dataSourceName;
    }
}
