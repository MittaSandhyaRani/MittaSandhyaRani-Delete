package Model;

import java.util.Objects;

public class Person {
    private int id;
    private String firstname;

    public Person() {
    }

    public Person(String firstname) {
        this.firstname = firstname;
    }

    public Person(int id, String firstname) {
        this.id = id;
        this.firstname = firstname;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(firstname, person.firstname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname);
    }

    @Override
    public String toString() {
        return "Model.Person{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                '}';
    }
}
