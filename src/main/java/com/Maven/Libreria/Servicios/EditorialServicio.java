/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Maven.Libreria.Servicios;


import com.Maven.Libreria.Entidades.Autor;
import com.Maven.Libreria.Entidades.Editorial;
import com.Maven.Libreria.Errores.ErrorServicio;
import com.Maven.Libreria.Repositorios.EditorialRepositorio;
import java.util.List;
import javax.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author matias
 */
@Service
public class EditorialServicio {
    
    @Autowired
   private EditorialRepositorio er;
    
    
    
    
    @Transactional(readOnly =true)
    public List<Editorial> listar() throws ErrorServicio{
        if (er.listar()==null) {
           throw new ErrorServicio("no hay ninguna editorial");
        }else{
          return er.listar();  
        }
    }
    
    
    public void verificar(String nombre) throws ErrorServicio{
       if (nombre==null || nombre.trim().isEmpty()) {//trim elimina los espacios y verifica si hay letras
            throw new ErrorServicio("editorial debe tener un nombre");
        }
        
    }
    
    @Transactional
    public void modificar(Integer id,String nombre) throws ErrorServicio{
        verificar(nombre);
        Editorial a=new Editorial();
        a.setId(id);
        a.setNombre(nombre);
        a.setAlta(true);
        er.save(a);
    }
    
    @Transactional(readOnly =true)
    public Editorial searchById(Integer id){
        return er.buscarEditorialPorId(id);
    }
    
    @Transactional
    public void bajalta(Integer id){
        Editorial a=searchById(id);
        if (a.getAlta()==true) {
            a.setAlta(false);
        }else{
            a.setAlta(true);
        }
        er.save(a);
    }
    
    @Transactional
    public void crear(String nombre) throws ErrorServicio{
        verificar(nombre);
        Editorial a= new Editorial();
        a.setNombre(nombre);
        a.setAlta(true);
        er.save(a);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
