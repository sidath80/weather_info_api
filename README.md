# Spring Boot Weather API

This endpoint allows clients to retrieve information about the current weather.

* @Header param api-key

* @Request param country
* @Request param city

* @return A Json with city country and describing today's weather.

## Table of Contents

- [Introduction](#introduction)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Testing](#testing)
- [Assumption](#assumption)
- [Improvements](#Improvements)

## Introduction

* **Protocol and Host**: `http GET` with `localhost:8088`
* **Path**: - `/v1/weather`
* **Header Parameter**: - `api-key=abcd-001`
* **Query Parameters**:
    - `country=uk`
    - `city=london`
* **Response**:

  Success response
  ```code
     {
      "city": "london",
      "country": "uk",
      "description": "scattered clouds"
     }
  ```

  Error response
  ```code
  {
  "errorCode": 400,
  "errorMessage": "apiKey is invalid"
  }
  ```

## Prerequisites

For building and running the application you need,
  * [JDK11] (http://www.oracle.com/technetwork/java/javase/downloads)
  * [GIT] (https://git-scm.com/downloads)

## Getting Started

### Building the application locally

Following command will run Unit tests, Integration tests, MVC tests, Generate client code and Build the application in locally

```shell
1 Download the code : git clone https://github.com/sidath80/weather_info_api.git
2 Build in local : ./gradlew clean build OR gradlew clean build

```

### Running the application locally

```shell
./gradlew bootRun OR gradlew bootRun
```
You can find the Open API Specification here :
* http://localhost:8088/swagger-ui/index.html

## Configuration

All configuration are in the application.yml

* weather.endpoint: https://api.openweathermap.org/data/2.5/weather?q=
* api.key: API_KEY

## Testing

* #### Manual Testing
    You can find the Open API Specification here for the browser testing.
    * http://localhost:8088/swagger-ui/index.html
    - Following keys are valid for access this API
        * abcd-001
        * abcd-002
        * abcd-003
        * abcd-004
        * abcd-005
      
* #### API Automate Testing
    - WeatherControllerMvcTest has all automate tests for this API.
  
* #### Integration Testing
  - WeatherRepositoryComponentTest has all integration tests with downstream API.
  
* #### Unit Testing

## Assumption

* I assume that API key generation and validation should be a simple implementation So it is stored in the application.yml.
* Too many requests only count when request parameter validation is passed.
* H2 DB is used to save weather information, and it is expected to be used for future data analytics

## Improvements

* In production API-KEY should implement using JWT and validating via API gateway. (KONG)



