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

<h2 id="technologies">💻 Technologies</h2>

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
All endpoints, except the login/register endpoints (Any request is allowed) and the /admin/** (Only Manager Role is allowed), require a JWT token, which is granted upon successful login.


|                  Route                   |                                         Description                                         |
|:----------------------------------------:|:-------------------------------------------------------------------------------------------:|
|      <kbd>POST auth/register</kbd>       | Registers a new user and returns the user's information along with an authentication token. |
|        <kbd>POST auth/login</kbd>        | Authenticates a user and returns the user's information along with an authentication token. |
|         <kbd> PUT /users </kbd>          |                              Updates logged user information.                               |
|        <kbd> DELETE /users </kbd>        |                                     Delete logged user                                      |
|         <kbd> GET /wallet </kbd>         |                                    Retrieve wallet info                                     |
|    <kbd> POST /wallet/incomes </kbd>     |                                      Add a new Income                                       |
|     <kbd> GET /wallet/incomes </kbd>     |                                      Find All Incomes                                       |
|    <kbd> DELETE /wallet/incomes/{id}     |                                    Delete a Income By Id                                    |
|      <kbd> PUT /wallet/incomes/{id}      |                                    Update a Income By Id                                    |
|   <kbd> POST wallet/categories </kbd>    |                                    Create a new category                                    |
|    <kbd> GET wallet/categories </kbd>    |                                     Get All Categories                                      |
|    <kbd> GET /categories/{id} </kbd>     |                                  Retrieve a category by Id                                  |
|    <kbd> PUT /categories/{id} </kbd>     |                                   Update a category by Id                                   |
|   <kbd> DELETE /categories/{id} </kbd>   |                                   Delete a category by Id                                   | 
|    <kbd> POST /wallet/incomes </kbd>     |                                      Add a new Income                                       |
|     <kbd> GET /wallet/incomes </kbd>     |                                      Find All Incomes                                       |
| <kbd> DELETE /wallet/incomes/{id} </kbd> |                                   Delete an Income By Id                                    |
|  <kbd> PUT /wallet/incomes/{id} </kbd>   |                                   Update an Income By Id                                    |
|          <kbd> GET /admin/users          |                                       Find All Users                                        |