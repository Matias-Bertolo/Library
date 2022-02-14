/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Maven.Libreria.Controladores;

import com.Maven.Libreria.Entidades.Cliente;
import com.Maven.Libreria.Entidades.Libro;
import com.Maven.Libreria.Errores.ErrorServicio;

import com.Maven.Libreria.Servicios.PrestamoServicio;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author matias
 */
@Controller
@RequestMapping("/prestamo")
public class prestamoController {
    
    
    @Autowired
    private PrestamoServicio service;
    
     @PreAuthorize("hasAnyRole('ADMIN','USUARIO')")
    @GetMapping("/prestar")
   public String formulario(ModelMap model){   
       model.addAttribute("clientes", service.listaClientes());
       model.addAttribute("libros", service.listaLibros());
    return "prestamo.html";
}
    
    @PreAuthorize("hasAnyRole('ADMIN','USUARIO')")
   @PostMapping("/prestar")
    public String prestar(ModelMap model,@RequestParam Integer idcliente,@RequestParam long isbn,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date datefin
) throws ErrorServicio {  
        try{
            Libro libro=service.buscarLibroPorId(isbn);
            Cliente cliente=service.buscarClientePorId(idcliente);
            service.prestar(datefin, libro, cliente);
          model.put("exito", "registro exitoso");
        }catch(ErrorServicio a){
           model.put("error", a.getMessage());
        return "prestamo";
        }
     return "prestamo";
    }
    
    @PreAuthorize("hasRole('USUARIO')")
     @GetMapping("/listar/{id}")
   public String lista(ModelMap model,@PathVariable Integer id){ 
       model.addAttribute("prestamos", service.listaPrestamos());
       model.addAttribute("prestamos", service.listaPrestamosPorId(id));
    return "lista-prestamo.html";
}
   @PreAuthorize("hasRole('ADMIN')")
   @GetMapping("/listar")
   public String listaAdmin(ModelMap model){ 
       model.addAttribute("prestamos", service.listaPrestamos());
    return "lista-prestamo.html";
}
    
   @PreAuthorize("hasAnyRole('ADMIN','USUARIO')")
   @GetMapping("/devolucion/{id}")
   public String devolucion(@PathVariable Integer id,ModelMap model) throws ErrorServicio{
       try{
       model.put("prestamos", service.listaPrestamos());
service.devolucion(id);
       return "redirect:/inicio";    
       }catch(ErrorServicio e){
           model.put("error", e.getMessage());
           model.put("prestamos", service.listaPrestamos());
           return "lista-prestamo.html";
       }
   }
    

    
}
