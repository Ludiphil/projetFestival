package com.example.projetfestival.service;

import com.example.projetfestival.entity.admin.Admin;
import com.example.projetfestival.entity.admin.AdminAuthImpl;
import com.example.projetfestival.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminAuthService implements UserDetailsService {
    final AdminRepository adminRepository;

    @Autowired
    public AdminAuthService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        return AdminAuthImpl.build(admin);
    }

}

