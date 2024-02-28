package com.william.curso.springboot.jpa.springbootjpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.william.curso.springboot.jpa.springbootjpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long>{ //<NombreDeClaseEntity, tipoDeDatoLlavePrimaria>

    @Query("SELECT p FROM Person p where p.id =?1")
    Optional<Person> findOne(Long id);

    @Query("SELECT p FROM Person p where p.name =?1")
    Optional<Person> findOneName(String name);

    Optional<Person> findByName(String name);

    @Query("SELECT p FROM Person p where p.name LIKE %?1%")
    Optional<Person> findOneLikeName(String name);

    Optional<Person> findByNameContaining(String name);

    List<Person> findByProgrammingLanguage(String programmingLanguage);

    @Query("SELECT p FROM Person p WHERE p.programmingLanguage=?1")    //es una consulta jpa(se usan los nombres de la clase y sus atributos), no sql (no el nombre de la tabla)    //=?1 número de argumento que se envía
    List<Person> buscarByProgrammingLanguage(String programmingLanguage);

    @Query("SELECT p FROM Person p WHERE p.programmingLanguage=?1 AND name=?2")
    List<Person> buscarByProgrammingLanguageAndName(String programmingLanguage, String name);

    @Query("SELECT p.name, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonData();

    @Query("SELECT p.name, p.programmingLanguage FROM Person p WHERE p.name=?1")
    List<Object[]> obtenerPersonaData(String name);

    @Query("SELECT p.name, p.programmingLanguage FROM Person p WHERE p.programmingLanguage=?1 AND p.name=?2")
    List<Object[]> obtenerPersonaData(String programmingLanguage, String name);

    @Query("SELECT p.name, p.programmingLanguage FROM Person p WHERE p.programmingLanguage=?1")
    List<Object[]> obtenerPersonDataByProgrammingLanguage(String programmingLanguage);
}
