-- insert for initializing the database with default users
insert into users (username, password_hash, roles, creation_time, created_by) values ('adas', '$2a$10$9uj1/JpNjlZyBO41pDxQ1exdp/STdLDyFRKA2AvZ9TP2RngHrwkA6', 'ROLE_ADMIN,ROLE_USER', current_timestamp, 'system');
insert into users (username, password_hash, roles, creation_time, created_by) values ('user', '$2a$10$ySnOf0BW0Ct93pt/MaQ2WeV.QcfGH1WOtlLaWn.IYkdSFr0B.iIfu', 'ROLE_USER', current_timestamp, 'system');
insert into users (username, password_hash, roles, creation_time, created_by) values ('newuser', '$2a$10$86fM56p47jB0.gSyVaQDKuwmwacvUJY0Z22l.WI.Q8/LDeffRppB.', 'ROLE_USER', current_timestamp, 'system');

-- insert for initializing the database with default items
insert into item (category, name, description, price, creation_time, created_by) values (0, 'Gin', 'Juniperberry alcoholic drink', 11.99, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values (5, 'Chicken', 'Boneless poultry', 7.59, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values (24, 'Tonic', 'Carbonated herbal drink', 2.59, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values (18, 'Cordino', 'Mixer', 4.59, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values (18, 'Sanbitter', 'Bitter mixer', 7.29, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values (12, 'Red curry paste', 'Spice mix', 3.59, current_timestamp, 'system');
insert into item (category, name, description, price, creation_time, created_by) values (11, 'Rice', 'Carobohyderate grains', 1.99, current_timestamp, 'system');