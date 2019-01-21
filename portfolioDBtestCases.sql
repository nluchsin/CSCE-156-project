#Nathan Luchsinger, Tarren Wenig, Rustin Haase
### This code creates a SQL database and populates it. The other datafile performs various queries on the data.
start transaction;
#Drop tables
drop table if exists PortfolioAssets;
drop table if exists Portfolio;
drop table if exists Assets;
drop table if exists PersonEmail;
drop table if exists Persons;
drop table if exists Savings;
drop table if exists Stocks;
drop table if exists Private;
drop table if exists Address;
drop table if exists Email;
drop table if exists State;
drop table if exists Country;

#Creates State table
create table State(
    PriKey int not null primary key auto_increment,
    StateName varchar(255) not null,
    CONSTRAINT State UNIQUE (StateName)
)engine=InnoDB,collate=latin1_general_cs;

#Creates Country Table
create table Country(
 PriKey int not null primary key auto_increment,
 CountryName varchar(255) not null,
 CONSTRAINT Country UNIQUE (CountryName)
)engine=InnoDB,collate=latin1_general_cs;

#Creates Savings table
create table Savings (
  PriKey int not null primary key auto_increment,
  APR float not null
)engine=InnoDB,collate=latin1_general_cs;

#Creates Stocks table
create table Stocks (
  PriKey int not null primary key auto_increment,
  QuarterlyDividend float not null,
  BaseRateOfReturn float not null,
  BetaMeasure float not null,
  StockSymbol varchar(255) not null,
  SharePrice float not null
)engine=InnoDB,collate=latin1_general_cs;

#Create private Table
create table Private (
  PriKey int not null primary key auto_increment,
  QuarterlyDividend float not null,
  BaseRateOfReturn float not null,
  OmegaMeasure float not null,
  SharePrice float not null
)engine=InnoDB,collate=latin1_general_cs;

#Create Address table
create table Address(
  PriKey int not null primary key auto_increment,
  Street varchar(200) not null,
  City varchar(200) not null,
  State int not null,
  Country int not null,
  Zip varchar(200) not null,
  foreign key (Country) references Country(PriKey),
  foreign key (State) references State(PriKey)
)engine=InnoDB,collate=latin1_general_cs;

#Create Persons Table
create table Persons(
  PriKey int not null primary key auto_increment,
  Code varchar(200) not null,
  BrokerLetter varchar(200),
  BrokerSEC varchar(200),
  FirstName varchar(200) not null,
  LastName varchar(200) not null,
  Address int not null,
  foreign key (Address) references Address(PriKey),
  CONSTRAINT State UNIQUE (Code)
)engine=InnoDB,collate=latin1_general_cs;

#Create PersonEmail Table
create table PersonEmail(
  PriKey int not null primary key auto_increment,
  PersonCode int not null,
  EmailAddress varchar(200) not null,
  foreign key (PersonCode) references Persons(PriKey)
)engine=InnoDB,collate=latin1_general_cs;

#Create Assets Table
create table Assets(
  PriKey int not null primary key auto_increment,
  Code1 varchar(250) not null,
  Letter varchar(200) not null,
  Label varchar(200) not null,
  SavingsId int,
  StocksId int,
  PrivateId int,
  foreign key (SavingsId) references Savings(PriKey),
  foreign key (StocksId) references Stocks(PriKey),
  foreign key (PrivateId) references Private(PriKey),
  CONSTRAINT Assets UNIQUE (Code1)
)engine=InnoDB,collate=latin1_general_cs;

#Create Portfolio Table
create table Portfolio(
  PriKey int not null primary key auto_increment,
  PortfolioCode varchar(255) not null,
  OwnerCode int not null,
  ManagerCode int not null,
  BeneficiaryCode int,
  foreign key (OwnerCode) references Persons(PriKey),
  foreign key (ManagerCode) references Persons(PriKey),
  foreign key (BeneficiaryCode) references Persons(PriKey),
  CONSTRAINT Portfolio UNIQUE (PortfolioCode)
)engine=InnoDB,collate=latin1_general_cs;

