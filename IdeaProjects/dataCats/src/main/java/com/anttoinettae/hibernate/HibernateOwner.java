package com.anttoinettae.hibernate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="owner", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class HibernateOwner {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Getter
    @Column(name = "birth_date")
    private Date birth_date;

    public HibernateOwner(String name, Date birth_date){
        this.name = name;
        this.birth_date = birth_date;
    }
}
