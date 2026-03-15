package com.ctf.platform.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
//anotacion builder sirve para implementar el patron de diseno Builder automaticamente
//para facilitar la creacion de objetos
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "challenges")
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String difficulty;
    private String flag;

//relacion bidireccional
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "challenge")
    private List<Solve> solves;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "challenge")
    private List<Hint> hints;
}
