package springmvcmocking.persistence;

public interface UserRepository {
    Integer create(User user);
    void update(User user);
    void setLastViewedMessageId(Integer userId, Long messageId);
}
