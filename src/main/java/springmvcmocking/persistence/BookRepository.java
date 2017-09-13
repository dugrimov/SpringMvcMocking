package springmvcmocking.persistence;

public interface BookRepository {
    Book get(Integer id);
    Integer create(Book book);
    void update(Book book);
    void delete(Integer id);
}
