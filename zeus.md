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
docker commit -m "add node-exporter" -a "michael" prom/prometheus prom/prometheus:v1
```

> TODO : Docker部署prometheus时, 如果同时部署上node-exporter, 则prometheus采集不到node-exporter的数据...
解决办法: ![Docker容器之间通信](https://birdben.github.io/2017/05/02/Docker/Docker%E5%AE%9E%E6%88%98%EF%BC%88%E4%BA%8C%E5%8D%81%E4%B8%83%EF%BC%89Docker%E5%AE%B9%E5%99%A8%E4%B9%8B%E9%97%B4%E7%9A%84%E9%80%9A%E4%BF%A1/)

- 举例:node-export -> prometheus -> grafana  ⚠️注意网络设置:
```jshelllanguage
1. docker run -p 9100:9100 --name node-exporter prom/node-exporter
2. docker run -p 9090:9090 --link node-exporter --name prometheus-v1 6c06cc29a23d  注:6c06cc29a23d是prometheus-v1的IMAGE-ID
3. docker run -p 3000:3000 --link prometheus-v1 --name grafana grafana/grafana
```

- docker ps -a : 显示所有运行的容器
- docker rm CONTAINER ID : 可以删除一个容器(需要先停止)，docker rm $(docker ps -a -q) 删除全部
- docker rmi : 删除images
- docker rmi $(docker images -q) -f  : 删除全部images ⚠️

- 打包prometheus项目为images: `docker build -t sprint-boot-prometheus-demo .` 然后执行docker images 就发现有镜像了, 然后就可以启动项目了

> 关于prometheus和springboot的集成, 核心点注意: prometheus的client的版本号以及springboot(小于2.0)的版本号.

- TODO 解决docker 环境下grafana的变更问题？