---password for all users:123---
insert into ROLE (name) values ('ROLE_USER');
insert into ROLE (name) values ('ROLE_ADMIN');
insert into ROLE (name) values ('ROLE_INSTRUCTOR');
insert into ROLE (name) values ('ROLE_COTTAGE_OWNER');
insert into ROLE (name) values ('ROLE_BOAT_OWNER');

insert into user_table(first_name,last_name,address,city,country,phone,email,username,password,enabled,last_password_reset_date,first_time_logged,penalty,loyalty_status,collected_points, member) values ('Milorad','Vulovic','Puskinova 3','Novi Sad','Srbija','887214','isaproject0@gmail.com','mickenzi','$2a$10$3N8f6MgFXc0PKfew.y4qQeEhLPh9d3g0FtDGGz4ep3xPz5AAsb9uW',true,'2017-10-01 21:58:58.508-07',false,0,'GOLD',52, false);
insert into user_table(first_name,last_name,address,city,country,phone,email,username,password,enabled,last_password_reset_date,first_time_logged,penalty,loyalty_status,collected_points, member) values ('Jovan','Jovic','Danila Kisa 15','Novi Sad','Srbija','021853','isaproject0@gmail.com','jovan','$2a$10$3N8f6MgFXc0PKfew.y4qQeEhLPh9d3g0FtDGGz4ep3xPz5AAsb9uW',true,'2017-10-01 21:58:58.508-07',false,0,'SILVER',20, false);
insert into user_table(first_name,last_name,address,city,country,phone,email,username,password,enabled,last_password_reset_date,first_time_logged,penalty,loyalty_status,collected_points, member) values ('Aleksa','Aleksic','Gogoljeva 25','Novi Sad','Srbija','174641','isaproject0@gmail.com','aleksa','$2a$10$3N8f6MgFXc0PKfew.y4qQeEhLPh9d3g0FtDGGz4ep3xPz5AAsb9uW',true,'2017-10-01 21:58:58.508-07',false,0,'SILVER',50, true);
insert into user_table(first_name,last_name,address,city,country,phone,email,username,password,enabled,last_password_reset_date,first_time_logged,penalty,loyalty_status,collected_points, member) values ('Milos','Milosevic','Tolstojeva 1','Novi Sad','Srbija','1123123641','isaproject0@gmail.com','milos','$2a$10$3N8f6MgFXc0PKfew.y4qQeEhLPh9d3g0FtDGGz4ep3xPz5AAsb9uW',false,'2017-10-01 21:58:58.508-07',false,0, 'BRONZE',19, false);
insert into user_table(first_name,last_name,address,city,country,phone,email,username,password,enabled,last_password_reset_date,first_time_logged,penalty,loyalty_status,collected_points, member) values ('Vladimir','Ciric','Boska Buhe 19','Novi Sad','Srbija','1231233','isaproject0@gmail.com','vladakac','$2a$10$3N8f6MgFXc0PKfew.y4qQeEhLPh9d3g0FtDGGz4ep3xPz5AAsb9uW',true,'2017-10-01 21:58:58.508-07',false,0, 'BRONZE',19, false);
insert into user_table(first_name,last_name,address,city,country,phone,email,username,password,enabled,last_password_reset_date,first_time_logged,penalty,loyalty_status,collected_points, member) values ('Marko','Markovic','Boska Buhe 19','Novi Sad','Srbija','1231234','isaproject0@gmail.com','marko','$2a$10$3N8f6MgFXc0PKfew.y4qQeEhLPh9d3g0FtDGGz4ep3xPz5AAsb9uW',true,'2017-10-01 21:58:58.508-07',false,0, 'BRONZE',19, false);

insert into USER_ROLE (user_id,role_id) values (1,2);
insert into USER_ROLE (user_id,role_id) values (2,3);
insert into USER_ROLE (user_id,role_id) values (3,1);
insert into USER_ROLE (user_id,role_id) values (5,4);
insert into USER_ROLE (user_id,role_id) values (6,5);

insert into loyalty_table(name,points,discount) values ('BRONZE', 0, 1.0);
insert into loyalty_table(name,points,discount) values ('SILVER', 20, 9.5);
insert into loyalty_table(name,points,discount) values ('GOLD', 50, 15.5);

insert into adventure_table (title,address,instructor_biography,image,max_number,rule,equipment,price_list,cancel_condition,description,user_id,reserved,average_grade, latitude, longitude) values ('Luda avantura','Zvornik','Na ovu avanturu povesce vas Jovan Jovic,strastveni ribolovac i borac za zastitu prirode.Covek koji je odrastao na reci Drini','/assets/img/fishing.jpg',10,'Neophodno je ispostovati pravila koja ce vam na predociti instruktor','Sva oprema neophodna za ribolov dolazi uz aranzman plus camac',170,'U slucaju otkaza rezervacije avans se ne vraca','Nezaboravna avantura na reci Drini pruzice uzitak mnogim ribolovcima i pravim ljubiteljima prirode',2,false,'0.0', 44.38605, 19.10247);
insert into adventure_table (title,address,instructor_biography,image,max_number,rule,equipment,price_list,cancel_condition,description,user_id,reserved,average_grade, latitude, longitude) values ('Moc prirode','Zlatibor','Na ovu avanturu povesce vas Jovan Jovic,strastveni ribolovac i borac za zastitu prirode.Covek koji je odrastao na reci Drini','/assets/img/cottage.jpg',15,'Neophodno je ispostovati pravila koja ce vam na predociti instruktor','Sva oprema neophodna za ribolov dolazi uz aranzman plus camac',210,'U slucaju otkaza rezervacije avans se ne vraca','Visednevno kampovanje na Zlatiboru u divljini sume',2,false,'4.0', 43.666664, 19.6999972);

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
insert into termin_table(start_date,end_date,duration,reserved,is_action,adventure_id, price, capacity, instructor_id) values('24-Aug-2022 10:20:20','25-Aug-2022 10:20:20',5,false,true,2, 170, 10, 2);
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

insert into request_table(title, body, username,admin_id) values ('Zahtev za brisanje naloga', 'Ne mogu vise', 'jovan',1);
insert into request_table(title, body, username,admin_id) values ('Zahtev za verifikaciju naloga', '', 'milos',1);

insert into review_table(comment,grade,enabled,user_id,admin_id,instructor_id,adventure_id) values ('Dobro je bilo',4,true,3,1,2,2);
insert into review_table(comment,grade,enabled,user_id,admin_id,instructor_id,adventure_id) values ('Lose je bilo',2,false,3,1,2,2);

insert into cottage_table(name,address,description,image,number_of_rooms,number_of_beds,rules,price,info,reserved,user_id, latitude, longitude) values ('Pogled', 'Tamo daleko', 'Vikendica na jezeru', '/assets/img/cottage.jpg', 3, 4, 'Ponasajte se lepo', '120', 'Kamin, ventilator, basta', false, 5, 12, 12);
insert into cottage_table(name,address,description,image,number_of_rooms,number_of_beds,rules,price,info,reserved,user_id, latitude, longitude) values ('Mir', 'Negde tamo 3', 'Vikendica u sumi', '/assets/img/cottageLink.jpg', 4, 2, 'Ponasajte se lepo', '100', 'Kamin, ventilator, basta', false, 5, 12, 12);

insert into boat_table(name,boat_type,lenght,engine_number,engine_power,max_speed,navigation_equipment,address,description,image,capacity, rules, fishing_equipment, price, info, cancel_terms, latitude, longitude, user_id) values
					('Sumaher', 'Brzi', '5m', '21b', '120kw', '120kmh', 'wifi,sporet,kamin', 'dunav 12', 'brz camac', '/assets/img/camac1.jpg', '3 ljudi', 'ponasajte se pristojno', 'pecaljka', 50, 'ponesite kupaci', 'nema otkazivanja ne vracamo pare', 45.228381, 19.833430, 6);


