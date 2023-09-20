# Spring Boot Weather API

This endpoint allows clients to retrieve information about the current weather.

* @param Country
* @param City
* @param Api Key
* @return A json with city country and describing today's weather.

## Table of Contents

- [Introduction](#introduction)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Testing](#testing)
- [Assumption](#assumption)
- [Improvements](#Improvements)

## Introduction

1. **Protocol and Host**:
    - `http GET`
    - `localhost:8088`
2. **Path**:
    - `/v1/weather`
3. **Query Parameters**:
    - `country=uk`
    - `city=london`
    - `apiKey=abcd`
4. **Response**:
```code
   {
    "city": "london",
    "country": "uk",
    "description": "scattered clouds"
   }
```

## Prerequisites

For building and running the application you need:
-[JDK11](http://www.oracle.com/technetwork/java/javase/downloads)

## Getting Started

### Building the application locally

Following command will run unit tests, generate client code and build the application locally

```shell
git clone https://github.com/sidath80/weather_info_api.git
./gradlew clean build OR gradlew clean build

```

### Running the application locally

```shell
./gradlew bootRun OR gradlew bootRun
```

## Configuration

All configuration are in the application.yml

* weather.endpoint: https://api.openweathermap.org/data/2.5/weather?q=
* api.key: API_KEY

## Testing

You can just type following URL in the Browser

* http://localhost:8088/v1/weather?country=uk&city=london&apiKey=abcd

- Following keys are valid for access this API
    * abcd
    * efgh
    * ijkl
    * mnop
    * qrst

## Assumption

1. I assume that API key generation and validation should be a simple implementation.
2. As a result, I did not incorporate any complex implementation and instead conducted manual testing.
3. A background thread is running to reset the key count.
4. H2 DB is used to save weather information, and it is expected to be used for future data analytics
5. It is not clear with this statements,
   The API will query the data from H2

## Improvements

1. Need to write integration tests for WeatherRepository class by having mock
   server (https://www.npmjs.com/package/stubby) using docker.
2. We store API keys in server memory, which poses a limitation on scalability. To address this issue, we can enhance
   our system by implementing a caching mechanism, such as Redis


