package com.anttoinettae.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cat", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Cat implements BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Basic
    private Date birth_date;

    @Basic
    private String breed;

    @Basic
    private Integer tail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", foreignKey = @ForeignKey(name = "FK_CAT_OWNER"))
    private Owner owner;

    public Cat(String name, Date birth_date, String breed, Integer tail, Owner owner){
        this.name = name;
        this.birth_date = birth_date;
        this.breed = breed;
        this.tail = tail;
        this.owner = owner;
    }
}
