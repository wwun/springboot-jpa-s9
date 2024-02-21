package com.william.curso.springboot.jpa.springbootjpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		
		//List<Person> persons = (List<Person>)repository.findAll();
		//List<Person> persons = (List<Person>)repository.findByProgrammingLanguage("java");
		//List<Person> persons = (List<Person>)repository.buscarByProgrammingLanguage("java");	//personalizado, hace lo mismo que findByProgrammingLanguage
		List<Person> persons = (List<Person>)repository.buscarByProgrammingLanguageAndName("java", "William");	//ahora con dos parámetros

		persons.stream().forEach(person -> {System.out.println(person);});
	}

}
