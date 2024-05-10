package com.example.hospital;

import com.example.hospital.entities.Patient;
import com.example.hospital.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class HospitalApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;

    public static void main(String[] args) {
        SpringApplication.run(HospitalApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        patientRepository.save(new Patient(null, "Mohamed", new Date(), false, 34));
        patientRepository.save(new Patient(null, "Hanane", new Date(), false, 4321));
        patientRepository.save(new Patient(null, "Imane", new Date(), true, 34));
    }

    @Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager) {//JdbcUserDetailsManager fournit les methodes
        PasswordEncoder passwordEncoder = passwordEncoder();
        return args -> {

            UserDetails u1= jdbcUserDetailsManager.loadUserByUsername("user11");
            if(u1==null)
                jdbcUserDetailsManager.createUser(
                    User.withUsername("user11").password(passwordEncoder.encode("1234")).roles("USER").build()
            );

            UserDetails u2= jdbcUserDetailsManager.loadUserByUsername("user22");
            if(u2==null)
                jdbcUserDetailsManager.createUser(
                    User.withUsername("user22").password(passwordEncoder.encode("1234")).roles("USER").build()
            );

            UserDetails u3= jdbcUserDetailsManager.loadUserByUsername("admin");
            if(u3==null)
                jdbcUserDetailsManager.createUser(
                    User.withUsername("admin2").password(passwordEncoder.encode("1234")).roles("USER","ADMIN").build()
            );
        };

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //Algo pour hashage de MDP comme MD5
    }
}