#Create PortfolioAssets Table
create table PortfolioAssets(
  PriKey int not null primary key auto_increment,
  PortfolioCode int not null,
  AssetCode int not null,
  ParseInt float not null,
  foreign key (PortfolioCode) references Portfolio(PriKey),
  foreign key (AssetCode) references Assets(PriKey)
)engine=InnoDB,collate=latin1_general_cs;


#Populate Table
insert into State(StateName) values ('IL');
insert into State(StateName) values ('NE');

insert into Country(CountryName) values('USA');

insert into Address(Street, City, State, Country, Zip) values ('95314 Nobel Place', 'Chicago', 1, 1, '69332');
insert into Address(Street, City, State, Country, Zip) values ('0 4th Crossing', 'Lincoln', 2, 1, '69508');
insert into Address(Street, City, State, Country, Zip) values ('7313 Service Way', 'Valentine', 2, 1, '69201');
insert into Address(Street, City, State, Country, Zip) values ('7 Morrow Hill', 'Hill', 2, 1, '68588');
insert into Address(Street, City, State, Country, Zip) values ('1838 Hudson Park', 'North', 2, 1, '68439');
insert into Address(Street, City, State, Country, Zip) values ('17219 Bunker Hill Street', 'Omaha', 2, 1, '68392');
insert into Address(Street, City, State, Country, Zip) values ('9825 Fulton Junction', 'Grand Island', 2, 1, '56381');
insert into Address(Street, City, State, Country, Zip) values ('00 Fremont Trail', 'Kearney', 2, 1, '78393');
insert into Address(Street, City, State, Country, Zip) values ('439 Monterey Trail', 'Hastings', 2, 1, '57834');
insert into Address(Street, City, State, Country, Zip) values ('1875 Cardinal Point', 'Holdrege', 2, 1, '67890');
insert into Address(Street, City, State, Country, Zip) values ('58049 Comanche Lane', 'Lexington', 2, 1, '67890');
insert into Address(Street, City, State, Country, Zip) values ('13871 SunBrook Circle', 'Cozad', 2, 1, '67832');
insert into Address(Street, City, State, Country, Zip) values ('57532 Doe Crossing Terrace', 'Gothenburg', 2, 1, '42637');
insert into Address(Street, City, State, Country, Zip) values ('1 Buena Vista Parkway', 'York', 2, 1, '67543');
insert into Address(Street, City, State, Country, Zip) values ('2688 Morrow Way', 'Broken Bow', 2, 1, '32157');
insert into Address(Street, City, State, Country, Zip) values ('54931 Fuller Court', 'McCook', 2, 1, '68943');
insert into Address(Street, City, State, Country, Zip) values ('9733 Butternut Hill', 'Ogallala', 2, 1, '68345');
insert into Address(Street, City, State, Country, Zip) values ('3 Goodland Crossing', 'Columbus', 2, 1, '64723');
insert into Address(Street, City, State, Country, Zip) values ('2 Londonderry Place', 'Norfolk', 2, 1, '62190');
insert into Address(Street, City, State, Country, Zip) values ('45 Old Gate Point', 'Beatrice', 2, 1, '48261');

