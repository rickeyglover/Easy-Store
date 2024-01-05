package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ProfileDao;
import org.yearup.models.Product;
import org.yearup.models.Profile;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private ProfileDao profileDao;

    @Autowired
    public ProfileController(ProfileDao profileDao)
    {
        this.profileDao = profileDao;
    }


    @GetMapping("{id}")
    @PreAuthorize("permitAll()")
    public Profile getByUserId(@PathVariable int id )
    {
        try
        {
            var profile = profileDao.getByUserID(id);

            if(profile == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            return profile;
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateProfile(@PathVariable int id, @RequestBody Profile profile)
    {
        try
        {
            profileDao.updateProfile(profile, id);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }


}