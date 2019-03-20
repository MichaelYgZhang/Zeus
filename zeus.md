> 笔记
- 进入docker镜像命令: 
```jshelllanguage
docker ps -a : 查看docker运行的容器
docker run -p 9090:9090 prom/prometheus : 运行prometheus 9090 映射本地 9090端口
ea53390c9f76: 表示运行的容器
docker exec -it ea53390c9f76 /bin/sh : 登陆容器ID为ea53390c9f76
```
> 注意: prometheus 修改完配置 prometheus.yml 后需要生成新的images镜像，否则重启，配置消失.
```jshelllanguage
开两个窗口, 1 个把容器启动，第二个修改文件并且执行如下commit
docker commit -m "增加localhost-node-export" -a "michael" 1c444f21c0bb prom/prometheus:v1
```

> TODO : Docker部署prometheus时, 如果同时部署上node-exporter, 则prometheus采集不到node-exporter的数据...