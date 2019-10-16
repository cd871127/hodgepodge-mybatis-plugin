# hodgepodge-mybatis-plugin

### 功能列表

##### 日志

日志中自动替换参数

##### 多数据源
在Mapper中用`@TargetDataSource`指定dataSource的名字

### 用法
##### 基于Mybatis配置文件
指定type为`io.github.cd871127.hodgepodge.mybatis.datasource.MultiDataSourceFactory`,
```$xml
    <dataSource type="io.github.cd871127.hodgepodge.mybatis.datasource.MultiDataSourceFactory">
        <property name="dataSourceConfig" value="mybatis-datasource.yml"/>
    </dataSource>
```

```$yml
hodgepodge:
  mybatis:
    datasource:
      - name: d1
        defaultDataSource: true
        driver: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://mysql.cd871127.github.io:3306/hodgepodge
        username: xxx
        password: xxx
        dataSourceType: POOL
      - name: d2
        driver: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://mysql.cd871127.github.io:3306/hodgepodge_read
        username: xxx
        password: xxx
        dataSourceType: HIKARI
```
dataSourceType: HIKARI POOL

实现DataSourceBuilder接口，自定义数据源，使用builderClass指定builder实现类，
优先使用dataSourceType指定的类型
            