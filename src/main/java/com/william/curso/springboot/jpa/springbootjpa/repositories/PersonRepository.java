package com.william.curso.springboot.jpa.springbootjpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.william.curso.springboot.jpa.springbootjpa.dto.PersonDto;
import com.william.curso.springboot.jpa.springbootjpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long>{ //<NombreDeClaseEntity, tipoDeDatoLlavePrimaria>

    @Query("select p from Person p where p.id in ?1")
    public List<Person> getPersonsByIds(List<Long> ids);

    @Query("select p.name, length(p.name) from Person p where length(p.name)=(select min(length(p.name)) from Person p)")
    public List<Object[]> getShorterName();

    @Query("select p from Person p where p.id=(select max(p.id) from Person p)")
    public Optional<Person> getLastRegistration();

    @Query("select min(p.id), max(p.id), sum(p.id), avg(length(p.name)), count(p.id) from Person p")
    public Object getResumeAggregationFunction();
    
    @Query("select min(length(p.name)) from Person p")
    public Integer getMinLengthName();
    
    @Query("select max(length(p.name)) from Person p")
    public Integer getMaxLengthName();

    @Query("select p.name, length(p.name) from Person p")
    public List<Object[]> getPersonNameLength();

    @Query("select count(p) from Person p")
    Long getTotalPerson();

    @Query("select min(p.id) from Person p")
    Long getMinId();
    
    @Query("select max(p.id) from Person p")
    Long getMaxId();

    List<Person> findAllByOrderByNameAscLastnameDesc();

    @Query("select p from Person p order by p.name, p.lastname desc")
    List<Person> getAllOrdered();

    List<Person> findByIdBetweenOrderByNameAsc(Long id1, Long id2);

    List<Person> findByNameBetweenOrderByNameDescLastnameDesc(String name1, String name2);

    @Query("select p from Person p where p.id between ?1 and ?2 order by p.name desc")
    List<Person> findAllBetweenId(Long id1, Long id2);

    @Query("select p from Person p where p.name between ?1 and ?2 order by p.name asc, p.lastname desc")
    List<Person> findAllBetweenName(String c1, String c2);

    @Query("select p.id, upper(p.name), lower(p.lastname), upper(p.programmingLanguage) from Person p")
    List<Object[]> findAllPersonDataListCase();
    
    @Query("select upper(p.name || ' ' || p.lastname) from Person p")
    List<String> findAllFullNameConcatUpper();
    
    @Query("select lower(concat(p.name, ' ', p.lastname)) from Person p")
    List<String> findAllFullNameConcatLower();

    // @Query("select CONCAT(p.name, ' ', p.lastname) from Person p")
    @Query("select p.name || ' ' || p.lastname from Person p")
    List<String> findAllFullNameConcat();
    
    @Query("SELECT COUNT(DISTINCT(p.programmingLanguage)) from Person p")
    Long findAllProgrammingLanguageDistinctCount();

    @Query("SELECT DISTINCT(p.programmingLanguage) from Person p")
    List<String> findAllProgrammingLanguageDistinct();

    @Query("SELECT p.name FROM Person p")
    List<String> findAllNames();
    
    @Query("SELECT DISTINCT(p.name) FROM Person p")
    List<String> findAllNamesDistinct();

    @Query("SELECT new com.william.curso.springboot.jpa.springbootjpa.dto.PersonDto(p.name, p.lastname) FROM Person p")
    List<PersonDto> findAllPersonDto();
    
    @Query("SELECT new Person(p.name, p.lastname) FROM Person p")   //método diferente para seleccionar una persona que solo retorna un objeto con los parámetros que se quieran
    List<Person> findAllObjectPersonPersonalized();

    @Query("SELECT p.name FROM Person p WHERE p.id=?1")
    String getNameById(Long id);

    @Query("SELECT p.name FROM Person p WHERE p.id=?1")
    Long getIdById(Long id);

    @Query("SELECT CONCAT(p.name, ' ', p.lastname) as fullname FROM Person p WHERE p.id=?1")
    String getFullNameById(Long id);

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

    @Query("SELECT p, p.programmingLanguage from Person p")
    List<Object[]> findAllMixPerson();

    @Query("SELECT p.id, p.name, p.lastname, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonDataList();

    @Query("SELECT p.id, p.name, p.lastname, p.programmingLanguage from Person p WHERE p.id=?1")
    Optional<Object> obtenerPersonDataByid(Long id);

    @Query("SELECT p.name, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonData();

    @Query("SELECT p.name, p.programmingLanguage FROM Person p WHERE p.name=?1")
    List<Object[]> obtenerPersonaData(String name);

    @Query("SELECT p.name, p.programmingLanguage FROM Person p WHERE p.programmingLanguage=?1 AND p.name=?2")
    List<Object[]> obtenerPersonaData(String programmingLanguage, String name);

    @Query("SELECT p.name, p.programmingLanguage FROM Person p WHERE p.programmingLanguage=?1")
    List<Object[]> obtenerPersonDataByProgrammingLanguage(String programmingLanguage);
}
