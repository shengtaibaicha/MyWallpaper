# 使用官方 OpenJDK 基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制 JAR 文件到容器中
COPY target/MyWallpaper.jar app.jar

# 暴露端口（与 application.properties 中的端口一致）
EXPOSE 8088

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"]