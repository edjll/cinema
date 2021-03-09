package ru.edjll.cinema.model;

import java.util.List;

public class MailRow {

    private List<MailRowItem> mailRowItems;

    public List<MailRowItem> getMailRowItems() {
        return mailRowItems;
    }

    public void setMailRowItems(List<MailRowItem> mailRowItems) {
        this.mailRowItems = mailRowItems;
    }

    public void addMailRowItem(MailRowItem mailRowItem) {
        this.mailRowItems.add(mailRowItem);
    }
}
