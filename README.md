## User Story

The goal of this project was to resolve bugs and implement new feautures into an ecommerce store.


## Phase 1: Resolving Categories

The Categogories part of the site was not loading properly, thuus some code needed to be added to implemented to fix the api connections grabbing the specified controllers from the database.




list categories
Starts by creating an ArrayList named categories to store the retrieved categories. Then
we define an SQL query to select all cloumns from the categories table with the SQL query.
Then inside the try method we establish a connection. Then we prepare a statement with the 
SQL query and use "ResultSet" to execute the query. Then we return the list of categories.

getById
This method retrieves a category based on it's ID in the database. Then it uses a SQL 
query to select a category by the provided id. Then we use the "ResultSet" to execute to
the query and maps the results to a row in Category in the database using mapRow. Then it
returns the retrieved category or null if not found.

create
This method inserts a new category into the database. Then it prepares and SQL statement 
for the insertion that includes the name, id and description. Then it executes the query 
and retrieves the auto-generated keys that includes the new created id. Then it returns the 
inserted category. 

update
This method updates an existing category in the databse based on it's id. It prepares a SQL
update query to set new values for the category name, id and description. Then executes the 
update query. 

delete
This method deletes a category from the database based on it's id. Then it prepares a 
SQL delete query and executes it. 

mapRow
This takes a "ResultSet" that represents a row from the database and maps it to "Category".
It takes the category id, name and description from the ResultSet and creates a Category
object with the values.


## Phase 2: Bug resolution:

Certainly! Here's an outline in a README format to summarize the changes made from the second code snippet to the first one:

---

# MySQL Product Search Functionality: Code Changes Overview

## Description
The following document outlines the changes made in the `search` method of the `MySqlProductDao` class related to product retrieval functionality.

## Changes Made
### SQL Query Logic
- **Second Version (from the second snippet):**
    ```java
    "SELECT * FROM products " +
    "WHERE (category_id = ? OR ? = -1) " +
    "AND (price <= ? OR ? = -1) " +
    "AND (color = ? OR ? = '') ";
    ```
- **First Version (from the first snippet):**
    ```java
    "SELECT * FROM products " +
    "WHERE (category_id = ? OR ? = -1) " +
    "AND ((price >= ? AND price <= ?) OR (? = -1 AND ? = -1)) " +
    "AND (color = ? OR ? = '') ";
    ```
    - **Changes:** The SQL query logic related to price range filtering was modified to a simpler structure in the second version, whereas the first version used a more complex but potentially more precise logic.

### Parameter Binding in PreparedStatement
- **Second Version (from the second snippet):**
    ```java
    statement.setInt(1, categoryId);
    statement.setInt(2, categoryId);
    statement.setBigDecimal(3, minPrice);
    statement.setBigDecimal(4, minPrice);
    statement.setString(5, color);
    statement.setString(6, color);
    ```
- **First Version (from the first snippet):**
    ```java
    statement.setInt(1, categoryId);
    statement.setInt(2, categoryId);
    statement.setBigDecimal(3, minPrice);
    statement.setBigDecimal(4, maxPrice);
    statement.setBigDecimal(5, minPrice);
    statement.setBigDecimal(6, maxPrice);
    statement.setString(7, color);
    statement.setString(8, color);
    ```
    - **Changes:** In the first version, parameters were set for `minPrice` and `maxPrice` with a different structure than in the second version, including two occurrences for each, potentially affecting the query execution.

## Impact
- **SQL Query Logic Changes:**
    - The alteration in the SQL query logic might affect the accuracy and precision of product retrieval based on the specified criteria.
- **Parameter Binding Adjustments:**
    - The changes in parameter settings within the `PreparedStatement` could lead to inconsistencies or incorrect execution of the query.

## Conclusion
The changes introduced in the first version diverge from the second version's structure in SQL query logic and parameter handling, potentially impacting the accuracy and expected behavior of the product retrieval functionality.

---

Feel free to tailor or expand upon this README-style documentation to suit your specific needs or context!



## Phase 3:
