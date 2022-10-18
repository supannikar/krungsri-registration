# krungsri-registration


Topic | Description
------ | ------
Developer: | Supannikar Nontarak
Stack: | Java
Framework: | SpringBoot
---

## Registration API
RESTFul API for registration a new user. It's including:

* **Authenication API** <br/>
* **User API** <br/>
---

## Postman collection:
> Collection: demo <br/>
> Variable environment: 1234 <br/>
---

## Database setup
Please execute script from below or might be create by manually.

```sql
CREATE DATABASE krungsriregistration

CREATE role krungsriregistration WITH 
	SUPERUSER
	CREATEDB
	CREATEROLE
	INHERIT
	LOGIN
	NOREPLICATION
	CONNECTION LIMIT -1;
 ```
 ---

## Architecture Setup
### Prerequisite
* java (require JDK version 17) <br/>
* gradle (require version higher than 5.x) <br/>
* git

### Step for running project

1. Clone project from repository: https://github.com/supannikar/krungsri-registration.git <br/>
2. Build project: gradle clean build <br/>
3. Run project: gradle bootRun

The API will be run on port 8080: http://localhost:8080/swagger-ui/index.html

#### Authentication API
- Generate access token.
  - http://localhost:8080/api/auth/token
    - Not require authorization header
- Register a new user.
  - http://localhost:8080/api/auth/register
    - Not requrie authorization header.
    - Request body
    ```json
    {
     "address":"krungsri-02",
     "password":"krungsri-02",
     "phone":"0851234567",
     "salary":90000,
     "username":"krungsri-02"
    }
    ```

#### User API
- Get user information by username.
  - http://localhost:8080/api/user/{{username}}
    - Require authorization header
    - Path variable: String
- Retrieve all user information.
  - http://localhost:8080/api/user/list
    - Require authorization header