insert into termin_cottage_table(start_date,end_date,reserved,is_action,action_expire, duration, cottage_id, user_id, price, capacity) values('24-Aug-2022 10:20:20','25-Aug-2022 10:20:20',false, false, null, 0, 1, null, 120, 3);
insert into termin_cottage_table(start_date,end_date,reserved,is_action,action_expire, duration, cottage_id, user_id, price, capacity) values('20-Aug-2022 10:20:20','22-Aug-2022 10:20:20',false, true, '29-Aug-2022 10:20:20', 3, 1, null, 120, 4);
insert into termin_cottage_table(start_date,end_date,reserved,is_action,action_expire, duration, cottage_id, user_id, price, capacity) values('10-May-2022 10:20:20','15-May-2022 10:20:20',false, true, '29-Aug-2022 10:20:20', 3, 1, null, 210, 4);
insert into termin_cottage_table(start_date,end_date,reserved,is_action,action_expire, duration, cottage_id, user_id, price, capacity) values('03-Apr-2022 10:20:20','13-Apr-2022 10:20:20',false, true, '29-Aug-2022 10:20:20', 3, 1, null, 90, 4);
insert into termin_cottage_table(start_date,end_date,reserved,is_action,action_expire, duration, cottage_id, user_id, price, capacity) values('20-Apr-2022 10:20:20','24-Apr-2022 10:20:20',false, false, null, 0, 1, null, 130, 5);
insert into termin_cottage_table(start_date,end_date,reserved,is_action,action_expire, duration, cottage_id, user_id, price, capacity) values('09-Mar-2022 10:20:20','12-Mar-2022 10:20:20',false, false, null, 0, 1, null, 400, 2);
insert into termin_cottage_table(start_date,end_date,reserved,is_action,action_expire, duration, cottage_id, user_id, price, capacity) values('09-Sep-2022 10:20:20','11-Sep-2022 10:20:20',true, false, null, 0, 1, 2, 200, 2);
insert into termin_cottage_table(start_date,end_date,reserved,is_action,action_expire, duration, cottage_id, user_id, price, capacity) values('13-Sep-2022 10:20:20','15-Sep-2022 10:20:20',true, false, null, 0, 1, 2, 300, 2);
insert into termin_cottage_table(start_date,end_date,reserved,is_action,action_expire, duration, cottage_id, user_id, price, capacity) values('20-Sep-2022 10:20:20','21-Sep-2022 10:20:20',true, false, null, 0, 1, 2, 250, 2);
insert into termin_cottage_table(start_date,end_date,reserved,is_action,action_expire, duration, cottage_id, user_id, price, capacity) values('08-Aug-2022 10:20:20','11-Aug-2022 10:20:20',true, false, null, 0, 1, 2, 200, 2);
insert into termin_cottage_table(start_date,end_date,reserved,is_action,action_expire, duration, cottage_id, user_id, price, capacity) values('14-Aug-2022 10:20:20','16-Aug-2022 10:20:20',true, false, null, 0, 1, 2, 300, 2);

insert into termin_boat_table(start_date,end_date,reserved,is_action,action_expire, duration, boat_id, user_id, price, capacity) values('09-Sep-2022 10:20:20','11-Sep-2022 10:20:20',true, false, null, 0, 1, 2, 200, 2);
insert into termin_boat_table(start_date,end_date,reserved,is_action,action_expire, duration, boat_id, user_id, price, capacity) values('13-Sep-2022 10:20:20','15-Sep-2022 10:20:20',true, false, null, 0, 1, 2, 300, 2);
insert into termin_boat_table(start_date,end_date,reserved,is_action,action_expire, duration, boat_id, user_id, price, capacity) values('20-Sep-2022 10:20:20','21-Sep-2022 10:20:20',true, false, null, 0, 1, 2, 250, 2);
insert into termin_boat_table(start_date,end_date,reserved,is_action,action_expire, duration, boat_id, user_id, price, capacity) values('08-Aug-2022 10:20:20','11-Aug-2022 10:20:20',true, false, null, 0, 1, 2, 200, 2);
insert into termin_boat_table(start_date,end_date,reserved,is_action,action_expire, duration, boat_id, user_id, price, capacity) values('14-Aug-2022 10:20:20','16-Aug-2022 10:20:20',true, false, null, 0, 1, 2, 300, 2);



insert into entity_subscriber_table(id, boat_id, cottage_id, user_id) values (1, 1, null, 3);

insert into entity_subscriber_table(id, boat_id, cottage_id, user_id) values (2, null, 1, 3);
insert into entity_subscriber_table(id, boat_id, cottage_id, user_id) values (3, null, 2, 3);
