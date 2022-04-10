# JHipster generated Docker-Compose configuration

## Usage

Launch all your infrastructure by running: `docker-compose up -d`.

## Configured Docker services

### Service registry and configuration server:

- [JHipster Registry](http://localhost:8761)

### Applications and dependencies:

- storeService (microservice application)
- storeService's postgresql database
- storeService's elasticsearch search engine
- orderService (microservice application)
- orderService's postgresql database
- orderService's elasticsearch search engine
- webStore (gateway application)
- webStore's postgresql database
- webStore's elasticsearch search engine

### Additional Services:

- Kafka
- Zookeeper
- [Keycloak server](http://localhost:9080)
