<h1 align="center" style="font-weight: bold;text-align: center">Agreement4 Backend</h1>

<p align="center" style="text-align: center">
  <a href="#tech">Technologies</a> •
  <a href="#started">Getting Started</a> •
  <a href="#routes">API Endpoints</a>
</p>

<p align="center" style="text-align: center">
    <b>Exploring Apache POI for filling placeholders in large enterprise documents ex. [Insert final words]</b>
</p>

<h2 id="technologies">💻 Technologies</h2>

- ![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)
- ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff)
- ![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=fff)

<h2 id="started">🚀 Getting started</h2>
<h3>Prerequisites</h3>

- [Java 17](https://adoptium.net/temurin/releases/?version=17)
- [Docker](https://www.docker.com/products/docker-desktop/)
- [Maven](https://maven.apache.org/download.cgi)

<h3>Starting the Project</h3>

- Clone the project, and run the following command to start the application using Docker Compose:

```bash
docker-compose up --build
```

Alternatively, if you want to run it locally:
- Execute the following maven command in order to download the necessary dependencies.

```
mvn clean install
```

<h2 id="routes">📍 API Endpoints</h2>

There's only a single endpoint available at the moment, which is the
http://localhost:8080/api/file. Upload the file as a multipart form data and the backend will return the file once everything is filled from the client side. 
With the same formatting and styling as the original file.
