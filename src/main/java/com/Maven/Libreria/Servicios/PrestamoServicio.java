/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Maven.Libreria.Servicios;

import com.Maven.Libreria.Entidades.Cliente;
import com.Maven.Libreria.Entidades.Libro;
import com.Maven.Libreria.Entidades.Prestamo;
import com.Maven.Libreria.Errores.ErrorServicio;
import com.Maven.Libreria.Repositorios.ClienteRepositorio;
import com.Maven.Libreria.Repositorios.LibroRepositorio;
import com.Maven.Libreria.Repositorios.PrestamoRepositorio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author matias
 */
@Service
public class PrestamoServicio {
    
    @Autowired
    private LibroRepositorio lr;
    
    @Autowired
    private PrestamoRepositorio pr;
    
    @Autowired 
    private ClienteRepositorio cr;
    
    @Transactional
     public void prestar(Date fechadev,Libro libro,Cliente cliente) throws ErrorServicio{
         verificar( fechadev, libro, cliente);
         
         if (libro.getEjemplaresRestantes()==0) {
            throw new ErrorServicio("no hay mas ejemplares para prestar");
        }
         libro.setEjemplaresRestantes(libro.getEjemplaresRestantes()-1);
         libro.setEjemplaresPrestados(libro.getEjemplaresPrestados()+1); 
         lr.save(libro);
         Prestamo prestamo=new Prestamo();
         prestamo.setFechaprestamo(new Date());
         prestamo.setFechadevolucion(fechadev);
         prestamo.setLibro(libro);
         prestamo.setCliente(cliente);
         pr.save(prestamo);
         
     }
       
     public void verificar(Date fechadev,Libro libro,Cliente cliente)throws ErrorServicio{
        
         if (fechadev==null) {
             throw new ErrorServicio("no existe fecha de devolucion");
         }
         if (libro==null) {
             throw new ErrorServicio("no existe el libro");
         }
         if (cliente==null) {
             throw new ErrorServicio("no existe el cliente");
         }
   
     }
     
     
     @Transactional(readOnly = true)
    public Libro buscarLibroPorId(long id){  
       return lr.getById(id);
    }
    
    @Transactional(readOnly = true)
    public Cliente buscarClientePorId(Integer id){
        
        return cr.buscarClientesPorId(id);
    }
    
    @Transactional(readOnly = true)
    public List<Cliente> listaClientes(){
 
        return pr.buscarClientes();
    }
    
    @Transactional(readOnly = true)
    public List<Libro> listaLibros(){

        return pr.buscarLibros();
    }
    
    @Transactional(readOnly = true)
    public List<Prestamo> listaPrestamos(){

        return pr.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Prestamo> listaPrestamosPorId(Integer id){

        return pr.buscarPrestamosPorId(id);
    }
    
    @Transactional
    public void devolucion(Integer id) throws ErrorServicio{
        if (pr.getById(id)==null) {
           throw new ErrorServicio ("error al devolver");
        }
        if (pr.getById(id).getFechadevolucion()==null) {
           throw new ErrorServicio ("este libro ya fue devuelto!!"); 
        }
        Prestamo prestamo=pr.getById(id);
        prestamo.setFechadevolucion(null);
        prestamo.getLibro().setEjemplaresPrestados(prestamo.getLibro().getEjemplaresPrestados()-1);
        prestamo.getLibro().setEjemplaresRestantes(prestamo.getLibro().getEjemplaresRestantes()+1);
        pr.save(prestamo);
    }
    
}
