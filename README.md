# Canteen Pro Backend
Java 17 / Spring Boot 3 / Maven / REST API. Uses in-memory collections (no database).

## Run
`mvn spring-boot:run`

## Endpoints
- GET/POST `/api/foods`; GET `/api/foods/{id}`; PUT/DELETE `/api/foods/{id}`; PATCH `/api/foods/{id}/stock?quantity=...`; search `/api/foods/search?q=...`; category `/api/foods/category/{name}`
- POST `/api/users/register`, POST `/api/users/login`; GET/PUT/DELETE `/api/users/{id}`; GET `/api/users`; PATCH `/api/users/{id}/toggle`
- POST `/api/orders` (checkout); GET `/api/orders`; GET `/api/orders/{id}`; GET `/api/orders/customer?email=...`; PATCH status/payment; cancel
- GET/POST `/api/reviews`; food reviews and average rating
- GET `/api/dashboard`

Data is held in memory and resets on restart. This sample login is not production-grade authentication; passwords are not hashed and authorization is not implemented.
