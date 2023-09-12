CREATE TRIGGER decrement_book_quantity
    AFTER INSERT ON book_borrow
    FOR EACH ROW
BEGIN
    UPDATE book
    SET quantity = quantity - 1
    WHERE isbn = NEW.isbn;
END;


CREATE TRIGGER increment_book_quantity
    AFTER UPDATE ON book_borrow
    FOR EACH ROW
BEGIN
    IF NEW.status = 'RETURNED' AND OLD.status <> 'RETURNED' THEN
        UPDATE book
        SET quantity = quantity + 1
        WHERE isbn = NEW.isbn;
    END IF;
END;


CREATE PROCEDURE update_overdue_books()
BEGIN
    DECLARE `current_date` DATE;
    SET current_date = CURDATE();

    -- Update the status of overdue borrowings to 'LOST'
    UPDATE book_borrow
    SET status = 'LOST'
    WHERE status <> 'LOST' AND return_date < current_date;
END;




CREATE PROCEDURE update_overdue_books()
BEGIN
    -- Update the status of overdue borrowings to 'LOST'
    UPDATE book_borrow
    SET status = 'LOST'
    WHERE status <> 'LOST' AND return_date < borrow_date;
END;

# incrementing the quantity if the book while been returned
CREATE TRIGGER update_quantity_when_returned
    AFTER UPDATE ON book_borrow
    FOR EACH ROW
    BEGIN
        IF NEW.status = 'RETURNED' AND OLD.status <> 'RETURNED' THEN
            UPDATE book set quantity = quantity + 1
            WHERE isbn = NEW.isbn;
        END IF;
    end;



# every day this I have to check if the all the book taken is returned or been lost if return_date is passed
CREATE EVENT check_overdue_books
    ON SCHEDULE EVERY 1 DAY
    STARTS CURRENT_TIMESTAMP + INTERVAL 1 DAY
    DO
    BEGIN
        CALL update_overdue_books();
    END;


# when a book is lost it should automatically added to the table book_lost
CREATE TRIGGER insert_into_book_lost
    AFTER UPDATE ON book_borrow
    FOR EACH ROW
BEGIN
    IF NEW.status = 'LOST' AND OLD.status <> 'LOST' THEN
        INSERT INTO book_lost VALUES (NULL, NEW.isbn, NOW());
    END IF;
END;