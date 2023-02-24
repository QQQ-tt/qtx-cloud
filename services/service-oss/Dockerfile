FROM openjdk:8u342

ARG JAR_FILE

# 添加 Spring Boot 包
ADD target/${JAR_FILE} oss.jar

#设置镜像对外暴露端口
EXPOSE 2001
EXPOSE 32001

# 执行启动命令
ENTRYPOINT ["java","-jar","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=32001","-XX:ParallelGCThreads=2","-Xmx128m","-Djava.compiler=NONE","/oss.jar"]