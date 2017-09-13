package springmvcmocking.samplecommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springmvcmocking.model.BookModel;
import springmvcmocking.persistence.Book;
import springmvcmocking.persistence.BookRepository;

@Component
public class CreateOrUpdateBookCommand {

    @Autowired
    private BookRepository bookRepository;

    public Integer execute(BookModel bookModel) {
        if (bookModel == null) {
            throw new IllegalArgumentException();
        }
        Book book = new Book();
        book.setId(bookModel.getId());
        book.setTitle(bookModel.getTitle());
        book.setAuthorName(bookModel.getAuthorName());
        if (bookModel.getId() > 0) {
            bookRepository.update(book);
            return bookModel.getId();
        }
        return bookRepository.create(book);
    }
}
