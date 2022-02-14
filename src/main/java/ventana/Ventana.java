/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventana;


import com.Maven.Libreria.Servicios.LibroServicio;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author matias
 */
public class Ventana extends JFrame {

    public Ventana() {
        setSize(500, 500);//establecemos el tamaño de la ventana
        setTitle("Nuevo titulo");//establecemos el titulo de la ventana
//        setLocation(100, 200);//establecemos laposicion inicial de la ventana
//        setBounds(100, 200, 500, 500);//engloba a location y size al mismo timpo
        setLocationRelativeTo(null);//deja la pantalla en el centro
        iniciarComponentes();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
   
    //metodo para agregar un panel sobre la ventana
    //es como poner una hoja sobre un escritorio para poder escribir
private void iniciarComponentes(){
    LibroServicio service =new LibroServicio();
    JPanel panel=new JPanel();//creacion de un panel
    panel.setBackground(Color.GREEN);//establecemos color del panel
    this.getContentPane().add(panel);//agregamos el panel a la ventana
    
    JLabel etiqueta =new JLabel();//creamos una etiqueta
    etiqueta.setText("hola");//establecemos el texto de la etiqueta
    panel.add(etiqueta);
       
    panel.setLayout(null);//desactivamos el diseño
    etiqueta.setBounds(50, 50, 100, 100);//ancho y alto,coordenadas del texto
   
    
}








}
