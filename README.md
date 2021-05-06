# Address Book Application

## API Document
```http
http://localhost:8080/swagger-ui.html
```
## Build
```mvn
mvn clean package docker:build
```

## Run in Docker
```docker
docker run -p 8080:8080 -t addressbook
```
