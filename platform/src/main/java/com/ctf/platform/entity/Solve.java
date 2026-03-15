package com.ctf.platform.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
//anotacion entity marca una cloase java como entidad persistente
//mapeandola automaticamente a una tabla de base de datos
//obligatoria para convertir una clase POJO en una entiedad
//permitiendo que sus atributos se correlacionen con columnas
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "solves")
public class Solve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime solvedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//el hijo (el que tiene el @ManyToOne, como Solve) lleva la contra parte
//para cortar el bucle cuando angular pida los datos
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

}
