FROM alpine:3.20

WORKDIR /tex

RUN apk add --no-cache \
    perl \
    tar \
    curl \
    xz \
    perl-net-ssleay \
    perl-io-socket-ssl \
    unzip

ADD https://mirror.ctan.org/systems/texlive/tlnet/install-tl-unx.tar.gz install-tl-unx.tar.gz

RUN mkdir install-tl \
    && tar -xzvf install-tl-unx.tar.gz -C install-tl \
    && rm install-tl-unx.tar.gz

RUN cd install-tl/*
    #&& perl ./install-tl --no-interaction

WORKDIR /kotlin

ADD https://github.com/JetBrains/kotlin/releases/download/v2.1.21/kotlin-compiler-2.1.21.zip kotlin-compiler.zip

RUN unzip kotlin-compiler.zip  \
    && rm kotlin-compiler.zip

ENV PATH="/usr/local/texlive/2025/bin/x86_64-linux:$PATH"
ENV PATH="/kotlin/kotlinc/bin:$PATH"

WORKDIR /app

COPY --chmod=+x compile.kts .

ENTRYPOINT ["./compile.kts"]