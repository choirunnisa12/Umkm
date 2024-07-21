# UMKM CRUD Application
## Description

This UMKM application is designed to help small businesses manage their finances and product stock. It also records purchase transactions, reduces product stock, and   increases the seller's wallet balance whenever a purchase is made.

## API

- **Product**:
    - Create: Add a new product.
    - Get All: show all products
    - GetById: View details of a specific product.
    - Update: Modify an existing product.
    - Delete: Remove a product.
    - lowstock : notify when product is low stock 
    - search : search products with id
    - expiring: Track and notify  products nearing their expiration date.

- **Wallet**:
    - Create: Add a new Wallet.
    - Get All: show all Wallet
    - GetById: View details of a specific Wallet.
    - Update: Modify an existing Wallet.
    - Delete: Remove a Wallet.

- **Transaction**:
    - Create: Add a new Transaction.
    - Get All: show all Transactions
    - Update: Modify an existing Transaction.
    - Delete: Remove a Transaction.

## using technology
- **Java Springboot**
- **Spring IoC**
- **Java Stream**
- **Native SQL Query**

## API

### Product

#### 1. Create Product

- **Request**:
    - **Method**: POST
    - **Endpoint**: `/products`
    - **Body**:
      ```json
      {
        "name": "Indomie Goreng",
        "price": 3000,
        "stock": 1,
        "expireDate": "2025-12-17"
      }
      ```

- **Response**: 
- - **Body**:
      ```json
      {
        "data": {
        "id": 18,
        "name": "Bengbeng",
        "price": 2500,
        "stock": 20,
        "expireDate": "2025-12-17"
    },
    "message": "Product created successfully"
      }
      ```

#### 2. Get All Products

- **Request**:
    - **Method**: GET
    - **Endpoint**: `/products`

- **Response**:
    - **Status**: 200 OK
    - **Body**:
      ```json
      {
        "data": [
        {
            "id": 1,
            "name": "Indomie Soto Lamongan",
            "price": 3000,
            "stock": 5,
            "expireDate": "2024-10-17"
        }
      ],
      "message": "Products retrieved successfully"
      }
      ```

#### 3. Get Product By ID

- **Request**:
    - **Method**: GET
    - **Endpoint**: `/products/{id}`

- **Response**:
    - **Status**: 200 OK
    - **Body**:
      ```json
      {
        "data": {
        "id": 1,
        "name": "Indomie Soto Lamongan",
        "price": 3000,
        "stock": 5,
        "expireDate": "2024-10-17"
      },
      "message": "Product retrieved successfully"
      }
      
      ```

#### 4. Update Product

- **Request**:
    - **Method**: PUT
    - **Endpoint**: `/products/{id}`
    - **Body**:
      ```json
      {
        "name": "Indomie Soto Lamongan",
      "price": 2900,
      "stock": 10,
      "expireDate": "2025-07-20"
      }
      
      ```

#### 5. Delete Product

- **Request**:
    - **Method**: DELETE
    - **Endpoint**: `/products/{id}`

- **Response**:
    - **Status**: 200 OK
    - **Body**:
      ```json
      {
        "message": "Product deleted successfully"
      }
      ``` 
      #### 6 LowStock

- **Request**:
    - **Method**: GET
    - **Endpoint**: `products/low-stock?stockThreshold=10
      `

- **Response**:
    - **Status**: 200 OK
    - **Body**:
      ```json
      {
      "id": 1, 
      "name": "Indomie Soto Lamongan",
      "price": 2900,
      "stock": 10,
      "expireDate": "2025-07-20"
      },
      {
      "id": 15,
      "name": "Bontea", 
      "price": 3500,
      "stock": 11, 
      "expireDate": "2025-07-20" 
      }
      ```
#### 7 Search

- **Request**:
    - **Method**: GET
    - **Endpoint**: `products/search?name=Bontea
    - **Query Parameters**:
      name: The name of the product to search for. 
  example = bontea

- **Response**:
    - **Status**: 200 OK
    - **Body**:
      ```json
      [
      {
      "id": 15,
      "name": "Bontea", 
      "price": 3500,
      "stock": 11, 
      "expireDate": "2025-07-20" 
      }
      ]
      ```

#### 8 Expiring

- **Request**:
    - **Method**: GET
    - **Endpoint**: `products/expiring
    - **Query Parameters**:
      thresholdDate (string, required): The date used as the threshold for identifying expiring products. Format: YYYY-MM-DD. 
    - example 2024-12-31
  
- **Response**:
    - **Status**: 200 OK
    - **Body**:
      ```json
      [
      {
      "id": 15,
      "name": "Bontea", 
      "price": 3500,
      "stock": 11, 
      "expireDate": "2025-07-20" 
      }
      ]
      ```
      ```json
      [
      {
      "id": integer,
      "name": string,
      "price": number,
      "stock": integer,
      "expireDate": string
      }
      ]
      
    ### Transaction

#### 1. Create Transaction

- **Request**:
    - **Method**: POST
    - **Endpoint**: `/transactions`
    - **Body**:
      ```json
      {
        "price": 3500,
      "quantity": 1,
      "description": "Bontea", 
      "dateTransaction": "2024-07-22",
      "productId": 15,
      "walletId": 1
      }
      ```

- **Response**:
- - **Body**:
      ```json
      {
    "id": 4,
    "price": 3500,
    "quantity": 1,
    "totalPrice": 3500,
    "description": "Bontea",
    "dateTransaction": "2024-07-21",
    "product": {
        "id": 15,
        "name": "Bontea",
        "price": 3500,
        "stock": 11,
        "expireDate": "2025-07-20"
    },
    "wallet": {
        "id": 1,
        "balance": 8500,
        "name": "Bank BCA"
    }
}
      ```

#### 2. Get All Transaction

- **Request**:
    - **Method**: GET
    - **Endpoint**: `/transactions`

- **Response**:
    - **Status**: 200 OK
    - **Body**:
      ```json
      {
        "id": 1,
        "price": 3000,
        "quantity": 1,
        "totalPrice": 3000,
        "description": "Indomie Goreng",
        "dateTransaction": "2024-07-21",
        "product": {
            "id": 3,
            "name": "Indomie Goreng",
            "price": 3000,
            "stock": 46,
            "expireDate": "2025-12-17"
        },
        "wallet": {
            "id": 1,
            "balance": 8500,
            "name": "Bank BCA"
        }
      ```

#### 3.  Update Transaction

- **Request**:
    - **Method**: PUT
    - **Endpoint**: `/transactions/{id}`
    - **Body**:
      ```json
      {
        "name": "Indomie Soto Lamongan",
      "price": 3000,
      "stock": 10,
      "expireDate": "2025-07-20"
      }
      
      ```

#### 5. Delete Transaction

- **Request**:
    - **Method**: DELETE
    - **Endpoint**: `/transactions/{id}`

- **Response**:
    - **Status**: 200 OK
    - **Body**:
      ```json
      {
        "message": "Transaction deleted successfully"
      }
      ```
