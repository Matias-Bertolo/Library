/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Maven.Libreria.Repositorios;

import com.Maven.Libreria.Entidades.Autor;
import com.Maven.Libreria.Entidades.Editorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author matias
 */
@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, Integer> {
    
    @Query("SELECT a FROM Editorial a")
    public List<Editorial> listar();
    
    @Query("SELECT e FROM Editorial e WHERE e.id= :nombre")
    public Editorial buscarEditorialPorId(@Param("nombre") Integer id);
}
