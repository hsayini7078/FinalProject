import java.util.Comparator;

public class Person implements Comparable<Person> {

    private final String name;
    private final double phoneNumber;
    private final String address;

    public Person(String name, double phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }


    public String getName() {
        return name;
    }

    public double getphoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "NAME = " + name + ", NUMBER = " + phoneNumber + ", ADDRESS = " + address;
    }

    @Override
    public int compareTo(Person o) {
        return 0;
    }
}
