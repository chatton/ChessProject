# https://gist.github.com/monkut/c4c07059444fd06f3f8661e13ccac619
FROM ubuntu:16.04

# Install python 3.6
RUN apt-get update
RUN apt-get install -y software-properties-common vim
RUN add-apt-repository ppa:jonathonf/python-3.6
RUN apt-get update

RUN apt-get install -y build-essential python3.6 python3.6-dev python3-pip python3.6-venv
RUN apt-get install -y git

RUN python3.6 -m pip install pip --upgrade
RUN python3.6 -m pip install wheel
# requests module required for the bot script.
RUN python3.6 -m pip install requests

# Install Java 8
# https://github.com/dockerfile/java/blob/master/oracle-java8/Dockerfile
RUN \
  echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
  add-apt-repository -y ppa:webupd8team/java && \
  apt-get update && \
  apt-get install -y oracle-java8-installer && \
  rm -rf /var/lib/apt/lists/* && \
  rm -rf /var/cache/oracle-jdk8-installer


# Define working directory.
WORKDIR /data

# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

# Copy the python bot into the /bot directory
COPY src/main/python/ /bot/

# Copy the
ADD /target/Chess-Online.jar Chess-Online.jar

# expose the port the server runs on to be visible
EXPOSE 8080

CMD ["java", "-jar", "Chess-Online.jar", "--db-ip", "db", "--db-pass", "password", "--bot-path", "/bot/chess-bot/chessbot.py"]