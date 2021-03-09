package ru.edjll.cinema.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "subscribe_period")
public class SubscribePeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer period;

    private Double price;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscribe_period_id")
    private Set<UserSubscribe> users;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subscribe_id")
    private Subscribe subscribe;

    private boolean active;

    public SubscribePeriod() {
    }

    public SubscribePeriod(Integer period, Double price) {
        this.period = period;
        this.price = price;
    }

    public SubscribePeriod(Integer period, Double price, boolean active) {
        this.period = period;
        this.price = price;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<UserSubscribe> getUsers() {
        return users;
    }

    public void setUsers(Set<UserSubscribe> users) {
        this.users = users;
    }

    public Subscribe getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Subscribe subscribe) {
        this.subscribe = subscribe;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
