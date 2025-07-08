🌤️ Weather Info for Pincode

A Spring Boot REST API that fetches and stores weather data for a given Indian pincode and date, using the OpenWeatherMap API. Data is cached in a relational database for optimized subsequent access.

✅ Features

🌐 Fetch weather data from OpenWeatherMap API

📍 Convert Indian Pincode to Latitude/Longitude

💾 Persist pincode and weather data in PostgreSQL

⚡ Optimized API calls (caching logic)

📮 RESTful interface (Test via Postman or Swagger)

🧪 Unit Tested (TDD-aligned)

📦 Tech Stack
Java 21

Spring Boot 3.5.3

Spring Web, JPA, WebFlux

PostgreSQL

OpenWeatherMap API

JUnit 5

🚀 Getting Started

Prerequisites

Java 21

Maven

PostgreSQL (or H2 for testing)

OpenWeatherMap API Key

🔧 Setup

Clone the repo

git clone https://github.com/your-username/weather-info-for-pincode.git

cd weather-info-for-pincode

Configure application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/weatherdb

spring.datasource.username=your_db_username

spring.datasource.password=your_db_password

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

openweather.api.key=your_openweather_api_key

Run the application

./mvnw spring-boot:run

📘 API Documentation

🌦️ Get Weather Info

Endpoint:

GET /weather?pincode={pincode}&for_date={yyyy-MM-dd}

Example:

GET /weather?pincode=226101&for_date=2025-07-07

Query Params:

Param	Type	Required	Description

pincode	String	✅ Yes	Indian postal code

for_date	String	✅ Yes	Date in yyyy-MM-dd format

✅ Sample Response (JSON)

json
{
  "pincode": "226101",
  "date": "2025-07-07",
  "description": "light rain",
  "temperature": 30.2,
  "humidity": 78.0
}

📦 How It Works

Check DB if weather data exists for pincode + date.

If yes → return cached data.

If no → fetch coordinates via OpenWeather Geo API.

Then → fetch weather using OpenWeather Current Weather API.

Save and return the response.

🧪 Running Tests

Tests include:

Unit tests for WeatherService logic

Mocked OpenWeatherClient responses

📫 API Key Registration

Go to: https://openweathermap.org/api

Sign up and verify your email

Go to "API Keys" tab and copy your key

Add it to your application.properties:

openweather.api.key=your_api_key_here

🔍 Example Postman Request

GET

http://localhost:8080/weather?pincode=226101&for_date=2025-07-07



Content-Type: application/json

📁 Project Structure


src/main/java/com/example/weather_info_for_pincode

├── controller

│   └── WeatherController.java

├── service

│   └── WeatherService.java

├── client

│   └── OpenWeatherClient.java

├── entity

│   ├── PincodeInfo.java

│   └── WeatherInfo.java

├── dto

│   ├── WeatherResponseDTO.java

│   ├── OpenWeatherResponse.java

│   └── ZipResponse.java

├── repository

│   ├── PincodeInfoRepository.java

│   └── WeatherInfoRepository.java

├── config

│   └── WebClientConfig.java

└── exception

    └── NotFoundException.java


🙌 Author

Utkarsh Singh

📧 utkarsh.singh25167@gmail.com

