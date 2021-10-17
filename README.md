# Relationships App

### Node correlation statistics app

## Description

- API allows to init process for download ZIP file with nodes correlations and parse it to specific data and statistics
- User get specific task ID and this specific task was run asynchronous
- User can check status for specific task
- User can get nodes with correlation list
- User can get statistics information using data created in task
- Provided docker-compose file is providing with ease-startup od application
- built with Java SE 14 and Spring-Boot


## Starting application

#### Requirements

- Maven
- Java Development Kit 14
- Docker

There are two ways to start the application

1. Development mode using internal H2Database

```shell
mvn spring-boot:run
```

2. In production ready environment

```shell
docker-compose up
```

By default application is starting at port 8080.

## API documentation

User is able to check the API documentation and interact with it under the URL when application is started.

```
http://{host}:{port}/doc/ui
```