insert into Persons(Code, BrokerLetter, BrokerSEC, FirstName, LastName, Address) values ('1', 'J', 'N', 'Jacqueline', 'Torres', 1);
insert into Persons(Code, BrokerLetter, BrokerSEC, FirstName, LastName, Address) values ('2', 'E', 'H', 'Andrew', 'Gutierrez', 2);
insert into Persons(Code, BrokerLetter, BrokerSEC, FirstName, LastName, Address) values ('3', NULL, NULL, 'Linda', 'Henry', 3);
insert into Persons(Code, BrokerLetter, BrokerSEC, FirstName, LastName, Address) values ('4', NULL, NULL, 'Julia', 'Meyer', 4);
insert into Persons(Code, BrokerLetter, BrokerSEC, FirstName, LastName, Address) values ('5', NULL, NULL, 'Jesse', 'Greene', 5);
insert into Persons(Code, BrokerLetter, BrokerSEC, FirstName, LastName, Address) values ('6', 'J', 'H', 'Annie', 'Price', 6);
insert into Persons(Code, BrokerLetter, BrokerSEC, FirstName, LastName, Address) values ('7', 'J', 'J', 'Janet', 'Evans', 7);
insert into Persons(Code, BrokerLetter, BrokerSEC, FirstName, LastName, Address) values ('8', 'J', 'H', 'Victor', 'Long', 8);
insert into Persons(Code, BrokerLetter, BrokerSEC, FirstName, LastName, Address) values ('9', NULL, NULL, 'Lori', 'Gonzalez', 9);
insert into Persons(Code, BrokerLetter, BrokerSEC, FirstName, LastName, Address) values ('10', 'E', 'H', 'Martha', 'Robinson', 10);
insert into Persons(Code, BrokerLetter, BrokerSEC, FirstName, LastName, Address) values ('11', 'E', 'H', 'Cheryl', 'Parker', 11);
insert into Persons(Code, BrokerLetter, BrokerSEC, FirstName, LastName, Address) values ('12', 'E', 'H', 'Samuel', 'Fernandez', 12);
insert into Persons(Code, BrokerLetter, BrokerSEC, FirstName, LastName, Address) values ('13', NULL, NULL, 'Christine', 'Clark', 13);
insert into Persons(Code, BrokerLetter, BrokerSEC, FirstName, LastName, Address) values ('14', 'J', 'H', 'Ashley', 'Burton', 14);
insert into Persons(Code, BrokerLetter, BrokerSEC, FirstName, LastName, Address) values ('15', NULL, NULL, 'Henry', 'Medina', 15);
insert into Persons(Code, BrokerLetter, BrokerSEC, FirstName, LastName, Address) values ('16', NULL, NULL, 'Linda', 'Sims', 16);
insert into Persons(Code, BrokerLetter, BrokerSEC, FirstName, LastName, Address) values ('17', NULL, NULL, 'Jimmy', 'Young', 17);
insert into Persons(Code, BrokerLetter, BrokerSEC, FirstName, LastName, Address) values ('18', 'J', 'H', 'Justin', 'Allen', 18);
insert into Persons(Code, BrokerLetter, BrokerSEC, FirstName, LastName, Address) values ('19', NULL, NULL, 'Jeremy', 'Gardner', 19);
insert into Persons(Code, BrokerLetter, BrokerSEC, FirstName, LastName, Address) values ('20', NULL, NULL, 'Walter', 'Barnes', 20);

