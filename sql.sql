CREATE DATABASE TrainStation;

USE TrainStation;

-- Employee Table
CREATE TABLE Employees (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(50),
    Address VARCHAR(100),
    Password VARCHAR(50),
    Salary FLOAT
);

-- Passenger Table
CREATE TABLE Passengers (
    PassengerID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(50)
);

-- Train Table
CREATE TABLE Trains (
    TrainCode INT PRIMARY KEY,
    TrainType VARCHAR(50),
    DepartureTime TIME,
    ArrivalTime TIME,
    Features TEXT
);

-- Reservation Table
CREATE TABLE Reservations (
    ReservationID INT PRIMARY KEY AUTO_INCREMENT,
    PassengerID INT,
    TrainCode INT,
    EmployeeID INT,
    TicketsReserved INT,
    FOREIGN KEY (PassengerID) REFERENCES Passengers(PassengerID),
    FOREIGN KEY (TrainCode) REFERENCES Trains(TrainCode),
    FOREIGN KEY (EmployeeID) REFERENCES Employees(ID)
);
