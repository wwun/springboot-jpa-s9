package com.william.curso.springboot.jpa.springbootjpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="persons")  //se puede omitir si la clase se llama igual a la tabla
public class Person {

    @Id //se puede omitir el nombre del id si se llama igual que en la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;

    @Column(name="programming_language")
    private String programmingLanguage;

    @Embedded
    private Audit audit = new Audit();

    public Person() {
    }
    
    public Person(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    public Person(Long id, String name, String lastname, String programmingLanguage) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.programmingLanguage = programmingLanguage;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getProgrammingLanguage() {
        return programmingLanguage;
    }
    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }
    
    //los métodos getters y setters de creatAt y updatedAt son enviados a la clase Audit
    @Override
    public String toString() {
        return "id=" + id + ", name=" + name + ", lastname=" + lastname + ", programmingLanguage="
                + programmingLanguage + ", createAt=" + audit.getCreateAt() + ", updated=" + audit.getUpdatedAt() +"]";
    }

}
