FROM openjdk:8u342-jdk-oracle
FROM maven:3.8.6-openjdk-8-slim

MAINTAINER wyler "1102214883@qq.com"


# 添加 Spring Boot 包
ADD . /cloud

ADD start.sh /cloud/start.sh
RUN chmod +x /cloud/start.sh

#设置镜像对外暴露端口
EXPOSE 3000

# 执行启动命令
ENTRYPOINT ["./cloud/start.sh"]