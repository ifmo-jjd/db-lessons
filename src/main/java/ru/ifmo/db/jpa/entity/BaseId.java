package ru.ifmo.db.jpa.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

// родитель для entity классов,
// сам entity классом не является
@MappedSuperclass
public abstract class BaseId {
    @Id // @Column и другие аннотации persistence api доступны
    // для использования в MappedSuperclass
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
