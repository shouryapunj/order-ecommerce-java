create table if not exists ecommerce_product
(
    id          bigserial       PRIMARY KEY,
    description varchar(60)     not null,
    price       NUMERIC (22, 10) not null,
    sku         varchar(60)     not null,
    title       varchar(60)     not null,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    version int4
);

insert into ecommerce_product (created_at, updated_at, description, price, sku, title, version)
values ('2022-10-17','2022-10-17', 'Whey', '1.99', '1001', 'protein',0);
