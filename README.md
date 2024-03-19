# Challenge Backend By AmeDigital
<hr>


## Clone the repo
```shell
git clone https://github.com/lemuelsousa/amedigital-challenge-backend.git
cd amedigital-challenge-backend
```

## Building
The application is available in 8080 port
### Via docker compose
**Requirements:** <br>
- Installed [Docker Engine](https://docs.docker.com/engine/) and [docker compose](https://docs.docker.com/compose/)
```shell
docker compose up
```
### Via Gradle
**Requirements:** <br>
- Installed jdk 17 or greater.Recommends installing via [SDKMAN](https://sdkman.io/install) or [ASDF + Java plugin](https://github.com/halcyon/asdf-java) 
- Installed [docker compose](https://docs.docker.com/compose/install/)
```shell
./gradlew bootrun
```