# REST Money Transfer 

Overview  
This repository contains the sample code for the following coding challenge : 
Design and implement a RESTful API for money transfer between accounts

Create an approach by complying with the following requirements :

1 - Keep it simple 
2 - Assume the API is invoked by multiple systems and services on behalf of end users
3 - Never use Spring and avoid heavy frameworks. 
4 - Produce an executable as standalone program, without a pre-installed container/server


Technologies : TomEE (Embedded Tomcat), Java EE, JTA, JPA and JAX-RS. 

Open the command prompt/Terminal 
to run the code clone the repository using below command -

	git clone https://github.com/Danco90/codingchallenge.git

## 1. compile the code with Maven to run test and generate executable jar
	mvn clean install
	
## 2. run only unit tests with Maven  
	mvn test

## 3. run application + test with Maven by using embedded Tomcat 
To run the application enter the following command in the project directory

	mvn package tomee:run


# Resource Endpoints for testing REST API with client
Open the command prompt/Terminal and type the following commands according to the
CRUD to be performed or use a rest client. 

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
DELETE http://localhost:8080/restmoneytx/api/bankAccounts/{id}
DELETE (ALL) http://localhost:8080/restmoneytx/api/bankAccounts

	curl -X "DELETE" http://localhost:8080/restmoneytx/api/bankAccounts/{id}
	curl -X "DELETE" http://localhost:8080/restmoneytx/api/bankAccounts
