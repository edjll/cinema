package ru.edjll.cinema.domain;

import javax.persistence.*;

@Entity
public class Access {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double price;

    @Enumerated(EnumType.ORDINAL)
    private AccessType type;

    public Access() { }

    public Access(double price, AccessType type) {
        this.price = price;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public AccessType getType() {
        return type;
    }

    public void setType(AccessType type) {
        this.type = type;
    }
}
