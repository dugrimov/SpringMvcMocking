package springmvcmocking.persistence;

import java.util.Date;

public class Message {
    private Long id;
    private String text;
    private Integer userSenderId;
    private Integer userRecipientId;
    private Date messageDate;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getUserSenderId() {
        return userSenderId;
    }

    public void setUserSenderId(Integer userSenderId) {
        this.userSenderId = userSenderId;
    }

    public Integer getUserRecipientId() {
        return userRecipientId;
    }

    public void setUserRecipientId(Integer userRecipientId) {
        this.userRecipientId = userRecipientId;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }
    
}
