---password:123---
INSERT INTO ROLE (name) VALUES ('ROLE_USER');
INSERT INTO ROLE (name) VALUES ('ROLE_ADMIN');
INSERT INTO ROLE (name) VALUES ('ROLE_INSTRUCTOR');
INSERT INTO ROLE (name) VALUES ('ROLE_COTTAGE_OWNER');
INSERT INTO ROLE (name) VALUES ('ROLE_BOAT_OWNER');

insert into user_table (address,city,country,email,first_name,last_name,phone,username,enabled,password,last_password_reset_date,deleted,first_time_logged,penalty) values ('Puskinova 3','Novi Sad','Srbija','isaproject0@gmail.com','Milorad','Vulovic','002312135','mickenzi',true,'$2a$10$3N8f6MgFXc0PKfew.y4qQeEhLPh9d3g0FtDGGz4ep3xPz5AAsb9uW','2017-10-01 21:58:58.508-07',false,false,0);
insert into user_table (address,city,country,email,first_name,last_name,phone,username,enabled,password,last_password_reset_date,deleted,first_time_logged,penalty) values ('Danila Kisa 15','Novi Sad','Srbija','isaproject0@gmail.com','Jovan','Jovic','001232132','jovan',true,'$2a$10$HUfSyJuAXZCK1ZLuolPWleNu2jacSMXm4OIJ6Bg9xHgo3jH0mtZ7G','2017-10-01 21:58:58.508-07',false,false,0);
insert into user_table (address,city,country,email,first_name,last_name,phone,username,enabled,password,last_password_reset_date,deleted,first_time_logged,penalty) values ('Mise Dimitrijevica 35','Novi Sad','Srbija','isaproject0@gmail.com','Aleksa','Aleksic','001235132','aleksa',true,'$2a$10$HUfSyJuAXZCK1ZLuolPWleNu2jacSMXm4OIJ6Bg9xHgo3jH0mtZ7G','2017-10-01 21:58:58.508-07',false,false,0);

INSERT INTO USER_ROLE (user_id,role_id) VALUES (1,2);
INSERT INTO USER_ROLE (user_id,role_id) VALUES (2,3);
INSERT INTO USER_ROLE (user_id,role_id) VALUES (3,1);


insert into cottage_table (name,address,description,room,term,price,info,termin,user_id) values ('Paradiso','Popovica','Vikendica','micpfko@gmail.com','Milorad','Vulovic','milorad','00',1);

insert into adventure_table (title,address,instructor_biography,image,max_number,rule,equipment,price_list,cancel_condition,description,user_id,reserved) values ('Luda avantura','Zvornik','Na ovu avanturu povesce vas Jovan Jovic,strastveni ribolovac i borac za zastitu prirode.Covek koji je odrastao na reci Drini','/assets/img/fishing.jpg',10,'Neophodno je ispostovati pravila koja ce vam na predociti instruktor','Sva oprema neophodna za ribolov dolazi uz aranzman plus camac',170,'U slucaju otkaza rezervacije avans se ne vraca','Nezaboravna avantura na reci Drini pruzice uzitak mnogim ribolovcima i pravim ljubiteljima prirode',2,false);
insert into adventure_table (title,address,instructor_biography,image,max_number,rule,equipment,price_list,cancel_condition,description,user_id,reserved) values ('Moc prirode','Zlatibor','Na ovu avanturu povesce vas Jovan Jovic,strastveni ribolovac i borac za zastitu prirode.Covek koji je odrastao na reci Drini','/assets/img/cottage.jpg',15,'Neophodno je ispostovati pravila koja ce vam na predociti instruktor','Sva oprema neophodna za ribolov dolazi uz aranzman plus camac',210,'U slucaju otkaza rezervacije avans se ne vraca','Visednevno kampovanje na Zlatiboru u divljini sume',2,false);

insert into termin_table(start_date,end_date,duration,reserved,is_action,adventure_id) values('19-Jan-2022 10:20:20','22-Jan-2022 10:20:20',5,false,true,1);
insert into termin_table(start_date,end_date,duration,reserved,is_action,adventure_id) values('26-Jan-2022 10:20:20','28-Jan-2022 10:20:20',5,false,true,1);

insert into reservation_table(start_date,end_date,is_created,adventure_id,user_id) values('19-Jan-2022 10:20:20','22-Jan-2022 10:20:20',false,1,3);
insert into reservation_table(start_date,end_date,is_created,adventure_id,user_id) values('13-Jan-2022 10:20:20','15-Jan-2022 10:20:20',false,1,3);