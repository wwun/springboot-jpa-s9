package com.william.curso.springboot.jpa.springbootjpa;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.william.curso.springboot.jpa.springbootjpa.dto.PersonDto;
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

	@Transactional(readOnly = true)
	public void whereIn() {
		System.out.println("================== consulta where in ==================");
		List<Person> persons = repository.getPersonsByIds(Arrays.asList(1L, 2L, 5L, 7L));
		persons.forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void subQueries() {
		System.out.println("================== consulta por el nombre mas corto y su largo ==================");
		List<Object[]> registers = repository.getShorterName();
		registers.forEach(reg -> {
			String name = (String) reg[0];
			Integer length = (Integer) reg[1];
			System.out.println("name=" + name + ", length=" + length);	
		});

		System.out.println("================== consulta pra obtener el ultimo registro de persona ==================");
		Optional<Person> optionalPerson = repository.getLastRegistration();
		optionalPerson.ifPresent(System.out::println);
	}
	
	@Transactional(readOnly = true)
	public void queriesFunctionAggregation() {

		
		System.out.println("================== consulta con el total de registros de la tabla persona ==================");
		Long count = repository.getTotalPerson();
		System.out.println(count);
		
		System.out.println("================== consulta con el valor minimo del id ==================");
		Long min = repository.getMinId();
		System.out.println(min);
		
		System.out.println("================== consulta con el valor maximo del id");
		Long max = repository.getMaxId();
		System.out.println(max);
		
		System.out.println("================== consulta con el nombre y su largo ==================");
		List<Object[]> regs = repository.getPersonNameLength();
		regs.forEach(reg -> {
			String name = (String) reg[0];
			Integer length = (Integer) reg[1];
			System.out.println("name=" + name + ", length=" + length);
		});
		
		System.out.println("================== consulta con el nombre mas corto ==================");
		Integer minLengthName = repository.getMinLengthName();
		System.out.println(minLengthName);
		
		System.out.println("================== consulta con el nombre mas largo ==================");
		Integer maxLengthName = repository.getMaxLengthName();
		System.out.println(maxLengthName);

		System.out.println("================== consultas resumen de funciones de agregacion min, max, sum, avg, count ==================");
		Object[] resumeReg = (Object[]) repository.getResumeAggregationFunction();
		System.out.println(
			    "min=" + resumeReg[0] +
				", max=" + resumeReg[1] +
				", sum=" + resumeReg[2] +
				", avg=" + resumeReg[3] +
		        ", count=" + resumeReg[4]);
	}
	
	@Transactional(readOnly=true)
	public void personalizedQueriesBetween() {
		System.out.println("================== consultas por rangos ==================");
		List<Person> persons = repository.findByIdBetweenOrderByNameAsc(2L, 5L);
		persons.forEach(System.out::println);
		
		persons = repository.findByNameBetweenOrderByNameDescLastnameDesc("J", "Q");
		persons.forEach(System.out::println);

		persons = repository.findAllByOrderByNameAscLastnameDesc();
		persons.forEach(System.out::println);

	}

	@Transactional(readOnly = true)
	public void personalizedQueriesConcatUpperAndLowerCase() {
		System.out.println("================== consulta nombres y apellidos de personas ==================");
		List<String> names = repository.findAllFullNameConcat();
		names.forEach(System.out::println);

		System.out.println("================== consulta nombres y apellidos mayuscula ==================");
		names = repository.findAllFullNameConcatUpper();
		names.forEach(System.out::println);

		System.out.println("================== consulta nombres y apellidos minuscula ==================");
		names = repository.findAllFullNameConcatLower();
		names.forEach(System.out::println);
		System.out.println("================== consulta personalizada persona upper y lower case ==================");
		List<Object[]> regs = repository.findAllPersonDataListCase();
		regs.forEach(reg -> System.out.println("id="+ reg[0] + ", nombre=" + reg[1] + ", apellido=" + reg[2]+ ", lenguaje="+reg[3]));

	}

	@Transactional(readOnly = true)
	public void personalizedQueriesDistinct(){
		System.out.println("Consultas con nombres de personas");
		List<String> names = repository.findAllNames();
		names.forEach(System.out::println);

		System.out.println("=================== Consultas con nombres únicos de personas ======================");
		names = repository.findAllNamesDistinct();
		names.forEach(System.out::println);

		System.out.println("=================== Consultas con lenguaje de porgramación únicas ======================");
		List<String> languages = repository.findAllProgrammingLanguageDistinct();
		languages.forEach(System.out::println);

		System.out.println("=================== Consultas total con lenguaje de porgramación únicas ======================");
		Long total = repository.findAllProgrammingLanguageDistinctCount();
		System.out.println("total de lenguajes de programación: "+total);
	}

	@Transactional(readOnly = true)
	public void personalizedQueries2(){
		System.out.println("==================Consulta por objeto persona y lenguaje de programación===================");
		List<Object[]> personRegs = repository.findAllMixPerson();

		personRegs.forEach(reg -> { 
			System.out.println("programmingLanguage: "+reg[1]+", person: "+reg[0]);
		});
		System.out.println("Consulta que pobla y devuelve objeto entity de una instancia personalizada");
		List<Person> persons = repository.findAllObjectPersonPersonalized();
		persons.forEach(System.out::println);

		System.out.println("Consulta que pobla y devuelve objeto dto de una clase personalizada");
		List<PersonDto> personsDto = repository.findAllPersonDto();
		personsDto.forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void personalizedQueries(){

		Scanner scanner = new Scanner(System.in);

		System.out.println("==================Consulta solo el nombre por id===================");
		System.out.println("Ingrese el id");
		Long id = scanner.nextLong();
		scanner.close();

		System.out.println("==================Mostrando solo el nombre===================");
		String name = repository.getNameById(id);
		System.out.println(name);
		
		System.out.println("==================Mostrando solo el id===================");
		Long idDb = repository.getIdById(id);	//obtiene el id siempre como long
		System.out.println(idDb);

		System.out.println("==================Mostrando nombre completo con concat===================");
		String fullName = repository.getFullNameById(id);
		System.out.println(fullName);

		System.out.println("==================Consulta por campos personalizados por id===================");
		Optional<Object> optionalReg = repository.obtenerPersonDataByid(id);
		if(optionalReg.isPresent()){
			Object[] personReg = (Object[])optionalReg.orElseThrow();
			System.out.println("id = "+personReg[0] + ", nombre = " + personReg[1] + ", apellido = " + personReg[2] + ", lenguaje = "+personReg[3]);
		}

		System.out.println("====== Consulta por campos personalidos lista =====");
		List<Object[]> regs = repository.obtenerPersonDataList();
		regs.forEach(reg -> {System.out.println("id = "+reg[0] + ", nombre = " + reg[1] + ", apellido = " + reg[2] + ", lenguaje = "+reg[3]);});
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
