# 使用 OpenJDK 8 基础镜像
FROM openjdk:19

# 暴露 8080 端口
EXPOSE 8080

# 定义 JAR_FILE 参数，默认值为你打包的 JAR 文件名
ARG JAR_FILE=Oastore-0.0.1-SNAPSHOT.jar

# 将构建的 JAR 文件添加到容器中，并重命名为 docker-test-demo-1.jar
ADD target/${JAR_FILE} /Oastore.jar

# 设置启动命令
ENTRYPOINT ["java", "-jar", "/Oastore.jar"]