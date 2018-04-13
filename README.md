# Chess Online

This is a group project for 3rd year Computing in Software Development at GMIT.

## Getting Chess Online running locally

### Prerequisites

We recommend using Docker to get Chess Online running locally.

You'll need to have [git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git) and [Docker](https://docs.docker.com/install/) installed before you begin.

### Installing

First clone the repository

```bash
git clone https://github.com/chatton/ChessProject.git
```

navigate into the newly created directory

```bash
cd ChessProject
```

You'll need to build the docker image locally. This step may take 10-15 minutes the first time you run it. Every other time it should only take a few seconds (unless you delete the docker images.)

```bash
docker build -t chess-online .
```

Finally you can set up all the required containers with the command

```bash
docker-compose up
```

if you navigate to localhost:80 you'll have the app running locally.

If you want to clean up the containers, run the command

```bash
docker-compose down
```

### Running PyBot

If you want to run the bot locally, you'll need to have [Python 3.6.5+](https://www.python.org/downloads/) installed.

The bot needs the [requests](http://docs.python-requests.org/en/master/) module as a dependency. So you'll need to install that first.

```bash
pip install requests
```

for instructions on running the bot, see [the Wiki page](https://github.com/chatton/ChessProject/wiki/PyBot);


## Making changes locally

If you want to edit the code and apply your own changes, some additional steps are required. 

### Spring Boot / Java

This application requires a MySQL database running on port 3306 with a database called "chess_db".

If you make any changes to the java code, make sure to re-build the jar. You can use your preferred IDE or run the command

```bash
mvn clean install -DskipTests
```

from the root of the project.

You can find Maven [here](https://maven.apache.org/install.html) if you don't have it installed already.

### React Font End

You'll need to have [yarn](https://yarnpkg.com/lang/en/docs/install/) installed. You'll need to re-build the project any time any changes are made. We recommend running the command

```bash
yarn run build
```

This will watch the project for changes and rebuild the react project each time. The Java server will need to be restarted any time you change these files.

### PyBot

There shouldn't be any additional steps required here. You can edit the code and re-run the script. (The Java server expects the script to behave in a certain way and take certain arguments, so some changes may be required in `ChessService.java` if extensive changes are made to the bot)


## Authors
* Cian Hatton - cianhatton@gmail.com
* Damian Gavin - damotaxo@gmail.com
* Marian Ziacik - majo.z@outlook.com

## License
This project is licensed under the MIT License.
