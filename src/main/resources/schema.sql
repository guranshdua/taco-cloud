create table if not exists Taco_Order(
                                         id SERIAL primary key not null,
                                         delivery_Name varchar(50) not null,
                                         delivery_Street varchar(50) not null,
                                         delivery_City varchar(50) not null,
                                         delivery_State varchar(10) not null,
                                         delivery_Zip varchar(10) not null,
                                         ccNumber varchar(16) not null,
                                         cc_expiration varchar(5) not null,
                                         cc_cvv varchar(3) not null,
                                         placed_at timestamp not null
);

create table if not exists Taco(
                                   id SERIAL primary key not null,
                                   name varchar(50) not null,
                                   taco_order bigint not null,
                                   taco_order_key bigint not null ,
                                   created_at timestamp not null
);

create table if not exists Ingredient_Ref(
                                             ingredient varchar(4) not null,
                                             taco bigint not null ,
                                             taco_key bigint not null
);

create table if not exists Ingredient(
                                         id varchar(4) not null primary key,
                                         name varchar(25) not null ,
                                         type varchar(10) not null
);

alter table Taco_Order add constraint Taco_Order_pkey PRIMARY KEY(id);
alter table Taco add constraint Taco_pkey PRIMARY KEY(id);

alter table Taco add foreign key (taco_order) references Taco_Order(id);
alter table Ingredient_Ref add foreign key (ingredient) references Ingredient(id);

delete from ingredient_ref;
delete from Taco;
delete from Taco_Order;

delete from Ingredient;
insert into Ingredient (id,name,type) values ('FLTO','Flour Tortilla', 'WRAP');
insert into Ingredient (id,name,type) values ('COTO','Corn Tortilla', 'WRAP');
insert into Ingredient (id,name,type) values ('GRBF','Ground Beef', 'PROTEIN');
insert into Ingredient (id,name,type) values ('CARN','Carnitas', 'PROTEIN');
insert into Ingredient (id,name,type) values ('TMTO','Tomato', 'VEGGIES');
insert into Ingredient (id,name,type) values ('LETC','Lettuce', 'VEGGIES');
insert into Ingredient (id,name,type) values ('CHED','Cheddar', 'CHEESE');
insert into Ingredient (id,name,type) values ('JACK','Monterrey Jack', 'CHEESE');
insert into Ingredient (id,name,type) values ('SLSA','Salsa', 'SAUCE');
insert into Ingredient (id,name,type) values ('SRCR','Sour Cream', 'SAUCE');

