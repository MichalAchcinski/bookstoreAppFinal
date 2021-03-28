CREATE TABLE SHOPPING_INFORMATIONS(
                       id int primary key auto_increment,
                       customer_id varchar(40) not null,
                       first_name varchar(40) not null,
                       last_name varchar(40) not null,
                       address varchar(150) not null,
                       postcode varchar(20) not null,
                       city varchar(100) not null,
                       phone_number int not null,
                       created_on datetime null,
                       updated_on datetime null
)