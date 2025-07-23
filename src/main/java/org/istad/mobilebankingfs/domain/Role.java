package org.istad.mobilebankingfs.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name="roles")
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String role;

    @ManyToMany(mappedBy = "roles",  cascade = CascadeType.ALL)
    private List<User> users;

    @Override
    public String getAuthority() {
        return "ROLE_" + role;
    }
}
