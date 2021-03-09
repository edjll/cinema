package ru.edjll.cinema.domain;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user_subscribe")
public class UserSubscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private Date dateStart;

    private Integer period;

    private Double price;

    private Boolean renewal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscribe_period_id")
    private SubscribePeriod subscribePeriod;

    @Transient
    public Date getDateEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.DAY_OF_MONTH, period);
        return calendar.getTime();
    }

    @Transient
    public Long getDateToEnd() {
        return getDateEnd().getTime() - new Date().getTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public SubscribePeriod getSubscribePeriod() {
        return subscribePeriod;
    }

    public void setSubscribePeriod(SubscribePeriod subscribePeriod) {
        this.subscribePeriod = subscribePeriod;
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

    public Boolean getRenewal() {
        return renewal;
    }

    public void setRenewal(Boolean renewal) {
        this.renewal = renewal;
    }
}
