CREATE DATABASE IF NOT EXISTS `library_management`;

USE `library_management`;

CREATE TABLE borrower (
  id INT AUTO_INCREMENT PRIMARY KEY,
  full_name VARCHAR(255),
  created_at DATETIME
);

CREATE TABLE librarian (
   id INT AUTO_INCREMENT PRIMARY KEY,
   firstname VARCHAR(255),
   lastname VARCHAR(255),
   email VARCHAR(255),
   password VARCHAR(255)
);

CREATE TABLE book (
  isbn VARCHAR(13) PRIMARY KEY,
  titre VARCHAR(255),
  description TEXT,
  author VARCHAR(255),
  quantity INT,
  created_by INT,
  created_at DATETIME,
  CONSTRAINT `fk_book_librarian` FOREIGN KEY (created_by) REFERENCES librarian(id)
    ON DELETE CASCADE ON UPDATE CASCADE

);

CREATE TABLE book_borrow (
    borrow_id INT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(13),
    borrower_id INT,
    status VARCHAR(50),
    borrow_date DATETIME,
    return_date DATETIME,
    created_at DATETIME,
    CONSTRAINT `fk_book_book_borrow` FOREIGN KEY (isbn) REFERENCES book(isbn)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_borrower_book_borrow` FOREIGN KEY (borrower_id) REFERENCES borrower(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE book_lost (
  id INT AUTO_INCREMENT PRIMARY KEY,
  isbn VARCHAR(13),
  created_at DATETIME,
  CONSTRAINT `fk_book_book_lost` FOREIGN KEY (isbn) REFERENCES book(isbn)
      ON DELETE CASCADE ON UPDATE CASCADE
);
