/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Maven.Libreria.Servicios;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.Maven.Libreria.Entidades.Libro;
import com.Maven.Libreria.Errores.ErrorServicio;
import com.Maven.Libreria.Repositorios.LibroRepositorio;
import com.Maven.Libreria.Servicios.EditorialServicio;
import com.Maven.Libreria.Entidades.Autor;
import com.Maven.Libreria.Entidades.Editorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author matias
 */
@Service
public class LibroServicio {

    
    
    @Autowired//la variable la inicializa el servidor de aplicaciones
  private LibroRepositorio lr;
    
    
    @Transactional(readOnly =true)
    public Libro searchById(Long id){
        return lr.getOne(id);
    }
    
    
    public void verificar(Long isbn,String titulo,Integer anio,Integer ejemplares, Integer ejemplaresprestados,
    Integer ejemplaresrestantes)throws ErrorServicio{
        
        if (titulo==null || titulo.trim().isEmpty()) {
            throw new ErrorServicio("el libro debe tener un titulo");      
        }
        if (isbn==0 || isbn==null) {
            throw new ErrorServicio("el libro debe tener ISBN");
        }
        if (anio<1800 || anio==null) {
            throw new ErrorServicio("no puede ser anterior al anio 1800 o vacio");
        }
        if (ejemplaresrestantes>(ejemplares+ejemplaresprestados)) {
            throw new ErrorServicio("no pueden haber mas ejemplares que el total");
        }
 
    }
    
  
    @Transactional
    public void crearLibro(String titulo,Long isbn,Integer anio,Integer ejemplares, Integer ejemplaresprestados,
    Integer ejemplaresrestantes,Autor autor,Editorial editorial)throws ErrorServicio{
        try{
            verificar(isbn,titulo,anio, ejemplares, ejemplaresprestados, ejemplaresrestantes);
        if (autor==null) {
            throw new ErrorServicio("el libro debe tener un autor");
        }
        if (editorial==null) {
            throw new ErrorServicio("el libro debe tener editorial");
        }
        
        
        Libro l1=new Libro();
        
        
        
        l1.setTitulo(titulo);

        l1.setIsbn(isbn);
        
        l1.setAnio(anio);

        l1.setEjemplares(ejemplares);
        

        l1.setEjemplaresPrestados(ejemplaresprestados);
        
            if(l1.getEjemplares()<l1.getEjemplaresPrestados()) {                
                throw new ErrorServicio("no pueden haber mas ejemplares prestados que el total");
            }           
    
        l1.setEjemplaresRestantes(ejemplaresrestantes);
        if(l1.getEjemplares()<(l1.getEjemplaresPrestados()+l1.getEjemplaresRestantes())){
             throw new ErrorServicio("no pueden haber mas ejemplares que el total");     
        }
        l1.setAlta(true);
        lr.save(l1);
        
        } catch(Exception e){
                   throw e;
                   }
           
       
   }
    
    @Transactional(readOnly =true)
    public List<Libro> listar(){
 List<Libro>libros=lr.findAll();
 return libros;
}
    
    @Transactional   
    public void bajalta(Long id){
        Libro a=lr.getById(id);
        if (a.isAlta()==true) {
            a.setAlta(false);
        }else{
            a.setAlta(true);
        }
        lr.save(a);
    }
    
    @Transactional
    public void modificar(Long id,String titulo,Integer anio,Integer ejemplares,Integer ejemplaresPrestados,
    Integer ejemplaresRestantes,Integer autor,Integer editorial) throws ErrorServicio{
        
        
        verificar(id, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes);
        Libro a=new Libro();
        a.setIsbn(id);
        a.setTitulo(titulo);
        a.setAnio(anio);
        a.setEjemplares(ejemplares);
        a.setEjemplaresPrestados(ejemplaresPrestados);
        if (a.getEjemplaresPrestados()>a.getEjemplares()) {
            throw new ErrorServicio("no pueden haber mas ejemplares que el total");
        }
        a.setEjemplaresRestantes(ejemplaresRestantes);
        if ((a.getEjemplaresPrestados()+a.getEjemplaresRestantes())>a.getEjemplares()) {
            throw new ErrorServicio("no pueden haber mas ejemplares que el total");
        }
        if (autor!=null) {
            a.setAutor(lr.autorPorId(autor));
        }else{
            a.setAutor(lr.getById(id).getAutor());
        }
        if (editorial!=null) {
           a.setEditorial(lr.editorialPorId(editorial)); 
        }else{
            a.setEditorial(lr.getById(id).getEditorial());
        }
        
        a.setAlta(true);
        
        lr.save(a);  
    }
    
    @Transactional(readOnly =true)
    public List<Autor> buscarAutores(){
        return lr.buscarAutores();
    }
    
    @Transactional(readOnly =true)
    public List<Editorial> buscarEditoriales(){
        return lr.buscarEditoriales();
    }
    
    
    
    }

