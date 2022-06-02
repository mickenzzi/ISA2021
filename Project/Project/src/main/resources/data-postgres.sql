---password for all users:123---
insert into ROLE (name) values ('ROLE_USER');
insert into ROLE (name) values ('ROLE_ADMIN');
insert into ROLE (name) values ('ROLE_INSTRUCTOR');
insert into ROLE (name) values ('ROLE_COTTAGE_OWNER');
insert into ROLE (name) values ('ROLE_BOAT_OWNER');

insert into user_table(first_name,last_name,address,city,country,phone,email,username,password,enabled,last_password_reset_date,first_time_logged,penalty,loyalty_status,collected_points, member) values ('Milorad','Vulovic','Puskinova 3','Novi Sad','Srbija','887214','isaproject0@gmail.com','mickenzi','$2a$10$3N8f6MgFXc0PKfew.y4qQeEhLPh9d3g0FtDGGz4ep3xPz5AAsb9uW',true,'2017-10-01 21:58:58.508-07',false,0,'GOLD',52, false);
insert into user_table(first_name,last_name,address,city,country,phone,email,username,password,enabled,last_password_reset_date,first_time_logged,penalty,loyalty_status,collected_points, member) values ('Jovan','Jovic','Danila Kisa 15','Novi Sad','Srbija','021853','isaproject0@gmail.com','jovan','$2a$10$3N8f6MgFXc0PKfew.y4qQeEhLPh9d3g0FtDGGz4ep3xPz5AAsb9uW',true,'2017-10-01 21:58:58.508-07',false,0,'SILVER',20, false);
insert into user_table(first_name,last_name,address,city,country,phone,email,username,password,enabled,last_password_reset_date,first_time_logged,penalty,loyalty_status,collected_points, member) values ('Aleksa','Aleksic','Gogoljeva 25','Novi Sad','Srbija','174641','isaproject0@gmail.com','aleksa','$2a$10$3N8f6MgFXc0PKfew.y4qQeEhLPh9d3g0FtDGGz4ep3xPz5AAsb9uW',true,'2017-10-01 21:58:58.508-07',false,0,'SILVER',49, true);
insert into user_table(first_name,last_name,address,city,country,phone,email,username,password,enabled,last_password_reset_date,first_time_logged,penalty,loyalty_status,collected_points, member) values ('Milos','Milosevic','Tolstojeva 1','Novi Sad','Srbija','1123123641','isaproject0@gmail.com','milos','$2a$10$3N8f6MgFXc0PKfew.y4qQeEhLPh9d3g0FtDGGz4ep3xPz5AAsb9uW',false,'2017-10-01 21:58:58.508-07',false,0, 'BRONZE',19, false);

insert into USER_ROLE (user_id,role_id) values (1,2);
insert into USER_ROLE (user_id,role_id) values (2,3);
insert into USER_ROLE (user_id,role_id) values (3,1);
insert into USER_ROLE (user_id,role_id) values (4,1);

insert into loyalty_table(name,points,discount) values ('BRONZE', 0, 1.0);
insert into loyalty_table(name,points,discount) values ('SILVER', 20, 9.5);
insert into loyalty_table(name,points,discount) values ('GOLD', 50, 15.5);

