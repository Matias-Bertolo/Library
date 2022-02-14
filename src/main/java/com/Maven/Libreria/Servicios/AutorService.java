/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Maven.Libreria.Servicios;

import com.Maven.Libreria.Entidades.Autor;
import com.Maven.Libreria.Errores.ErrorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Maven.Libreria.Repositorios.AutorRepositorio;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author matias
 */
@Service
public class AutorService {
    
    @Autowired
   private AutorRepositorio ar;
    
    
    
    @Transactional
    public void crearAutor(String nombre,String apellido) throws ErrorServicio{       
        verificar(nombre,apellido);
        Autor b=buscarPorNombre(nombre, apellido);
        if (b==null){
             Autor a=new Autor();
        a.setNombre(nombre);
        a.setApellido(apellido);
        a.setAlta(true);
        ar.save(a); 
        }else{
           throw new ErrorServicio("El autor ya existe");
        }
                     
    }
    
    public void verificar(String nombre,String apellido) throws ErrorServicio{
       if (nombre==null || nombre.trim().isEmpty()) {//trim elimina los espacios y verifica si hay letras
            throw new ErrorServicio("el autor debe tener un nombre");
        }
        if (apellido==null || apellido.trim().isEmpty()) {
            throw new ErrorServicio("el autor debe tener un apellido");
        } 
    }
@Transactional(readOnly =true)
    public Autor searchById(Integer id){
        return ar.buscarAutoresPorId(id);
    }
   
    @Transactional(readOnly =true)//crea una lista de autores en general
    public List<Autor>autorList(){
        return ar.findAll();
    }
    
    @Transactional(readOnly =true)
    public List<Autor> listar(){
        List<Autor>autores=ar.listar();
        return autores;
    }
    @Transactional(readOnly =true)
    public Autor buscarPorNombre(String nombre,String apellido) throws ErrorServicio{
        verificar(nombre, apellido);
            return ar.buscarAutoresPorNombre(nombre, apellido);
      
        }
    
    @Transactional
    public void modificar(Integer id,String nombre,String apellido) throws ErrorServicio{
        verificar(nombre, apellido);
        Autor a=new Autor();
        a.setId(id);
        a.setNombre(nombre);
        a.setApellido(apellido);
        a.setAlta(true);
        ar.save(a);
        
       
    }
      @Transactional   
    public void bajalta(Integer id){
        Autor a=searchById(id);
        if (a.getAlta()==true) {
            a.setAlta(false);
        }else{
            a.setAlta(true);
        }
        ar.save(a);
    }
       
       
    }
    
    
    
    
    

