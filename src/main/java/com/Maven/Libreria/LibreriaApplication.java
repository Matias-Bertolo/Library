package com.Maven.Libreria;

import com.Maven.Libreria.Servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LibreriaApplication {
    
     @Autowired
     private ClienteServicio clienteServicio;

	public static void main(String[] args) {
		SpringApplication.run(LibreriaApplication.class, args);
	}

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception{
            auth
                    
                    .userDetailsService(clienteServicio)
                    .passwordEncoder(new BCryptPasswordEncoder());
            
        }
        
        
}
