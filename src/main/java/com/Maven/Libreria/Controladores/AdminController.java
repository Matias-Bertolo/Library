/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Maven.Libreria.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author matias
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @GetMapping("/inicio")
    public String inicio(){
        return "inicio.html";
    }
    
    
}
