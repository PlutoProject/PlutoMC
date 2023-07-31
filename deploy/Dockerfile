# 选择一个 openjdk 镜像并在其基础上构建
FROM eclipse-temurin:19

# 安装 dumb-init 来防止服务端错误的接受 STOPSIGNAL
RUN wget -O /usr/bin/dumb-init https://github.com/Yelp/dumb-init/releases/download/v1.2.5/dumb-init_1.2.5_x86_64
RUN chmod +x /usr/bin/dumb-init

# && mkdir /usr/bin/dumb-init && chmod o+x /usr/bin/dumb-init
# 新建用户，运行时需要映射宿主上 uid 为 1000 的用户到容器以防止无法操作挂载的卷
RUN groupadd --gid 1000 server \
  && useradd --uid 1000 --gid server --shell /bin/bash --create-home server

# 指定运行使用的用户
USER server

MAINTAINER NostalFinals

WORKDIR /data/
VOLUME /data/

STOPSIGNAL SIGINT

ENTRYPOINT ["/usr/bin/dumb-init", "--"]
CMD ["bash", "-c", "java -jar $JAVA_ARG $JAR_NAME $JAR_ARG"]
