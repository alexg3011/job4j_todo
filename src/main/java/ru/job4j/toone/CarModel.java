package ru.job4j.toone;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "model")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "carmark_id")
    private CarMark mark;
    public static CarModel of(String name, CarMark carMark) {
        CarModel carModel = new CarModel();
        carModel.name = name;
        carModel.mark = carMark;
        return carModel;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarModel carModel = (CarModel) o;
        return id == carModel.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CarModel{"
                + "name='" + name + '\''
                + '}';
    }

    public CarMark getMark() {
        return mark;
    }

    public void setMark(CarMark mark) {
        this.mark = mark;
    }
}
