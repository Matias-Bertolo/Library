/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Maven.Libreria.Repositorios;


import com.Maven.Libreria.Entidades.Cliente;
import com.Maven.Libreria.Entidades.Libro;
import com.Maven.Libreria.Entidades.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author matias
 */
public interface PrestamoRepositorio extends JpaRepository<Prestamo, Integer> {
    
    
    @Query("SELECT a FROM Cliente a")
    public List<Cliente> buscarClientes();
    
    @Query("SELECT a FROM Libro a")
    public List<Libro> buscarLibros();
    
  @Query("SELECT a FROM Prestamo a WHERE a.cliente.id= :id")
    public List<Prestamo> buscarPrestamosPorId(@Param("id") Integer id);
    
    
    
    
    
    
    
    
    
    
    
}
