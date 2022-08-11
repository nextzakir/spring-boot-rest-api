# spring-boot-rest-api

A REST API using Spring Boot, Spring Data JPA and MySQL.

## Project setup
```
git clone https://github.com/nextzakir/spring-boot-rest-api.git
cd ./spring-boot-rest-api
```

### Update/Install dependencies
```
./mvnw clean install -U
```

### To run the application
```
./mvnw spring-boot:run
```

### To compile and package (.jar) for production
```
./mvnw clean package
```

### To run the packaged application
```
/path/to/java -jar ./target/*.jar
```

## Notes

1. After cloning the repository set up the sql database found in the ```./src/main/resources``` folder and adjust ```application.properties```  connection values accordingly.
2. After running the application it will be available on <http://localhost:8080>.