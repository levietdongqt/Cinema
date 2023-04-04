use CinemaDB
select * from Room
select * from RoomType
select * from SeatType
select * from RoomTypeDetails
insert into Room (roomID,roomName,description)
values ('R1','Room 1','This is small 2D room'),
		('R2','Room 2','This is large 2D room'),
		('R3','Room 3','This is small 3D room')
go
insert into RoomType
values ('RT1','small 2D','This is small 2D room',1),
		('RT2','the same price small 2D','The price of ticket is the same',1),
		('RT3','large 2D','This is large 2D room',1),
		('RT4','the same price large 2D','The price of ticket is the same',1),
		('RT5','small 3D','This is large 3D room',1),
		('RT6','the same price small 3D','The price of ticket is the same',1)
		go
insert into RoomTypeDetails (roomID,rTypeID)
values ('R1','RT1'),('R1','RT2'),('R2','RT3'),('R2','RT4'),('R3','RT5'),('R3','RT6')
go

insert into SeatType
values	('ST1','normal 2D','NORMAL',70000,1),
		('ST2','vip 2D','VIP',90000,1),
		('ST3','couple 2D','COUPLE',100000,1),
		('ST4','same price 2D','NORMAL',60000,1),
		('ST5','same price 2D','VIP',60000,1),
		('ST6','same price 2D','COUPLE',60000,1),
		('ST7','normal 3D','NORMAL',110000,1),
		('ST8','vip 3D','VIP',140000,1),
		('ST9','couple 3D','COUPLE',150000,1),
		('ST10','same price 3D','NORMAL',90000,1),
		('ST11','same price 3D','VIP',90000,1),
		('ST12','same price 3D','COUPLE',90000,1)
go
insert into Product
values ('P01','Pepsi','Drinks','src\main\resources\images\products\pepsi',20000,1),
 ('P02','Coca','Drinks','src\main\resources\images\products\coca',20000,1),
 ('P03','7Up','Drinks','src\main\resources\images\products\7up',20000,1),
 ('P04','Aquafina','Drinks','src\main\resources\images\products\aquafina',15000,1),
 ('P05','Popcorn','Snacks','src\main\resources\images\products\popcorn',60000,1),
 ('P06','Snack','Snacks','src\main\resources\images\products\snack',30000,1),
 ('P07','Combo 1','Combo','src\main\resources\images\products\combo1',70000,1),
 ('P08','Combo 2','Combo','src\main\resources\images\products\combo2',80000,1)

		
		
		

		