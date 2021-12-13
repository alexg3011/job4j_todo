package ru.job4j.toone;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mark")
public class CarMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarModel> cars = new ArrayList<>();

    public static CarMark of(String name) {
        CarMark carMark = new CarMark();
        carMark.name = name;
        return carMark;
    }

    public void addCar(CarModel cm) {
        this.cars.add(cm);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CarModel> getCars() {
        return cars;
    }

    public void setUsers(List<CarModel> cars) {
        this.cars = cars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarMark carMark = (CarMark) o;
        return id == carMark.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CarMark{"
                + ", name='" + name + '\''
                + ", cars=" + cars
                + '}';
    }
}
