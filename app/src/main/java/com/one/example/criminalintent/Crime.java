package com.one.example.criminalintent;

import java.util.Date;
import java.util.UUID;

public class Crime {
    private UUID id;
    private String title;
    private Date date;
    private boolean solved;

    public Crime() {
        id = UUID.randomUUID();
        date = new Date();
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
