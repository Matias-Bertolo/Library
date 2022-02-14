    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Maven.Libreria.Entidades;



import com.Maven.Libreria.Entidades.Libro;
import com.Maven.Libreria.Entidades.Cliente;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author matias
 */
@Entity
public class Prestamo implements Serializable  {
  @Id  
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
  @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaprestamo;
  @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechadevolucion;
  @OneToOne
    private Libro libro;
  @ManyToOne
    private Cliente cliente;

    public Prestamo() {
    }
  

    public Prestamo(Date fechaprestamo, Date fechadevolucion, Libro libro, Cliente cliente) {
        this.fechaprestamo = fechaprestamo;
        this.fechadevolucion = fechadevolucion;
        this.libro = libro;
        this.cliente = cliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaprestamo() {
        return fechaprestamo;
    }

    public void setFechaprestamo(Date fechaprestamo) {
        this.fechaprestamo = fechaprestamo;
    }

    public Date getFechadevolucion() {
        return fechadevolucion;
    }

    public void setFechadevolucion(Date fechadevolucion) {
        this.fechadevolucion = fechadevolucion;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Prestamo{" + "id=" + id + ", fechaprestamo=" + fechaprestamo + ", fechadevolucion=" + fechadevolucion +
                ", libro=" + libro +
                ", cliente=" + cliente + '}';
    }
    
    
    
}
