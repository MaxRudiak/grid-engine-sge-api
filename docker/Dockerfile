FROM robsyme/docker-sge

RUN add-apt-repository ppa:openjdk-r/ppa
RUN apt-get -y update
RUN apt install -y openjdk-11-jdk
COPY ./build/libs /opt

EXPOSE 8080
EXPOSE 5005
WORKDIR /opt
CMD runuser -l sgeuser -c "java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 /opt/grid-engine-api-1.0-SNAPSHOT.jar"
