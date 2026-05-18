/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pantallas;

import Clases.Alumno;
import Clases.Curso;
import Clases.ManejadorAcciones;
import Clases.SolicitudCalificacion;
import Estructuras.ArbolBinarioBusqueda;
import Estructuras.Cola;
import Estructuras.DiccionarioHash;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author DANIEL
 */
public class PantallaInicioSesion extends JFrame{
    private ArbolBinarioBusqueda<Alumno> arbolEstudiantes;
    private DiccionarioHash<String, Curso> diccionarioCursos;
    private ManejadorAcciones manejadorAcciones;
    private Cola<SolicitudCalificacion> colaSolicitudes;
    
    private final Color CIAN_PRINCIPAL = new Color(74, 158, 188);
    private final Color CIAN_CLARO = new Color(235, 245, 250);
    private final Color CIAN_OSCURO = new Color(42, 107, 132);

    public PantallaInicioSesion(ArbolBinarioBusqueda<Alumno> arbol, DiccionarioHash<String, Curso> diccionario, Cola<SolicitudCalificacion> cola, ManejadorAcciones manejador) {
        this.arbolEstudiantes = arbol;
        this.diccionarioCursos = diccionario;
        this.colaSolicitudes = cola;
        this.manejadorAcciones = manejador;
        
        setTitle("Acceso al Sistema");
        setSize(450, 350); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // BARRA SUPERIOR 
        JPanel barraSuperior = new JPanel();
        barraSuperior.setBackground(CIAN_PRINCIPAL);
        barraSuperior.setPreferredSize(new Dimension(450, 50));
        add(barraSuperior, BorderLayout.NORTH);

        // PANEL CENTRAL 
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(Color.WHITE);
        panelCentral.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Mensaje de Bienvenida
        JLabel lblBienvenida = new JLabel("¡Bienvenido al Sistema!", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblBienvenida.setForeground(Color.BLACK);
        gbc.gridy = 0;
        panelCentral.add(lblBienvenida, gbc);

        JLabel lblInstruccion = new JLabel("Presiona Entrar para acceder a la gestión escolar", SwingConstants.CENTER);
        lblInstruccion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblInstruccion.setForeground(Color.GRAY);
        gbc.gridy = 1;
        panelCentral.add(lblInstruccion, gbc);

        // CONTENEDOR PARA LOS BOTONES 
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(Color.WHITE);

        // Botón Entrar 
        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setPreferredSize(new Dimension(130, 40));
        estilizarBoton(btnEntrar, Color.GREEN, Color.BLACK);
        
        // Acción del botón Entrar
        btnEntrar.addActionListener(e -> {
            this.dispose(); 
            PantallaEstudiantes principal = new PantallaEstudiantes(arbolEstudiantes, diccionarioCursos, colaSolicitudes, manejadorAcciones); 
            principal.setVisible(true); 
        });
        

        JButton btnSalir = new JButton("Salir");
        btnSalir.setPreferredSize(new Dimension(130, 40));
        estilizarBoton(btnSalir, Color.RED, Color.BLACK);
        
        // Acción del botón Salir
        btnSalir.addActionListener(e -> System.exit(0));

        panelBotones.add(btnEntrar);
        panelBotones.add(btnSalir);

        gbc.gridy = 2;
        gbc.insets = new Insets(25, 0, 0, 0); 
        panelCentral.add(panelBotones, gbc);

        add(panelCentral, BorderLayout.CENTER);
    }

    /**
     * Aplica el formato visual y el comportamiento dinámico de clic (Azul Oscuro)
     */
    private void stylizedBoton(JButton btn, Color fondoOriginal, Color textoOriginal) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setBackground(fondoOriginal);
        btn.setForeground(textoOriginal);
        btn.setBorder(new LineBorder(CIAN_PRINCIPAL, 1, true));

        // Listener para interceptar los clics del mouse
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                btn.setBackground(CIAN_OSCURO); // Se vuelve azul oscuro al presionarse
                btn.setForeground(Color.WHITE);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                btn.setBackground(fondoOriginal); // Vuelve a su color base al soltar
                btn.setForeground(textoOriginal);
            }
        });
    }
    
    private void estilizarBoton(JButton btn, Color f, Color t){
        stylizedBoton(btn, f, t);
    }
    
}
