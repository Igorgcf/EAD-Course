package com.ead.course.config.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
public class UserDetailsImpl implements UserDetails, Serializable {
    public static final long SerialVersionUID = 1L;

    private UUID id;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(UUID id, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.authorities = authorities;
    }

    public static UserDetailsImpl buildFromEntity(UUID userId, String rolesStr) {

        List<GrantedAuthority> authorities = Arrays.stream(rolesStr.split(","))
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());

        authorities.forEach(a ->
                log.info("LOADED AUTHORITY -> {}", a.getAuthority())
        );

        return new UserDetailsImpl(
                userId,
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return id.toString();
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
