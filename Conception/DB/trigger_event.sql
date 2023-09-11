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
    DECLARE todysDate DATE;
    SET todysDate = CURDATE();

    -- Update the status of overdue borrowings to 'LOST'
    UPDATE book_borrow
    SET status = 'LOST'
    WHERE status <> 'LOST' AND return_date < todysDate;
END;

CREATE TRIGGER update_quantity_when_returned
    AFTER UPDATE ON book_borrow
    FOR EACH ROW
    BEGIN
        IF NEW.status = 'RETURNED' AND OLD.status <> 'RETURNED' THEN
            UPDATE book set quantity = quantity + 1
            WHERE isbn = NEW.isbn;
        END IF;
    end;

CREATE EVENT check_overdue_books
    ON SCHEDULE EVERY 1 DAY
    DO
    BEGIN
        CALL update_overdue_books();
    END;