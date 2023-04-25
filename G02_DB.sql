USE master
GO
if exists(SELECT * FROM master..sysdatabases WHERE name='CinemaDB')
DROP DATABASE CinemaDB
GO
create  database CinemaDB
go
use CinemaDB
go

CREATE TABLE Customer (
  [cusPhone] varchar(15) PRIMARY KEY,
  [customerName] VARCHAR(100) not null,
  [birthDate] DATE,
  [address] VARCHAR(255),
  [email] VARCHAR(100),
  [totalpoints] INT default 1
);
go

CREATE TABLE Promotion (
  [promoID] INT IDENTITY(1,1) PRIMARY KEY,
  [promoName] VARCHAR(100),
  [startTime] DATETIME,
  [endTime] DATETIME,
  [status] BIT default 0
);
go

CREATE TABLE Employee (
  [userName] varchar(100) primary key,
  [empName] VARCHAR(100) not null,
  [password] VARCHAR(255) not null,
  [position] VARCHAR(50) not null,
  [birthDate] DATE,
  [gender] bit,
  [startDate] DATE default getDate(),
  [email] VARCHAR(100),
  [status] BIT default 1,
  [empPhone] varchar(20)
);
go
CREATE TABLE Bill (
  [billID] int identity PRIMARY KEY,
  [printDate] DATETIME,
  [cusPhone] varchar(15),
  [promoID] INT,
  [userName] varchar(100),
  [exchangePoints] Float,
  FOREIGN KEY (cusPhone) REFERENCES Customer(cusPhone),
  FOREIGN KEY (promoID) REFERENCES Promotion(promoID),
  FOREIGN KEY (userName) REFERENCES Employee(userName)
);
go
CREATE TABLE WorkSession (
  [sessionID] INT IDENTITY(1,1) PRIMARY KEY,
  [userName] varchar(100),
  [startTime] DATETIME,
  [endTime] DATETIME,
  FOREIGN KEY (userName) REFERENCES Employee(userName)
);
go
CREATE TABLE Product (
  [productID] VARCHAR(10) PRIMARY KEY,
  [productName] VARCHAR(100) not null,
  [type] VARCHAR(50),
  [imgUrl] VARCHAR(255),
  [price] int not null,
  [status] BIT default 1
);
go
CREATE TABLE ProductBill (
  [proBillID] VARCHAR(20) PRIMARY KEY,
  [productID] VARCHAR(10),
  [billID] int,
  [quantity] INT,
  FOREIGN KEY (productID) REFERENCES Product(productID),
  FOREIGN KEY (billID) REFERENCES Bill(billID)
);
go


CREATE TABLE FilmGenre (
	[fGenreID] int identity primary key,
	[fGenreName] VARCHAR(100) not null
)
go
CREATE TABLE Actors (
	[actorID] int identity primary key,
	[actorName] VARCHAR(100) not null,
	[birthDate] Date,
	[homeTown] varchar(100)
)
go
CREATE TABLE Film (
	[filmID] varchar(20) primary key,
	[filmName] VARCHAR(100) not null,
	[limitAge] int not null,
	[startDate] date not null,
	[endDate] date not null,
	[duration] int not null,
	[imageUrl] varchar(255) not null,
	[director] varchar(100),
	[viewFilm] int default 0,
	[description] varchar(255),
	[status] bit default 1
)
go

CREATE TABLE FilmGenreDetails (
	[fgDetailsID] int identity primary key,
	[fGenreID] int,
	[filmID] varchar(20),
	FOREIGN KEY (fGenreID) REFERENCES FilmGenre(fGenreID),
	FOREIGN KEY (filmID) REFERENCES Film(filmID)
)
go
CREATE TABLE ActorOfFilm (
	[aoFilmID] int identity primary key,
	[actorID] int ,
	[filmID] varchar(20),
	FOREIGN KEY (actorID) REFERENCES Actors(actorID),
	FOREIGN KEY (filmID) REFERENCES Film(filmID)
)
go
CREATE TABLE RoomType (
	[rTypeID] varchar(10) primary key,
	[rTypeName]  varchar(100) not null,
	[Description] varchar(255),
	[status] bit default 1
)
go
CREATE TABLE SeatType (
	[sTypeID] varchar(5) primary key,
	[sTypeName]  varchar(100),
	[typeGroup] varchar(20),
	[seatPrice] int default 0,
	[status] bit default 1
)
go
CREATE TABLE SeatMap (
	[sMapID] varchar(5) primary key,
	[seatRow] varchar(5),
	[seatNum] int
)
go

