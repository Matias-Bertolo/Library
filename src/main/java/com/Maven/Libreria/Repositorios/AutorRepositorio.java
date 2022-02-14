/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Maven.Libreria.Repositorios;

import com.Maven.Libreria.Entidades.Autor;
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
public interface AutorRepositorio extends JpaRepository<Autor, Integer>{
    
    @Query("SELECT a FROM Autor a")
    public List<Autor> listar();
    
    @Query("SELECT a FROM Autor a WHERE a.id= :nombre")
    public Autor buscarAutoresPorId(@Param("nombre") Integer id);
    
    @Query("SELECT a FROM Autor a WHERE a.nombre= :nombre and a.apellido= :apellido")
    public Autor buscarAutoresPorNombre(@Param("nombre") String nombre,@Param("apellido")String apellido);
    
//    @Query("UPDATE autor a SET a.nombre=:nombre,a.apellido=:apellido where a.id=:id")
//    public void modificar(@Param("id")Integer id,@Param("nombre")String nombre,@Param("apellido")String apellido);
//    
    
    
  
    
    
    
    
    
    
    
   
    
}
