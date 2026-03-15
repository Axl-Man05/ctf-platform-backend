package com.ctf.platform.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
//@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    //verifica que el email sea unico
    @Column(unique = true)
    private String email;
    private String verificationCode;
    private Boolean isVerified;

    //El "managed"(el que manda) va en clase padre
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Solve> solves;
}
