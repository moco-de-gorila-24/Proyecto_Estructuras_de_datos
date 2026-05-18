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
import Estructuras.ListaDobleEnlazadaCircular;
import Estructuras.Nodos.NodoListaDobleEnlazada;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * Pantalla de Inscripciones adaptada a tu clase ListaDobleEnlazadaCircular
 * real.
 *
 * @author DANIEL
 */
public class PantallaInscripciones extends JFrame {

    private ArbolBinarioBusqueda<Alumno> arbolEstudiantes;
    private DiccionarioHash<String, Curso> diccionarioCursos;
    private Cola<SolicitudCalificacion> colaSolicitudes;
    private ManejadorAcciones manejadorAcciones;

    // Control de navegación interactiva usando tu Nodo real
    private NodoListaDobleEnlazada<Alumno> nodoEsperaActual = null;
    private Curso cursoSeleccionadoActual = null;

    // Componentes gráficos
    private JLabel lblInfoCurso, lblInfoEstudiante, lblAlumnoEspera, lblTitularRol;
    private JTextArea txtAreaConsolaEspera; // Para imprimir recorridos completos y los N primeros
    private JTextField txtCodCurso, txtMatricula;
    private JSpinner spTopN;

    private final Color CIAN_PRINCIPAL = new Color(74, 158, 188);
    private final Color CIAN_CLARO = new Color(235, 245, 250);
    private final Color CIAN_OSCURO = new Color(42, 107, 132);

