/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Maven.Libreria.Repositorios;

import com.Maven.Libreria.Entidades.Autor;
import com.Maven.Libreria.Entidades.Cliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author matias
 */
@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
    
    @Query("SELECT a FROM Cliente a WHERE a.id= :id")
    public Cliente buscarClientesPorId(@Param("id") Integer id);
    
    
    @Query("SELECT c FROM Cliente c WHERE c.mail = :correo")
    public Cliente buscarPorCorreo(@Param("correo") String mail);
    
//    Optional<Cliente> findByUserName(String userName);

//    public boolean existsUserByUserName(String userName);
}



