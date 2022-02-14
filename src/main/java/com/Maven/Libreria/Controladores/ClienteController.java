/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Maven.Libreria.Controladores;

import com.Maven.Libreria.Entidades.Cliente;
import com.Maven.Libreria.Errores.ErrorServicio;
import com.Maven.Libreria.Servicios.ClienteServicio;
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
@RequestMapping("/cliente")
public class ClienteController {
    
    @Autowired
    private ClienteServicio service;
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/crear")
   public String formulario(){
    return "crear-cliente.html";
}
    
    
    @PostMapping("/crear")
    public String crearAutor(ModelMap model,@RequestParam String nombre,@RequestParam String apellido,@RequestParam String mail,@RequestParam String clave,@RequestParam long documento
    ,@RequestParam String telefono) throws ErrorServicio {  
        try{          
           service.registrar(nombre, apellido,mail,clave, documento, telefono);
          model.put("exito", "registro exitoso");
        }catch(ErrorServicio a){
           model.put("error", "falta algun dato");
        return "crear-cliente.html";
        }
     return "crear-cliente.html";
    }
    
    
    
    
    
    
    
    
    
    
    
     @GetMapping("/listar")
    public String listaclientes(ModelMap model){//se puede hacer tambien con Model que llega como parametro
        List<Cliente>clientes=service.listar();
        model.addAttribute("clientes",clientes);
        
        return "cliente-list";
    }
    
@PreAuthorize("hasAnyRole('ADMIN','USUARIO')")
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Integer  id,ModelMap modelo){
        modelo.addAttribute("cliente", service.buscarPorId(id));     
        
        return "modificarCliente";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN','USUARIO')")
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable Integer id,@RequestParam String nombre,
            @RequestParam String apellido,@RequestParam String mail,@RequestParam String clave,
            @RequestParam Long documento,@RequestParam String telefono,ModelMap model) throws ErrorServicio{
        try{
        service.modificar(id, nombre, apellido,mail,clave,documento,telefono);
        model.put("exito", "cargado correctamente");
        return "redirect:/cliente/listar";
        }catch(ErrorServicio e){
            model.put("error", e.getMessage());
            model.put("cliente", service.buscarPorId(id));
            return "modificarCliente";
        }
    
}
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/bajalta/{id}")
    public String bajalta(@PathVariable Integer id,ModelMap modelo){//se puede hacer tambien con Model que llega como parametro
       //modelo.put("autor", service.searchById(id));
       service.bajalta(id);
        return "redirect:/cliente/listar";
    }   
    
    
    
    
    
    
}