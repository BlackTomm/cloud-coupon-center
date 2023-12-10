## 参考

1. [sleuth\+zipkin整合rabbitMQ详解](https://www.cnblogs.com/gyyyblog/p/11594412.html)
2. [Spring Cloud 系列之 Sleuth 链路追踪（一）](https://www.cnblogs.com/mrhelloworld/p/sleuth1.html)
3. [Spring Cloud 系列之 Sleuth 链路追踪（二）](https://www.cnblogs.com/mrhelloworld/p/sleuth2.html)



## 安装RabbitMQ

#### 查找镜像

1. 进入docker hub镜像仓库地址：https://hub.docker.com/_/rabbitmq?tab=description ，选择带有 "management" 的版本(包含管理界面)

2. 拉取镜像
    docker pull rabbitmq:3.12-management 

3. 启动 docker start --privileged=true --name rabbitmq -d -p 15672:15672 -p 5672:5672 rabbitmq:3.12-management

    ```
    此方式默认用户名：guest,密码：guest，手动配置参考：
    -d 后台运行容器；
    --name 指定容器名；
    -p 指定服务运行的端口（5672：应用访问端口；15672：控制台Web端口号）；
    -v 映射目录或文件；
    --hostname 主机名（RabbitMQ的一个重要注意事项是它根据所谓的 “节点名称” 存储数据，默认为主机名）；
    -e 指定环境变量；（RABBITMQ_DEFAULT_VHOST：默认虚拟机名；RABBITMQ_DEFAULT_USER：默认的用户名；RABBITMQ_DEFAULT_PASS：默认用户名的密码）
    ```

4.访问rabbitMQ WEB管理界面，地址 http://localhost:15672/#/queues

## 启动 zipkin

### jar包启动

用jar包启动模式，因为配置方便，不用写`docker-compose.yml` 环境配置文件

- 下载jar包，地址 https://search.maven.org/remote_content?g=io.zipkin&a=zipkin-server&v=LATEST&c=exec
- 运行jar包

```
java -jar zipkin-server-2.17.0-exec.jar --zipkin.collector.rabbitmq.addresses=localhost
```

后面的`--zipkin.collector.rabbitmq.addresses=localhost` 就是RabbitMQ的配置，这是默认的

如果要自己指定的用户名和密码可以参考下面的启动命令：

```
java -jar zipkin-server-2.17.0-exec.jar --zipkin.collector.rabbitmq.addresses=localhost --zipkin.collector.rabbitmq.username=guest --zipkin.collector.rabbitmq.password=guest
```

- 可配置的环境变量参考表

| 属性                                         | 环境变量                  | 描述                                                         |
| :------------------------------------------- | :------------------------ | :----------------------------------------------------------- |
| zipkin.collector.rabbitmq.concurrency        | RABBIT_CONCURRENCY        | 并发消费者数量，默认为 1                                     |
| zipkin.collector.rabbitmq.connection-timeout | RABBIT_CONNECTION_TIMEOUT | 建立连接时的超时时间，默认为 60000 毫秒，即 1 分钟           |
| zipkin.collector.rabbitmq.queue              | RABBIT_QUEUE              | 从中获取 span 信息的队列，默认为 zipkin                      |
| zipkin.collector.rabbitmq.uri                | RABBIT_URI                | 符合 RabbitMQ URI 规范 的 URI，例如 amqp://user:pass@host:10000/vhost |

如果设置了URL，则以下属性将被忽略

| 属性                                   | 环境变量            | 描述                                                         |
| :------------------------------------- | :------------------ | :----------------------------------------------------------- |
| zipkin.collector.rabbitmq.addresses    | RABBIT_ADDRESSES    | 用逗号分隔的 RabbitMQ 地址列表，例如 localhost:5672,localhost:5673 |
| zipkin.collector.rabbitmq.password     | RABBIT_PASSWORD     | 连接到 RabbitMQ 时使用的密码，默认为 guest                   |
| zipkin.collector.rabbitmq.username     | RABBIT_USER         | 连接到 RabbitMQ 时使用的用户名，默认为 guest                 |
| zipkin.collector.rabbitmq.virtual-host | RABBIT_VIRTUAL_HOST | 使用的 RabbitMQ virtual host，默认为 /                       |
| zipkin.collector.rabbitmq.use-ssl      | RABBIT_USE_SSL      | 设置为 true 则用 SSL 的方式与 RabbitMQ 建立链接              |

### docker 启动 zipkin

持久到 Mysql，建库表，如库 zipkin，表 https://github.com/openzipkin/zipkin/blob/master/zipkin-storage/mysql-v1/src/main/resources/mysql.sql

zipkin.collector.rabbitmq.addresses本地启动时，需要设置成本地机器 ip，否则连不上 rabbitmq

```
指定mysql 会有问题，提示
java.lang.noclassdeffounderror: javax/sql/rowset/serial/serialexception

docker  run \
--name zipkin-server -d \
--restart=always \
-p 9411:9411 \
-e MYSQL_USER=root \
-e MYSQL_PASS=1234 \
-e MYSQL_HOST=127.0.0.1 \
-e STORAGE_TYPE=mysql \
-e MYSQL_DB=zipkin \
-e MYSQL_TCP_PORT=3306 \
-e zipkin.collector.rabbitmq.addresses=192.168.1.4:5672 \
-e zipkin.collector.rabbitmq.username=root \
-e zipkin.collector.rabbitmq.password=1234 \
openzipkin/zipkin


暂不指定持久化
docker  run \
--name zipkin-server -d \
--restart=always \
-p 9411:9411 \
-e zipkin.collector.rabbitmq.addresses=192.168.1.4:5672 \
-e zipkin.collector.rabbitmq.username=root \
-e zipkin.collector.rabbitmq.password=1234 \
openzipkin/zipkin
```

### 持久化到ES

持久化到`ES`不需要建立什么配置信息，只需要在启动`jar`时指定`ES`地址即可，将脚本中的启动命令修改为：

```shell
docker  run \
--name zipkin-server -d \
-p 9411:9411 \
--restart=always \
-e STORAGE_TYPE=elasticsearch \
-e ES_HOSTS=localhost:9200
openzipkin/zipkin:2.21.7
```

如果是连接ES集群，`--ES_HOSTS`通过逗号分割，比如：`--ES_HOSTS=http://1.1.1.1:9200,http://2.2.2.2:9200`

**连接ES参数：**

| 环境变量                | 描述                                                   |
| ----------------------- | ------------------------------------------------------ |
| ES_HOSTS                | 连接ES地址，多个由`,`分隔。默认为http://localhost:9200 |
| ES_PIPELINE             | 指定span被索引之前的pipeline                           |
| ES_TIMEOUT              | 连接ES的超时时间，单位`ms`。默认10000(10S)             |
| ES_INDEX                | Zipkin持久化所使用的索引。默认`zipkin`                 |
| ES_DATE_SEPARATOR       | Zipkin建立索引的日期分隔符。默认为`-`                  |
| ES_INDEX_SHARDS         | 分片(shard)个数，默认5个                               |
| ES_INDEX_REPLICAS       | 副本(replica)个数，默认1个                             |
| ES_HTTP_LOGGING         | ES的日志级别，BASIC, HEADERS, BODY                     |
| ES_USERNAME/ES_PASSWORD | 登录ES的用户名和密码                                   |



搭建日志检索系统