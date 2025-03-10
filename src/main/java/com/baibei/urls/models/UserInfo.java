package com.baibei.urls.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserInfo implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private long links;

    @OneToMany(
            mappedBy = "userInfo",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MagicURL> magicURLs = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    private List<Subscription> subscriptions;

    public UserInfo() {
        if (role == null) {
            role = "ROLE_USER";
        }
        enabled = true;
    }

    public synchronized void reduceLinks() {
        links--;
    }

    public boolean hasSubscription() {
        if (subscriptions != null && !subscriptions.isEmpty()) {
            for (Subscription subscription : subscriptions) {
                if (subscription.getExpiryDate().toLocalDate().isAfter(LocalDate.now())) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
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
}
