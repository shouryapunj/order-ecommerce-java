create table if not exists ecommerce_payment
(
    id                  bigserial       PRIMARY KEY,
    amount              NUMERIC (22, 10) not null,
    confirmation_number varchar(60)      not null,
    payment_mode        varchar(60)      not null,
    payment_status      varchar(60)      not null,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    version int4
);

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

create table if not exists ecommerce_address
(
    id             bigserial       PRIMARY KEY,
    address1       varchar(60) not null,
    address2       varchar(60) not null,
    city           varchar(60) not null,
    email          varchar(60) not null,
    phone          varchar(60) not null,
    state          varchar(60) not null,
    zip            varchar(60) not null,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    version int4
);

create table if not exists ecommerce_order
(
    id                  bigserial       PRIMARY KEY,
    customer_id         varchar(60),
    order_status        varchar(60),
    shipping_charges    NUMERIC (22, 10),
    shipping_mode       varchar(60),
    sub_total           NUMERIC (22, 10),
    tax                 NUMERIC (22, 10),
    title               varchar(60),
    total_amt           NUMERIC (22, 10),
    payment_id          bigserial,
    billing_address_id  bigserial,
    shipping_address_id bigserial,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    version int4,
    FOREIGN KEY (payment_id) REFERENCES ecommerce_payment (id),
    FOREIGN KEY (billing_address_id) REFERENCES ecommerce_address (id),
    FOREIGN KEY (shipping_address_id) REFERENCES ecommerce_address (id)
);

create table if not exists ecommerce_order_item
(
    order_id       bigserial,
    product_id     bigserial,
    quantity       varchar(60) not null,

    PRIMARY KEY (order_id,product_id),
    FOREIGN KEY (order_id) REFERENCES ecommerce_order (id),
    FOREIGN KEY (product_id) REFERENCES ecommerce_product (id)
);

insert into ecommerce_product (created_at, updated_at, description, price, sku, title, version)
values (current_date,current_date, 'Whey', '1.99', '1001', 'protein', 0);

insert into ecommerce_product (created_at, updated_at,  description, price, sku, title, version)
values (current_date,current_date, 'Orgain', '5.99', '1005', 'protein', 0);