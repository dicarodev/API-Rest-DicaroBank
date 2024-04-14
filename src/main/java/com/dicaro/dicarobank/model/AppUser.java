package com.dicaro.dicarobank.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Table(name = "USERS")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String dni;

    private String name;
    private String surname;
    private String phone;
    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AppUserAuthorization> authorities;

    @OneToMany(mappedBy = "appUser", fetch = FetchType.EAGER)
    private List<Account> accountList = new ArrayList<>();

}
