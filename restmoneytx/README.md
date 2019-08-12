# TomEE Java EE CRUD Rest Starter Project
# Run
To run the application enter the following command in the project directory

$ mvn package tomee:run

# Resource Endpoints

## CREATION
POST http://localhost:8080/restmoneytx/api/bankAccounts

curl -d '{"id":0, "name":"Matteo","number":12345678912, "balance":200.0}' -H "Content-Type: application/json" -X POST http://localhost:8080/restmoneytx/api/bankAccount 
curl -d '{"id":2, "name":"Daniele","number":12345678913, "balance":300.0}' -H "Content-Type: application/json" -X POST http://localhost:8080/restmoneytx/api/bankAccount 

## READ
GET
curl http://localhost:8080/restmoneytx/api/
curl http://localhost:8080/restmoneytx/api/bankAccounts

## DEPOSIT
PUT http://localhost:8080/restmoneytx/api/bankAccounts/deposit/{amount}
Example:
curl -d '{"name":"Matteo","number":12345678913, "balance":1000.0}' -H "Content-Type: application/json" -X PUT http://localhost:8080/restmoneytx/api/bankAccounts/deposit/500.0

## WITHDRAWAL
PUT http://localhost:8080/restmoneytx/api/bankAccounts/withdraw/{amount}/{fee}
Example:
curl -d '{"name":"Matteo","number":12345678913, "balance":1000.0}' -H "Content-Type: application/json" -X PUT http://localhost:8080/restmoneytx/api/bankAccounts/withdraw/500.0/0.5


## MONEY TRANSFER
PUT http://localhost:8080/restmoneytx/api/bankAccounts/transfer
Example:
curl -d '{"senderAccountNumber":12345678912,"receiverAccountNumber":12345678913, "amount":100.0, "fee":2.0}' -H "Content-Type: application/json" -X PUT http://localhost:8080/restmoneytx/api/bankAccounts/withdraw/500.0/0.5


## REMOVAL
DELETE
curl -X "DELETE" http://localhost:8080/restmoneytx/api/bankAccounts/{id}
