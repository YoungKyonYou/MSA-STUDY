-- DROP TABLE IF EXISTS catalog;
--
-- create table catalog (
--                          stock integer not null,
--                          unit_price integer not null,
--                          created_at timestamp(6) default CURRENT_TIMESTAMP not null unique,
--                          id bigint not null,
--                          product_id varchar(120) not null unique,
--                          product_name varchar(255) not null,
--                          primary key (id)
-- );

insert into catalog(id, product_id, product_name, stock, unit_price)
values(1,'CATALOG-001', 'Berlin', 100, 1500);
insert into catalog(id,product_id, product_name, stock, unit_price)
values(2,'CATALOG-002', 'Tokyo', 110, 1000);
insert into catalog(id,product_id, product_name, stock, unit_price)
values(3,'CATALOG-003', 'Stockholm', 120, 2000);