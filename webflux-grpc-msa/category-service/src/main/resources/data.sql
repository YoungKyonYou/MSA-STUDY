DROP TABLE IF EXISTS catalog;

CREATE TABLE catalog (
                         stock INT NOT NULL,
                         unit_price INT NOT NULL,
                         created_at TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL UNIQUE,
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         product_id VARCHAR(120) NOT NULL UNIQUE,
                         product_name VARCHAR(255) NOT NULL,
                         PRIMARY KEY (id)
);

insert into catalog(id, product_id, product_name, stock, unit_price)
values(1,'CATALOG-001', 'Berlin', 100, 1500);
insert into catalog(id,product_id, product_name, stock, unit_price)
values(2,'CATALOG-002', 'Tokyo', 110, 1000);
insert into catalog(id,product_id, product_name, stock, unit_price)
values(3,'CATALOG-003', 'Stockholm', 120, 2000);