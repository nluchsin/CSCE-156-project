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
  foreign key (PersonCode) references Persons(PriKey),
  CONSTRAINT PersonEmail UNIQUE (EmailAddress)
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
