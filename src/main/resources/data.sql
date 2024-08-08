INSERT INTO concert (`concert_id`,`organizer`,`title`)
VALUES (1,'아이유','아이유10주년콘서트'),
       (2,'에스파','에스파컴백콘서트');

INSERT INTO concert_option (`concert_date_time`,`concert_id`,`concert_option_id`,`place`)
VALUES ('2024-04-16 00:17:12.689711',1,1,'장충체육관'),
       ('2024-04-16 12:36:34.297179',1,2,'상암월드컵경기장'),
       ('2024-04-16 12:41:07.508016',2,3,'코엑스');

INSERT INTO seat (`concert_option_id`,`seat_id`,`seat_no`,`booking_status`,`grade`)
VALUES (1,1,'A_1','AVAILABLE','A'),
        (1,2,'A_2','AVAILABLE','A'),
        (1,3,'A_3','AVAILABLE','A'),
        (1,4,'A_4','AVAILABLE','A'),
        (1,5,'A_5','AVAILABLE','A'),
        (1,6,'A_6','AVAILABLE','A'),
        (1,7,'A_7','AVAILABLE','A'),
        (1,8,'A_8','AVAILABLE','A'),
        (1,9,'A_9','AVAILABLE','A'),
        (1,10,'A_10','AVAILABLE','A'),
        (2,11,'A_1','AVAILABLE','B'),
        (2,12,'A_2','AVAILABLE','B'),
        (2,13,'A_3','AVAILABLE','B'),
        (2,14,'A_4','AVAILABLE','B'),
        (2,15,'A_5','AVAILABLE','B'),
        (2,16,'A_6','AVAILABLE','B'),
        (2,17,'A_7','AVAILABLE','B'),
        (2,18,'A_8','AVAILABLE','B'),
        (2,19,'A_9','AVAILABLE','B'),
        (2,20,'A_10','AVAILABLE','B'),
        (3,21,'A_1','AVAILABLE','C'),
        (3,22,'A_2','AVAILABLE','C'),
        (3,23,'A_3','AVAILABLE','C'),
        (3,24,'A_4','AVAILABLE','C'),
        (3,25,'A_5','AVAILABLE','C'),
        (3,26,'A_6','AVAILABLE','C'),
        (3,27,'A_7','AVAILABLE','C'),
        (3,28,'A_8','AVAILABLE','C'),
        (3,29,'A_9','AVAILABLE','C'),
        (3,30,'A_10','AVAILABLE','C');

INSERT INTO users (`balance`,`user_id`,`versions`,`name`)
VALUES (2500000,1,1,'김민수'),
       (1500000,2,1,'최영희');
