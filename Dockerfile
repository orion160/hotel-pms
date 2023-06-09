FROM eclipse-temurin:17-jdk

RUN apt update && apt upgrade -y \
    && apt install -y sudo \
    && groupadd -g 1000 devgroup \
    && useradd -u 1000 -g devgroup -G sudo -m -s /bin/bash dev \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /workspace

RUN chown -R dev:devgroup /workspace

USER dev
