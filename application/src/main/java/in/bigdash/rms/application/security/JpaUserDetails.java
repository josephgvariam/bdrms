package in.bigdash.rms.application.security;

import in.bigdash.rms.model.Role;
import in.bigdash.rms.model.User;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class JpaUserDetails implements UserDetails, CredentialsContainer {
    private Long id;
    private String username;
    private String password;
    private boolean locked;
    private Collection<? extends GrantedAuthority> roles;
    private User user;

    public JpaUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.locked = user.getLocked();
        this.user = user;

        if (user.getRoles() == null) {
            this.roles = Collections.emptySet();
        } else {
            Set<SimpleGrantedAuthority> roleSet =
                    new HashSet<SimpleGrantedAuthority>(user.getRoles().size());
            for (Role role : user.getRoles()) {
                roleSet.add(new SimpleGrantedAuthority(role.getName()));
            }
            this.roles = roleSet;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
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
    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    public boolean equals(Object otherUser) {
        if (otherUser instanceof JpaUserDetails) {
            return this.username.equals(((JpaUserDetails) otherUser).getUsername());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

}
