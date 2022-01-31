package vehiclerental;

import java.time.LocalTime;
import java.util.*;

public class RentService {

    private Set<Rentable> rentables = new HashSet<>();
    private Set<User> users = new HashSet<>();
    private Map<Rentable,User> actualRenting = new TreeMap<>();

    public Set<Rentable> getRentables() {
        return rentables;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Map<Rentable, User> getActualRenting() {
        return actualRenting;
    }

    public void registerUser(User user){
        if (users.stream().anyMatch(n->n.getUserName().equals(user.getUserName()))){
            throw new UserNameIsAlreadyTakenException("Username is taken!");
        }
        users.add(user);
    }

    public void addRentable(Rentable rentable){
        rentables.add(rentable);
    }

    public void rent(User user, Rentable rentable, LocalTime time){
        if (rentable.getRentingTime()!= null){
            throw new IllegalStateException();
        }
        if (rentable instanceof Car && user.getBalance() < rentable.calculateSumPrice(180)){
            throw new IllegalStateException();
        }
        if (rentable instanceof Bike && user.getBalance() < rentable.calculateSumPrice(180)){
            throw new IllegalStateException();
        }
        rentable.rent(time);
        actualRenting.put(rentable,user);
    }

    public void closeRent(Rentable rentable, int minutes){
        if (actualRenting.containsKey(rentable)){
            actualRenting.get(rentable).minusBalance(rentable.calculateSumPrice(minutes));
            actualRenting.remove(rentable);
            rentable.closeRent();
        }
    }

}
