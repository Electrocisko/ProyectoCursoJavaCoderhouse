CREATE TABLE clients(
id Integer PRIMARY KEY AUTO_INCREMENT,
name varchar(75) NOT NULL,
lastname varchar(75) NOT NULL,
docnumber varchar(11) UNIQUE NOT NULL
);

CREATE TABLE invoice(
id Integer PRIMARY KEY AUTO_INCREMENT,
created_at varchar(12) NOT NULL,
total double,
client_id Integer,
CONSTRAINT FK_CLIENT_ID FOREIGN KEY(client_id) REFERENCES clients(id)
);

CREATE TABLE products(
id Integer PRIMARY KEY AUTO_INCREMENT,
description varchar(150),
code varchar(50) UNIQUE NOT NULL,
stock Integer,
price double
);

CREATE TABLE invoice_details(
invoice_detail_id Integer PRIMARY KEY AUTO_INCREMENT,
invoice_id Integer,
amount Integer,
product_id Integer,
price double,
CONSTRAINT FK_INVOICE_ID FOREIGN KEY(invoice_id) REFERENCES invoice(id),
CONSTRAINT FK_PRODUCT_ID FOREIGN KEY(product_id) REFERENCES products(id)
);



