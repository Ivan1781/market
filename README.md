# tests
```
curl -X POST -v -H "Content-Type:application/json" -H "Authorization: Bearer token" "http://localhost:8080/users" -d "{\"name\":\"name\"}"
```
```
curl -X GET -v "http://localhost:8080/user"
```
```
curl -X POST -v -H "Content-Type:application/json" http://localhost:8080/api/auth/register -d 
"{\"userName\":\"userName\",\"password\":\"password\"}" 
```
```
curl -X POST -v -H "Content-Type:application/json" http://localhost:8080/api/auth/login -d 
"{\"username\":\"username\",\"password\":\"password\"}" 
```
