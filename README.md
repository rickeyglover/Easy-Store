# User Story

This project aimed to enhance an ecommerce store by addressing existing bugs and incorporating new features.

## Phase 1: Resolving Categories

The Categories section of the site was experiencing loading issues, necessitating the addition of code to rectify API connections and fetch the specified controllers from the database.

![image](https://github.com/scrutch93/Easy-Store/assets/80648971/3ee98504-7635-48c4-9eae-586fb3c3e76e)

### Category Operations

#### List Categories
- Creates an ArrayList named "categories" to store retrieved categories.
- Defines an SQL query to select all columns from the categories table.
- Establishes a connection, prepares a statement with the SQL query, and executes it to return the list of categories.

#### getById
- Retrieves a category based on its ID in the database.
- Uses a SQL query to select a category by the provided ID.
- Executes the query, maps the results to a Category row in the database using mapRow, and returns the retrieved category or null if not found.

#### create
- Inserts a new category into the database.
- Prepares an SQL statement for insertion with name, ID, and description.
- Executes the query, retrieves the auto-generated keys with the new created ID, and returns the inserted category.

#### update
- Updates an existing category in the database based on its ID.
- Prepares an SQL update query to set new values for the category name, ID, and description.
- Executes the update query.

#### delete
- Deletes a category from the database based on its ID.
- Prepares an SQL delete query and executes it.

#### mapRow
- Takes a "ResultSet" representing a row from the database and maps it to a "Category."
- Extracts category ID, name, and description from the ResultSet to create a Category object with the values.

## Phase 2: Bug resolution

### MySQL Product Search Functionality: Code Changes Overview

#### Description
The following outlines changes made in the `search` method of the `MySqlProductDao` class related to product retrieval functionality.

#### Changes Made

##### SQL Query Logic
- **Second Version:**
    ```java
    "SELECT * FROM products " +
    "WHERE (category_id = ? OR ? = -1) " +
    "AND (price <= ? OR ? = -1) " +
    "AND (color = ? OR ? = '') ";
    ```
- **First Version:**
    ```java
    "SELECT * FROM products " +
    "WHERE (category_id = ? OR ? = -1) " +
    "AND ((price >= ? AND price <= ?) OR (? = -1 AND ? = -1)) " +
    "AND (color = ? OR ? = '') ";
    ```
    - **Changes:** Modified the SQL query logic related to price range filtering to a simpler structure in the second version.

##### Parameter Binding in PreparedStatement
- **Second Version:**
    ```java
    statement.setInt(1, categoryId);
    statement.setInt(2, categoryId);
    statement.setBigDecimal(3, minPrice);
    statement.setBigDecimal(4, minPrice);
    statement.setString(5, color);
    statement.setString(6, color);
    ```
- **First Version:**
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
    - **Changes:** Adjusted parameter settings within the `PreparedStatement` for `minPrice` and `maxPrice` in the second version.

#### Impact
- **SQL Query Logic Changes:** Alteration might affect the accuracy and precision of product retrieval.
- **Parameter Binding Adjustments:** Changes could lead to inconsistencies or incorrect query execution.

#### Conclusion
The changes in the first version diverge from the second version in SQL query logic and parameter handling, potentially impacting the accuracy and expected behavior of product retrieval functionality.

Feel free to tailor or expand upon this README-style documentation to suit your specific needs or context!

## Phase 3:
[Add relevant information for Phase 3 here.]
