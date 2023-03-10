FROM openjdk:8u342

MAINTAINER wyler "1102214883@qq.com"

# 添加 Spring Boot 包
ADD service/service-auth/target/service-auth-1.0-SNAPSHOT.jar auth.jar
ADD service/service-oss/target/service-oss-1.0-SNAPSHOT.jar oss.jar
ADD service-gateway/target/service-gateway-1.0-SNAPSHOT.jar gateway.jar

ADD start.sh /start.sh

RUN CHMOD +x /start.sh

#设置镜像对外暴露端口
EXPOSE 3000

# 执行启动命令
ENTRYPOINT ["./start.sh"]