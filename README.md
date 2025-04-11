# Nutrition Tracker Web App with MongoDB Dashboard

This web application provides a nutrition tracking service with a logging and analytics dashboard. It's designed to work with the Nutrition Tracker Android app and store logging data in MongoDB Atlas.

## Features

- RESTful API endpoint for nutrition information lookup
- MongoDB integration for persistent data storage
- Web-based dashboard with analytics and logs visualization
- Docker containerization for easy deployment
- GitHub Codespaces compatibility

## Architecture

The application consists of the following components:

1. **API Service**: Handles requests from the mobile app, communicates with the Nutritionix API, and logs data to MongoDB
2. **Dashboard**: Web interface displaying analytics and logs from MongoDB
3. **MongoDB Database**: Cloud-hosted database for storing log data

## Prerequisites

- Java 11 or higher
- Maven
- Docker
- MongoDB Atlas account
- Nutritionix API credentials

## Setup

### 1. MongoDB Atlas Setup

1. Create a MongoDB Atlas account at https://www.mongodb.com/cloud/atlas/register
2. Create a new cluster (the free tier is sufficient)
3. Create a database user with read/write permissions
4. Add your IP address to the IP whitelist (or use 0.0.0.0/0 for development)
5. Get your connection string from the Atlas dashboard

### 2. Configure MongoDB Connection

Update the MongoDB connection details in `src/main/java/ds/nutrition/db/MongoDBConnection.java`:

```java
private static final String USERNAME = "your_username";
private static final String PASSWORD = "your_password";
private static final String CLUSTER = "your_cluster.mongodb.net";
private static final String DATABASE_NAME = "your_database_name";
```

### 3. Configure Nutritionix API Credentials

Update the Nutritionix API credentials in `src/main/java/ds/nutrition/api/NutritionixApiClient.java`:

```java
private static final String APP_ID = "your_nutritionix_app_id";
private static final String API_KEY = "your_nutritionix_api_key";
```

## Building and Running Locally

### Using Maven

```bash
mvn clean package
```

This will create a WAR file in the `target` directory.

### Using Docker

Make the build script executable and run it:

```bash
chmod +x build.sh
./build.sh
```

This will:
1. Build the WAR file using Maven
2. Copy it to the root directory as ROOT.war
3. Build the Docker image
4. Provide instructions for running the container

To run the Docker container:

```bash
docker run --rm -it -p 8080:8080 nutrition-tracker
```

The application will be available at http://localhost:8080/

## Deploying to GitHub Codespaces

1. Push your code to a GitHub repository
2. Click on the "Code" button and select "Open with Codespaces"
3. Create a new codespace
4. Once the codespace is ready, run the build script:

```bash
chmod +x build.sh
./build.sh
```

5. Run the Docker container:

```bash
docker run --rm -it -p 8080:8080 nutrition-tracker
```

6. Click on the "Ports" tab to find the URL for accessing your application

## API Documentation

### POST /api/nutrition

Endpoint for retrieving nutrition information for a food query.

**Request Body:**

```json
{
  "query": "1 apple and 2 bananas",
  "deviceModel": "Pixel 6",
  "timestamp": "2023-04-09T12:34:56Z",
  "requestId": "abc123"
}
```

**Response:**

```json
{
  "foods": [
    {
      "foodName": "apple",
      "calories": 95,
      "totalFat": 0.3,
      "totalCarbohydrate": 25,
      "protein": 0.5,
      "timestamp": "2023-04-09T12:34:56Z",
      "deviceModel": "Pixel 6",
      "requestId": "abc123"
    },
    {
      "foodName": "banana",
      "calories": 105,
      "totalFat": 0.4,
      "totalCarbohydrate": 27,
      "protein": 1.3,
      "timestamp": "2023-04-09T12:34:56Z",
      "deviceModel": "Pixel 6",
      "requestId": "abc123"
    }
  ],
  "requestId": "abc123",
  "timestamp": "2023-04-09T12:34:56Z",
  "deviceModel": "Pixel 6",
  "apiResponseTime": 345
}
```

## Dashboard

The dashboard is available at the root URL of the application. It displays:

1. Top searched foods
2. Average API response time
3. Top device models
4. Average calories per request
5. Detailed logs of all requests

## Author

Ashay Koradia