insert into PersonEmail(PersonCode, EmailAddress) values (1, 'jtorres0@nature.com');
insert into PersonEmail(PersonCode, EmailAddress) values (1, 'tStupid@gmail.com');
insert into PersonEmail(PersonCode, EmailAddress) values (2, 'agutierrez1@lycos.com');
insert into PersonEmail(PersonCode, EmailAddress) values (2, 'palen@gmail.com');
insert into PersonEmail(PersonCode, EmailAddress) values (3, 'henry2@exblog.jp');
insert into PersonEmail(PersonCode, EmailAddress) values (3, 'hexapple@unl.edu');
insert into PersonEmail(PersonCode, EmailAddress) values (4, 'jmeyer3@pbs.org');
insert into PersonEmail(PersonCode, EmailAddress) values (4, 'yaren@gmail.com');
insert into PersonEmail(PersonCode, EmailAddress) values (5, 'jgreene4@pen.io');
insert into PersonEmail(PersonCode, EmailAddress) values (5, 'jinnnging@unl.edu');
insert into PersonEmail(PersonCode, EmailAddress) values (6, 'abob@pen.ie');
insert into PersonEmail(PersonCode, EmailAddress) values (6, 'fanboy@like.com');
insert into PersonEmail(PersonCode, EmailAddress) values (7, 'jevans6@about.me');
insert into PersonEmail(PersonCode, EmailAddress) values (7, 'dr@who.com');
insert into PersonEmail(PersonCode, EmailAddress) values (8, 'vlong7@stanford.edu');
insert into PersonEmail(PersonCode, EmailAddress) values (8, 'whartnell@doctors.com');
insert into PersonEmail(PersonCode, EmailAddress) values (9, 'lgonzalez8@naver.com');
insert into PersonEmail(PersonCode, EmailAddress) values (9, 'bobking@up.com');
insert into PersonEmail(PersonCode, EmailAddress) values (10, 'mrobinson9@simplemachines.org');
insert into PersonEmail(PersonCode, EmailAddress) values (10, 'There.he@jake.com');
insert into PersonEmail(PersonCode, EmailAddress) values (11, 'therar@mans.com');
insert into PersonEmail(PersonCode, EmailAddress) values (11, 'youare@noble.com');
insert into PersonEmail(PersonCode, EmailAddress) values (12, 'sfernandezb@time.com');
insert into PersonEmail(PersonCode, EmailAddress) values (12, 'Heis@king.com');
insert into PersonEmail(PersonCode, EmailAddress) values (13, 'cclarkc@amazon.co.jp');
insert into PersonEmail(PersonCode, EmailAddress) values (13, 'Dadis@nice.com');
insert into PersonEmail(PersonCode, EmailAddress) values (14, 'aburtond@ifeng.com');
insert into PersonEmail(PersonCode, EmailAddress) values (14, 'Momis@nice.com');
insert into PersonEmail(PersonCode, EmailAddress) values (15, 'hmedinae@sitemeter.com');
insert into PersonEmail(PersonCode, EmailAddress) values (15, 'jaymap@hi.com');
insert into PersonEmail(PersonCode, EmailAddress) values (16, 'lsimsf@surveymonkey.com');
insert into PersonEmail(PersonCode, EmailAddress) values (16, 'CountryBoy@Country.com');
insert into PersonEmail(PersonCode, EmailAddress) values (17, 'jyoungg@chron.com');
insert into PersonEmail(PersonCode, EmailAddress) values (17, 'IamYoung@bob.com');
insert into PersonEmail(PersonCode, EmailAddress) values (18, 'jallenh@lycos.com');
insert into PersonEmail(PersonCode, EmailAddress) values (18, 'Yourk@body.com');
insert into PersonEmail(PersonCode, EmailAddress) values (19, 'jgardneri@nba.com');
insert into PersonEmail(PersonCode, EmailAddress) values (19, 'opNoob@legaue.com');
insert into PersonEmail(PersonCode, EmailAddress) values (20, 'wbarnesj@usgs.gov');
insert into PersonEmail(PersonCode, EmailAddress) values (20, 'Iamgover@goverment.com');

insert into Savings(APR) values (0);
insert into Savings(APR) values (.99);
insert into Savings(APR) values (.35);
insert into Savings(APR) values (.38);
insert into Savings(APR) values (.51);
insert into Savings(APR) values (.6);
insert into Savings(APR) values (.36);

