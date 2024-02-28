package com.william.curso.springboot.jpa.springbootjpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.william.curso.springboot.jpa.springbootjpa.entities.Person;
import com.william.curso.springboot.jpa.springbootjpa.repositories.PersonRepository;



@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner{

	@Autowired
	private PersonRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		update();

	}

	@Transactional
	public void delete2(){

		repository.findAll().forEach(System.out::println);

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingresar el ID de la persona");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);

		// if(optionalPerson.isPresent()){
		// 	repository.deleteById(id);
		// }

		//usando ifPresentOrElse
		optionalPerson.ifPresentOrElse(repository::delete, () -> System.out.println("No encontrado"));	//person -> repository.delete(person) = repository::delete

		repository.findAll().forEach(System.out::println);

		scanner.close();
	}

	@Transactional
	public void delete(){

		repository.findAll().forEach(System.out::println);

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingresar el ID de la persona");
		Long id = scanner.nextLong();

		repository.deleteById(id);
		
		repository.findAll().forEach(System.out::println);

		scanner.close();
	}

	@Transactional
	public void update(){

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingresar el ID de la persona: ");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);

		//usando ifPresent
		//optionalPerson.ifPresent(person -> {
		//usando isPresent
		if(optionalPerson.isPresent()){
			Person personDb = optionalPerson.orElseThrow();
			System.out.println(personDb);
			System.out.println("Ingrese el lenguaje de programación");
			String programmingLanguage = scanner.next();
			personDb.setProgrammingLanguage(programmingLanguage);
			Person personUpdated = repository.save(personDb);
			System.out.println(personUpdated);
		}else{
			System.out.println("El usuario no existe");
		}
		//);

		scanner.close();
	}

	@Transactional	//es un aspecto también
	public void create(){

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingresar el nombre: ");
		String name = scanner.next();
		System.out.println("Ingresar el apellido: ");
		String lastname = scanner.next();
		System.out.println("Ingresar el lenguaje de programación: ");
		String programmingLanguage = scanner.next();
		scanner.close();
		
		Person person = new Person(null, name, lastname, programmingLanguage);

		Person personNew = repository.save(person);	//save no es necesario agregarlo a Person Repository porque se está usando el nombre predefinido, en el caso de findByNameContaining lo que hace tabmién está predefinido pero no el nombre
		System.out.println(personNew);

		repository.findById(personNew.getId()).ifPresent(System.out::println);
	}

	@Transactional(readOnly = true)
	public void findOne(){

		// Person person = null;
		// Optional<Person> optionalPerson = repository.findById(7L);	//findById devuelve optional
		// //if(!optionalPerson.isEmpty()){
		// if(optionalPerson.isPresent()){
		// 	person = optionalPerson.get();
		// }
		// System.out.println(person);

		//repository.findById(7L).ifPresent(System.out::println);	//repository.findById(7L).ifPresent(person -> System.out.println(person));	//person se pasa al print
		//repository.findOne(7L).ifPresent(System.out::println);
		//repository.findOneName("William").ifPresent(System.out::println);
		//repository.findByName("William").ifPresent(System.out::println);
		//repository.findOneLikeName("Will").ifPresent(System.out::println);
		repository.findByNameContaining("Will").ifPresent(System.out::println);

	}

	@Transactional(readOnly = true)
	public void List(){
		//List<Person> persons = (List<Person>)repository.findAll();
		//List<Person> persons = (List<Person>)repository.findByProgrammingLanguage("java");
		//List<Person> persons = (List<Person>)repository.buscarByProgrammingLanguage("java");	//personalizado, hace lo mismo que findByProgrammingLanguage
		List<Person> persons = (List<Person>)repository.buscarByProgrammingLanguageAndName("java", "William");	//ahora con dos parámetros

		persons.stream().forEach(person -> {System.out.println(person);});

		List<Object[]> personValues = repository.obtenerPersonaData("William");

		personValues.stream().forEach(person -> {System.out.println(person[0]+ " es experto en : " + person[1]);});
	}

}
