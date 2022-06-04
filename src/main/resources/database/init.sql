create table if not exists user_role (
    user_id int8 not null,
    roles varchar(255)
);

create table if not exists users (
    id int8 not null,
    activation_code varchar(255),
    active boolean not null,
    email varchar(255),
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
);

create table if not exists menu (
    id_food int8 not null,
    cost_food float8,
    filename varchar(255),
    is_have boolean not null,
    name_food varchar(255),
    type_food varchar(255),
    user_id int8,
    primary key (id_food)
);

create table if not exists past_order (
    id_past_order int8 not null,
    client_id int8,
    is_done boolean,
    past_order_text varchar(255),
    date varchar(255),
    past_id int8,
    primary key (id_past_order)
);

create table if not exists past_order_order_meal (
    past_order_id_past_order int8 not null,
    order_meal int4,
    order_meal_key int8 not null,
    primary key (past_order_id_past_order, order_meal_key)
);

create table if not exists real_order (
    id_order int8 not null,
    client_id int8,
    is_done boolean,
    primary key (id_order)
);

create table if not exists real_order_order_meal (
    real_order_id_order int8 not null,
    order_meal int4,
    order_meal_key int8 not null,
    primary key (real_order_id_order, order_meal_key)
);
