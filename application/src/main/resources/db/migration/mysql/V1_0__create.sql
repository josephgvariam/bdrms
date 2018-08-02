create table bdrms.bd_box (id bigint not null auto_increment, barcode varchar(255) not null, created_by varchar(255), created_date datetime, location varchar(255), modified_by varchar(255), modified_date datetime, version bigint, shelf_id bigint, primary key (id)) ENGINE=InnoDB;
create table bdrms.bd_box_aud (id bigint not null, rev integer not null, revtype tinyint, barcode varchar(255), created_by varchar(255), created_date datetime, location varchar(255), modified_by varchar(255), modified_date datetime, shelf_id bigint, primary key (id, rev)) ENGINE=InnoDB;
create table bdrms.bd_client (id bigint not null auto_increment, address varchar(255) not null, created_by varchar(255), created_date datetime, department varchar(255) not null, modified_by varchar(255), modified_date datetime, name varchar(255) not null, version bigint, primary key (id)) ENGINE=InnoDB;
create table bdrms.bd_client_aud (id bigint not null, rev integer not null, revtype tinyint, address varchar(255), created_by varchar(255), created_date datetime, department varchar(255), modified_by varchar(255), modified_date datetime, name varchar(255), primary key (id, rev)) ENGINE=InnoDB;
create table bdrms.bd_client_storage_types (client_id bigint not null, storage_type_id bigint not null, primary key (client_id, storage_type_id)) ENGINE=InnoDB;
create table bdrms.bd_document (id bigint not null auto_increment, barcode varchar(255) not null, created_by varchar(255), created_date datetime, location varchar(255), modified_by varchar(255), modified_date datetime, version bigint, file_id bigint, primary key (id)) ENGINE=InnoDB;
create table bdrms.bd_document_aud (id bigint not null, rev integer not null, revtype tinyint, barcode varchar(255), created_by varchar(255), created_date datetime, location varchar(255), modified_by varchar(255), modified_date datetime, file_id bigint, primary key (id, rev)) ENGINE=InnoDB;
create table bdrms.bd_facility (id bigint not null auto_increment, address varchar(255) not null, created_by varchar(255), created_date datetime, modified_by varchar(255), modified_date datetime, name varchar(255) not null, version bigint, primary key (id)) ENGINE=InnoDB;
create table bdrms.bd_facility_aud (id bigint not null, rev integer not null, revtype tinyint, address varchar(255), created_by varchar(255), created_date datetime, modified_by varchar(255), modified_date datetime, name varchar(255), primary key (id, rev)) ENGINE=InnoDB;
create table bdrms.bd_file (id bigint not null auto_increment, barcode varchar(255) not null, created_by varchar(255), created_date datetime, location varchar(255), modified_by varchar(255), modified_date datetime, version bigint, box_id bigint, primary key (id)) ENGINE=InnoDB;
create table bdrms.bd_file_aud (id bigint not null, rev integer not null, revtype tinyint, barcode varchar(255), created_by varchar(255), created_date datetime, location varchar(255), modified_by varchar(255), modified_date datetime, box_id bigint, primary key (id, rev)) ENGINE=InnoDB;
create table bdrms.bd_inventory_item (type varchar(20) not null, id bigint not null auto_increment, ref1 varchar(255) not null, ref2 varchar(255), ref3 varchar(255), ref4 varchar(255), ref5 varchar(255), status varchar(255), version bigint, facility_id bigint, user_created_id bigint not null, box_id bigint, document_id bigint, file_id bigint, primary key (id)) ENGINE=InnoDB;
create table bdrms.bd_inventory_item_aud (id bigint not null, rev integer not null, type varchar(20) not null, revtype tinyint, ref1 varchar(255), ref2 varchar(255), ref3 varchar(255), ref4 varchar(255), ref5 varchar(255), status varchar(255), facility_id bigint, user_created_id bigint, file_id bigint, box_id bigint, document_id bigint, primary key (id, rev)) ENGINE=InnoDB;
create table bdrms.bd_request (type varchar(20) not null, id bigint not null auto_increment, created_by varchar(255), created_date datetime, modified_by varchar(255), modified_date datetime, notes varchar(255), status varchar(255) not null, version bigint, document_type varchar(255), number_files integer, pickup_date_time datetime, storage_type_id bigint not null, user_assigned_id bigint, user_created_id bigint not null, from_facility_id bigint, to_facility_id bigint, primary key (id)) ENGINE=InnoDB;
create table bdrms.bd_request_aud (id bigint not null, rev integer not null, type varchar(20) not null, revtype tinyint, created_by varchar(255), created_date datetime, modified_by varchar(255), modified_date datetime, notes varchar(255), status varchar(255), user_assigned_id bigint, user_created_id bigint, document_type varchar(255), number_files integer, pickup_date_time datetime, from_facility_id bigint, to_facility_id bigint, primary key (id, rev)) ENGINE=InnoDB;
create table bdrms.bd_request_inventory_item (request_id bigint not null, inventory_item_id bigint not null, primary key (request_id, inventory_item_id)) ENGINE=InnoDB;
create table bdrms.bd_role (id bigint not null auto_increment, description varchar(255), name varchar(255) not null, version bigint, primary key (id)) ENGINE=InnoDB;
create table bdrms.bd_shelf (id bigint not null auto_increment, barcode varchar(255) not null, status varchar(255), version bigint, facility_id bigint, primary key (id)) ENGINE=InnoDB;
create table bdrms.bd_shelf_aud (id bigint not null, rev integer not null, revtype tinyint, barcode varchar(255), status varchar(255), facility_id bigint, primary key (id, rev)) ENGINE=InnoDB;
create table bdrms.bd_storage_type (id bigint not null auto_increment, description varchar(255), name varchar(255) not null, version bigint, primary key (id)) ENGINE=InnoDB;
create table bdrms.bd_user (id bigint not null auto_increment, created_by varchar(255), created_date datetime, email varchar(255) not null, employee_number varchar(255), locked bit not null, modified_by varchar(255), modified_date datetime, name varchar(255) not null, password varchar(255) not null, phone varchar(255) not null, username varchar(255) not null, version bigint, client_id bigint not null, primary key (id)) ENGINE=InnoDB;
create table bdrms.bd_user_aud (id bigint not null, rev integer not null, revtype tinyint, created_by varchar(255), created_date datetime, email varchar(255), employee_number varchar(255), locked bit, modified_by varchar(255), modified_date datetime, name varchar(255), password varchar(255), phone varchar(255), username varchar(255), client_id bigint, primary key (id, rev)) ENGINE=InnoDB;
create table bdrms.bd_user_role (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) ENGINE=InnoDB;
create table bdrms.revinfo (rev integer not null auto_increment, revtstmp bigint, primary key (rev)) ENGINE=InnoDB;
alter table bdrms.bd_box add constraint UK_61urdgo4aqtd742d3rt6a23ij unique (barcode);
alter table bdrms.bd_document add constraint UK_san92ky6rtboioc79qrrjsc32 unique (barcode);
alter table bdrms.bd_file add constraint UK_qp3a3ia4ynrfwefxmdb869n4y unique (barcode);
alter table bdrms.bd_role add constraint UK_fp4cn7ud39q3ymwvq2yw1vvfr unique (name);
alter table bdrms.bd_shelf add constraint UK_k0v4wkrngu18xtx3dcnfdxa23 unique (barcode);
alter table bdrms.bd_user add constraint UK_f8m4vn4ueqoa138xx9d0lh4n8 unique (username);
alter table bdrms.bd_box add constraint FK2c5yx4s3qvqq0yfajqwme1xs6 foreign key (shelf_id) references bd_shelf (id);
alter table bdrms.bd_box_aud add constraint FKhfaxfkiwx2hiiv8tlg3kjho5g foreign key (rev) references revinfo (rev);
alter table bdrms.bd_client_aud add constraint FK2fdloexke73q36vssvfopqie1 foreign key (rev) references revinfo (rev);
alter table bdrms.bd_client_storage_types add constraint FKqk8xhr1ffjm7whu4m7qa3hfig foreign key (storage_type_id) references bd_storage_type (id);
alter table bdrms.bd_client_storage_types add constraint FKpx5h8g2wte6uv26it3lporwo0 foreign key (client_id) references bd_client (id);
alter table bdrms.bd_document add constraint FKp8srkf4cdye0my6g3wtt91ewc foreign key (file_id) references bd_file (id);
alter table bdrms.bd_document_aud add constraint FKavr6a3lwmbs5rfv8guf0gq9wm foreign key (rev) references revinfo (rev);
alter table bdrms.bd_facility_aud add constraint FKfaasw5xnwp7g6dklhhsebs0et foreign key (rev) references revinfo (rev);
alter table bdrms.bd_file add constraint FKfnd5cvn305i0wdmcmpxx7cntd foreign key (box_id) references bd_box (id);
alter table bdrms.bd_file_aud add constraint FKpictog8ebyyclvj7cvumsju24 foreign key (rev) references revinfo (rev);
alter table bdrms.bd_inventory_item add constraint FKkwv2dlqthgx50wy1v4warpdvd foreign key (facility_id) references bd_facility;
alter table bdrms.bd_inventory_item add constraint FKs09xae4h9cgkyyc4tksesoueu foreign key (user_created_id) references bd_user (id);
alter table bdrms.bd_inventory_item add constraint FKkwjfso49y9isuev1on6kuonkn foreign key (box_id) references bd_box (id);
alter table bdrms.bd_inventory_item add constraint FKfv10n2g5elpapyohn06lsl0hf foreign key (document_id) references bd_document (id);
alter table bdrms.bd_inventory_item add constraint FKqo1svkky77g75moxwuy0rph1l foreign key (file_id) references bd_file (id);
alter table bdrms.bd_inventory_item_aud add constraint FKhqb252dcpuwxgrvhnly68enht foreign key (rev) references revinfo (rev);
alter table bdrms.bd_request add constraint FKsp8r0x8d7ej0625dy4bs9yq3r foreign key (storage_type_id) references bd_storage_type (id);
alter table bdrms.bd_request add constraint FKict2oflenkawn24fi1ox0r8l2 foreign key (user_assigned_id) references bd_user (id);
alter table bdrms.bd_request add constraint FK6g2bp6wogl38yvigi9cwp7gqc foreign key (user_created_id) references bd_user (id);
alter table bdrms.bd_request add constraint FKeuxwau3sytar8lotuh64tnwy3 foreign key (from_facility_id) references bd_facility;
alter table bdrms.bd_request add constraint FKsauo48jclv1kjr5wkj1mpolpw foreign key (to_facility_id) references bd_facility;
alter table bdrms.bd_request_aud add constraint FKql71vklb6ft68f3hbk9f3h17r foreign key (rev) references revinfo (rev);
alter table bdrms.bd_request_inventory_item add constraint FKnv9tsx3mioj1jd5qabv375r58 foreign key (inventory_item_id) references bd_inventory_item (id);
alter table bdrms.bd_request_inventory_item add constraint FKo5kj0epb8uw3ylqfy7kfvkl7l foreign key (request_id) references bd_request (id);
alter table bdrms.bd_shelf add constraint FK12tmyh5foeoo00cdlgnyts7jd foreign key (facility_id) references bd_facility (id);
alter table bdrms.bd_shelf_aud add constraint FKdb6rak799xdmdfi1ijru18qt2 foreign key (rev) references revinfo (rev);
alter table bdrms.bd_user add constraint FK8odu6j33wu0o3l8bso8wqhw7e foreign key (client_id) references bd_client (id);
alter table bdrms.bd_user_aud add constraint FKajhmkayj0k4kmp1gymgrvokog foreign key (rev) references revinfo (rev);
alter table bdrms.bd_user_role add constraint FK82p0x0e47k911rkyfxt2kemah foreign key (role_id) references bd_role (id);
alter table bdrms.bd_user_role add constraint FKkg5qwik6m3pju9evn44kv6kjg foreign key (user_id) references bd_user (id);
alter table bdrms.bd_client auto_increment=1001;
alter table bdrms.bd_facility auto_increment=1001;
alter table bdrms.bd_role auto_increment=1001;
alter table bdrms.bd_storage_type auto_increment=1001;
alter table bdrms.bd_user auto_increment=1001;