package ru.edjll.cinema.domain;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

@Entity
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Length(min = 1, max = 50, message = "error.validation.film.title.length")
    private String title;

    @Length(min = 1, max = 255, message = "error.validation.film.description.length")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;

    private Integer ageLimit = 0;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "film_id")
    private Set<Rating> rating;

    private String preview;

    private String trailer;

    private String video;

    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "film_category",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "user_film",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;

    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "film_subscribe",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "subscribe_id")
    )
    private Set<Subscribe> subscribes;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "access_id")
    private Access access;

    public Film() { }

    public Film(String title, String description, Date releaseDate, Integer ageLimit, String preview, String trailer, Set<Category> categories, Set<Subscribe> subscribes, Access access) {
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.ageLimit = ageLimit;
        this.preview = preview;
        this.trailer = trailer;
        this.categories = categories;
        this.subscribes = subscribes;
        this.access = access;
    }

    @Transient
    public Boolean hasActiveSubscribes() {
        for (Subscribe subscribe : subscribes) {
            if (subscribe.getActiveSubscribePeriods().size() > 0) return true;
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(Integer ageLimit) {
        this.ageLimit = ageLimit;
    }

    public Set<Rating> getRating() {
        return rating;
    }

    public void setRating(Set<Rating> rating) {
        this.rating = rating;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Subscribe> getSubscribes() {
        return subscribes;
    }

    public void setSubscribes(Set<Subscribe> subscribes) {
        this.subscribes = subscribes;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
