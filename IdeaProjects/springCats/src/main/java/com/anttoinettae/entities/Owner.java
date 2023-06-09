package com.anttoinettae.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="owner", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Owner implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Basic
    @Column(name = "birth_date")
    private Date birth_date;

    public Owner(String name, Date birth_date){
        this.name = name;
        this.birth_date = birth_date;
    }
}
