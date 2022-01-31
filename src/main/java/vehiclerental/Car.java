package vehiclerental;

import java.time.LocalTime;

public class Car implements Rentable {

    private String id;
    private int rentCost;
    private LocalTime rentTime;

    public Car(String id, int rentCost) {
        this.id = id;
        this.rentCost = rentCost;
    }

    @Override
    public int calculateSumPrice(long minutes) {
        return rentCost* (int) minutes;
    }

    @Override
    public LocalTime getRentingTime() {
        return rentTime;
    }

    @Override
    public void rent(LocalTime time) {
        rentTime = time;
    }

    @Override
    public void closeRent() {
        rentTime = null;
    }
}
