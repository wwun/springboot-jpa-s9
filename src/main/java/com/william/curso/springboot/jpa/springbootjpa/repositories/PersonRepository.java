package com.william.curso.springboot.jpa.springbootjpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.william.curso.springboot.jpa.springbootjpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long>{ //<NombreDeClaseEntity, tipoDeDatoLlavePrimaria>

    List<Person> findByProgrammingLanguage(String programmingLanguage);

    @Query("SELECT p FROM Person p WHERE p.programmingLanguage=?1")    //es una consulta jpa(se usan los nombres de la clase y sus atributos), no sql (no el nombre de la tabla)    //=?1 número de argumento que se envía
    List<Person> buscarByProgrammingLanguage(String programmingLanguage);

    @Query("SELECT p FROM Person p WHERE p.programmingLanguage=?1 AND name=?2")
    List<Person> buscarByProgrammingLanguageAndName(String programmingLanguage, String name);
}
