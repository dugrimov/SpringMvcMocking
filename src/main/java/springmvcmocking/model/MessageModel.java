package springmvcmocking.model;

public class MessageModel {

    private String text;
    private Integer userSenderId;
    private Integer userRecipientId;

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

}
