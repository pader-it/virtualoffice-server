# virtualoffice-server

## Testing
curl -X GET http://localhost:8080/
curl -X POST --location "http://localhost:8080" -H "Content-Type: application/x-www-form-urlencoded" -d "username=jetbrains&password=foobar"
curl -X POST --header "Content-Type: application/json" --data '{"username":"pader","password":"it"}' http://localhost:8080/login
curl -X GET --header "Content-Type: application/json" --data '{"token":"keyboardcat"}' http://localhost:8080/userinfo
