---password for all users:123---
insert into ROLE (name) values ('ROLE_USER');
insert into ROLE (name) values ('ROLE_ADMIN');
insert into ROLE (name) values ('ROLE_INSTRUCTOR');
insert into ROLE (name) values ('ROLE_COTTAGE_OWNER');
insert into ROLE (name) values ('ROLE_BOAT_OWNER');

insert into user_table(first_name,last_name,address,city,country,phone,email,username,password,enabled,last_password_reset_date,first_time_logged,penalty) values ('Milorad','Vulovic','Puskinova 3','Novi Sad','Srbija','887214','isaproject0@gmail.com','mickenzi','$2a$10$3N8f6MgFXc0PKfew.y4qQeEhLPh9d3g0FtDGGz4ep3xPz5AAsb9uW',true,'2017-10-01 21:58:58.508-07',false,0);
insert into user_table(first_name,last_name,address,city,country,phone,email,username,password,enabled,last_password_reset_date,first_time_logged,penalty) values ('Jovan','Jovic','Danila Kisa 15','Novi Sad','Srbija','021853','isaproject0@gmail.com','jovan','$2a$10$3N8f6MgFXc0PKfew.y4qQeEhLPh9d3g0FtDGGz4ep3xPz5AAsb9uW',true,'2017-10-01 21:58:58.508-07',false,0);
insert into user_table(first_name,last_name,address,city,country,phone,email,username,password,enabled,last_password_reset_date,first_time_logged,penalty) values ('Aleksa','Aleksic','Gogoljeva 25','Novi Sad','Srbija','174641','isaproject0@gmail.com','aleksa','$2a$10$3N8f6MgFXc0PKfew.y4qQeEhLPh9d3g0FtDGGz4ep3xPz5AAsb9uW',true,'2017-10-01 21:58:58.508-07',false,0);

insert into USER_ROLE (user_id,role_id) values (1,2);
insert into USER_ROLE (user_id,role_id) values (2,3);
insert into USER_ROLE (user_id,role_id) values (3,1);


insert into adventure_table (title,address,instructor_biography,image,max_number,rule,equipment,price_list,cancel_condition,description,user_id,reserved) values ('Luda avantura','Zvornik','Na ovu avanturu povesce vas Jovan Jovic,strastveni ribolovac i borac za zastitu prirode.Covek koji je odrastao na reci Drini','/assets/img/fishing.jpg',10,'Neophodno je ispostovati pravila koja ce vam na predociti instruktor','Sva oprema neophodna za ribolov dolazi uz aranzman plus camac',170,'U slucaju otkaza rezervacije avans se ne vraca','Nezaboravna avantura na reci Drini pruzice uzitak mnogim ribolovcima i pravim ljubiteljima prirode',2,false);
insert into adventure_table (title,address,instructor_biography,image,max_number,rule,equipment,price_list,cancel_condition,description,user_id,reserved) values ('Moc prirode','Zlatibor','Na ovu avanturu povesce vas Jovan Jovic,strastveni ribolovac i borac za zastitu prirode.Covek koji je odrastao na reci Drini','/assets/img/cottage.jpg',15,'Neophodno je ispostovati pravila koja ce vam na predociti instruktor','Sva oprema neophodna za ribolov dolazi uz aranzman plus camac',210,'U slucaju otkaza rezervacije avans se ne vraca','Visednevno kampovanje na Zlatiboru u divljini sume',2,false);

insert into termin_table(start_date,end_date,duration,reserved,is_action,adventure_id) values('19-Jan-2022 10:20:20','22-Jan-2022 10:20:20',5,false,true,1);
insert into termin_table(start_date,end_date,duration,reserved,is_action,adventure_id) values('26-Jan-2022 10:20:20','28-Jan-2022 10:20:20',5,false,true,1);

insert into reservation_table(start_date,end_date,is_created,adventure_id,user_id) values('19-Jan-2022 10:20:20','22-Jan-2022 10:20:20',false,1,3);
insert into reservation_table(start_date,end_date,is_created,adventure_id,user_id) values('13-Jan-2022 10:20:20','15-Jan-2022 10:20:20',false,1,3);
