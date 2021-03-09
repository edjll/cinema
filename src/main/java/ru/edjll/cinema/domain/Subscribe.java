package ru.edjll.cinema.domain;

import org.hibernate.validator.constraints.Length;
import ru.edjll.cinema.validation.subscribe.name.UniqueName;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@UniqueName
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Length(min = 1, max = 30, message = "error.validation.subscribe.name.length")
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "subscribe_id")
    private Set<SubscribePeriod> periods = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "film_subscribe",
            joinColumns = @JoinColumn(name = "subscribe_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id")
    )
    private Set<Film> films;

    @Transient
    public Set<SubscribePeriod> getActiveSubscribePeriods() {
        return periods.stream().filter(SubscribePeriod::isActive).collect(Collectors.toSet());
    }

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

    public Set<SubscribePeriod> getPeriods() {
        return periods;
    }

    public void setPeriods(Set<SubscribePeriod> periods) {
        this.periods = periods;
    }

    public Set<Film> getFilms() {
        return films;
    }

    public void setFilms(Set<Film> films) {
        this.films = films;
    }
}
