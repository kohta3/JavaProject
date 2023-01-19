package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ロール情報
 */
@Entity
@Table(name = "ROLES")
public class Role {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", length = 40, nullable = false, unique = true)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
