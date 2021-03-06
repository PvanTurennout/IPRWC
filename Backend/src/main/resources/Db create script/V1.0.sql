-- noinspection SqlNoDataSourceInspectionForFile

-- DB build script for IPRWC webshop (postgesql)

-- #cleanup for fresh install#
-- drop table Account cascade;
-- drop table Bracelet cascade;
-- drop table Watch cascade;
-- drop table Product cascade;
-- drop table Product_Item_Junction_Table cascade;
-- drop table WishList cascade;
-- drop table Account_Group_Junction cascade;
-- drop table "Group" cascade;
-- drop table Role_Group_Junction cascade;
-- drop table Role cascade;
-- drop table "Order" cascade;
-- drop table Order_Products cascade;sql enum
-- drop type Status cascade;

-- TYPES

-- TABLES
CREATE TABLE Account (
    account_id SERIAL NOT NULL,
    mailAddress text UNIQUE NOT NULL,
    password text NOT NULL,
    CONSTRAINT account_primary_key PRIMARY KEY (account_id)
);

CREATE TABLE "Group" (
    group_id SERIAL NOT NULL,
    group_name text UNIQUE NOT NULL,
    CONSTRAINT group_pk PRIMARY KEY (group_id)
);

CREATE TABLE Role (
    role_id SERIAL NOT NULL,
    role_name text UNIQUE NOT NULL,
    CONSTRAINT role_pk PRIMARY KEY (role_id)
);


CREATE TABLE Account_Group_Junction(
    account_id int NOT NULL,
    group_id int NOT NULL,
    CONSTRAINT group_account_fk FOREIGN KEY (group_id) REFERENCES "Group"(group_id),
    CONSTRAINT account_fk FOREIGN KEY (account_id) REFERENCES Account(account_id),
    CONSTRAINT account_group_pk PRIMARY KEY (account_id, group_id)
);

CREATE TABLE Role_Group_Junction(
    group_id int NOT NULL,
    role_id int NOT NULL,
    CONSTRAINT group_role_fk FOREIGN KEY (group_id) REFERENCES "Group"(group_id),
    CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES Role(role_id),
    CONSTRAINT role_group_pk PRIMARY KEY (group_id, role_id)
);

CREATE TABLE Bracelet(
    bracelet_id SERIAL NOT NULL,
    bracelet_length int,
    bracelet_material text,
    bracelet_style text,
    bracelet_color text,
    CONSTRAINT bracelet_pk PRIMARY KEY (bracelet_id)
);

CREATE TABLE Watch(
    watch_id SERIAL NOT NULL,
    watch_bracelet_id int NOT NULL,
    chassis_material text,
    chassis_size int,
    chassis_color_dial text,
    chassis_color_pointers text,
    CONSTRAINT watch_pk PRIMARY KEY (watch_id),
    CONSTRAINT bracelet_fk FOREIGN KEY (watch_bracelet_id) REFERENCES Bracelet(bracelet_id)
);

CREATE TABLE Product(
    product_id SERIAL NOT NULL,
    product_name text,
    product_brand text,
    product_price double precision,
    product_description text,
    product_stock int,
    product_sold int,
    product_image_path text,
    product_watch int NOT NULL,
    CONSTRAINT watch_fk FOREIGN KEY (product_watch) REFERENCES Watch(watch_id),
    CONSTRAINT product_pk PRIMARY KEY (product_id)
);

CREATE TABLE WishList(
    wishlist_account_id int NOT NULL,
    wishlist_product_id int NOT NULL,
    CONSTRAINT wishlist_pk PRIMARY KEY (wishlist_account_id, wishlist_product_id),
    CONSTRAINT account_fk FOREIGN KEY (wishlist_account_id) REFERENCES Account(account_id),
    CONSTRAINT product_fk FOREIGN Key (wishlist_product_id) REFERENCES Product(product_id)
);

CREATE TABLE Account_Order(
    account_id_ref int NOT NULL,
    order_id_ref int NOT NULL,
    CONSTRAINT account_fk FOREIGN KEY (account_id_ref) REFERENCES Account(account_id),
    CONSTRAINT order_fk FOREIGN KEY (order_id_ref) REFERENCES "Order"(order_id),
    CONSTRAINT account_order_pk PRIMARY KEY (account_id_ref, order_id_ref)
);

CREATE TABLE "Order"(
    order_id SERIAL NOT NULL,
    order_status int NOT NULL,
    first_name text NOT NULL,
    last_name text NOT NULL,
    address text NOT NULL,
    zipcode text NOT NULL,
    town text NOT NULL,
    shipping_method text NOT NULL,
    totalPrice double precision NOT NULL,
    order_date timestamp without time zone DEFAULT now() NOT NULL,
    CONSTRAINT order_pk PRIMARY KEY (order_id)
);

