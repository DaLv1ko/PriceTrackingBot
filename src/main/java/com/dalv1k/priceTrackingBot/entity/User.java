package com.dalv1k.priceTrackingBot.entity;


import javax.persistence.*;

@Entity
@Table(name = "users")
public class User  {

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private int id;

    @Column(name = "chat_id")
    private long chatId;

    @Column(name = "bot_state")
    private String botState;

    @Column(name = "delete_track_id")
    private int deleteTrackId;

    @Column(name = "wrong_link")
    private String wrongLink;

    public User() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWrongLink() {
        return wrongLink;
    }

    public void setWrongLink(String wrongLink) {
        this.wrongLink = wrongLink;
    }

    public int getDeleteTrackId() {
        return deleteTrackId;
    }

    public void setDeleteTrackId(int deleteTrackId) {
        this.deleteTrackId = deleteTrackId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getBotState() {
        return botState;
    }

    public void setBotState(String botState) {
        this.botState = botState;
    }
}
