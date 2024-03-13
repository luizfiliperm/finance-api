<h1 align="center" style="font-weight: bold;">Finance-Api 💻</h1>


<p align="center">
 <a href="#tech">Technologies</a> • 
 <a href="#started">Getting Started</a> • 
  <a href="#routes">API Endpoints</a> •
 <a href="#colab">Collaborators</a> •
 <a href="#contribute">Contribute</a>
</p>

<p align="center">
    <b>An API that controls daily income and expenses from a user!</b>
</p>

<h2 id="tech">💻 Technologies</h2>

- Java
- Springboot
- Mysql

<h2 id="started">🚀 Getting started</h2>

<h3>Prerequisites</h3>

- Java Development Kit (JDK)

<h3>Setting Up the Project</h3>

1. Clone the Repository
    ```bash
    git clone https://github.com/luizfiliperm/finance-api.git
    ```
2. Make sure to create a MYSQL database and configure the path, login and password at the [application.properties](https://github.com/luizfiliperm/finance-api/blob/main/src/main/resources/application.properties) file

3. Navigate to the project directory
    ```bash
    cd <project-directory>
    ```

<h3>Running the Project</h3>

1. Build the project
    ```bash
     .\mvnw clean install
    ```
2. Run the application
    ```bash
    .\mvnw spring-boot:run
    ```

<h2 id="routes">📍API Endpoints</h2>
The route pattern is: [localhost:8080/finances/**]()

|                   Route                   |                        Description                        |
|:-----------------------------------------:|:---------------------------------------------------------:|
|       <kbd>POST auth/register</kbd>       |     Registers a new user. [details](#auth-endpoints)      |
|        <kbd>POST auth/login</kbd>         |     Authenticates a user. [details](#login-endpoint)      |
|          <kbd> PUT /users </kbd>          | Updates logged user information. [details](#update-user)  |
|        <kbd> DELETE /users </kbd>         |        Delete logged user. [details](#delete-user)        |
|         <kbd> GET /wallet </kbd>          |       Retrieve wallet info. [details](#user-wallet)       |
|     <kbd> POST /wallet/incomes </kbd>     |      Add a new Income. [details](#income-endpoints)       |
|     <kbd> GET /wallet/incomes </kbd>      |        Find All Incomes.  [details](#all-incomes)         |
|     <kbd> DELETE /wallet/incomes/{id}     |    Delete a Income By Id.   [details](#delete-income)     |
|      <kbd> PUT /wallet/incomes/{id}       |   Update a Income By Id.     [details](#update-income)    |
|    <kbd> POST wallet/categories </kbd>    | Create a new category.     [details](#category-endpoints) |
|    <kbd> GET wallet/categories </kbd>     |    Get All Categories.      [details](#all-categories)    |
|     <kbd> GET /categories/{id} </kbd>     |   Retrieve a category by Id.   [details](#category-id)    |
|     <kbd> PUT /categories/{id} </kbd>     |  Update a category by Id.    [details](#update-category)  |
|   <kbd> DELETE /categories/{id} </kbd>    |  Delete a category by Id.    [details](#category-delete)  | 
|    <kbd> POST /wallet/expenses </kbd>     |  Add a new Expense.        [details](#expense-endpoints)  |
|     <kbd> GET /wallet/expenses </kbd>     |    Find All Expenses.        [details](#all-expenses)     |
| <kbd> DELETE /wallet/expenses/{id} </kbd> |  Delete an Expense By Id.     [details](#delete-expense)  |
|  <kbd> PUT /wallet/expenses/{id} </kbd>   |  Update an Expense By Id.     [details](#update-expense)  |
|          <kbd> GET /admin/users           |    Find All Users.         [details](#admin-endpoints)    |

<h3 id="auth-endpoints">🔒Auth Endpoints</h3>

<h4>Register</h4>

Registers a new user and returns the user data and the JWT Token

- URL: [/auth/register]()
- Method: POST
- Authentication: Not required

Request Body

| Parameter                          | Type    | Description                     | Required |
|:-----------------------------------|---------|---------------------------------|---------:|
| name                               | string  | Name of the user                |      Yes |
| email                              | string  | Email address of the user       |      Yes |
| password                           | string  | Password for the user account   |      Yes |
| personalInformation                | object  | Additional personal information |      Yes |
| personalInformation.phoneNumber    | string  | Phone number of the user        |      Yes |
| personalInformation.birthDate      | string  | Birth date of the user          |      Yes |
| personalInformation.nationality    | string  | Nationality of the user         |      Yes |

<h4 id="login-endpoint">Login</h4>

Registers a new user and returns the user data and the JWT Token

- URL: [/auth/login]()
- Method: POST
- Authentication: Not required

| Parameter                          | Type    | Description                     | Required |
|:-----------------------------------|---------|---------------------------------|---------:|
| email                              | string  | Email address of the user       |      Yes |
| password                           | string  | Password for the user account   |      Yes |

Response (same for register and login)
- **200 OK**
  - **Content:** JSON
  ```json
  {
     "user": {
       "id": 1,
       "name": "user",
       "email": "luiz@example.com",
       "role": "ROLE_USER",
       "personalInformation": {
         "phoneNumber": "123456789",
         "birthDate": "17/04/2004",
         "nationality": "Brazilian"
       },
       "wallet": {
         "balance": 0
       }
     },
     "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdWl6QGV4YW1wbGUuY29tIiwiaWF0IjoxNzEwMzI0MTMwLCJleHAiOjE3MTA0MTA1MzB9.DwKfj34-QXttfAqDtlRIuksuQ8loXIbI7R7O64MkJkA"
  }
  ```
<h4 id="update-user">Update User</h4>

Updates the logged User

- URL: [/users]()
- Method: PUT
- Authentication: Logged

Request Body

| Parameter                       | Type    | Description                       | Required |
|:--------------------------------|---------|-----------------------------------|---------:|
| name                            | string  | Name of the user                  |      Yes |
| currentPassword                 | string  | Current password for verification |      Yes |
| newPassword                     | string  | New password for the user         |      Yes |
| personalInformation             | object  | Additional personal information   |      Yes |
| personalInformation.phoneNumber | string  | Phone number of the user          |      Yes |
| personalInformation.birthDate   | string  | Birth date of the user            |      Yes |
| personalInformation.nationality | string  | Nationality of the user           |      Yes |

Response
- **200 OK**
  - **Content:** Json
  ```Json
  {
     "id": 4,
     "name": "Luiz Filipe",
     "email": "luiz@example.com",
     "role": "ROLE_USER",
     "personalInformation": {
       "phoneNumber": "123456729",
       "birthDate": "17/04/2004",
       "nationality": "Brazilian"
     },
     "wallet": {
       "balance": 0.00
     }
   }
  ```

<h4 id="delete-user">Delete Logged User</h4>

Deletes the currently logged-in user.

- URL: [/users]()
- Method: DELETE
- Authentication: Required

Request Body

This endpoint does not require a request body.

Responses
- **204 No Content**

<h4 id="user-wallet">Get Users Wallet</h4>

Retrieves the wallet balance of the currently logged-in user.

- URL: [/wallet]()
- Method: GET
- Authentication: Required

Request Body

This endpoint does not require a request body.

Responses
- **200 OK**
  - **Content:** JSON
  ```Json
  {
    "balance": 0.00
   }
  ```
<h3 id="income-endpoints">💵Income Endpoints</h3>
<h4>Add Income to Wallet</h4>
Adds a new income to the wallet of the logged-in user.

- URL: [/wallet/incomes]()
- Method: POST
- Authentication: Required

Request Body

| Parameter    | Type    | Description                                             | Required |
|:-------------|---------|---------------------------------------------------------|---------:|
| name         | string  | Name of the income                                      |      Yes |
| amount       | number  | Amount of the income                                    |      Yes |
| incomeType   | string  | Type of income ('monthly' or 'sporadic')                |      Yes |
| dateTime     | string  | Date and time of the income (format: dd/MM/yyyy HH:mm)  |      Yes |

Response
- **200 OK**
  - **Content**: Json
  ```Json
   {
     "id": 8,
     "name": "Monthly salary",
     "amount": 1330.50,
     "incomeType": "MONTHLY",
     "dateTime": "13/03/2024 11:00"
   }
   ```
<h4 id="all-incomes">Get Income List</h4>

- URL: [/wallet/incomes]()
- Method: GET
- Authentication: Required

Query Parameters

| Parameter  | Type   | Description                         | Required | Default  |
|------------|--------|-------------------------------------|----------|:--------:|
| pageNo     | number | Page number for pagination          | No       |    0     |
| pageSize   | number | Number of items per page            | No       |    10    |
| sortBy     | string | Field to sort the results           | No       | dateTime |
| sortDir    | string | Sorting direction ('asc' or 'desc') | No       |   desc   |

Response
- **200 OK**
  - **Content:** Json
  ```Json
   {
     "content": [
       {
         "id": 8,
         "name": "Monthly salary",
         "amount": 1330.50,
         "incomeType": "MONTHLY",
         "dateTime": "13/03/2024 11:00"
       }
     ],
     "pageNo": 0,
     "pageSize": 10,
     "totalElements": 1,
     "totalPages": 1,
     "last": true
   }
  ```
  
<h4 id="delete-income">Delete Income</h4>

Deletes the specified income from the wallet of the logged-in user.

- URL: [/wallet/incomes/{incomeId}]()
- Method: DELETE
- Authentication: Required

Path Parameters

| Parameter | Type   | Description                | Required |
|-----------|--------|----------------------------|----------|
| incomeId  | number | ID of the income to delete | Yes      |

Responses
- **204 No Content**

<h4 id="update-income">Update Income</h4>

Updates the details of the specified income in the wallet of the logged-in user.

- URL: [/wallet/incomes/{incomeId}]()
- Method: PUT
- Authentication: Required

Path Parameters

| Parameter | Type   | Description                 | Required |
|-----------|--------|-----------------------------|----------|
| IncomeId  | number | ID of the income to update  | Yes      |

Request Body

| Parameter   | Type    | Description                                                | Required |
|-------------|---------|------------------------------------------------------------|----------|
| name        | string  | New name of the income                                     | Yes      |
| amount      | number  | New amount of the income                                   | Yes      |
| incomeType  | string  | New type of income ('monthly' or 'sporadic')               | Yes      |
| dateTime    | string  | New date and time of the income (format: dd/MM/yyyy HH:mm) | Yes      |

Response
- **200 OK**
  - **Content:** Json
  ```Json
   {
      "id": 9,
      "name": "Monthly salary",
      "amount": 1230.50,
      "incomeType": "MONTHLY",
      "dateTime": "11/03/2024 20:57"
   }
  ```

<h3 id="category-endpoints">📕Category Endpoints</h3>

<h4>Add Category to Wallet</h4>

- URL: [/wallet/categories]()
- Method: POST
- Authentication: Required

Request Body

| Parameter   | Type    | Description                      | Required |
|-------------|---------|----------------------------------|----------|
| name        | string  | Name of the category             | Yes      |
| description | string  | Description of the category      | Yes      |

Responses
- **200 OK**
  - **Content:** Json
  ```Json
   {
     "id": 8,
     "name": "Games",
     "description": "Anything that I buy related to games"
   }
  ```

<h4 id="all-categories">Get Category List</h4>

Retrieves a list of categories from the wallet of the logged-in user.

- URL: [/wallet/categories]()
- Method: GET
- Authentication: Required

Query Parameters

| Parameter  | Type   | Description                                  | Required | Default |
|------------|--------|----------------------------------------------|----------|---------|
| pageNo     | number | Page number for pagination                   | Yes      | 0       |
| pageSize   | number | Number of items per page                     | Yes      | 10      |
| sortBy     | string | Field to sort the results                    | Yes      | id      |
| sortDir    | string | Sorting direction ('asc' or 'desc')          | Yes      | asc     |

Response
- **200 OK**
  - **Content:** Json
  ```Json
  {
     "content": [
       {
         "id": 8,
         "name": "Games",
         "description": "Anything that I buy related to games"
       }
     ],
     "pageNo": 0,
     "pageSize": 10,
     "totalElements": 1,
     "totalPages": 1,
     "last": true
   }
  ```
  
<h4 id="category-id">Get Category by ID</h4>

Retrieves the details of a specific category from the wallet of the logged-in user.

- URL: [/wallet/categories/{categoryId}]()
- Method: GET
- Authentication: Required

Path Parameters

| Parameter  | Type   | Description         | Required |
|------------|--------|---------------------|----------|
| categoryId | number | ID of the category  | Yes      |

Response
- **200 OK**
  - **Content:** Json
  ```Json
   {
     "id": 8,
     "name": "Games",
     "description": "Anything that I buy related to games"
   }
  ```

<h4 id="update-category">Update Category</h4>

Updates the details of a specific category in the wallet of the logged-in user.

- URL: [/wallet/categories/{categoryId}]()
- Method: PUT
- Authentication: Required

Path Parameters

| Parameter  | Type   | Description            | Required |
|------------|--------|------------------------|----------|
| categoryId | number | ID of the category     | Yes      |

Request Body

| Parameter   | Type    | Description                     | Required |
|-------------|---------|---------------------------------|----------|
| name        | string  | New name of the category        | Yes      |
| description | string  | New description of the category | Yes      |

<h4 id="category-delete">Delete Category by Id</h4>

Deletes the specified category from the wallet of the logged-in user.

- URL: [/wallet/categories/{categoryId}]()
- Method: Delete
- Authentication: Required

Path Parameters

| Parameter  | Type   | Description           | Required |
|------------|--------|-----------------------|----------|
| categoryId | number | ID of the category    | Yes      |

Response
- **204 No Content**

<h3 id="expense-endpoints">💸Expense Endpoints<h3>

<h4> Add expense to Wallet</h4>

Adds a new expense to the wallet of the logged-in user.

- URL: [/wallet/expenses]()
- Method: POST
- Authentication: Required

Request Body

| Parameter      | Type    | Description                                             | Required |
|----------------|---------|---------------------------------------------------------|----------|
| name           | string  | Name of the expense                                     | Yes      |
| amount         | number  | Amount of the expense                                   | Yes      |
| paymentMethod  | string  | Payment method ('Debit' or 'Credit')                    | Yes      |
| dateTime       | string  | Date and time of the expense (format: dd/MM/yyyy HH:mm) | Yes      |
| categoryId     | number  | ID of the category for the expense                      | Yes      |

Responses
- **200 OK**
  - **Content:** Json
  ```Json
  {
     "id": 5,
     "name": "Dark souls 3",
     "amount": 180,
     "paymentMethod": "DEBIT",
     "dateTime": "12/03/2024 10:00",
     "category": {
       "id": 8,
       "name": "Games",
       "description": "Anything that I buy related to games"
     }
   } 
  ```
  
<h4 id="all-expenses">Get Expense List</h4>

Retrieves a list of expenses from the wallet of the logged-in user.

- URL: [/wallet/expenses]()
- Method: GET
- Authentication: Required

Query Parameters

| Parameter  | Type   | Description                                  | Required | Default |
|------------|--------|----------------------------------------------|----------|---------|
| pageNo     | number | Page number for pagination                   | Yes      | 0       |
| pageSize   | number | Number of items per page                     | Yes      | 10      |
| sortBy     | string | Field to sort the results                    | Yes      | dateTime|
| sortDir    | string | Sorting direction ('asc' or 'desc')          | Yes      | desc    |

Response
- **200 OK**
  - **Content:** Json
  ```Json
  {
     "content": [
       {
         "id": 5,
         "name": "Dark souls 3",
         "amount": 180.00,
         "paymentMethod": "DEBIT",
         "dateTime": "12/03/2024 10:00",
         "category": {
           "id": 8,
           "name": "Games",
           "description": "Anything that I buy related to games"
         }
       }
     ],
     "pageNo": 0,
     "pageSize": 10,
     "totalElements": 1,
     "totalPages": 1,
     "last": true
   }
  ```
  
<h4 id="delete-expense">Delete Expense</h4>

Deletes the specified expense from the wallet of the logged-in user.

- URL: [/wallet/expenses/{expenseId}]()
- Method: DELETE
- Authentication: Required

Path parameters

| Parameter  | Type   | Description            | Required |
|------------|--------|------------------------|----------|
| expenseId  | number | ID of the expense      | Yes      |

Response
- **204 No content**

<h4 id="update-expense">Update Expense</h4>

Updates the details of a specific expense in the wallet of the logged-in user.

- URL: [wallet/expenses/{expenseId}]()
- Method: PUT
- Authentication: Required

Path Parameters

| Parameter  | Type   | Description            | Required |
|------------|--------|------------------------|----------|
| expenseId  | number | ID of the expense      | Yes      |

Request Body

| Parameter      | Type    | Description                                                 | Required |
|----------------|---------|-------------------------------------------------------------|----------|
| name           | string  | New name of the expense                                     | Yes      |
| amount         | number  | New amount of the expense                                   | Yes      |
| paymentMethod  | string  | New payment method ('Debit' or 'Credit')                    | Yes      |
| dateTime       | string  | New date and time of the expense (format: dd/MM/yyyy HH:mm) | Yes      |
| categoryId     | number  | New ID of the category for the expense                      | Yes      |

Response
- **200 OK**
  - **Content:** Json
  ```Json
  {
     "id": 5,
     "name": "Dark souls 2",
     "amount": 180,
     "paymentMethod": "DEBIT",
     "dateTime": "12/03/2024 10:00",
     "category": {
       "id": 8,
       "name": "Games",
       "description": "Anything that I buy related to games"
     }
   }
  ```
<h3 id="admin-endpoints">🔐Admin endpoints</h3>

<h4>Get User List (Admin)</h4>

Retrieves a list of users with administrator privileges.

- URL: [/admin/users]()
- Method: Get
- Authentication: Required(ROLE_MANAGER)

Query Parameters

| Parameter  | Type   | Description                                  | Required | Default |
|------------|--------|----------------------------------------------|----------|---------|
| pageNo     | number | Page number for pagination                   | Yes      | 0       |
| pageSize   | number | Number of items per page                     | Yes      | 10      |
| sortBy     | string | Field to sort the results                    | Yes      | id      |
| sortDir    | string | Sorting direction ('asc' or 'desc')          | Yes      | asc     |

Response
- **200 OK**
  - **Content:** Json
  ```Json
   {
     "content": [
       {
         "id": 1,
         "name": "Luiz Filipe",
         "email": "admin@example.com",
         "role": "ROLE_MANAGER",
         "personalInformation": {
           "phoneNumber": "123456729",
           "birthDate": "17/04/2004",
           "nationality": "Brazilian"
         },
         "wallet": {
           "balance": 3965.93
         }
       },
       {
         "id": 3,
         "name": "user",
         "email": "user@example.com",
         "role": "ROLE_USER",
         "personalInformation": {
           "phoneNumber": "123456789",
           "birthDate": "17/04/2004",
           "nationality": "Brazilian"
         },
         "wallet": {
           "balance": 0.00
         }
       },
       {
         "id": 4,
         "name": "Luiz Filipe",
         "email": "luiz@example.com",
         "role": "ROLE_USER",
         "personalInformation": {
           "phoneNumber": "123456729",
           "birthDate": "17/04/2004",
           "nationality": "Brazilian"
         },
         "wallet": {
           "balance": 1050.50
         }
       }
     ],
     "pageNo": 0,
     "pageSize": 3,
     "totalElements": 3,
     "totalPages": 1,
     "last": true
   }
  ```
  