CREATE TABLE Order_Products(
    order_ref_id int NOT NULL,
    product_ref_id int NOT NULL,
    amount int NOT NULL,
    CONSTRAINT order_fk FOREIGN KEY (order_ref_id) REFERENCES "Order"(order_id),
    CONSTRAINT product_fk FOREIGN KEY (product_ref_id) REFERENCES Product(product_id),
    CONSTRAINT order_product_pk PRIMARY KEY (order_ref_id, product_ref_id)
);


-- TRIGGERS & FUNCTIONS

CREATE OR REPLACE FUNCTION Add_Role_To_Account()
    RETURNS TRIGGER AS
$$
BEGIN
    INSERT INTO Account_Group_Junction VALUES (NEW.account_id, 3);

    RETURN NEW;
END;
$$
LANGUAGE 'plpgsql';

CREATE TRIGGER Add_Account_To_Customer_Group
    AFTER INSERT ON Account
    FOR EACH ROW
EXECUTE PROCEDURE Add_Role_To_Account();

-- VIEWS
CREATE OR REPLACE VIEW full_watch AS
SELECT watch_id,
       watch_bracelet_id,
       chassis_material,
       chassis_size,
       chassis_color_dial,
       chassis_color_pointers,
       bracelet_id,
       bracelet_length,
       bracelet_material,
       bracelet_style,
       bracelet_color
FROM Watch
JOIN Bracelet ON watch_bracelet_id = bracelet_id;

CREATE OR REPLACE VIEW full_product AS
SELECT product_id,
    product_name,
    product_brand,
    product_price,
    product_description,
    product_stock,
    product_sold,
    product_image_path,
    watch_id,
    watch_bracelet_id,
    chassis_material,
    chassis_size,
    chassis_color_dial,
    chassis_color_pointers,
    bracelet_id,
    bracelet_length,
    bracelet_material,
    bracelet_style,
    bracelet_color
FROM Product
JOIN full_watch ON product_watch = watch_id;

CREATE OR REPLACE VIEW top_selling_products AS
SELECT * FROM Product WHERE product_stock != 0
ORDER BY product_sold DESC
LIMIT 3;

CREATE OR REPLACE VIEW highlighted_products AS
SELECT * FROM Product WHERE product_stock != 0
ORDER BY product_sold
LIMIT 3;

CREATE OR REPLACE VIEW full_account AS
    SELECT
           a.account_id,
           a.mailaddress,
           a.password,
           g.group_name
    FROM Account_Group_Junction
    JOIN "Group" g ON Account_Group_Junction.group_id = g.group_id
    JOIN Account a ON Account_Group_Junction.account_id = a.account_id
;

-- GROUP & ROLE DATA
INSERT INTO "Group" VALUES (1, 'Admin');
INSERT INTO "Group" VALUES (2, 'Seller');
INSERT INTO "Group" VALUES (3, 'Customer');

INSERT INTO Role VALUES (1, 'product_read');
INSERT INTO Role VALUES (2, 'product_crud');
INSERT INTO Role VALUES (3, 'watch_read');
INSERT INTO Role VALUES (4, 'watch_crud');
INSERT INTO Role VALUES (5, 'bracelet_read');
INSERT INTO Role VALUES (6, 'bracelet_crud');
INSERT INTO Role VALUES (7, 'account_read');
INSERT INTO Role VALUES (8, 'account_crud');
INSERT INTO Role VALUES (9, 'order_read_own');
INSERT INTO Role VALUES (10, 'order_delete');
INSERT INTO Role VALUES (11, 'order_change_status');
INSERT INTO Role VALUES (12, 'order_update');
INSERT INTO Role VALUES (13, 'order_create');
INSERT INTO Role VALUES (14, 'order_read_all');


INSERT INTO Role_Group_Junction VALUES (1, 1);
INSERT INTO Role_Group_Junction VALUES (1, 2);
INSERT INTO Role_Group_Junction VALUES (1, 3);
INSERT INTO Role_Group_Junction VALUES (1, 4);
INSERT INTO Role_Group_Junction VALUES (1, 5);
INSERT INTO Role_Group_Junction VALUES (1, 6);
INSERT INTO Role_Group_Junction VALUES (1, 7);
INSERT INTO Role_Group_Junction VALUES (1, 8);
INSERT INTO Role_Group_Junction VALUES (1, 9);
INSERT INTO Role_Group_Junction VALUES (1, 10);
INSERT INTO Role_Group_Junction VALUES (1, 11);
INSERT INTO Role_Group_Junction VALUES (1, 12);
INSERT INTO Role_Group_Junction VALUES (1, 13);
INSERT INTO Role_Group_Junction VALUES (1, 14);
INSERT INTO Role_Group_Junction VALUES (2, 1);
INSERT INTO Role_Group_Junction VALUES (2, 2);
INSERT INTO Role_Group_Junction VALUES (2, 3);
INSERT INTO Role_Group_Junction VALUES (2, 5);
INSERT INTO Role_Group_Junction VALUES (2, 9);
INSERT INTO Role_Group_Junction VALUES (2, 11);
INSERT INTO Role_Group_Junction VALUES (2, 13);
INSERT INTO Role_Group_Junction VALUES (2, 14);
INSERT INTO Role_Group_Junction VALUES (3, 1);
INSERT INTO Role_Group_Junction VALUES (3, 3);
INSERT INTO Role_Group_Junction VALUES (3, 5);
INSERT INTO Role_Group_Junction VALUES (3, 9);
INSERT INTO Role_Group_Junction VALUES (3, 13);

