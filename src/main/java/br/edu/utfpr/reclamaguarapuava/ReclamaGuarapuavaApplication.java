package br.edu.utfpr.reclamaguarapuava;

import br.edu.utfpr.reclamaguarapuava.members.entities.Profile;
import br.edu.utfpr.reclamaguarapuava.members.entities.User;
import br.edu.utfpr.reclamaguarapuava.members.repositories.UserRepository;
import br.edu.utfpr.reclamaguarapuava.occurrences.entities.City;
import br.edu.utfpr.reclamaguarapuava.occurrences.entities.State;
import br.edu.utfpr.reclamaguarapuava.occurrences.repositories.CityRepository;
import br.edu.utfpr.reclamaguarapuava.occurrences.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
public class ReclamaGuarapuavaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ReclamaGuarapuavaApplication.class, args);
	}

	//TODO remover
	@Autowired
	UserRepository userRepository;
	@Autowired
	CityRepository cityRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void run(String... args) throws Exception {

		/*
		 {
		 "name":"testes de usuário",
		 "email":"email@admin.com",
		 "password":"123456788",
		 "cpf": "11111111111",
		 "genre":"Masc",
		 "birthday":"2018-01-01",
		 "cityId":"1"
		 }
		 */

		/*
		 {
		 "email":"email@admin.com",
		 "password":"123456788"
		 }
		 */
		User user = new User();
		user.setName("Marcus ");
		user.setPassword(bCryptPasswordEncoder.encode("123456788"));
		user.setCpf("11111111111");
		user.setBirthday(LocalDate.now());
		user.setEmail("email@admin.com");
		user.setGenre("Masculino");
		user.addProfile(Profile.ADMIN);

		State state = new State();
		state.setName("Paraná");
		state.setAbbvr("PR");

		state = stateRepository.save(state);

		City city = new City();
		city.setName("Guarapuava");
		city.setState(state);


		city = cityRepository.save(city);
		user.setCity(city);

		userRepository.save(user);

		User user1 = new User();
		user1.setName("Vinicius");
		user1.setPassword(bCryptPasswordEncoder.encode("123456788"));
		user1.setCpf("11111111112");
		user1.setBirthday(LocalDate.now());
		user1.setEmail("email@user.com");
		user1.setGenre("Masculino");

		user1.setCity(city);

		userRepository.save(user1);

		System.out.println("[auto create] -> user profile admin - id: " + user.getId() + " city: " + city.getId() );
		System.out.println("[auto create] -> user profile user - id: " + user1.getId() + " city: " + city.getId() );
	}
}