insert into Stocks(QuarterlyDividend, BaseRateOfReturn, BetaMeasure, StockSymbol, SharePrice) values (51.33, 16.81, 58.09, 'Thoughtsphere', 49.06);
insert into Stocks(QuarterlyDividend, BaseRateOfReturn, BetaMeasure, StockSymbol, SharePrice) values (0.44, 51.11, 92.56, 'Zava', 20.84);
insert into Stocks(QuarterlyDividend, BaseRateOfReturn, BetaMeasure, StockSymbol, SharePrice) values (95.82, 59.87, 44.22, 'Yata', 377.51);
insert into Stocks(QuarterlyDividend, BaseRateOfReturn, BetaMeasure, StockSymbol, SharePrice) values (58.74, 79.25, 21.7, 'Yoveo', 74.42);
insert into Stocks(QuarterlyDividend, BaseRateOfReturn, BetaMeasure, StockSymbol, SharePrice) values (13.27, 32.55, 41.34, 'Shufflebeat',860.51);
insert into Stocks(QuarterlyDividend, BaseRateOfReturn, BetaMeasure, StockSymbol, SharePrice) values (99.51,99.44,82.88,'Skyba',374.73);
insert into Stocks(QuarterlyDividend, BaseRateOfReturn, BetaMeasure, StockSymbol, SharePrice) values (41.55, 69.93, 79.42, 'Skibox', 1085.59);

insert into Private(QuarterlyDividend, BaseRateOfReturn, OmegaMeasure, SharePrice) values (17.7, 1.31, 3.43, 555.03);
insert into Private(QuarterlyDividend, BaseRateOfReturn, OmegaMeasure, SharePrice) values (2.6, 19.91, 3.36, 845.88);
insert into Private(QuarterlyDividend, BaseRateOfReturn, OmegaMeasure, SharePrice) values (1.53, 22.31, 22.52, 587.83);
insert into Private(QuarterlyDividend, BaseRateOfReturn, OmegaMeasure, SharePrice) values (1.97, 9.51, 2.65, 682.98);
insert into Private(QuarterlyDividend, BaseRateOfReturn, OmegaMeasure, SharePrice) values (4.38, 23.01, 2.61, 853.61);
insert into Private(QuarterlyDividend, BaseRateOfReturn, OmegaMeasure, SharePrice) values (3.55, 24.18, 3.54, 706.78);

insert into Assets(Code1, Letter, Label, SavingsId) values ('1', 'D', 'Savings Account', 1);
insert into Assets(Code1, Letter, Label, SavingsId) values ('2', 'D', 'Savings Account', 2);
insert into Assets(Code1, Letter, Label, SavingsId) values ('3', 'D', 'Savings Account', 3);
insert into Assets(Code1, Letter, Label, SavingsId) values ('4', 'D', 'Savings Account', 4);
insert into Assets(Code1, Letter, Label, SavingsId) values ('5', 'D', 'Savings Account', 5);
insert into Assets(Code1, Letter, Label, SavingsId) values ('6', 'D', 'Savings Account', 6);
insert into Assets(Code1, Letter, Label, SavingsId) values ('7', 'D', 'Savings Account', 7);


insert into Assets(Code1, Letter, Label, StocksId) values ('8', 'S', 'Stocks', 1);
insert into Assets(Code1, Letter, Label, StocksId) values ('9', 'S', 'Stocks', 2);
insert into Assets(Code1, Letter, Label, StocksId) values ('10', 'S', 'Stocks', 3);
insert into Assets(Code1, Letter, Label, StocksId) values ('11', 'S', 'Stocks', 4);
insert into Assets(Code1, Letter, Label, StocksId) values ('12', 'S', 'Stocks', 5);
insert into Assets(Code1, Letter, Label, StocksId) values ('13', 'S', 'Stocks', 6);
insert into Assets(Code1, Letter, Label, StocksId) values ('14', 'S', 'Stocks', 7);

insert into Assets(Code1, Letter, Label, PrivateId) values ('15', 'P', 'PrivateId', 1);
insert into Assets(Code1, Letter, Label, PrivateId) values ('16', 'P', 'PrivateId', 2);
insert into Assets(Code1, Letter, Label, PrivateId) values ('17', 'P', 'PrivateId', 3);
insert into Assets(Code1, Letter, Label, PrivateId) values ('18', 'P', 'PrivateId', 4);
insert into Assets(Code1, Letter, Label, PrivateId) values ('19', 'P', 'PrivateId', 5);
insert into Assets(Code1, Letter, Label, PrivateId) values ('20', 'P', 'PrivateId', 6);

