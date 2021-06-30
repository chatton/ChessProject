FROM openjdk:11

# Install python 3.6.8
ARG PYTHON_VER="3.6.8"
ARG BUILDDIR=/build

RUN apt-get update -qq && \
    apt-get upgrade -y  > /dev/null 2>&1 && \
    apt-get install wget gcc make zlib1g-dev -y -qq > /dev/null 2>&1 && \
    wget --quiet https://www.python.org/ftp/python/${PYTHON_VER}/Python-${PYTHON_VER}.tgz > /dev/null 2>&1 && \
    tar zxf Python-${PYTHON_VER}.tgz && \
    cd Python-${PYTHON_VER} && \
    ./configure  > /dev/null 2>&1 && \
    make > /dev/null 2>&1 && \
    make install > /dev/null 2>&1 && \
    rm -rf ${BUILDDIR}


RUN apt-get update && apt-get install -y \
    python-pip


ADD requirements.txt .
RUN python -m pip install -r requirements.txt

# Copy the python bot into the /bot directory
COPY src/main/python/ /bot/

# Copy the
ADD /target/Chess-Online.jar Chess-Online.jar
# expose the port the server runs on to be visible
EXPOSE 8080
CMD ["java", "-jar", "Chess-Online.jar", "--db-ip", "db", "--db-pass", "password", "--bot-path", "/bot/chess-bot/chessbot.py"]
