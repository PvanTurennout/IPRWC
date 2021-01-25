-- noinspection SqlNoDataSourceInspectionForFile

-- DB build script for IPRWC webshop (postgesql)

-- #cleanup for fresh install#
-- drop table Account cascade;
-- drop table Bracelet cascade;
-- drop table Watch cascade;
-- drop table Product cascade;
-- drop table Product_Item_Junction_Table cascade;
-- drop table WishList cascade;
-- drop table Refresh_Token cascade;
-- drop table Account_Group_Junction cascade;
-- drop table "Group" cascade;
-- drop table Role_Group_Junction cascade;
-- drop table Role cascade;
-- drop table "Order" cascade;
-- drop table Order_Products cascade;
-- drop table Customer cascade;
-- drop type Status cascade;

-- TYPES

CREATE TYPE Status AS ENUM (
    'received',
    'processed',
    'send',
    'delivered',
    'archived'
);

-- TABLES
CREATE TABLE Account (
    account_id SERIAL NOT NULL,
    mailaddress text UNIQUE NOT NULL,
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

CREATE TABLE Product_Item_Junction_Table(
    item_id SERIAL NOT NULL,
    item_watch_id int,
    item_bracelet_id int,
    CONSTRAINT item_pk PRIMARY KEY (item_id),
    CONSTRAINT item_watch_id_fk FOREIGN KEY (item_watch_id) REFERENCES Watch(watch_id),
    CONSTRAINT item_bracelet_id_fk FOREIGN KEY (item_bracelet_id) references Bracelet(bracelet_id)
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
    product_item int NOT NULL,
    CONSTRAINT item_fk FOREIGN KEY (product_item) REFERENCES Product_Item_Junction_Table(item_id),
    CONSTRAINT product_pk PRIMARY KEY (product_id)
);

CREATE TABLE WishList(
    wishlist_account_id int NOT NULL,
    wishlist_product_id int NOT NULL,
    wishlist_amount int,
    CONSTRAINT wishlist_pk PRIMARY KEY (wishlist_account_id, wishlist_product_id),
    CONSTRAINT account_fk FOREIGN KEY (wishlist_account_id) REFERENCES Account(account_id),
    CONSTRAINT product_fk FOREIGN Key (wishlist_product_id) REFERENCES Product(product_id)
);

CREATE TABLE Customer(
    customer_id SERIAL NOT NULL,
    account_ref_id int NOT NULL,
    first_name text NOT NULL,
    last_name text NOT NULL,
    address text NOT NULL,
    zipcode text NOT NULL,
    CONSTRAINT account_ref_fk FOREIGN KEY (account_ref_id) REFERENCES Account(account_id),
    CONSTRAINT customer_pk PRIMARY key (customer_id)
);

CREATE TABLE "Order"(
    order_id SERIAL NOT NULL,
    order_status Status NOT NULL,
    customer_ref_id int NOT NULL,
    CONSTRAINT customer_fk FOREIGN KEY (customer_ref_id) REFERENCES Customer(customer_id),
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

CREATE TABLE Refresh_Token(
    token text NOT NULL,
    CONSTRAINT refresh_token_pk PRIMARY KEY (token)
);

-- TRIGGERS WITH FUNCTIONS FOR ITEM JUNCTION TABLE
CREATE OR REPLACE FUNCTION Add_Watch_To_Item_Junction_Table()
    RETURNS TRIGGER AS
$$
BEGIN
    INSERT INTO Product_Item_Junction_Table
    (item_watch_id, item_bracelet_id) VALUES (NEW.watch_id, NULL);

    RETURN NEW;
END;
$$
    LANGUAGE 'plpgsql';

CREATE TRIGGER Watch_Insert_Add_To_Items
    AFTER INSERT ON Watch
    FOR EACH ROW
EXECUTE PROCEDURE Add_Watch_To_Item_Junction_Table();

CREATE OR REPLACE FUNCTION Add_Bracelet_To_Item_Junction_Table()
    RETURNS TRIGGER AS
$$
BEGIN
    INSERT INTO Product_Item_Junction_Table
    (item_watch_id, item_bracelet_id) VALUES (NULL, NEW.bracelet_id);

    RETURN NEW;
END;
$$
    LANGUAGE 'plpgsql';

CREATE TRIGGER Bracelet_Insert_Add_To_Items
    AFTER INSERT ON Bracelet
    FOR EACH ROW
EXECUTE PROCEDURE Add_Bracelet_To_Item_Junction_Table();

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

CREATE OR REPLACE FUNCTION delete_product(int)
    RETURNS VOID AS
$$
    DECLARE
        productId ALIAS FOR $1;
        itemId int;
        watchId Product_Item_Junction_Table.item_watch_id%type;
        braceletId Product_Item_Junction_Table.item_bracelet_id%type;
    BEGIN
        SELECT item_id INTO itemId FROM product_id_to_item WHERE product_id = productId;
        SELECT item_watch_id, item_bracelet_id INTO watchId, braceletId
            FROM Product_Item_Junction_Table WHERE item_id = itemId;

        DELETE FROM Product WHERE product_id = productId;
        DELETE FROM Product_Item_Junction_Table WHERE item_id = itemId;

        IF watchId IS NOT NULL THEN
            DELETE FROM Watch WHERE watch_id = watchId;
        ELSE
            DELETE FROM Bracelet WHERE bracelet_id = braceletId;
        END IF;
    END;
$$
LANGUAGE 'plpgsql';

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

CREATE OR REPLACE VIEW full_product_item_watch AS
SELECT product_id,
    product_name,
    product_brand,
    product_price,
    product_description,
    product_stock,
    product_sold,
    product_image_path,
    product_item,
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
JOIN full_watch ON (SELECT item_watch_id FROM Product_Item_Junction_Table WHERE item_id = product_item) = watch_id;

CREATE OR REPLACE VIEW full_product_item_bracelet AS
SELECT product_id,
       product_name,
       product_brand,
       product_price,
       product_description,
       product_stock,
       product_sold,
       product_image_path,
       product_item,
       bracelet_id,
       bracelet_length,
       bracelet_material,
       bracelet_style,
       bracelet_color
FROM Product
JOIN Bracelet ON (SELECT item_bracelet_id FROM Product_Item_Junction_Table WHERE item_id = product_item) = bracelet_id;

CREATE OR REPLACE VIEW product_id_to_item AS
SELECT product_id,
       item_id,
       item_watch_id,
       item_bracelet_id
FROM Product_Item_Junction_Table
JOIN Product ON Product_Item_Junction_Table.item_id = Product.product_item;

CREATE OR REPLACE VIEW All_Bracelet_Products AS
    SELECT full_product_item_bracelet.product_id,
           product_name,
           product_brand,
           product_price,
           product_description,
           product_stock,
           product_sold,
           product_image_path,
           product_item,
           bracelet_id,
           bracelet_length,
           bracelet_material,
           bracelet_style,
           bracelet_color
    FROM full_product_item_bracelet
    JOIN product_id_to_item ON full_product_item_bracelet.product_id = product_id_to_item.product_id
    WHERE product_id_to_item.item_bracelet_id IS NOT NULL;

CREATE OR REPLACE VIEW All_Watch_Products AS
    SELECT full_product_item_watch.product_id,
           product_name,
           product_brand,
           product_price,
           product_description,
           product_stock,
           product_sold,
           product_image_path,
           product_item,
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
    FROM full_product_item_watch
    JOIN product_id_to_item ON full_product_item_watch.product_id = product_id_to_item.product_id
    WHERE product_id_to_item.item_watch_id IS NOT NULL;

CREATE OR REPLACE VIEW top_selling_products AS
SELECT * FROM Product
ORDER BY product_sold DESC
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
INSERT INTO Role VALUES (9, 'order_read');
INSERT INTO Role VALUES (10, 'order_crud');
INSERT INTO Role VALUES (11, 'order_edit');
INSERT INTO Role VALUES (12, 'customer_read');
INSERT INTO Role VALUES (13, 'customer_crud');
INSERT INTO Role VALUES (14, 'customer_edit');


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
INSERT INTO Role_Group_Junction VALUES (2, 12);
INSERT INTO Role_Group_Junction VALUES (3, 1);
INSERT INTO Role_Group_Junction VALUES (3, 3);
INSERT INTO Role_Group_Junction VALUES (3, 5);
INSERT INTO Role_Group_Junction VALUES (3, 9);
INSERT INTO Role_Group_Junction VALUES (3, 11);

-- TEST DATA
INSERT INTO Account VALUES (1, 'admin@test.nl', '$2a$14$luaRK6hTU31rXF5KHoNFI.Bk9DiNkHcUvSGns6S5DQHkn/Ft5iFQm');
INSERT INTO Account VALUES (2, 'seller@test.nl', '$2a$14$KqiZariHZGSFYO28.vqXd.ldvVo7x0SbDwU6e8o8xZnJeXzAG2b0q');
INSERT INTO Account VALUES (3, 'customer@test.nl', '$2a$14$hexpFzs11NaiildA6vw/7eYzVT6lqkxrr53nN4XBUgWa0R8ZbDb7W');

UPDATE Account_Group_Junction SET group_id = 1 WHERE account_id = 1;
UPDATE Account_Group_Junction SET group_id = 2 WHERE account_id = 2;

-- Daydate goud groen
INSERT INTO Bracelet (bracelet_length, bracelet_material, bracelet_style, bracelet_color)
    VALUES (50, 'goud', 'schakels', 'goud');
INSERT INTO Watch (watch_bracelet_id, chassis_material, chassis_size, chassis_color_dial, chassis_color_pointers)
    VALUES (1, 'goud', 40, 'groen', 'goud');
INSERT INTO Product (product_name, product_brand, product_price,
                     product_description, product_stock, product_sold, product_image_path, product_item)
    VALUES ('Day-Date', 'Rolex', 14999.99,
            'Gouden Rolex Day-Date met groene wijzerplaat. Dagen in het Engels', 1, 0, 'rolex-daydate-goud.jpg', 2);

-- Seiko 5
INSERT INTO Bracelet (bracelet_length, bracelet_material, bracelet_style, bracelet_color)
    VALUES (20, 'staal', 'schakels', 'zilver');
INSERT INTO Watch (watch_bracelet_id, chassis_material, chassis_size, chassis_color_dial, chassis_color_pointers)
    VALUES (2, 'staal', 38, 'blauw', 'zilver');
INSERT INTO Product (product_name, product_brand, product_price,
                     product_description, product_stock, product_sold, product_image_path, product_item)
    VALUES ('SNKD99K1', 'Seiko 5', 179.99,
            'Stalen Seiko 5 met datum en dag van de week (Engels en Spaans). Waterdicht tot 50 meter.',
            10, 20, 'seiko-5-staal.jpg', 4);

-- Paul Hewitt
INSERT INTO Bracelet (bracelet_length, bracelet_material, bracelet_style, bracelet_color)
    VALUES (20, 'staal', 'mesh', 'zwart');
INSERT INTO Watch (watch_bracelet_id, chassis_material, chassis_size, chassis_color_dial, chassis_color_pointers)
    VALUES (3, 'staal', 40, 'zwart', 'zilver');
INSERT INTO Product (product_name, product_brand, product_price,
                     product_description, product_stock, product_sold, product_image_path, product_item)
    VALUES ('Sailor Black Sunray', 'Paul-Hewitt', 179.99,
            'Zwart stalen dress-watch van Paul-Hewitt kast is het sailor model met een zwart stalen mesh band. ' ||
            'Met gratis armband (zwart nylon touw met zwart stalen anker)', 100, 50, 'paulhewitt-sailor-zwart.jpg', 6);

-- AP royal oak staal wit
INSERT INTO Bracelet (bracelet_length, bracelet_material, bracelet_style, bracelet_color)
    VALUES (30, 'staal', 'schakels', 'staal');
INSERT INTO Watch (watch_bracelet_id, chassis_material, chassis_size, chassis_color_dial, chassis_color_pointers)
    VALUES ('staal', 41, 'wit', 'zilver');
INSERT INTO Product (product_name, product_brand, product_price,
                     product_description, product_stock, product_sold, product_image_path, product_item)
    VALUES ('Royal Oak Ref 15400', 'Audemars Piquet', 24999.99,
            'stalen ap royal-oak referentie 15400. Waterbestendigheid: 5 ATM', 1, 2, 'ap-royaloak15400-staal.jpg', 8);
