
INSERT INTO `librarian` (`id`, `firstname`, `lastname`, `email`, `password`) VALUES
         (1, 'khalid', 'fifel', 'khalid@gmail.com', 'password');


INSERT INTO `borrower` (`id`, `full_name`, `created_at`) VALUES
        (1, 'abd elouahed zahim', '2023-09-05 14:44:50'),
        (2, 'yassine el ouahi', '2023-09-05 14:44:50');


INSERT INTO `book` (`isbn`, `titre`, `description`, `author`, `quantity`, `created_by`, `created_at`) VALUES
        ('ISBN1', 'The Alchemist', 'A novel about following your dreams.', 'Paulo Coelho', 5, 1, '2023-09-05 14:43:21'),
        ('ISBN2', 'One Thousand and One Nights', 'A collection of Arabian folktales.', 'Various (Arabic Folklore)', 8, 1, '2023-09-05 14:43:21'),
        ('ISBN3', 'The Yacoubian Building', 'A novel exploring life in Cairo.', 'Alaa Al Aswany', 7, 1, '2023-09-05 14:43:21'),
        ('ISBN4', 'Season of Migration to the North', 'A classic Arabic novel.', 'Tayeb Salih', 6, 1, '2023-09-05 14:43:21'),
        ('ISBN5', 'The Bastard of Istanbul', 'A contemporary novel with Turkish and Arabic elements.', 'Elif Shafak', 5, 1, '2023-09-05 14:43:21');



INSERT INTO `book_borrow` (`isbn`, `borrower_id`, `status`, `borrow_date`, `return_date`) VALUES
         ('ISBN1', 1, 'TAKEN', '2023-09-05 14:44:55', NULL),
         ('ISBN2', 2, 'TAKEN', '2023-09-05 14:44:55', NULL),
         ('ISBN3', 1, 'RETURNED', '2023-09-05 14:44:55', NULL);
