package ru.edjll.cinema.domain;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import ru.edjll.cinema.validation.promo.activation.ActivationCount;
import ru.edjll.cinema.validation.promo.name.UniqueName;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
@UniqueName
@ActivationCount
public class Promo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Length(min = 3, max = 10, message = "error.validation.promo.name.length")
    private String name;

    private Integer activationCount;

    @NotNull(message = "error.validation.promo.dateEnd.null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "error.validation.promo.dateEnd.future")
    private Date dateEnd;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "promo_subscribe",
            joinColumns = @JoinColumn(name = "promo_id"),
            inverseJoinColumns = @JoinColumn(name = "subscribe_id")
    )
    private List<SubscribePeriod> subscribes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "promo_user",
            joinColumns = @JoinColumn(name = "promo_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

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

    public Integer getActivationCount() {
        return activationCount;
    }

    public void setActivationCount(Integer activationCount) {
        this.activationCount = activationCount;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public List<SubscribePeriod> getSubscribes() {
        return subscribes;
    }

    public void setSubscribes(List<SubscribePeriod> subscribes) {
        this.subscribes = subscribes;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
