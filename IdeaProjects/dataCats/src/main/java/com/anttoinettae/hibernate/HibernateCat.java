package com.anttoinettae.hibernate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Entity
@Table(name="cat", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class HibernateCat {
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

    @Getter
    @Column(name = "breed", length = 30)
    private String breed;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "owner_id", foreignKey = @ForeignKey(name = "FK_CAT_OWNER"))
    private HibernateOwner owner;

    public HibernateCat(String name, Date birth_date, String breed, HibernateOwner owner){
        this.name = name;
        this.birth_date = birth_date;
        this.breed = breed;
        this.owner = owner;
    }
}
