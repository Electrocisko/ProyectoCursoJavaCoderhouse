insert INTO clients(name,lastname,doc)
VALUES
('Pedro','Garcia','1234'),
('Pablo','Lopez','1235'),
('Carla','Conte','1236'),
('Marina','Bal','1237');

insert into products(description,code,stock,price)
VALUES
('Remera','REM',20,3500),
('Camisa','CAM',10,6000),
('Short','SHT',8,5000),
('Gorro','GOR',20,3000);

Insert into invoice(created_at,total,client_id)
VALUES('202-01-01',0,1);

INSERT into invoice_details(invoice_id,amount,product_id,price)
VALUES(1,2,1,7000);

INSERT into invoice_details(invoice_id,amount,product_id,price)
VALUES(1,1,2,5300);

UPDATE  INVOICE SET TOTAL=19300
where id=1;