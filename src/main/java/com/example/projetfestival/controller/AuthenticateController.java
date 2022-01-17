package com.example.projetfestival.controller;

import com.example.projetfestival.entity.admin.Admin;
import com.example.projetfestival.entity.admin.AdminAuthImpl;
import com.example.projetfestival.payload.response.JwtResponse;
import com.example.projetfestival.repository.AdminRepository;
import com.example.projetfestival.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/admin")
public class AuthenticateController {

    AdminRepository adminRepository;

    PasswordEncoder encoder;

    AuthenticationManager authenticationManager;

    JwtUtils jwtUtils;

    @Autowired
    public void AdminController(
            AdminRepository adminRepository,
            PasswordEncoder encoder,
            @Qualifier("admin_manager") AuthenticationManager authenticationManager,
            JwtUtils jwtUtils
    ) {
        this.adminRepository = adminRepository;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        AdminAuthImpl adminImpl = (AdminAuthImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(adminImpl.getUsername());
        System.out.println(email+password);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }



    @PostMapping("/register")
    public ResponseEntity<?> register(String name, String email, String password) {

        try {
            Admin newAdmin = new Admin(
                    name,
                    email,
                    encoder.encode(password)
            );
            adminRepository.save(newAdmin);
            return new ResponseEntity<>(
                    "C'est ok",
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(
                    "C'est pas ok",
                    HttpStatus.BAD_REQUEST);
        }
    }

}
