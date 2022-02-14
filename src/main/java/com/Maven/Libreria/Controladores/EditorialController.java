/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Maven.Libreria.Controladores;

import com.Maven.Libreria.Entidades.Editorial;
import com.Maven.Libreria.Errores.ErrorServicio;
import com.Maven.Libreria.Servicios.EditorialServicio;
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
@RequestMapping("/editorial")
public class EditorialController {
    
    @Autowired
    private EditorialServicio service;
    
    @PreAuthorize("hasAnyRole('ADMIN','USUARIO')")
    @GetMapping("/listar")
    public String main(ModelMap modelo) throws ErrorServicio{
        List<Editorial> editoriales=service.listar();
        modelo.addAttribute("editoriales", editoriales);
        return "listar-editorial";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Integer  id,ModelMap modelo){
        modelo.put("editorial", service.searchById(id));
        return "mod-editorial";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable Integer  id,@RequestParam String nombre,ModelMap model) throws ErrorServicio{
        try{
       service.modificar(id, nombre);
        }catch(ErrorServicio e){
            model.addAttribute("editorial", service.searchById(id));
            model.addAttribute("error", e.getMessage());
            return "mod-editorial";
        }
        return "redirect:/editorial/listar";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/bajalta/{id}")
    public String bajalta(@PathVariable Integer id,ModelMap modelo){//se puede hacer tambien con Model que llega como parametro
       //modelo.put("autor", service.searchById(id));
       service.bajalta(id);
        return "redirect:/editorial/listar";
    }  
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/crear")
    public String crear(){
        return "crear-editorial";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public String crear(String nombre,ModelMap model) throws ErrorServicio{
        try{
            
        service.crear(nombre);
        
        }catch(ErrorServicio e){
            model.addAttribute("error", e.getMessage());
           return "crear-editorial";
        }
        return "redirect:/editorial/listar";
    }
    
    
}
