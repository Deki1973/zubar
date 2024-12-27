package org.smg.springsecurity.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.smg.springsecurity.constants.RoleConstants.ROLE_PREFIX;

@Entity
@Table(name = "users")  // table name
@Data  // Lombok generates getters, setters, toString, equals, and hashCode
@Builder// Lombok allows for builder pattern creation of objects
@NoArgsConstructor  // Lombok generates a no-args constructor
@AllArgsConstructor  // Lombok generates an all-args constructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)  // Add column constraints for better database validation!
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "account_locked", nullable = false)
    private boolean accountLocked = false;

    @Column(name = "failed_attempts", nullable = false)
    private int failedAttempts = 0;

    @Column(name = "lock_time")
    private LocalDateTime lockTime;

    //FetchType.EAGER: This parameter specifies how the associated collection should be loaded.
    // In this case, FetchType.EAGER means that the collection should be fetched from the database immediately along with the entity that owns it.
    // This means that whenever you load an instance of the entity, its associated collection will also be loaded right away.
    // If roles are simple and you don’t need additional properties for them, the current implementation with @ElementCollection and a separate table for roles is sufficient.
    // However, if roles are expected to grow in complexity (e.g., they will have descriptions, permissions, or other attributes),
    // it would be better to create a separate Role entity with its own table and link it to the User entity via a @ManyToMany relationship.
    // If roles in your application become more complex and need additional attributes (e.g., name, description, permissions), it would be beneficial to create a dedicated Role entity.
    // This would allow you to define those attributes in a structured way.
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    // Method to convert roles to GrantedAuthority
    public Set<GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))//needs ROLE prefix!
                .collect(Collectors.toSet());
    }
}