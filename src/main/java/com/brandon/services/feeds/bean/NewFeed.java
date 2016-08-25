package com.brandon.services.feeds.bean;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * Created by brandon Lee on 2016-08-25.
 */
public class NewFeed implements Serializable {
    private static final long serialVersionUID = -4181342066190991278L;
    String subject;
    String link;

    public NewFeed(String subject, String link) {
        this.subject = subject;
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewFeed newFeed = (NewFeed) o;
        return Objects.equal(subject, newFeed.subject) &&
                Objects.equal(link, newFeed.link);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(subject, link);
    }

    public String getSubject() {
        return subject;
    }

    public String getLink() {
        return link;
    }
}
