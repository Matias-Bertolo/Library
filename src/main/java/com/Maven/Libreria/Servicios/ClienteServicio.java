/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Maven.Libreria.Servicios;


import com.Maven.Libreria.Entidades.Autor;
import java.util.List;
import java.util.Scanner;
import javax.persistence.NoResultException;
import com.Maven.Libreria.Entidades.Cliente;
import com.Maven.Libreria.Enum.Rol;
import com.Maven.Libreria.Errores.ErrorServicio;
import com.Maven.Libreria.Repositorios.ClienteRepositorio;
import java.util.ArrayList;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author matias
 */
@Service
public class ClienteServicio implements UserDetailsService{
    
    @Autowired 
    private ClienteRepositorio cr;
    
    @Transactional
    public void registrar( String nombre, String apellido,String mail,String clave,long documento, String telefono) throws ErrorServicio{
        verificar( nombre, apellido,documento, telefono);
        Cliente c =new Cliente();
        c.setNombre(nombre);
        c.setApellido(apellido);
        c.setTelefono(telefono);
        c.setDocumento(documento); 
        c.setMail(mail);
        
        String encriptada = new BCryptPasswordEncoder().encode(clave);
        c.setClave(encriptada);
        
        c.setRol(Rol.USUARIO);
        c.setAlta(true);
        cr.save(c);
    }
    
    public void verificar( String nombre, String apellido,long documento, String telefono) throws ErrorServicio{
        if (documento==0) {
            throw new ErrorServicio("el documento no puede estar vacio");
        }
        if (nombre==null) {
          throw new ErrorServicio("el cliente debe tener nombre");
        }
        if (apellido==null) {
          throw new ErrorServicio("el cliente debe tener apellido");
        }
        
        
        
    }
    @Transactional
    public void modificar(Integer id,String nombre, String apellido,String mail,String clave,long documento, String telefono) throws ErrorServicio{       
        Cliente cliente=cr.buscarClientesPorId(id);
        verificar(nombre, apellido, documento, telefono);
            cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setDocumento(documento);
        cliente.setMail(mail);
        
        String encriptada = new BCryptPasswordEncoder().encode(clave);
        cliente.setClave(encriptada);
        cliente.setTelefono(telefono);       
        cr.save(cliente);
       

        }
   
    @Transactional(readOnly = true)
    public List<Cliente>listar(){
        return cr.findAll();
    }
    @Transactional(readOnly = true)
    public Cliente buscarPorId(Integer id){
        return cr.buscarClientesPorId(id);
    }
    
     @Transactional   
    public void bajalta(Integer id){
        Cliente a=buscarPorId(id);
        if (a.isAlta()==true) {
            a.setAlta(false);
        }else{
            a.setAlta(true);
        }
        cr.save(a);
    }
    
   
@Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {

        Cliente cliente = cr.buscarPorCorreo(correo);

        if (cliente != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            /*Creo una lista de permisos - "ROLE_" + cliente.getRol() - concateno la palabra ROL con el enumerador
            ADMIN O USUARIO*/
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + cliente.getRol());
            permisos.add(p1);

            //Esto me permite guardar el OBJETO USUARIO LOGUEADO, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            
            /* HttpSession - RETIENE Y MANTIENE INFORMACIÓN DE LA SESIÓN LOGUEADA CON CIERTO USUARIO*/
            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("clienteSession", cliente); // llave + valor

            User user = new User(cliente.getMail(), cliente.getClave(), permisos);

            return user;

        } else {
            
            return null;
        }
    }
    
//    public Cliente session(){
//        Authentication authentication = SecurityContextHolder.
//        getContext().getAuthentication();
//        String currentUser = authentication.getName();
//        Optional<Cliente> user = cr.findByUserName(currentUser);
//        if (user.isPresent()) {
//            return user.get();
//        }else{
//        return null;
//    }
        
//    }
    
    
    
    
    
    
    
    
    
    
    }
    

