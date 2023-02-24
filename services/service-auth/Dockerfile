FROM openjdk:8u342

ARG JAR_FILE

# 添加 Spring Boot 包
ADD target/${JAR_FILE} auth.jar

#设置镜像对外暴露端口
EXPOSE 2008
EXPOSE 32008

# 执行启动命令
ENTRYPOINT ["java","-jar","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=32008","-XX:ParallelGCThreads=2","-Xmx128m","-Djava.compiler=NONE","/auth.jar"]