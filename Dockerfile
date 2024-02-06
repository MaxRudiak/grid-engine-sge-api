FROM robsyme/docker-sge

USER root

RUN apt-get update \
    && apt-get install -y openjdk-8-jdk \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

COPY ./build/libs /opt
EXPOSE 8080
EXPOSE 5005

WORKDIR /opt
CMD runuser -l sgeuser -c "java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 /opt/grid-engine-1.0-SNAPSHOT.jar"