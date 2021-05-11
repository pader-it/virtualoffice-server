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

## REST API

| REST | URL | Description | JSON |
| --- | --- | --- | --- |
| GET | `/hello` | hello world | |
| POST | `/login` | login user| {"username":name, "password":password} |
| POST | `/register` | register new user | requires {"username":name, "password":password, "name":fullname, "email":email} |
| GET | `/userinfo` | return name of logged in user | |
| GET | `/office` | return list of offices | [1, 2, 3] |
| GET | `/office/{officeid}` | return office info | {"id":id, "members":list} |
| POST | `/office/{officeid}/enter` | add user to office | |
| GET | `/office/{officeid}/leave` | remove user from office| |
| GET | `/tasks` | return list of tasks | [1, 2 ,3] |
| POST | `/tasks/create` | create a new task | requires {"name":name} |
| GET | `/tasks/{taskid}` | get task info | {"id":id,"name":name,"creator":creatorid,"creationTime":time,"assigned":idList,"status":status} | 
| POST | `/tasks/{taskid}/assign` | assign user to task | |
| POST | `/tasks/{taskid}/diassign` | dissasign user from task | |
| POST | `/tasks/{taskid}/close` | close task | |

## Testing
Simple tests can be done via console.  
Registration:  
```
curl -X POST --header "Content-Type: application/json" --data '{"username":"oliver","password":"veryhard"}' http://localhost:8080/register
```  
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
Office list:  
```
curl -X GET -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" http://localhost:8080/office
```
Enter Office:  
```
curl -X POST -I -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" http://localhost:8080/office/${OfficeID}/enter
```
Leave Office:  
```
curl -X POST -I -H "Content-Type: application/json" -H "Authorization: Bearer ${TOKEN}" http://localhost:8080/office/${OfficeID}/leave
```
