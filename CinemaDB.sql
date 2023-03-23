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
  [price] DECIMAL(10,2) not null,
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
	[description] varchar(255)	
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
CREATE TABLE ShowTime (
	[sTimeID] varchar(10) primary key,
	[startTime] Time not null
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
	[rTypeID] varchar(5),
	[sMapID] varchar(5),
	FOREIGN KEY (sTypeID) REFERENCES SeatType(sTypeID),
	FOREIGN KEY (rTypeID) REFERENCES RoomType(rTypeID),
	FOREIGN KEY (sMapID) REFERENCES SeatMap(sMapID)
)
go
CREATE TABLE Room (
	[roomID] varchar(10) primary key,
	[roomName]  varchar(50) not null,
	[rTypeID] varchar(5),
	[seatQuanlity] int, 
	description varchar(255),
	[status] bit default 1,
	FOREIGN KEY (rTypeID) REFERENCES RoomType(rTypeID)
)
go
CREATE TABLE TimeDetails (
	[timeDetailsID] int identity primary key,
	[roomID]  varchar(5),
	[sTimeID] varchar(5),
	[showDate] Date default getdate(),
	status bit default 1, 
	FOREIGN KEY (roomID) REFERENCES Room(roomID),
	FOREIGN KEY (sTimeID) REFERENCES ShowTime(sTimeID)
)
go
CREATE TABLE Schedule (
	[scheduleID] varchar(20) primary key,
	[filmID]  varchar(20),
	[timeDetailsID] int,
	[note] varchar(255),
	[status] bit default 1, 
	FOREIGN KEY (filmID) REFERENCES Film(filmID),
	FOREIGN KEY (timeDetailsID) REFERENCES TimeDetails(timeDetailsID)
)
go

Create table Ticket (
	[ticketID] int identity primary key,
	[billID] int,
	[scheduleID] varchar(20),
	[seatMap] varchar(10),
	[status] bit default(1),	
	FOREIGN KEY (scheduleID) REFERENCES Schedule(scheduleID),
	FOREIGN KEY (billID) REFERENCES Bill(billID)
)
go
