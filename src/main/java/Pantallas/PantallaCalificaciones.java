/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pantallas;

/**
 *
 * @author DANIEL
 */
import Clases.Alumno;
import Clases.Curso;
import Clases.SolicitudCalificacion;
import Clases.ContextoCalificacion;
import Clases.ManejadorAcciones;
import Estructuras.ArbolBinarioBusqueda;
import Estructuras.DiccionarioHash;
import Estructuras.Cola;
import Estructuras.Pila;
import Validaciones.Validaciones;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * Pantalla completa de Calificaciones con procesamiento por Cola (FIFO) y
 * registro en Pila de acciones para deshacer.
 *
 * * @author DANIEL
 */
public class PantallaCalificaciones extends JFrame {

    private ArbolBinarioBusqueda<Alumno> arbolEstudiantes;
    private DiccionarioHash<String, Curso> diccionarioCursos;
    private Cola<SolicitudCalificacion> colaSolicitudes;
    private ManejadorAcciones manejadorAcciones;

    private JTextArea txtAreaCola;
    private JTextField txtMatricula;
    private JTextField txtCalificacion;
    // Agregamos un campo opcional por si quieren mandar un índice a modificar (vacío = nueva)
    private JTextField txtIndiceModificar; 

    private final Color CIAN_PRINCIPAL = new Color(74, 158, 188);
    private final Color CIAN_CLARO = new Color(235, 245, 250);
    private final Color CIAN_OSCURO = new Color(42, 107, 132);

    public PantallaCalificaciones(ArbolBinarioBusqueda<Alumno> arbol, 
                                  DiccionarioHash<String, Curso> diccionario, 
                                  Cola<SolicitudCalificacion> cola, 
                                  ManejadorAcciones manejador) {
        
        this.arbolEstudiantes = arbol;
        this.diccionarioCursos = diccionario;
        this.colaSolicitudes = cola;
        this.manejadorAcciones = manejador;

        setTitle("Sistema de Gestión Escolar - Calificaciones");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // --- BARRA SUPERIOR ---
        JPanel panelTabs = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelTabs.setBackground(CIAN_PRINCIPAL);
        String[] tabs = {"Estudiantes", "Cursos", "Inscripciones", "Calificaciones", "Reportes"};
        for (int i = 0; i < tabs.length; i++) {
            panelTabs.add(crearBotonTab(tabs[i], i == 3));
        }
        add(panelTabs, BorderLayout.NORTH);

        // --- CONTENEDOR CENTRAL ---
        JPanel contenedorCentral = new JPanel(new GridBagLayout());
        contenedorCentral.setBackground(Color.WHITE);
        contenedorCentral.setBorder(new EmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        // 1. PANEL FORMULARIO
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(new TitledBorder(new LineBorder(CIAN_PRINCIPAL), "Nueva Solicitud de Calificación", 
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), CIAN_PRINCIPAL));
        
        GridBagConstraints gbf = new GridBagConstraints();
        gbf.insets = new Insets(10, 10, 10, 10);
        gbf.anchor = GridBagConstraints.WEST;

        gbf.gridx = 0; gbf.gridy = 0;
        JLabel lblMatricula = new JLabel("Matrícula Alumno:");
        lblMatricula.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelFormulario.add(lblMatricula, gbf);

        gbf.gridx = 1;
        txtMatricula = new JTextField(15);
        panelFormulario.add(txtMatricula, gbf);

        gbf.gridx = 0; gbf.gridy = 1;
        JLabel lblCalificacion = new JLabel("Calificación (Nota):");
        lblCalificacion.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelFormulario.add(lblCalificacion, gbf);

        gbf.gridx = 1;
        txtCalificacion = new JTextField(15);
        panelFormulario.add(txtCalificacion, gbf);

        gbf.gridx = 0; gbf.gridy = 2;
        JLabel lblIndice = new JLabel("Índice a Modificar (Opcional):");
        lblIndice.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        panelFormulario.add(lblIndice, gbf);

        gbf.gridx = 1;
        txtIndiceModificar = new JTextField(5);
        txtIndiceModificar.setToolTipText("Dejar vacío si es una nueva calificación");
        panelFormulario.add(txtIndiceModificar, gbf);

        gbf.gridx = 0; gbf.gridy = 3;
        gbf.gridwidth = 2;
        gbf.anchor = GridBagConstraints.CENTER;
        JButton btnEncolar = new JButton("Enviar a Cola de Espera");
        estilizarBotonAccion(btnEncolar, CIAN_PRINCIPAL, Color.BLACK);
        panelFormulario.add(btnEncolar, gbf);

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0.4; gbc.weighty = 1.0;
        contenedorCentral.add(panelFormulario, gbc);

        // 2. PANEL COLA VISUAL
        JPanel panelCola = new JPanel(new BorderLayout(10, 10));
        panelCola.setBackground(Color.WHITE);
        panelCola.setBorder(new TitledBorder(new LineBorder(CIAN_PRINCIPAL), "Solicitudes Pendientes", 
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), CIAN_PRINCIPAL));

