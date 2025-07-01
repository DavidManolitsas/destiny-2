# Destiny 2

A Java SpringBoot 3 microservice that interacts with the Bungie.net APIs to retrieve in game data.

## Quick Start

Run the application with:

```bash
gradle bootRun
```

## Local Setup

**Prerequisite**

- Docker
- Bungie API Key
- MongoDB Compass (optional)

To run this application on your local machine you will need to run an instance of MongoDB.

Pull the latest docker image of Mongo Community Edition with:

```bash
docker pull mongodb/mongodb-community-server:latest
```

Run the MongoDB docker image with: 

```bash
docker run --name mongodb -p 27017:27017 -d mongodb/mongodb-community-server:latest
```

You will also need to obtain an API key from [http://bungie.net](https://bungie-net.github.io).

Then set it as a environment variable with:

```bash
export BUNGIE_API_KEY="PASTE_YOU_API_KEY_HERE"
```
