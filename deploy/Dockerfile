# 选择一个 openjdk 镜像并在其基础上构建
FROM eclipse-temurin:19

MAINTAINER NostalFinals

# 安装 dumb-init 来防止服务端错误的接受 STOPSIGNAL
RUN wget -O /usr/bin/dumb-init https://github.com/Yelp/dumb-init/releases/download/v1.2.5/dumb-init_1.2.5_x86_64
RUN chmod +x /usr/bin/dumb-init

WORKDIR /data/
VOLUME /data/

STOPSIGNAL SIGINT

ENTRYPOINT ["/usr/bin/dumb-init", "--"]
CMD ["bash", "-c", "java -jar $JAVA_ARG $JAR_NAME $JAR_ARG"]