-- TEST DATA
INSERT INTO Account VALUES (1, 'admin@test.nl', '$2a$14$Yefz8cWL2.FW83y4GDIhN.idKKNBIJVnIn1oU5Ty..t64CcA9ayYy');
-- password: Admin@8080
INSERT INTO Account VALUES (2, 'seller@test.nl', '$2a$14$dDsKArVU61.ve7bW3KcZbubNaOYVmWqJaKgcLZfiMq/asegr.a8gq');
-- password: Seller@8080
INSERT INTO Account VALUES (3, 'customer@test.nl', '$2a$14$DrAZtm7zZRFDbqKtfha.K.mQ.VFc7kK8RAOk9SNoKXwwJaGehHhTS');
-- password: Customer@8080

UPDATE Account_Group_Junction SET group_id = 1 WHERE account_id = 1;
UPDATE Account_Group_Junction SET group_id = 2 WHERE account_id = 2;

INSERT INTO WishList VALUES (1, 1);
INSERT INTO WishList VALUES (2, 2);
INSERT INTO WishList VALUES (3, 3);

-- Daydate goud groen
INSERT INTO Bracelet (bracelet_length, bracelet_material, bracelet_style, bracelet_color)
    VALUES (50, 'goud', 'schakels', 'goud');
INSERT INTO Watch (watch_bracelet_id, chassis_material, chassis_size, chassis_color_dial, chassis_color_pointers)
    VALUES (1, 'goud', 40, 'groen', 'goud');
INSERT INTO Product (product_name, product_brand, product_price,
                     product_description, product_stock, product_sold, product_image_path, product_watch)
    VALUES ('Day-Date', 'Rolex', 14999.99,
            'Gouden Rolex Day-Date met groene wijzerplaat. Dagen in het Engels', 1, 0, 'rolex-daydate-goud.jpg', 1);

-- Seiko 5
INSERT INTO Bracelet (bracelet_length, bracelet_material, bracelet_style, bracelet_color)
    VALUES (20, 'staal', 'schakels', 'zilver');
INSERT INTO Watch (watch_bracelet_id, chassis_material, chassis_size, chassis_color_dial, chassis_color_pointers)
    VALUES (2, 'staal', 38, 'blauw', 'zilver');
INSERT INTO Product (product_name, product_brand, product_price,
                     product_description, product_stock, product_sold, product_image_path, product_watch)
    VALUES ('SNKD99K1', 'Seiko 5', 179.99,
            'Stalen Seiko 5 met datum en dag van de week (Engels en Spaans). Waterdicht tot 50 meter.',
            10, 20, 'seiko-5-staal.jpg', 2);

-- Paul Hewitt
INSERT INTO Bracelet (bracelet_length, bracelet_material, bracelet_style, bracelet_color)
    VALUES (20, 'staal', 'mesh', 'zwart');
INSERT INTO Watch (watch_bracelet_id, chassis_material, chassis_size, chassis_color_dial, chassis_color_pointers)
    VALUES (3, 'staal', 40, 'zwart', 'zilver');
INSERT INTO Product (product_name, product_brand, product_price,
                     product_description, product_stock, product_sold, product_image_path, product_watch)
    VALUES ('Sailor Black Sunray', 'Paul-Hewitt', 179.99,
            'Zwart stalen dress-watch van Paul-Hewitt kast is het sailor model met een zwart stalen mesh band. ' ||
            'Met gratis armband (zwart nylon touw met zwart stalen anker)', 100, 50, 'paulhewitt-sailor-zwart.jpg', 3);

-- AP royal oak staal wit
INSERT INTO Bracelet (bracelet_length, bracelet_material, bracelet_style, bracelet_color)
    VALUES (30, 'staal', 'schakels', 'staal');
INSERT INTO Watch (watch_bracelet_id, chassis_material, chassis_size, chassis_color_dial, chassis_color_pointers)
    VALUES ('staal', 41, 'wit', 'zilver');
INSERT INTO Product (product_name, product_brand, product_price,
                     product_description, product_stock, product_sold, product_image_path, product_watch)
    VALUES ('Royal Oak Ref 15400', 'Audemars Piquet', 24999.99,
            'stalen ap royal-oak referentie 15400. Waterbestendigheid: 5 ATM', 1, 2, 'ap-royaloak15400-staal.jpg', 4);
