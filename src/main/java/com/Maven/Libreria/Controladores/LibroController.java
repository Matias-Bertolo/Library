/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Maven.Libreria.Controladores;

import com.Maven.Libreria.Entidades.Autor;
import com.Maven.Libreria.Entidades.Editorial;
import com.Maven.Libreria.Entidades.Libro;
import com.Maven.Libreria.Errores.ErrorServicio;
import com.Maven.Libreria.Servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/libro")
public class LibroController {
    
    
    @Autowired
    private LibroServicio service;
    
    @PreAuthorize("permitAll")
    @GetMapping("/listar")
   public String formulario(ModelMap model){
       List<Libro> libros=service.listar();
       model.addAttribute("libros", libros);
    return "libro-list.html";
}
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id,ModelMap model){
        model.addAttribute("libro",service.searchById(id)); 
        model.addAttribute("autor", service.buscarAutores());
        model.addAttribute("editorial", service.buscarEditoriales());
        return "mod-libro";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id,@RequestParam String titulo,@RequestParam Integer anio,
         @RequestParam Integer ejemplares,@RequestParam Integer ejemplaresPrestados,@RequestParam Integer ejemplaresRestantes,
         @RequestParam(required=false) Integer autor,@RequestParam(required=false) Integer editorial,ModelMap model) throws ErrorServicio{
        
        try{
       service.modificar(id, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, autor, editorial);
       model.put("exito", "registro exitoso");
       return "redirect:/libro/listar";
        }catch(ErrorServicio e){
            model.put("error", e.getMessage());
            return "mod-libro";
        }
        
    }
    @PreAuthorize("hasRole('ADMIN')")
     @GetMapping("/crear")
    public String crear(ModelMap model){ 
        model.addAttribute("autor", service.buscarAutores());
        model.addAttribute("editorial", service.buscarEditoriales());
        return "crear-libro";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public String crear(@RequestParam Long id,@RequestParam String titulo,@RequestParam Integer anio,
         @RequestParam Integer ejemplares,@RequestParam Integer ejemplaresPrestados,@RequestParam Integer ejemplaresRestantes,
         @RequestParam Integer autor,@RequestParam Integer editorial,ModelMap model) throws ErrorServicio{
        
        try{
       service.modificar(id, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, autor, editorial);
        }catch(ErrorServicio e){
            model.addAttribute("error", e.getMessage());
         return "crear-libro";
        }
        return "redirect:/libro/listar";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/bajalta/{id}")
    public String bajalta(@PathVariable Long id,ModelMap modelo){//se puede hacer tambien con Model que llega como parametro
       //modelo.put("autor", service.searchById(id));
       service.bajalta(id);
        return "redirect:/libro/listar";
    }   
    
    
    
    
    
    
}
