


INSERT INTO regiones (id, nombre) VALUES (1, 'Sudamérica');
INSERT INTO regiones (id, nombre) VALUES (2, 'Centroamérica');
INSERT INTO regiones (id, nombre) VALUES (3, 'Norteamérica');
INSERT INTO regiones (id, nombre) VALUES (4, 'Europa');
INSERT INTO regiones (id, nombre) VALUES (5, 'Asia');
INSERT INTO regiones (id, nombre) VALUES (6, 'Africa');
INSERT INTO regiones (id, nombre) VALUES (7, 'Oceanía');
INSERT INTO regiones (id, nombre) VALUES (8, 'Antártida');

INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(1, 'Andrés', 'Guzmán', 'profesor@bolsadeideas.com', '2018-01-01');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(2, 'Mr. John', 'Doe', 'john.doe@gmail.com', '2018-01-02');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(4, 'Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2018-01-03');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(4, 'Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2018-01-04');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(4, 'Erich', 'Gamma', 'erich.gamma@gmail.com', '2018-02-01');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(3, 'Richard', 'Helm', 'richard.helm@gmail.com', '2018-02-10');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(3, 'Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2018-02-18');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(3, 'John', 'Vlissides', 'john.vlissides@gmail.com', '2018-02-28');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(3, 'Dr. James', 'Gosling', 'james.gosling@gmail.com', '2018-03-03');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(5, 'Magma', 'Lee', 'magma.lee@gmail.com', '2018-03-04');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(6, 'Tornado', 'Roe', 'tornado.roe@gmail.com', '2018-03-05');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(7, 'Jade', 'Doe', 'jane.doe@gmail.com', '2018-03-06');

INSERT INTO `usuario`(username, password, enabled, nombre, apellido, email) VALUES('andres','$2a$10$5V7DzhgJzmLrB0LDa7CtiORURWHhl2VELqmekRGaM8DcHgXnn9c.G',1,'Andres', 'Guzman', 'andresguzman@gmail.com');
INSERT INTO `usuario`(username, password, enabled, nombre, apellido, email) VALUES('admin','$2a$10$.t2XQRb8HFLN3bL4L6ZT4uq5Npo.SnNuUU9Pajc96tnRDar3MJHv2',1,'Bernardo', 'Martinelli', 'bermartinelli@gmail.com');

INSERT INTO `rol`(nombre) VALUES('ROLE_USER');
INSERT INTO `rol`(nombre) VALUES('ROLE_ADMIN');
INSERT INTO `rol`(nombre) VALUES('ROLE_VISIT');



INSERT INTO `usuario_roles`(usuario_id, rol_id) VALUES(1,1);
INSERT INTO `usuario_roles`(usuario_id, rol_id) VALUES(2,2);
INSERT INTO `usuario_roles`(usuario_id, rol_id) VALUES(2,1);

INSERT INTO productos(nombre, precio,create_at) VALUES('Panasonic',800,now());
INSERT INTO productos(nombre,precio,create_at) VALUES('Sony',700,now());
INSERT INTO productos(nombre,precio,create_at) VALUES('Apple',1000,now());
INSERT INTO productos(nombre,precio,create_at) VALUES('Sony Notebook',1000,now());
INSERT INTO productos(nombre,precio,create_at) VALUES('Hewlett Packard',500,now());
INSERT INTO productos(nombre,precio,create_at) VALUES('Bianchi',600,now());
INSERT INTO productos(nombre,precio,create_at) VALUES('Nike',100,now());
INSERT INTO productos(nombre,precio,create_at) VALUES('Adidas',200,now());
INSERT INTO productos(nombre,precio,create_at) VALUES('Reebok',300,now());

INSERT INTO facturas(descripcion,observacion,cliente_id,create_at) VALUES('Factura equipos 1',null,1,now());
INSERT INTO facturas_item(cantidad,factura_id,producto_id) VALUES(1,1,1);
INSERT INTO facturas_item(cantidad,factura_id,producto_id) VALUES(2,1,4);
INSERT INTO facturas_item(cantidad,factura_id,producto_id) VALUES(1,1,5);
INSERT INTO facturas_item(cantidad,factura_id,producto_id) VALUES(1,1,7);

INSERT INTO facturas(descripcion,observacion,cliente_id,create_at) VALUES('Factura IPo 2',null,1,now());
INSERT INTO facturas_item(cantidad,factura_id,producto_id) VALUES(3,2,6);