insert into Portfolio(PortfolioCode, OwnerCode, ManagerCode, BeneficiaryCode) values ('1', 5, 6, 14);
insert into Portfolio(PortfolioCode, OwnerCode, ManagerCode, BeneficiaryCode) values ('2', 5, 18, NULL);
insert into Portfolio(PortfolioCode, OwnerCode, ManagerCode, BeneficiaryCode) values ('3', 4, 1, 6);
insert into Portfolio(PortfolioCode, OwnerCode, ManagerCode, BeneficiaryCode) values ('4', 17, 18, 18);
insert into Portfolio(PortfolioCode, OwnerCode, ManagerCode, BeneficiaryCode) values ('5', 20, 11, 7);
insert into Portfolio(PortfolioCode, OwnerCode, ManagerCode, BeneficiaryCode) values ('6', 15, 7, 11);
insert into Portfolio(PortfolioCode, OwnerCode, ManagerCode, BeneficiaryCode) values ('7', 15, 14, 18);
insert into Portfolio(PortfolioCode, OwnerCode, ManagerCode, BeneficiaryCode) values ('8', 20, 2, 18);
insert into Portfolio(PortfolioCode, OwnerCode, ManagerCode, BeneficiaryCode) values ('9', 19, 7, 18);
insert into Portfolio(PortfolioCode, OwnerCode, ManagerCode, BeneficiaryCode) values ('10', 17, 7, 6);

insert into PortfolioAssets(PortfolioCode, AssetCode, ParseInt) values (1, 1, 20000);
insert into PortfolioAssets(PortfolioCode, AssetCode, ParseInt) values (1, 8, 500);
insert into PortfolioAssets(PortfolioCode, AssetCode, ParseInt) values (1, 15, 75);
insert into PortfolioAssets(PortfolioCode, AssetCode, ParseInt) values (3, 2, 1500);
insert into PortfolioAssets(PortfolioCode, AssetCode, ParseInt) values (3, 4, 5000);
insert into PortfolioAssets(PortfolioCode, AssetCode, ParseInt) values (4, 9,5000);
insert into PortfolioAssets(PortfolioCode, AssetCode, ParseInt) values (4, 12,100);
insert into PortfolioAssets(PortfolioCode, AssetCode, ParseInt) values (5, 5,20000);
insert into PortfolioAssets(PortfolioCode, AssetCode, ParseInt) values (6, 20, 50);
insert into PortfolioAssets(PortfolioCode, AssetCode, ParseInt) values (6, 17, 100);
insert into PortfolioAssets(PortfolioCode, AssetCode, ParseInt) values (7, 7, 1000);
insert into PortfolioAssets(PortfolioCode, AssetCode, ParseInt) values (7, 3, 1000);
insert into PortfolioAssets(PortfolioCode, AssetCode, ParseInt) values (8, 10, 100);
insert into PortfolioAssets(PortfolioCode, AssetCode, ParseInt) values (8, 6, 20000);
insert into PortfolioAssets(PortfolioCode, AssetCode, ParseInt) values (9, 11, 5000);
insert into PortfolioAssets(PortfolioCode, AssetCode, ParseInt) values (9, 16, 75);
insert into PortfolioAssets(PortfolioCode, AssetCode, ParseInt) values (10, 18, 69);
insert into PortfolioAssets(PortfolioCode, AssetCode, ParseInt) values (10, 19, 100);
insert into PortfolioAssets(PortfolioCode, AssetCode, ParseInt) values (10, 13, 500);
insert into PortfolioAssets(PortfolioCode, AssetCode, ParseInt) values (10, 14, 100);
insert into PortfolioAssets(PortfolioCode, AssetCode, ParseInt) values (10, 3, 10000);