    public PantallaInscripciones(ArbolBinarioBusqueda<Alumno> arbol, DiccionarioHash<String, Curso> diccionario, Cola<SolicitudCalificacion> cola, ManejadorAcciones manejador) {
        this.arbolEstudiantes = arbol;
        this.diccionarioCursos = diccionario;
        this.colaSolicitudes = cola;
        this.manejadorAcciones = manejador;

        setTitle("Sistema de Gestión Escolar - Inscripciones y Listas de Espera");
        setSize(1200, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // BARRA SUPERIOR (Tabs)
        JPanel panelTabs = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelTabs.setBackground(CIAN_PRINCIPAL);
        String[] tabs = {"Estudiantes", "Cursos", "Inscripciones", "Calificaciones", "Reportes"};
        for (int i = 0; i < tabs.length; i++) {
            panelTabs.add(crearBotonTab(tabs[i], i == 2));
        }
        add(panelTabs, BorderLayout.NORTH);

        // CONTENEDOR CENTRAL (2 columnas x 2 filas)
        JPanel contenedorCentral = new JPanel(new GridLayout(2, 2, 20, 20));
        contenedorCentral.setBackground(Color.WHITE);
        contenedorCentral.setBorder(new EmptyBorder(20, 20, 20, 20));

        // FORMULARIO DE INSCRIPCIÓN
        JPanel panelInscribir = crearPanelContenedor("Registro de Inscripción (Validar Cupos)");
        panelInscribir.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelInscribir.add(new JLabel("Código Único del Curso:"), gbc);
        txtCodCurso = new JTextField(12);
        gbc.gridx = 1;
        panelInscribir.add(txtCodCurso, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelInscribir.add(new JLabel("Matrícula del Alumno:"), gbc);
        txtMatricula = new JTextField(12);
        gbc.gridx = 1;
        panelInscribir.add(txtMatricula, gbc);

        JButton btnInscribir = new JButton("Procesar Inscripción");
        estilizarBotonAccion(btnInscribir, CIAN_PRINCIPAL, Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 0, 0, 0);
        panelInscribir.add(btnInscribir, gbc);

        contenedorCentral.add(panelInscribir);

        // PANEL: ESTADO Y MONITOREO DEL CURSO
        JPanel panelConsulta = crearPanelContenedor("Estado de Ocupación del Curso");
        panelConsulta.setLayout(new BoxLayout(panelConsulta, BoxLayout.Y_AXIS));

        JButton btnVerCurso = new JButton("Cargar Datos de Curso");
        estilizarBotonAccion(btnVerCurso, CIAN_CLARO, Color.BLACK);
        btnVerCurso.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblInfoCurso = new JLabel("Ningún curso seleccionado.");
        lblInfoCurso.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblInfoCurso.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblInfoEstudiante = new JLabel(" ");
        lblInfoEstudiante.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblInfoEstudiante.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelConsulta.add(Box.createVerticalStrut(15));
        panelConsulta.add(btnVerCurso);
        panelConsulta.add(Box.createVerticalStrut(20));
        panelConsulta.add(lblInfoCurso);
        panelConsulta.add(Box.createVerticalStrut(10));
        panelConsulta.add(lblInfoEstudiante);

        contenedorCentral.add(panelConsulta);

        //  NAVEGACIÓN DE LA LISTA DE ESPERA (REQUERIMIENTO DOBLE CIRCULAR)
        JPanel panelEspera = crearPanelContenedor("Lista de Espera Interactiva (Doble Circular)");
        panelEspera.setLayout(new BorderLayout(10, 10));

        lblAlumnoEspera = new JLabel("Sin estudiantes seleccionados", SwingConstants.CENTER);
        lblAlumnoEspera.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panelEspera.add(lblAlumnoEspera, BorderLayout.NORTH);

        JPanel panelControlesNavegacion = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelControlesNavegacion.setBackground(Color.WHITE);
        JButton btnAnterior = new JButton("<< Anterior (getAnterior)");
        JButton btnSiguiente = new JButton("Siguiente (getSiguiente) >>");
        estilizarBotonAccion(btnAnterior, CIAN_CLARO, Color.BLACK);
        estilizarBotonAccion(btnSiguiente, CIAN_CLARO, Color.BLACK);
        panelControlesNavegacion.add(btnSiguiente);
        panelControlesNavegacion.add(btnAnterior);
        panelEspera.add(panelControlesNavegacion, BorderLayout.CENTER);

        // Subpanel inferior para herramientas nativas (Mostrar N, recorrer en Consola)
        JPanel panelHerramientasNativas = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelHerramientasNativas.setBackground(Color.WHITE);
        panelHerramientasNativas.add(new JLabel("Ver N Primeros:"));
        spTopN = new JSpinner(new SpinnerNumberModel(3, 1, 50, 1));
        panelHerramientasNativas.add(spTopN);

        JButton btnMostrarTop = new JButton("Imprimir");
        JButton btnRecorrerAdelante = new JButton("Recorrer Delante");
        JButton btnRecorrerAtras = new JButton("Recorrer Atras");
        estilizarBotonAccion(btnMostrarTop, CIAN_PRINCIPAL, Color.BLACK);
        estilizarBotonAccion(btnRecorrerAdelante, CIAN_CLARO, Color.BLACK);
        estilizarBotonAccion(btnRecorrerAtras, CIAN_CLARO, Color.BLACK);

        panelHerramientasNativas.add(btnMostrarTop);
        panelHerramientasNativas.add(btnRecorrerAdelante);
        panelHerramientasNativas.add(btnRecorrerAtras);
        panelEspera.add(panelHerramientasNativas, BorderLayout.SOUTH);

        contenedorCentral.add(panelEspera);

        // REPORTE DE ESTRUCTURAS Y ROTACIÓN
        JPanel panelReporteYRoles = crearPanelContenedor("Consola de Estructuras e Indicadores de Roles");
        panelReporteYRoles.setLayout(new BorderLayout(10, 10));

        txtAreaConsolaEspera = new JTextArea();
        txtAreaConsolaEspera.setEditable(false);
        txtAreaConsolaEspera.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtAreaConsolaEspera.setBackground(new Color(248, 249, 250));
        JScrollPane scrollConsola = new JScrollPane(txtAreaConsolaEspera);
        panelReporteYRoles.add(scrollConsola, BorderLayout.CENTER);

        JPanel panelBotoneraRoles = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelBotoneraRoles.setBackground(Color.WHITE);
        lblTitularRol = new JLabel("Rol: Sin iniciar ");
        lblTitularRol.setFont(new Font("Segoe UI", Font.BOLD, 12));

        JButton btnRotar = new JButton("Rotar Rol");
        estilizarBotonAccion(btnRotar, CIAN_PRINCIPAL, Color.BLACK);

        panelBotoneraRoles.add(lblTitularRol);
        panelBotoneraRoles.add(btnRotar);
        panelReporteYRoles.add(panelBotoneraRoles, BorderLayout.SOUTH);

        contenedorCentral.add(panelReporteYRoles);

        add(contenedorCentral, BorderLayout.CENTER);

        // LÓGICA DE ACCIONES E INTERACCIONES
        // PROCESAR INSCRIPCIÓN (REQUERIMIENTO 1 Y 2)
        btnInscribir.addActionListener(e -> {
            String idCurso = txtCodCurso.getText().trim();
            String matricula = txtMatricula.getText().trim();

            if (idCurso.isEmpty() || matricula.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar el código del curso y la matrícula.", "Campos Requeridos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Curso curso = diccionarioCursos.get(idCurso);
            Alumno alumno = arbolEstudiantes.buscarAlumno(matricula);

            if (curso == null) {
                JOptionPane.showMessageDialog(this, "Curso no encontrado en el Diccionario.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (alumno == null) {
                JOptionPane.showMessageDialog(this, "El estudiante no existe en el registro.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!curso.isFull()) {
                curso.getInscritos().add(alumno);
                curso.getRoles().add(alumno);
                JOptionPane.showMessageDialog(this, "Inscripción exitosa en el curso: " + curso.getNombre());
            } else {
                // Si excede, agregamos a ListaDobleEnlazadaCircular
                curso.getListaEspera().add(alumno);
                JOptionPane.showMessageDialog(this, "Capacidad excedida. Añadido a la lista de espera (Doble Circular).", "Curso Lleno", JOptionPane.INFORMATION_MESSAGE);
            }

            cursoSeleccionadoActual = curso;
            sincronizarCamposUI();
        });

        // CARGAR CURSO
        btnVerCurso.addActionListener(e -> {
            String idCurso = txtCodCurso.getText().trim();
            Curso curso = diccionarioCursos.get(idCurso);
            if (curso != null) {
                cursoSeleccionadoActual = curso;
                sincronizarCamposUI();
                txtAreaConsolaEspera.setText("Curso cargado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "El código de curso ingresado no existe.");
            }
        });

        // INTERACTIVIDAD ADELANTE (getSiguiente)
        btnSiguiente.addActionListener(e -> {
            if (nodoEsperaActual != null && nodoEsperaActual.getSiguiente() != null) {
                nodoEsperaActual = nodoEsperaActual.getSiguiente();
                Alumno al = nodoEsperaActual.getDato();
                lblAlumnoEspera.setText("Visualizando: " + al.getNombre() + " (Matrícula: " + al.getMatricula() + ")");
            }
        });

        // INTERACTIVIDAD ATRÁS (getAnterior)
        btnAnterior.addActionListener(e -> {
            if (nodoEsperaActual != null && nodoEsperaActual.getAnterior() != null) {
                nodoEsperaActual = nodoEsperaActual.getAnterior();
                Alumno al = nodoEsperaActual.getDato();
                lblAlumnoEspera.setText("Visualizando: " + al.getNombre() + " (Matrícula: " + al.getMatricula() + ")");
            }
        });

        // MOSTRAR N PRIMEROS DE LA ESPERA 
        btnMostrarTop.addActionListener(e -> {
            if (cursoSeleccionadoActual == null) {
                return;
            }
            int n = (Integer) spTopN.getValue();
            String salida = cursoSeleccionadoActual.getListaEspera().getNPrimeros(n);
            txtAreaConsolaEspera.setText(salida);
        });

        // RECORRER ADELANTE 
        btnRecorrerAdelante.addActionListener(e -> {
            if (cursoSeleccionadoActual == null) {
                return;
            }
            String salida = cursoSeleccionadoActual.getListaEspera().recorrerAdelante();
            txtAreaConsolaEspera.setText(salida);
        });

        // RECORRER ATRÁS 
        btnRecorrerAtras.addActionListener(e -> {
            if (cursoSeleccionadoActual == null) {
                return;
            }
            String salida = cursoSeleccionadoActual.getListaEspera().recorrerAtras();
            txtAreaConsolaEspera.setText(salida);
        });

        // ROTACIÓN DE ROLES (REQUERIMIENTO 3)
        btnRotar.addActionListener(e -> {
            if (cursoSeleccionadoActual == null) {
                JOptionPane.showMessageDialog(this, "Cargue un curso activo primero.");
                return;
            }
            if (cursoSeleccionadoActual.getRoles().estaVacia()) {
                lblTitularRol.setText("Rol: Sin alumnos inscritos");
                return;
            }

            try {
                // Capturamos el String directamente
                String infoNuevoLider = cursoSeleccionadoActual.getRoles().rotarRol();

                // Colocamos ese texto en la etiqueta de la interfaz
                lblTitularRol.setText("<html><center>" + infoNuevoLider + "</center></html>");

                // Actualizamos la consola gráfica para ver la flecha "<- rol actual"
                txtAreaConsolaEspera.setText(cursoSeleccionadoActual.getRoles().getContenido());

            } catch (Exception ex) {
                txtAreaConsolaEspera.setText("Error al rotar en la lista circular del curso.");
            }
        });

        // BOTÓN INFERIOR SALIR
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.setBackground(Color.WHITE);
        JButton btnSalir = new JButton("Volver al Catálogo");
        btnSalir.setPreferredSize(new Dimension(150, 35));
        estilizarBotonAccion(btnSalir, CIAN_CLARO, Color.BLACK);
        btnSalir.addActionListener(e -> {
            this.dispose();
            new PantallaCursos(arbolEstudiantes, diccionarioCursos, colaSolicitudes, manejadorAcciones).setVisible(true);
        });
        panelInferior.add(btnSalir);
        add(panelInferior, BorderLayout.SOUTH);
    }

    /**
     * Sincroniza dinámicamente el estado de los componentes gráficos con las
     * propiedades de la lista
     */
    private void sincronizarCamposUI() {
        if (cursoSeleccionadoActual == null) {
            return;
        }

        int numInscritos = cursoSeleccionadoActual.getInscritos().size();
        int maxCupos = cursoSeleccionadoActual.getCapacidadMaxima();

        lblInfoCurso.setText("Curso Seleccionado: " + cursoSeleccionadoActual.getNombre() + " (" + cursoSeleccionadoActual.getId() + ")");
        lblInfoEstudiante.setText("Estatus Cupos: " + numInscritos + " ocupados de " + maxCupos + " permitidos.");

        // Inicializar el puntero interactivo apuntando al primer nodo (ListaDobleEnlazadaCircular)
        if (!cursoSeleccionadoActual.getListaEspera().estaVacia()) {

            try {

                lblAlumnoEspera.setText("Lista de espera activa: " + cursoSeleccionadoActual.getListaEspera().size() + " en fila.");
            } catch (Exception e) {
                lblAlumnoEspera.setText("Lista de espera activa.");
            }
        } else {
            nodoEsperaActual = null;
            lblAlumnoEspera.setText("La lista de espera está vacía.");
        }
    }

    private JPanel crearPanelContenedor(String titulo) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(new TitledBorder(new LineBorder(CIAN_PRINCIPAL, 1), titulo, TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 13), CIAN_PRINCIPAL));
        return panel;
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
                } else if (texto.equals("Calificaciones")) {
                    this.dispose();
                    new PantallaCalificaciones(arbolEstudiantes, diccionarioCursos, colaSolicitudes, manejadorAcciones).setVisible(true);
                } else if (texto.equals("Cursos")) {
                    this.dispose();
                    new PantallaCursos(arbolEstudiantes, diccionarioCursos, colaSolicitudes, manejadorAcciones).setVisible(true);
                } else if (texto.equals("Reportes")) {
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
            @Override
            public void mousePressed(MouseEvent e) {
                btn.setBackground(CIAN_OSCURO);
                btn.setForeground(Color.WHITE);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                btn.setBackground(fondoOriginal);
                btn.setForeground(textoOriginal);
            }
        });
    }
}
