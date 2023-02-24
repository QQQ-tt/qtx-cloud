FROM openjdk:8u342

# 执行启动命令
ENTRYPOINT ["java","-jar","-XX:ParallelGCThreads=2","-Xmx128m","-Djava.compiler=NONE","/app.jar"]

ARG JAR_FILE

# 添加 Spring Boot 包
ADD target/${JAR_FILE} app.jar

#设置镜像对外暴露端口
EXPOSE 2000

