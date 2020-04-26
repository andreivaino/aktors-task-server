INSERT INTO clients(personal_id, first_name, last_name, phone, country, address)
VALUES
(3880892256, 'Andrei', 'Vaino', '+372 000 000 00', 'Estonia', 'Tallinn, Test Str. 0 - 0'),
(3880892257,'Jack', 'Oneil', '+372 000 000 01', 'Germany', 'Berlin, Test Str. 0 - 0'),
(3880892299,'Lee', 'Jackson', '+372 000 000 01', 'Russia', 'Moscow, Test Str. 0 - 0'),
(3880892258, 'Samantha', 'Fox', '+372 000 000 02', 'Finland', 'Helsinki, Test Str. 0 - 0');

INSERT INTO products(name, base_price, description, release_date, barcode)
VALUES
('Mobile Phone', 356.25, 'Info about Mobile Phone.', (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')), '5644565684362'),
('Laptop', 1356.25, 'Info about laptop.', (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')),'5648833675162'),
('Bike', 3256.25, 'Info about Bike.', (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')),'9367516784362'),
('Phone', 356.25, 'Info about Phone.', (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')),'1643675184362'),
('Book', 56.5, 'Info about Book.', (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')),'4648443675152'),
('Icecream', 6.7, 'Info about Icecream.', (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')),'1648436751752'),
('House', 224556.00, 'Info about House.', (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')),'9648367514498'),
('Bicycle', 123.5, 'Info about Bicycle.', (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')),'8763675184452'),
('TV bench', 118.00, 'Info about TV bench with drawers.', (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')),'4344563675152'),
('Glass-door cabinet', 129.5, 'Info about Glass-door cabinet.', (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')),'7689367514452'),
('Wash-stand', 378.5, 'Info about Wash-stand.', (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')),'4367511334654'),
('Shower curtain', 7.99, 'Info about Shower curtain.', (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')),'4367516484654'),
('Mirror', 5.5, 'Info about Mirror.', (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')),'3675112344452'),
('2Mirror', 5.5, 'Info about Mirror.', (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')),'1367514344452'),
('3Mirror', 5.5, 'Info about Mirror.', (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')),'1534443675152'),
('Microwave oven', 234.90, 'Info about Microwave oven.', (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')),'3675126434362'),
('Lamborgini', 150500.99, 'Info about Lamborgini.', (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')),'1234449012643'),
('Built-in refrigerator', 399, 'Info about Built-in refrigerator.', (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')),'1264314344491'),
('Wine cooler', 599.5, 'Info about Wine cooler.', (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')),'1264314377491'),
('Hair removal device', 17.3, 'Info about Hair removal device.', (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')),'2126436434393');

INSERT INTO orders(price, transaction_date, client_personal_id)
VALUES
(536.25, (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')), 3880892256),
(1497.95, (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')), 3880892257),
(228196.25, (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')), 3880892258),
(722.64, (select DATEADD('SECOND', RAND() * (1588275200 - 1486825600) + 1486825600, DATE '1970-01-01')), 3880892258);

UPDATE products SET order_number = 1 WHERE barcode = 5644565684362;
UPDATE products SET order_number = 2 WHERE barcode = 5648833675162;
UPDATE products SET order_number = 3 WHERE barcode = 9367516784362;
UPDATE products SET order_number = 4 WHERE barcode = 1643675184362;
UPDATE products SET order_number = 1 WHERE barcode = 4648443675152;
UPDATE products SET order_number = 2 WHERE barcode = 1648436751752;
UPDATE products SET order_number = 3 WHERE barcode = 9648367514498;
UPDATE products SET order_number = 4 WHERE barcode = 8763675184452;
UPDATE products SET order_number = 1 WHERE barcode = 4344563675152;
UPDATE products SET order_number = 2 WHERE barcode = 7689367514452;
UPDATE products SET order_number = 3 WHERE barcode = 4367511334654;
UPDATE products SET order_number = 4 WHERE barcode = 4367516484654;
UPDATE products SET order_number = 1 WHERE barcode = 3675112344452;
UPDATE products SET order_number = 2 WHERE barcode = 1367514344452;
UPDATE products SET order_number = 3 WHERE barcode = 1534443675152;
UPDATE products SET order_number = 4 WHERE barcode = 3675126434362;

