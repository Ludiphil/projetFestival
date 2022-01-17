package com.example.projetfestival.controller;


import com.example.projetfestival.entity.Artist;
import com.example.projetfestival.entity.admin.Admin;
import com.example.projetfestival.repository.AdminRepository;
import com.example.projetfestival.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5432")
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminController(AdminRepository adminRepository){
        this.adminRepository=adminRepository;
    }

    @GetMapping("")
    public List<Admin> getAdmin(){
        List<Admin> admins = new ArrayList<>();
        try{
            admins = adminRepository.findAll();
        }catch (Exception e){
            System.out.println(e);
        }
        return admins;
    }

    @PostMapping("")
    public ResponseEntity<Admin> createAdmin(@RequestBody String username, @RequestBody String email, @RequestBody String password){
        try
        {
            Admin admin = adminRepository.save(new Admin(username,email,password));
            return new ResponseEntity<>(admin, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{id},{username}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable("id") int id,@PathVariable("username") String username) {
        Optional<Admin> userData = adminRepository.findById((long) id);

        if (userData.isPresent()) {
            Admin admin = userData.get();
            admin.setName(username);
            return new ResponseEntity<>(adminRepository.save(admin), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAdmin(@PathVariable("id") int id)
    {
        try
        {
            adminRepository.deleteById((long) id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
