package com.Maven.Libreria.Repositorios;

import com.Maven.Libreria.Entidades.Autor;
import com.Maven.Libreria.Entidades.Editorial;
import com.Maven.Libreria.Entidades.Libro;
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
public interface LibroRepositorio extends JpaRepository<Libro, Long> {

    @Query("SELECT a FROM Autor a")
    public List<Autor> buscarAutores();

    @Query("SELECT e FROM Editorial e")
    public List<Editorial> buscarEditoriales();

    @Query("SELECT a FROM Autor a WHERE id= :id")
    public Autor autorPorId(@Param("id") Integer id);
    
    @Query("SELECT a FROM Editorial a WHERE id= :id")
    public Editorial editorialPorId(@Param("id") Integer id);
    
}
