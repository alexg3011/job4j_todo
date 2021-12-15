package ru.job4j.hql;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "base")
public class VacancyBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public VacancyBase() {
    }

    public static VacancyBase of(String name) {
        VacancyBase vb = new VacancyBase();
        vb.name = name;
        return vb;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Vacancy> vacancies = new ArrayList<>();

    public void addVacancy(Vacancy vacancy) {
        this.vacancies.add(vacancy);
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

    public List<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VacancyBase that = (VacancyBase) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "VacancyBase{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", vacancies=" + vacancies
                + '}';
    }
}