insert into adventure_table (title,address,instructor_biography,image,max_number,rule,equipment,price_list,cancel_condition,description,user_id,reserved,average_grade) values ('Luda avantura','Zvornik','Na ovu avanturu povesce vas Jovan Jovic,strastveni ribolovac i borac za zastitu prirode.Covek koji je odrastao na reci Drini','/assets/img/fishing.jpg',10,'Neophodno je ispostovati pravila koja ce vam na predociti instruktor','Sva oprema neophodna za ribolov dolazi uz aranzman plus camac',170,'U slucaju otkaza rezervacije avans se ne vraca','Nezaboravna avantura na reci Drini pruzice uzitak mnogim ribolovcima i pravim ljubiteljima prirode',2,false,'0.0');
insert into adventure_table (title,address,instructor_biography,image,max_number,rule,equipment,price_list,cancel_condition,description,user_id,reserved,average_grade) values ('Moc prirode','Zlatibor','Na ovu avanturu povesce vas Jovan Jovic,strastveni ribolovac i borac za zastitu prirode.Covek koji je odrastao na reci Drini','/assets/img/cottage.jpg',15,'Neophodno je ispostovati pravila koja ce vam na predociti instruktor','Sva oprema neophodna za ribolov dolazi uz aranzman plus camac',210,'U slucaju otkaza rezervacije avans se ne vraca','Visednevno kampovanje na Zlatiboru u divljini sume',2,false,'4.0');

insert into image_table(url,adventure_id) values ('/assets/img/meeting1.jpg',1);
insert into image_table(url,adventure_id) values ('/assets/img/meeting2.jpg',1);
insert into image_table(url,adventure_id) values ('/assets/img/meeting3.jpg',1);
insert into image_table(url,adventure_id) values ('/assets/img/meeting4.jpg',2);
insert into image_table(url,adventure_id) values ('/assets/img/meeting5.jpg',2);
insert into image_table(url,adventure_id) values ('/assets/img/meeting6.jpg',2);

insert into termin_table(start_date,end_date,duration,reserved,is_action,adventure_id, price, capacity, instructor_id) values('19-Jan-2022 10:20:20','22-Jan-2022 10:20:20',5,false,true,1, 160, 10, 2);
insert into termin_table(start_date,end_date,duration,reserved,is_action,adventure_id, price, capacity, instructor_id) values('26-Jan-2022 10:20:20','28-Jan-2022 10:20:20',5,false,true,1, 170, 10, 2);
insert into termin_table(start_date,end_date,duration,reserved,is_action,adventure_id, price, capacity, instructor_id) values('22-May-2022 10:20:20','25-May-2022 10:20:20',5,true,false,2, 170, 10, 2);
insert into termin_table(start_date,end_date,duration,reserved,is_action,adventure_id, price, capacity, instructor_id) values('10-May-2022 10:20:20','12-May-2022 10:20:20',5,false,true,2, 170, 10, 2);

insert into reservation_table(start_date,end_date,is_created,adventure_id,user_id,price) values('10-May-2022 10:20:20','12-May-2022 10:20:20',false,2,3,270);
insert into reservation_table(start_date,end_date,is_created,adventure_id,user_id,price) values('22-May-2022 10:20:20','25-May-2022 10:20:20',true,2,3,270);


insert into financies_table(percent, define) values (10.55, '10-Jan-2021 10:20:20');
insert into financies_table(percent, define) values (10.55, '25-Jan-2022 10:20:20');

insert into comment_table(content,is_enabled,is_negative,user_id,instructor_id) values('Pozitivan komentar',false,false,3,2);
insert into comment_table(content,is_enabled,is_negative,user_id,instructor_id) values('Negativan komentar',false,true,3,2);
insert into comment_table(content,is_enabled,is_negative,user_id,instructor_id) values('Pozitivan komentar',true,false,3,2);

insert into complaint_table(content,answered,user_id,instructor_id,admin_id) values('Zalba na instruktora',false,3,2,1);
insert into complaint_table(content,answered,user_id,instructor_id,admin_id) values('Odgovor na zalbu na instruktora',true,3,2,1);

insert into request_table(title,username,admin_id) values ('Zahtev za brisanje naloga','jovan',1);
insert into request_table(title,username,admin_id) values ('Zahtev za verifikaciju naloga','milos',1);

insert into review_table(comment,grade,enabled,user_id,admin_id,instructor_id,adventure_id) values ('Dobro je bilo',4,true,3,1,2,2);
insert into review_table(comment,grade,enabled,user_id,admin_id,instructor_id,adventure_id) values ('Lose je bilo',2,false,3,1,2,2);