package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.List;

// add the annotations to make this a REST controller
<<<<<<< HEAD
=======
// add the annotation to make this controller the endpoint for the following url
    // http://localhost:8080/categories
// add annotation to allow cross site origin requests
>>>>>>> ab48118a98168681d5101ab55de2fd153afca83d
@RestController
@CrossOrigin
public class CategoriesController
{
    private CategoryDao categoryDao;
   // private ProductDao productDao;


    // create an Autowired controller to inject the categoryDao and ProductDao

    // add the appropriate annotation for a get action
    @Autowired
<<<<<<< HEAD
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao ){
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }
=======
    public CategoriesController(CategoryDao categoryDao){
        this.categoryDao = categoryDao;

    }

    // add the appropriate annotation for a get action
    @RequestMapping(path = "/categories", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public List<Category> getAll()
    {
        try {
            // find and return all categories
            return categoryDao.getAllCategories();
        }  catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
        }
>>>>>>> ab48118a98168681d5101ab55de2fd153afca83d

    // add the appropriate annotation for a get action
    @RequestMapping(path = "/categories", method = RequestMethod.GET)
    @PreAuthorize("permitAll()")
    public List<Category> getAll()
    {
        try {
            // find and return all categories
            return categoryDao.getAllCategories();
        }  catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // add the appropriate annotation for a get action
    @RequestMapping(path ="/categories/{id}", method = RequestMethod.GET)
    public Category getById(@PathVariable int id)
    {
        // get the category by id
        return categoryDao.getById(id);
    }

    // the url to return all products in category 1 would look like this
    // https://localhost:8080/categories/1/products
    @RequestMapping(path = "/{categoryId}/products", method = RequestMethod.GET)
    public List<Product> getProductsById(@PathVariable int categoryId) {
        // get a list of product by categoryId
        return productDao.listByCategoryId(categoryId);
    }


    // add annotation to call this method for a POST action
    @PostMapping("/categories")
    // add annotation to ensure that only an ADMIN can call this function
    @PreAuthorize("hasRole('ADMIN')")
    public Category addCategory(@RequestBody Category category)
    {
        // insert the category
        return categoryDao.create(category);
    }

    // add annotation to call this method for a PUT (update) action - the url path must include the categoryId
    @PutMapping("/categories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    // add annotation to ensure that only an ADMIN can call this function

    public void updateCategory(@PathVariable int id, @RequestBody Category category)
    {
        // update the category by id
        categoryDao.update(id, category);
    }


    // add annotation to call this method for a DELETE action - the url path must include the categoryId
    @DeleteMapping("/categories/{id}")
    // add annotation to ensure that only an ADMIN can call this function
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(@PathVariable int id)
    {
        // delete the category by id
        categoryDao.delete(id);
    }
}