        txtAreaCola = new JTextArea();
        txtAreaCola.setEditable(false);
        txtAreaCola.setFont(new Font("Consolas", Font.PLAIN, 13));
        txtAreaCola.setBackground(CIAN_CLARO);
        JScrollPane scrollCola = new JScrollPane(txtAreaCola);
        panelCola.add(scrollCola, BorderLayout.CENTER);

        JButton btnProcesar = new JButton("Procesar Siguiente Solicitud");
        btnProcesar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        estilizarBotonAccion(btnProcesar, CIAN_OSCURO, Color.BLACK);
        panelCola.add(btnProcesar, BorderLayout.SOUTH);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.weightx = 0.6; gbc.weighty = 1.0;
        contenedorCentral.add(panelCola, gbc);

        add(contenedorCentral, BorderLayout.CENTER);

        // --- BARRA INFERIOR ---
        JPanel panelBotonesInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotonesInferior.setBackground(Color.WHITE);
        JButton btnSalir = new JButton("Salir");
        btnSalir.setPreferredSize(new Dimension(120, 35));
        estilizarBotonAccion(btnSalir, CIAN_CLARO, Color.BLACK);
        panelBotonesInferior.add(btnSalir);
        add(panelBotonesInferior, BorderLayout.SOUTH);

        // --- LOGICA DE EVENTOS ---

        actualizarAreaColaVisual();