CREATE TABLE RoomSeatDetails (
	[rsDetailsID] varchar(20) primary key,
	[sTypeID] varchar(5),
	[rTypeID] varchar(10),
	[sMapID] varchar(5),
	FOREIGN KEY (sTypeID) REFERENCES SeatType(sTypeID),
	FOREIGN KEY (rTypeID) REFERENCES RoomType(rTypeID),
	FOREIGN KEY (sMapID) REFERENCES SeatMap(sMapID)
)
go
CREATE TABLE Room (
	[roomID] varchar(10) primary key,
	[roomName]  varchar(50) not null,
	[seatQuanlity] int, 
	description varchar(255),
	[status] bit default 1,
)
Create table RoomTypeDetails (
	[rtDetailsID] int identity primary key,
	[roomID] varchar(10),
	[rTypeID] varchar(10),
	[status] bit default 1,
	FOREIGN KEY (roomID) REFERENCES Room(roomID),
	FOREIGN KEY (rTypeID) REFERENCES RoomType(rTypeID)
)
go
CREATE TABLE Schedule (
	[scheduleID] varchar(20) primary key,
	[filmID]  varchar(20),
	[rtDetailsID] int,
	[startTime] datetime,
	[endTime] datetime,
	[note] varchar(255),
	[status] bit default 1, 
	FOREIGN KEY (filmID) REFERENCES Film(filmID),
	FOREIGN KEY (rtDetailsID) REFERENCES RoomTypeDetails(rtDetailsID)
)
go

Create table Ticket (
	[ticketID] int identity primary key,
	[billID] int,
	[price] int,
	[scheduleID] varchar(20),
	[seatMap] varchar(10),
	[status] bit default(1),	
	FOREIGN KEY (scheduleID) REFERENCES Schedule(scheduleID),
	FOREIGN KEY (billID) REFERENCES Bill(billID)
)
go


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
values ('P01','Pepsi','Drinks','src\main\resources\images\products\pepsi.png',20000,1),
 ('P02','Coca','Drinks','src\main\resources\images\products\coca.png',20000,1),
 ('P03','7Up','Drinks','src\main\resources\images\products\7up.png',20000,1),
 ('P04','Aquafina','Drinks','src\main\resources\images\products\aquafina.png',15000,1),
 ('P05','Popcorn','Snacks','src\main\resources\images\products\popcorn.png',60000,1),
 ('P06','Snack','Snacks','src\main\resources\images\products\snack.png',30000,1),
 ('P07','Combo 1','Combo','src\main\resources\images\products\combo1.png',70000,1),
 ('P08','Combo 2','Combo','src\main\resources\images\products\combo2.png',80000,1)
go

insert into FilmGenre
values('Action'),
	('Adventure'),
	('Sci-fi'),
	('Fantasy'),
	('Drama'),
	('Comedy'),
	('Horror'),
	('Musical'),
	('Romance'),
	('Thriller'),
	('Historial'),
	('Animated')
go

insert into Actors
values('Chris Evans','1981-03-06','Boston, Massachussets, U.S'),
('Christoper Hemsworth','1983-08-11','Boston, Melbourne, Victoria, Australia')
go

insert into Film
values ('PAE300000','Avenger EndGame',13,'2023-04-07','2023-04-30',150,
'src\main\resources\images\endgame.png','Joss Whedon',0,
'After the devastating events of Avengers: the universe is in ruins. With the help of remaining allies, 
the Avengers assemble once more in order to reverse Thanos actions and restore balance to the universe.',1)
go
