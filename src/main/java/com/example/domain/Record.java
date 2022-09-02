package com.example.domain;

import java.util.Date;

public class Record {
    private Long id;
    private String content;
    private Date created;
    private Long from_user;
    private Long to_user;
    private Boolean used; //是否已经读取过，true代表读取过，false代表未读

    public Record() {
    }

    public Record(Long id, String content, Date created, Long from_user, Long to_user, Boolean used) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.from_user = from_user;
        this.to_user = to_user;
        this.used = used;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }



    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Long getFrom_user() {
        return from_user;
    }

    public void setFrom_user(Long from_user) {
        this.from_user = from_user;
    }

    public Long getTo_user() {
        return to_user;
    }

    public void setTo_user(Long to_user) {
        this.to_user = to_user;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created=" + created +
                ", from_user=" + from_user +
                ", to_user=" + to_user +
                ", used=" + used +
                '}';
    }
}
