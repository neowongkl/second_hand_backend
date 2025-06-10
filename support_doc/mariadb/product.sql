CREATE OR REPLACE TABLE product(
    id bigint auto_increment,
    name varchar(255) not null,
    length DOUBLE(5,2) not null,
    width DOUBLE(5,2) not null,
    height DOUBLE(5,2) not null,
    price DOUBLE(5,2) not null,
    description varchar(255),
    category varchar(20),
    status varchar(20),
    created_by varchar(255) not null,
    created_on datetime not null,
    last_modified_by varchar(255) not null,
    last_modified_date datetime not null,
    primary key(id)
);

INSERT INTO demo.product
(id, name, `length`, width, height, price, description, category, status, created_by, created_on, last_modified_by, last_modified_date)
VALUES(1, 'temp', 10.1, 10.2, 10.3, 100.4, 'temp description', 'OTHERS', 'STOCK', 'neo', current_timestamp, 'neo', current_timestamp);

INSERT INTO demo.product
(id, name, `length`, width, height, price, description, category, status, created_by, created_on, last_modified_by, last_modified_date)
VALUES(2, 'temp2', 20.1, 20.2, 20.3, 200.4, 'temp description 2', 'OTHERS', 'STOCK', 'neo', current_timestamp, 'neo', current_timestamp);

INSERT INTO demo.product
(id, name, `length`, width, height, price, description, category, status, created_by, created_on, last_modified_by, last_modified_date)
VALUES(3, 'temp3', 30.1, 30.2, 30.3, 300.4, 'temp description 3', 'OTHERS', 'STOCK', 'neo', current_timestamp, 'neo', current_timestamp);
