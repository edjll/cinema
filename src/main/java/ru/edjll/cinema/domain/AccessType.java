package ru.edjll.cinema.domain;

public enum AccessType {

    FREE("Бесплатно"),
    BUY("Покупка");

    private String title;

    AccessType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
