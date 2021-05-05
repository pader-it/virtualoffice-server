# Kotlin server for Virtual Office

## Requires

- Kotlin 1.4.32
- Gradle 6.8.3

## Build

First, create the gradle wrapper script:

```
gradle wrapper
```

Then, run:

```
./gradlew check assemble
```

## Running

To start the service, run
```
java -jar ./build/libs/virtualoffice-server.jar
```

## Testing
Simple tests can be done via console.  
Authentication:  
```
TOKEN=$(curl -X POST --header "Content-Type: application/json" --data '{"username":"pader","password":"it"}' http://localhost:8080/login)
```  
Hello World:  
```
curl -X GET -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" http://localhost:8080/hello
```  
User Information:  
```
curl -X GET -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" http://localhost:8080/userinfo
```
