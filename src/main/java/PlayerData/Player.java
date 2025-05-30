package PlayerData;

import java.io.Serializable;
import java.util.Objects;

public class Player implements Serializable {
    private String Name;
    private String Country;
    private int Age;
    private double Height;
    private String Club;
    private String Position;
    private int Number;
    private int Salary;

    public Player(String name, String country, int age,
                  double height, String club, String position,
                  int number, int salary) {
        Name = name;
        Country = country;
        Age = age;
        this.Height = height;
        Club = club;
        Position = position;
        Number = number;
        Salary = salary;
    }

    public String getName() {
        return Name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Age == player.Age && Double.compare(Height, player.Height) == 0 && Number == player.Number && Salary == player.Salary && Objects.equals(Name, player.Name) && Objects.equals(Country, player.Country) && Objects.equals(Club, player.Club) && Objects.equals(Position, player.Position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name, Country, Age, Height, Club, Position, Number, Salary);
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public double getHeight() {
        return Height;
    }

    public void setHeight(double height) {
        this.Height = height;
    }

    public String getClub() {
        return Club;
    }

    public void setClub(String club) {
        Club = club;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public int getSalary() {
        return Salary;
    }

    public void setSalary(int salary) {
        Salary = salary;
    }

    @Override
    public String toString() {
        return "Name: " + Name + ", Country: " + Country + ", Age: " + Age + ", Height: " + Height + ", Club: " + Club + ", Position: " + Position + "," + ((Number == -1) ? "" : (" Number: " + Number)) + ", Salary: " + Salary;
    }
}
