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

## Setup

### MongoDB Atlas Setup

1. Create a MongoDB Atlas account at https://www.mongodb.com/cloud/atlas/register
2. Create a new cluster (the free tier is sufficient)
3. Create a database user with read/write permissions
4. Add your IP address to the IP whitelist (or use 0.0.0.0/0 for development)
5. Get your connection string from the Atlas dashboard

## How to build and run

### Using Maven

```bash
mvn clean package
```

This will create a WAR file in the `target` directory.

### OR easy way to run directly using Docker

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

Then To run the Docker container:

```bash
docker run --rm -it -p 8080:8080 nutrition-tracker
```

## Author

Ashay Koradia
AndrewId: akoradia