        // EVENTO: Encolar usando tus Constructores sobrecargados
        btnEncolar.addActionListener(e -> {
            Validaciones validador = new Validaciones();
            String mat = txtMatricula.getText().trim();
            String calStr = txtCalificacion.getText().trim();
            String indStr = txtIndiceModificar.getText().trim();

            if (mat.isEmpty() || !validador.validarPrecio(calStr)) { 
                JOptionPane.showMessageDialog(this, "Ingrese datos numéricos válidos.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Alumno estudiante = arbolEstudiantes.buscarAlumno(mat);
            if (estudiante == null) {
                JOptionPane.showMessageDialog(this, "El estudiante no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double nota = Double.parseDouble(calStr);
            SolicitudCalificacion nuevaSolicitud;

            // Determinar qué constructor usar basado en si se pasó un índice
            if (indStr.isEmpty()) {
                // Constructor 1: Calificación Nueva (indice asigna internamente -1)
                nuevaSolicitud = new SolicitudCalificacion(estudiante, nota);
            } else {
                if (!validador.validarCantiadad(indStr)) {
                    JOptionPane.showMessageDialog(this, "El índice debe ser un número entero válido.", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int idx = Integer.parseInt(indStr);
                if (idx < 0 || idx >= estudiante.getCalificaciones().size()) {
                    JOptionPane.showMessageDialog(this, "Índice fuera de rango para las calificaciones actuales.", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // Constructor 2: Modificación de calificación existente
                nuevaSolicitud = new SolicitudCalificacion(estudiante, idx, nota);
            }

            colaSolicitudes.enqueue(nuevaSolicitud);
            JOptionPane.showMessageDialog(this, "Solicitud añadida a la cola.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            txtMatricula.setText("");
            txtCalificacion.setText("");
            txtIndiceModificar.setText("");
            actualizarAreaColaVisual();
        });

        // EVENTO: Procesar atendiendo las propiedades .esNueva() e .getIndice()
        btnProcesar.addActionListener(e -> {
            if (colaSolicitudes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay solicitudes pendientes.", "Cola Vacía", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Desencolar la solicitud nativa
            SolicitudCalificacion solicitud = colaSolicitudes.dequeue();
            Alumno alumno = solicitud.getAlumno(); // Obtenemos el puntero del Alumno directamente

            if (alumno != null) {
                int indiceAccion;
                double valorAnterior;

                if (solicitud.esNueva()) {
                    // Es NUEVA: Capturamos la posición donde va a caer la nota
                    indiceAccion = alumno.getCalificaciones().size();
                    valorAnterior = -1; // Bandera para que el deshacer sepa eliminarla por completo

                    alumno.agregarCalificacion(solicitud.getValorNuevo());
                } else {
                    // Es MODIFICACIÓN: Extraemos la posición exacta solicitada
                    indiceAccion = solicitud.getIndice();
                    valorAnterior = alumno.getCalificaciones().get(indiceAccion);

                    // Reemplazamos el valor en el arreglo del alumno
                    alumno.getCalificaciones().set(indiceAccion, solicitud.getValorNuevo());
                }

                // Guardamos en tu Manejador de Historial usando el Contexto esperado por tu switch
                ContextoCalificacion cc = new ContextoCalificacion(alumno, indiceAccion, valorAnterior);
                manejadorAcciones.registrarAccion("AGREGAR_CALIFICACION", cc);

                String tipoOperacion = solicitud.esNueva() ? "Nueva Calificación" : "Modificación de Nota";
                JOptionPane.showMessageDialog(this, tipoOperacion + " procesada con éxito para " + alumno.getNombre());
            }

            actualizarAreaColaVisual();
        });

        btnSalir.addActionListener(e -> {
            this.dispose();
            new PantallaInicioSesion(arbolEstudiantes, diccionarioCursos, colaSolicitudes, manejadorAcciones).setVisible(true);
        });
    }

    private void actualizarAreaColaVisual() {
        if (colaSolicitudes.isEmpty()) {
            txtAreaCola.setText("\n   No hay solicitudes en espera.");
            return;
        }

        txtAreaCola.setText("");
        txtAreaCola.append(String.format("   %-12s | %-20s | %-8s | %-10s\n", "MATRÍCULA", "ALUMNO", "ESTADO", "CALIICACION"));
        txtAreaCola.append("   -----------------------------------------------------------------\n");
        
        Cola<SolicitudCalificacion> tempCola = new Cola<>();
        while (!colaSolicitudes.isEmpty()) {
            SolicitudCalificacion s = colaSolicitudes.dequeue();
            String tipo = s.esNueva() ? "NUEVA" : "MODIF (Idx:" + s.getIndice() + ")";
            txtAreaCola.append(String.format("   %-12s | %-20s | %-8s | %-10.2f\n", 
                    s.getAlumno().getMatricula(), s.getAlumno().getNombre(), tipo, s.getValorNuevo()));
            tempCola.enqueue(s);
        }
        while (!tempCola.isEmpty()) {
            colaSolicitudes.enqueue(tempCola.dequeue());
        }
    }

    private JButton crearBotonTab(String texto, boolean activo) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", activo ? Font.BOLD : Font.PLAIN, 13));
        btn.setPreferredSize(new Dimension(130, 35));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (activo) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(CIAN_PRINCIPAL);
        } else {
            btn.setBackground(CIAN_PRINCIPAL);
            btn.setForeground(Color.WHITE);

            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setBackground(CIAN_OSCURO);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setBackground(CIAN_PRINCIPAL);
                }
            });

            btn.addActionListener(e -> {
                if (texto.equals("Estudiantes")) {
                    this.dispose();
                    new PantallaEstudiantes(arbolEstudiantes, diccionarioCursos, colaSolicitudes, manejadorAcciones).setVisible(true);
                } else if (texto.equals("Inscripciones")) {
                    this.dispose();
                    new PantallaInscripciones(arbolEstudiantes, diccionarioCursos, colaSolicitudes, manejadorAcciones).setVisible(true);
                } else if(texto.equals("Cursos")){
                    this.dispose();
                    new PantallaCursos(arbolEstudiantes, diccionarioCursos, colaSolicitudes, manejadorAcciones).setVisible(true);
                } else if(texto.equals("Reportes")){
                    this.dispose();
                    new PantallaReportes(arbolEstudiantes, diccionarioCursos, colaSolicitudes, manejadorAcciones).setVisible(true);
                }
            });
        }
        return btn;
    }

    private void estilizarBotonAccion(JButton btn, Color fondoOriginal, Color textoOriginal) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setBackground(fondoOriginal);
        btn.setForeground(textoOriginal);
        btn.setBorder(new LineBorder(CIAN_PRINCIPAL, 1, true));
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) { btn.setBackground(CIAN_OSCURO); btn.setForeground(Color.WHITE); }
            @Override public void mouseReleased(MouseEvent e) { btn.setBackground(fondoOriginal); btn.setForeground(textoOriginal); }
        });
    }
}