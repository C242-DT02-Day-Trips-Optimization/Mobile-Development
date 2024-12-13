# Roamio (Day Trips Optimization)

![App Logo](https://i.postimg.cc/yY7zrGT8/1.png) <!-- Replace with your app logo URL -->

## Table of Contents
- [Description](#description)
- [Features](#features)
- [Preview](#preview)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Figma Design](#figma-design)

## Description
Roamio (Day Trips Optimization) is an Android application designed to help users plan their day trips efficiently. The app provides clustering recommendations based on user-defined destinations, optimizing travel time and enhancing the overall experience. Users can register, log in, and view their trip history, making it a comprehensive tool for travel enthusiasts.

## Features
- User authentication (registration and login)
- Clustering recommendations for optimal trip planning
- View trip history with detailed information
- Interactive map displaying trip destinations
- Custom input fields for user-defined destinations and timings
- Responsive UI with Material Design components

## Preview
![App Preview](https://your-preview-image-url.com/preview.png) <!-- Replace with your app preview image URL -->

## Technologies Used
- **Kotlin**: Primary programming language for Android development
- **Retrofit**: For API calls
- **Room**: For local database management
- **Google Maps API**: For displaying maps and locations
- **Coroutines**: For asynchronous programming
- **Material Design**: For UI components

## Installation
To run this project locally, follow these steps:

1. Clone the repository:
   [https://github.com/C242-DT02-Day-Trips-Optimization/Mobile-Development.git](https://github.com/C242-DT02-Day-Trips-Optimization/Mobile-Development.git)
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Build and run the application on an Android device or emulator.

## Usage
1. Launch the app and register a new account or log in with existing credentials.
2. Navigate to the home screen to start planning your trip.
3. Enter the province and destinations, then set the start and end times for your trip.
4. View the recommended trip plan and details in the trip result screen.
5. Access your trip history from the history section.

## API Endpoints
- **User  Registration**: `POST /user/register`
- **User  Login**: `POST /user/login`
- **Get User Details**: `GET /user/get_user/{userId}`
- **Clustering Recommendations**: `POST /cluster/`
- **Get Recommendations**: `POST /recommend/`

## Figma Design
You can view the design mockups for this application on Figma: [Figma Design Link](https://www.figma.com/design/nUVSpYEs1aqUGbLmz4xVPq/C242-DT02-%7C-Capstone-Project-Bizzagi?node-id=22-8&t=XrUZfGiIubuDSwL8-1) <!-- Replace with your Figma link -->
