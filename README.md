# Promo Code API

This project is a RESTful API for managing discount codes for sales or promotions (promo codes).

## Overview

The Promo Code API allows users to create, manage, and apply promo codes to purchases.

Promo codes can be created with specific expiration dates, discount amounts, currencies, and maximum usage limits. 

Users can apply promo codes to products to receive discounts on their purchases.
Purchase is possible with either a promocode or without one.

## API Documentation

### Endpoints

1. **Create a new product**
    - **Method:** `POST`
    - **URL:** `/api/products`
    - **Request Body:**
      ```json
      {
        "name": "Mechanical keyboard",
        "price" : 249.99,
        "currency" : "USD",
        "description": "Some fancy keyboard." (optional)
      }
      ```
    - **Response:**
        - `200 OK`: Returns the ID of the created product.
        - `400 Bad Request`: If the request body is invalid or missing required fields.

2. **Get all products**
    - **Method:** `GET`
    - **URL:** `/api/products`
    - **Response:**
        - `200 OK`: Returns a list of all products.
        - `404 Not Found`: If no products are found.

3. **Update product data**
    - **Method:** `PUT`
    - **URL:** `/api/products/{id}`
    - **Request Body:**
      ```json
      {
        "name": "Mechanical keyboard",
        "price" : 99.99,
        "currency" : "USD",
        "description": "Changed keyboard"
      }
      ```
    - **Response:**
        - `200 OK`: Returns the updated product data.
        - `400 Bad Request`: If the request body is invalid or missing required fields.
        - `404 Not Found`: If the product with the specified ID is not found.

4. **Delete a product by ID**
    - **Method:** `DELETE`
    - **URL:** `/api/products/{id}`
    - **Response:**
        - `204 No Content`: If the product is successfully deleted.
        - `404 Not Found`: If the product with the specified ID is not found.

5. **Create a new promo code**
    - **Method:** `POST`
    - **URL:** `/api/promo`
    - **Request Body:**
      ```json
      {
        "code": "PROMO10euro",
        "expirationDate": "2024-12-31",
        "amount" : "10.00",
        "currency" : "EUR",
        "maximumUses": 10
      }
      ```
    - **Response:**
        - `200 OK`: Returns the code of the created promo code.
        - `400 Bad Request`: If the request body is invalid or missing required fields.

6. **Get all promo codes**
    - **Method:** `GET`
    - **URL:** `/api/promo`
    - **Response:**
        - `200 OK`: Returns a list of all promo codes.
        - `404 Not Found`: If no promo codes are found.

7. **Get promo code details by code**
    - **Method:** `GET`
    - **URL:** `/api/promo/{code}`
    - **Response:**
        - `200 OK`: Returns the details of the promo code.
        ```json 
        {
          "promoCode": {
          "code": "20bucks",
          "expirationDate": "2024-08-01",
          "amount": 20.00,
          "currency": "USD"
        },
          "timesHasBeenUsed": 5
        }
        ```
        - `404 Not Found`: If the promo code with the specified code is not found.

8. **Get promo code details including the number of usages**
    - **Method:** `GET`
    - **URL:** `/api/promo/{code}/details`
    - **Response:**
        - `200 OK`: Returns the details of the promo code including the number of usages.
      ```json
      {
        "promoCode" : {
          "code" : "10dollar$",
          "expirationDate" : "2024-08-01",
          "amount" : "10.00",
          "currency" : "USD"
      },
        "hasBeenUsedTimes" : 3
      }
      ```
      
        - `404 Not Found`: If the promo code with the specified code is not found.

9. **Get all promo code details including the number of usages**
    - **Method:** `GET`
    - **URL:** `/api/promo/details`
    - **Response:**
        - `200 OK`: Returns the list of details of the promo code including the number of usages.
        - `404 Not Found`: If the promo code with the specified code is not found._

10. **Get the discount price by providing a product ID and a promo code**
    - **Method:** `GET`
    - **URL:** `/api/purchases/quotation`
    - **Request Parameters:**
        - `productId`: ID of the product
        - `promoCode`: Promo code to apply (optional)
    - **Response:**
        - `200 OK`: Returns the discount price for the product with or without the promo code.
        - `404 Not Found`: If the product with the specified ID is not found.

11. **Simulate purchase**
    - **Method:** `POST`
    - **URL:** `/api/purchases`
    - **Request Body:**
      ```json
      {
        "productId": 123,
        "promoCode": "PROMO123" (optional)
      }
      ```
    - **Response:**
        - `200 OK`: Returns the details of the purchase.
        - `404 Not Found`: If the product with the specified ID is not found.

11. **Get all purchases**
    - **Method:** `GET`
    - **URL:** `/api/purchases`
    - **Response:**
        - `200 OK`: Returns a list of all purchases.
        - `404 Not Found`: If no purchases are found.

12. **Get a purchase by ID**
    - **Method:** `GET`
    - **URL:** `/api/purchases/{id}`
    - **Response:**
        - `200 OK`: Returns the details of the purchase.
        - `404 Not Found`: If the purchase with the specified ID is not found.

13. **Get the sales report**
    - **Method:** `GET`
    - **URL:** `/api/purchases/report`
    - **Response:**
        - `200 OK`: Returns a sales report including the number of purchases and total value by currency.
        - `404 Not Found`: If no sales report

### Running the Project

To run the project locally, follow these steps:

1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Open a terminal window and run the following command to start the application
    - "mvn clean package" -- compile and create executable jar file using maven
    - "cd target" -- change directory to target directory with executable .jar files
    - "java -jar promocode-api-0.0.1-SNAPSHOT.jar" -- run compiled file
4. Import "src/main/resources/promo_code_api.postman_collection.json" to Postman in order to send example request to the api service.
5. h2 database console is available at "localhost:8080/h2-console"
   - user : "sa"
   - password : "password"
6. Please keep in mind that h2 database loads example data from "src/main/resources/dump.sql" file on startup.
7. By default the api service listens to port 8080
