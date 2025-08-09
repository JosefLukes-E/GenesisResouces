package cz.engeto.GenesisResouces.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    @Column(name = "PersonID")
    private String personId;
    private String UUID;

    public User() {
    }


//    public User(Long id, String name, String surname, String personId) {
//        this.id = id;
//        this.name = name;
//        this.surname = surname;
//        this.personId = personId;
//        this.UUID = java.util.UUID.randomUUID().toString();
//    }

    public User(String name, String surname, String personId) {
        this.name = name;
        this.surname = surname;
        this.personId = personId;
        this.UUID = java.util.UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPersonId() {
        return personId;
    }

    public String getUUID() {
        return UUID;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", personId='" + personId + '\'' +
                ", UUID='" + UUID + '\'' +
                '}';
    }
}
