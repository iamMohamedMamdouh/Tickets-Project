<?php
include 'database.php';
$passengerID = $_POST['PassengerID'];
$trainCode = $_POST['TrainCode'];
$employeeID = $_POST['EmployeeID'];
$tickets = $_POST['TicketsReserved'];
$fromStation = $_POST['FromStation'];
$toStation = $_POST['ToStation'];

try {

    if (empty($passengerID) || empty($trainCode) || empty($employeeID) || empty($tickets) || empty($fromStation) || empty($toStation)) {
        throw new Exception("All fields are Done");
    }

    if ($tickets < 1 || $tickets > 4) {
        throw new Exception("(1-4) tickets only.");
    }


    $stmt = $conn->prepare("SELECT TrainType, PricePerStation, DepartureTime FROM Trains WHERE TrainCode = ?");
    $stmt->execute([$trainCode]);
    $train = $stmt->fetch(PDO::FETCH_ASSOC);

    if (!$train) {
        throw new Exception("Invalid Code.");
    }

    $trainType = $train['TrainType'];
    $pricePerStation = $train['PricePerStation'];
    $departureTime = $train['DepartureTime'];

    $currentTime = new DateTime();
    $departureDateTime = new DateTime($departureTime);
    $interval = $currentTime->diff($departureDateTime);

    if ($interval->invert == 0 && $interval->h < 1) { // 1 hour
        throw new Exception("Reservations are not allowed within 1 hour of the train's departure time.");
    }

    
    $stationStmt = $conn->prepare("SELECT StationID FROM Stations WHERE StationName = ?"); //total price
    $stationStmt->execute([$fromStation]);
    $fromStationID = $stationStmt->fetchColumn();

    $stationStmt->execute([$toStation]);
    $toStationID = $stationStmt->fetchColumn();

    if (!$fromStationID || !$toStationID) {
        throw new Exception("Invalid From or To Station.");
    }

    $totalStations = abs($toStationID - $fromStationID);
    $totalPrice = $totalStations * $pricePerStation * $tickets;

   
    $stmt = $conn->prepare("INSERT INTO Reservations (PassengerID, TrainCode, EmployeeID, TicketsReserved, FromStation, ToStation, ReservationTime)
                            VALUES (?, ?, ?, ?, ?, ?, NOW())");
    $stmt->execute([$passengerID, $trainCode, $employeeID, $tickets, $fromStation, $toStation]);

    echo "Reservation Successful!<br>";
    echo "Passenger ID: " . htmlspecialchars($passengerID) . "<br>";
    echo "Train Code: " . htmlspecialchars($trainCode) . "<br>";
    echo "Train Type: " . htmlspecialchars($trainType) . "<br>";
    echo "From: " . htmlspecialchars($fromStation) . "<br>";
    echo "To: " . htmlspecialchars($toStation) . "<br>";
    echo "Tickets Reserved: " . htmlspecialchars($tickets) . "<br>";
    echo "Total Price: " . htmlspecialchars($totalPrice) . " EGP";

} catch (Exception $e) {
    echo "Error: " . $e->getMessage();
}
?>





<!--
<?php
include 'database.php';

$passengerID = $_POST['PassengerID'];
$trainCode = $_POST['TrainCode'];
$employeeID = $_POST['EmployeeID'];
$tickets = $_POST['TicketsReserved'];

try {
    $stmt = $conn->prepare("INSERT INTO Reservations (PassengerID, TrainCode, EmployeeID, TicketsReserved)
                            VALUES (?, ?, ?, ?)");
    $stmt->execute([$passengerID, $trainCode, $employeeID, $tickets]);
    echo "Reservation Successful";
} catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
}
?>*/-->
