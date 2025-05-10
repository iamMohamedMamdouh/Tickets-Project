//import java.util.*;
//
//public class Train2 {
//    private String type;
//    private int trainCode;
//    private int pricePerStation;
//    private List<Station> stations;
//
//    public Train2(String type, int trainCode, int pricePerStation) {
//        this.type = type;
//        this.trainCode = trainCode;
//        this.pricePerStation = pricePerStation;
//        this.stations = new ArrayList<>();
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public int getTrainCode() {
//        return trainCode;
//    }
//
//    public int getPricePerStation() {
//        return pricePerStation;
//    }
//
//    public List<Station> getStations() {
//        return stations;
//    }
//
//    public void addStation(Station station) {
//        stations.add(station);
//    }
//
//    public void setStations(List<String> asList) {
//    }
//}

////////////////////////////////The Last Version////////////////////////////////////
import java.util.*;
    class Train2 {
        private String type;
        private int trainCode;
        private int pricePerStation;
        private List<Station> stations;

        private boolean airConditioned;
        private boolean wheelchairAccessible;
        private boolean spacious;
        private int totalSeats;
        private int reservedSeats;

//        public Train2(String type, int trainCode, int pricePerStation) {
//            this.type = type;
//            this.trainCode = trainCode;
//            this.pricePerStation = pricePerStation;
//            this.stations = new ArrayList<>();
//        }
        public Train2(String type, int trainCode, int pricePerStation, boolean airConditioned, boolean wheelchairAccessible, boolean spacious) {
            this.type = type;
            this.trainCode = trainCode;
            this.pricePerStation = pricePerStation;
            this.airConditioned = airConditioned;
            this.wheelchairAccessible = wheelchairAccessible;
            this.spacious = spacious;
            this.totalSeats = 100;
            this.reservedSeats = 0;
            this.stations = new ArrayList<>();
        }

        public void addStation(Station station) {
            stations.add(station);
        }

        public List<Station> getStations() { // Added getter
            return stations;
        }

        public int getTravelTime(String startStation, String endStation) {
            int startIndex = -1;
            int endIndex = -1;
            int totalTime = 0;

            for (int i = 0; i < stations.size(); i++) {
                if (stations.get(i).getName().equalsIgnoreCase(startStation)) {
                    startIndex = i;
                }
                if (stations.get(i).getName().equalsIgnoreCase(endStation)) {
                    endIndex = i;
                }

                if (startIndex != -1 && i >= startIndex && i < endIndex) {
                    totalTime += stations.get(i).getTravelTime();
                }
            }

            if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
                return -1;
            }
            return totalTime;
        }

        public String getType() {
            return type;
        }

        public int getTrainCode() {
            return trainCode;
        }

        public boolean isAirConditioned() {
            return airConditioned;
        }

        public boolean isWheelchairAccessible() {
            return wheelchairAccessible;
        }

        public boolean isSpacious() {
            return spacious;
        }

        public int getAvailableSeats() {
            return totalSeats - reservedSeats;
        }
        public int getPricePerStation() {
            return pricePerStation;
        }

        public boolean reserveSeats(int numSeats) {
            if (reservedSeats + numSeats <= totalSeats) {
                reservedSeats += numSeats;
                return true;
            }
            return false;
        }
    }
