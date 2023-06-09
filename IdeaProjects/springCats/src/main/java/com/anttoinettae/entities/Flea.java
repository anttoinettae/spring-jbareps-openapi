package com.anttoinettae.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="flea", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Flea implements BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "cat_id", foreignKey = @ForeignKey(name = "FK_FLEA_OWNER"))
    private Cat cat;

    public Flea(String name, Cat cat){
        this.name = name;
        this.cat = cat;
    }
}
