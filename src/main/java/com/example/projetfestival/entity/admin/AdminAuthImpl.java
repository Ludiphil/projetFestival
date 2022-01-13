package com.example.projetfestival.entity.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Objects;

public class AdminAuthImpl implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String email;
    @JsonIgnore
    private String password;
    private String name;

    public Collection<? extends GrantedAuthority> authorities;

    public AdminAuthImpl(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static AdminAuthImpl build(Admin admin) {
        return new AdminAuthImpl(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getPassword()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AdminAuthImpl admin = (AdminAuthImpl) o;
        return Objects.equals(id, admin.id);
    }
}

