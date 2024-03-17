# **Product Service**
Product management service.

## **Requirements**
* JDK 17+
* Maven 3
* MySQL
* Redis
* Postman application

## **Prerequisites**
Set these environment variables containing database credentials and user service url(OAUTH server).
`PRODUCT_SERVICE_DB_URL=jdbc:mysql://<host>:3306/<db_name>;
PRODUCT_SERVICE_DB_USERNAME=<db_username>;
PRODUCT_SERVICE_DB_PASSWORD=<db_password>
USER_SERVICE_URL=<oauth_url>`

## **Running the application locally**
`mvn spring-boot:run`

It will run on port 8282

To create jar and execute jar file:
`mvn package`

This will create jar file inside target\e-commerce-1.0.0.jar
To execute jar file:

`java -jar e-commerce-1.0.0.jar`

## **STEPS to obtain OAUTH token**
1. Open Postman application. 
2. Under Authorization tab set as "OAuth 2.0"
3. Set below props: 
   Grant Type - Authorization Code
   Callback url - https://oauth.pstmn.io/v1/callback
   Auth url - <user_service_url>/oauth2/authorize
   Access token url - <user_service_url>/oauth2/token
   Client ID : oidc-client
   Client secret : secret
   Scope : profile
4. Click on "Get New Access token"
5. Enter username as email id and password of signed up user.
6. Use token to send requests.

## **Endpoints:**
1. To get all products
   GET products?pageNumber=<>&pageSize=<>&sortBy=<>&orderBy=<ASC/DESC>
2. To add product (only ADMIN role)
   POST products
   `{
      "title":"Redmi 11 pro",
      "description" : "latest launch",
      "price" : 2550,
      "quantity" : 10,
      "imageUrl" : "http://image.com",
      "category" : {
         "name" : "Mobile"
      } 
    }`
