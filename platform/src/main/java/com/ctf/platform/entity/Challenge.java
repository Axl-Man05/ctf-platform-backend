package com.ctf.platform.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String flag;
    private String difficulty;
    private int points;
    private boolean isSolved;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
//relacion bidireccional
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "challenge")
    private List<Solve> solves;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "challenge")
    @JsonIgnore
    private List<Hint> hints;
}
