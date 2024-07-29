package com.chocs;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {
    private String type;
    private String subject;
    private String description;
    private int daysLeft;
    private LocalDate date;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void calcDaysLeft() {
        daysLeft = date.getDayOfYear() - LocalDate.now().getDayOfYear();

        if (daysLeft < 0) {
            daysLeft += 365;
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return type + " [" + subject + ", " + description + ", " +
                date.format(DateTimeFormatter.ofPattern("dd/MM/uuuu")) +
                ", " + daysLeft + " days left]";
    }
}
