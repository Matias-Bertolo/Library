/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Maven.Libreria.Controladores;

import com.Maven.Libreria.Entidades.Autor;
import com.Maven.Libreria.Errores.ErrorServicio;
import com.Maven.Libreria.Servicios.AutorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author matias
 */
@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/autor")
public class AutorController {
   
    
    
    @Autowired
    private AutorService service;
    
    @PreAuthorize("hasRole('ADMIN')")
   @GetMapping("/crear")
   public String formulario(){
    return "guardarAutores.html";
}
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/guardar")
    public String crearAutor(ModelMap model,@RequestParam String nombre,@RequestParam String apellido) throws ErrorServicio {  
        try{          
           service.crearAutor(nombre,apellido); 
          model.put("exito", "registro exitoso");
        }catch(ErrorServicio a){
           model.put("error", "falta algun dato");
        return "guardarAutores";
        }
     return "guardarAutores";
    }
     @PreAuthorize("permitAll")
    @GetMapping("/listar")
    public String listaAutores(ModelMap model){//se puede hacer tambien con Model que llega como parametro
        List<Autor>autores=service.listar();
        model.addAttribute("autores",autores);
        return "autor-list.html";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Integer  id,ModelMap modelo){
        modelo.put("autor", service.searchById(id));
        
        return "modificarAutor";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable Integer id,@RequestParam String nombre,@RequestParam String apellido,ModelMap model) throws ErrorServicio{
        try{
        service.modificar(id, nombre, apellido);
        model.put("exito", "cargado correctamente");
        return "redirect:/autor/listar";
        }catch(ErrorServicio e){
            model.put("error", e.getMessage());
            model.put("autor", service.searchById(id));
            return "modificarAutor";
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
     @GetMapping("/bajalta/{id}")
    public String bajalta(@PathVariable Integer id,ModelMap modelo){//se puede hacer tambien con Model que llega como parametro
       //modelo.put("autor", service.searchById(id));
       service.bajalta(id);
        return "redirect:/autor/listar";
    }   
    
    
    
    
  
    
  
    
    
    
    
    
}
