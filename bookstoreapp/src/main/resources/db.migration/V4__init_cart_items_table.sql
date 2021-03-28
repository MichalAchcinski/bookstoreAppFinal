CREATE TABLE CART_ITEMS(
    id int primary key auto_increment,
    customer_id varchar(40) not null,
    book_id int,
    FOREIGN KEY (book_id) references books(id)
)