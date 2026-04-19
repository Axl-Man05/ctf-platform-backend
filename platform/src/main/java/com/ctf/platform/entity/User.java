package com.ctf.platform.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Getter
@Setter
@Builder
//@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    @JsonIgnore
    private String password;
    //verifica que el email sea unico
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String verificationCode;
    private Boolean isVerified;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    //El "managed"(el que manda) va en clase padre
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Solve> solves;


    @Override
    @JsonIgnore
    public String getUsername() {
        return this.email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired(){
        return true; //la cuenta nunca expira
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked(){
        return true; //la cuenta no esta bloqueada
    }
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired(){
        return true; //la contrasena no caduca cada 90 dias
    }
    @Override
    @JsonIgnore
    public boolean isEnabled(){
        return Boolean.TRUE.equals(this.isVerified); //El usuario esta activo desde que se registra
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return (this.roles == null) ? Collections.emptyList() : this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }


}
