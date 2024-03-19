# Challenge Backend By AmeDigital
This is my resolution of the [challenge](https://github.com/AmeDigital/challenge-back-end-hit) promoted by [Ame Digital](https://github.com/AmeDigital)

## Clone this repo
```shell
git clone https://github.com/lemuelsousa/amedigital-challenge-backend.git
cd amedigital-challenge-backend
```

## Building
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

## Routes
The application is available in 8080 port.
The following examples are made using [HTTPie cli](https://httpie.io/cli) <br>
**Create a planet:**
```shell
http POST :8080/api/planet name="Polis Massa" climate="artificial temperate" terrain="airless asteroid"
```
**Search planet by name:**
```shell
http GET ':8080/api/planet/search?name=Polis Massa'
```
output:
```json
{
"climate": "artificial temperate",
"filmAppearances": 1,
"id": 1,
"name": "Polis Massa",
"terrain": "airless asteroid"
}
```

**Search planet by id:**
```shell
http GET :8080/api/planet/1
```
output:
```json
{
"climate": "artificial temperate",
"filmAppearances": 1,
"id": 1,
"name": "Polis Massa",
"terrain": "airless asteroid"
}
```
**List all planets:**
```shell
http GET :8080/api/planet/list
```
output:
```json
[
  {
    "climate": "artificial temperate",
    "filmAppearances": 1,
    "id": 1,
    "name": "Polis Massa",
    "terrain": "airless asteroid"
  }
]
```

**Delete planet by id:**
```shell
http DELETE :8080/api/planet/1
```