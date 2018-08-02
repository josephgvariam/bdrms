insert into bdrms.bd_facility( id, address, created_by, created_date, modified_by, modified_date, name, version ) values (1 , 'Hadapsar', 'system', current_timestamp, 'system', current_timestamp, 'Pune', 0);
insert into bdrms.bd_facility( id, address, created_by, created_date, modified_by, modified_date, name, version ) values (2 , 'Andheri', 'system', current_timestamp, 'system', current_timestamp, 'Mumbai', 0);
insert into bdrms.bd_role ( id, description, name, version ) values (1 , 'user', 'ROLE_USER', 0);
insert into bdrms.bd_role ( id, description, name, version ) values (2 , 'operator', 'ROLE_OPERATOR', 0);
insert into bdrms.bd_role ( id, description, name, version ) values (3 , 'administrator', 'ROLE_ADMIN', 0);
insert into bdrms.bd_role ( id, description, name, version ) values (4 , 'supervisor', 'ROLE_SUPERVISOR', 0);
insert into bdrms.bd_client( id, address, created_by, created_date, department, modified_by, modified_date, name, version ) values ( 1, 'pune', 'system', current_timestamp, 'na', 'system', current_timestamp, 'Big Dash', 0);
insert into bdrms.bd_client( id, address, created_by, created_date, department, modified_by, modified_date, name, version ) values ( 2, 'pune', 'system', current_timestamp, 'hr', 'system', current_timestamp, 'Flower Motors', 0);
insert into bdrms.bd_client( id, address, created_by, created_date, department, modified_by, modified_date, name, version ) values ( 3, 'pune', 'system', current_timestamp, 'finance', 'system', current_timestamp, 'Globe Bank', 0);
insert into bdrms.bd_user( id, created_by, created_date, email, employee_number, locked, modified_by, modified_date, name, password, phone, username, version, client_id ) values ( 1, 'system', current_timestamp, 'user1@test.com', '123', 0, 'system', current_timestamp, 'user one', '$2a$04$mplyqakpH7eWM/1glPYga.Gm65tTYc.whDzVLAY3TKz0YOeEu7BHO', '9876543210', 'user1', 0, 2);
insert into bdrms.bd_user( id, created_by, created_date, email, employee_number, locked, modified_by, modified_date, name, password, phone, username, version, client_id ) values ( 2, 'system', current_timestamp, 'user2@test.com', '345', 0, 'system', current_timestamp, 'user two', '$2a$04$cnUST0a7drFgilqBdZayceNCl88ijRGyv8jWZrdDD3NqOJUwL19WK', '9876543210', 'user2', 0, 3);
insert into bdrms.bd_user( id, created_by, created_date, email, employee_number, locked, modified_by, modified_date, name, password, phone, username, version, client_id )values ( 5, 'system', current_timestamp, 'user3@test.com', '123', 0, 'system', current_timestamp, 'user three', '$2a$04$mplyqakpH7eWM/1glPYga.Gm65tTYc.whDzVLAY3TKz0YOeEu7BHO', '9876543210', 'user3', 0, 2);
insert into bdrms.bd_user( id, created_by, created_date, email, employee_number, locked, modified_by, modified_date, name, password, phone, username, version, client_id ) values ( 3, 'system', current_timestamp, 'operator@test.com', '345', 0, 'system', current_timestamp, 'hello operator', '$2a$04$NRDSxbHBfY2w7gGopZ/L0eCtrt2HdFNv2xQAUcxKYe9m8JjnQccUa', '9876543210', 'operator', 0, 1);
insert into bdrms.bd_user( id, created_by, created_date, email, employee_number, locked, modified_by, modified_date, name, password, phone, username, version, client_id ) values ( 4, 'system', current_timestamp, 'admin@test.com', '345', 0, 'system', current_timestamp, 'hello administrator', '$2a$04$0qA7xL5CQK1TFygLt0JrpOPNiWX9azHzNvX.GTCL1y4H6Ae/4DYT.', '9876543210', 'admin', 0, 1);
insert into bdrms.bd_user_role ( role_id, user_id ) values (1 , 1);
insert into bdrms.bd_user_role ( role_id, user_id ) values (1 , 2);
insert into bdrms.bd_user_role ( role_id, user_id ) values (1 , 3);
insert into bdrms.bd_user_role ( role_id, user_id ) values (2 , 3);
insert into bdrms.bd_user_role ( role_id, user_id ) values (1 , 4);
insert into bdrms.bd_user_role ( role_id, user_id ) values (2 , 4);
insert into bdrms.bd_user_role ( role_id, user_id ) values (3 , 4);
insert into bdrms.bd_storage_type( id, description, name, version ) values ( 1, 'box storage type', 'BOX', 0);
insert into bdrms.bd_storage_type( id, description, name, version ) values ( 2, 'file storage type', 'FILE', 0);
insert into bdrms.bd_storage_type( id, description, name, version ) values ( 3, 'document storage type', 'DOCUMENT', 0);
insert into bdrms.bd_client_storage_types( storage_type_id, client_id ) values (1 , 2);
insert into bdrms.bd_client_storage_types( storage_type_id, client_id ) values (2 , 2);
insert into bdrms.bd_client_storage_types( storage_type_id, client_id ) values (1 , 3);
insert into bdrms.bd_client_storage_types ( storage_type_id, client_id ) values (2 , 3);
insert into bdrms.bd_client_storage_types( storage_type_id, client_id ) values (3 , 3);
insert into bdrms.bd_client_storage_types( storage_type_id, client_id ) values (1 , 1);
insert into bdrms.bd_client_storage_types ( storage_type_id, client_id ) values (2 , 1);
insert into bdrms.bd_client_storage_types( storage_type_id, client_id ) values (3 , 1);
insert into bdrms.bd_shelf(id, barcode, status, version, facility_id) values ( 1, 'shelf1', 'ACTIVE', 1, 1);