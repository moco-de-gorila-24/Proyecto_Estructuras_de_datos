/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Presentacion;

import Clases.Alumno;
import Clases.Curso;
import Clases.ManejadorAcciones;
import Clases.SolicitudCalificacion;
import Estructuras.ArbolBinarioBusqueda;
import Estructuras.Cola;
import Estructuras.DiccionarioHash;
import Pantallas.PantallaEstudiantes;
import Pantallas.PantallaInicioSesion;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class InicioAplicacion {

    public static void main(String[] args) {
 
//Aplicar el estilo visual nativo del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo establecer el Look and Feel nativo.");
        }

    ArbolBinarioBusqueda<Alumno> arbolEstudiantes = new ArbolBinarioBusqueda<>();
    DiccionarioHash<String, Curso> diccionarioCursos = new DiccionarioHash<>(20);
    ManejadorAcciones manejadorAcciones = new ManejadorAcciones(arbolEstudiantes);
    Cola<SolicitudCalificacion> colaSolicitudes = new Cola<SolicitudCalificacion>();
    
    
        Alumno a1 = new Alumno("2023001", "Juan Pérez", "6441234567", "juan@mail.com", "Hidalgo", "123", "Centro", "Obregón");
        Alumno a2 = new Alumno("2023002", "María López", "6449876543", "maria@mail.com", "Zaragoza", "456", "Norte", "Navojoa");

        // Agregamos calificaciones de ejemplo para comprobar el método de promedio recursivo
        a1.agregarCalificacion(90.0);
        a1.agregarCalificacion(85.5);

        arbolEstudiantes.insertar(a1);
        arbolEstudiantes.insertar(a2);
        
        diccionarioCursos.agregar("INF-101", new Curso("INF-101", "Estructuras de Datos", 30));
        diccionarioCursos.agregar("MAT-202", new Curso("MAT-202", "Cálculo Integral", 25));

        //Inicializar la interfaz gráfica
        SwingUtilities.invokeLater(() -> {
            PantallaInicioSesion ventana = new PantallaInicioSesion(arbolEstudiantes, diccionarioCursos, colaSolicitudes, manejadorAcciones);
            ventana.setVisible(true);
        });
    }
}
