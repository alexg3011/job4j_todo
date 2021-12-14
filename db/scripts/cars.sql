create table engines(
                       id serial primary key,
                       name text
);

create table drivers(
                       id serial primary key,
                       name text
);

create table cars(
                    id serial primary key,
                    name text,
                    engine_id int not null unique references engines(id)
);

create table history_owner(
                              id serial primary key,
                              car_id int not null references cars(id),
                              driver_id int not null references drivers(id)

);
