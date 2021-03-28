CREATE TABLE ORDERS(
    id int primary key auto_increment,
    price double not null,
    customer_id varchar(40) not null,
    bought_books varchar(500) not null,
    created_on datetime null,
    FOREIGN KEY (customer_id) references keycloak.user_entity(ID)
)