# This is a Standar Implementation of Rest API Authentication with Spring Boot Security

## Implementation of List
- [x] Login with Spring Security and JWT token generated
- [x] JWT Authentication with per http request of API
- [x] User and Roler Relation Deign and Repository and Init Daba
- [x] Method Level access control with indicated Role
- [ ] User and Roler register with a public API

## API Call Testing

- Call a Public API without access control
```
curl -i localhost:8080/greeting
```

- Call a Get API with Authentication
```
curl -i localhost:8080/customers
```

- Call a Post API withAuthentication
```
curl -i -H "Content-Type: application/json" -X POST -d '{              
        "firstName": "Jason",
        "lastName": "Zhong"
}' http://localhost:8080/customers
```

- Call Login API with a non-access User
```
url -i -H "Content-Type: application/json" -X POST -d '{
    "username": "staff",
    "password": "password"
}' http://localhost:8080/login
```

- Call a authentication API with non-access User
```
curl -i  -H "Authorization: Bearer tonken-from-staff-login-response-header" -H "Content-Type: application/json" -X POST -d '{
        "firstName": "Jason",
        "lastName": "Zhong"
}' http://localhost:8080/customers
```

- Call Login API with a full access User
```
url -i -H "Content-Type: application/json" -X POST -d '{
    "username": "admin",
    "password": "admin"
}' http://localhost:8080/login
```

- Call Authentication API with a tocken of full access Role of User
```
curl -i  -H "Authorization: Bearer tonken-from-admin-login-response-header" -H "Content-Type: application/json" -X POST -d '{
        "firstName": "Jason",
        "lastName": "Zhong"
}' http://localhost:8080/customers
```

- Call Authentication API with a tocken of full access Role of User
```
curl -i -H "Authorization: Bearer tonken-from-admin-login-response-header" http://localhost:8080/customers
```