package Pantallas;

import Clases.Alumno;
import Clases.Curso;
import Clases.ManejadorAcciones;
import Clases.SolicitudCalificacion;
import Estructuras.ArbolBinarioBusqueda;
import Estructuras.ArbolAVL; // Tu árbol balanceado por promedio
import Estructuras.ArregloDinamico;
import Estructuras.Cola;
import Estructuras.DiccionarioHash;
import Estructuras.ListaEnlazadaCircular; // Tu lista para roles cíclicos
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class PantallaReportes extends JFrame {

    private ArbolBinarioBusqueda<Alumno> arbolEstudiantes;
    private DiccionarioHash<String, Curso> diccionarioCursos;
    private Cola<SolicitudCalificacion> colaSolicitudes;
    private ManejadorAcciones manejadorAcciones;

    // Lista circular compartida para la rotación de roles (Requerimiento 6.2)
    private static ListaEnlazadaCircular<Alumno> listaTutores = new ListaEnlazadaCircular<>();

    // Paleta de colores CIAN
    private final Color CIAN_PRINCIPAL = new Color(74, 158, 188);
    private final Color CIAN_CLARO = new Color(235, 245, 250);
    private final Color CIAN_OSCURO = new Color(42, 107, 132);

    private DefaultTableModel modeloEstudiantes;
    private JLabel lblLiderActual;
    private JTextArea txtAreaListaTutores;

    public PantallaReportes(ArbolBinarioBusqueda<Alumno> arbol,
            DiccionarioHash<String, Curso> diccionario,
            Cola<SolicitudCalificacion> cola,
            ManejadorAcciones manejador) {

        this.arbolEstudiantes = arbol;
        this.diccionarioCursos = diccionario;
        this.colaSolicitudes = cola;
        this.manejadorAcciones = manejador;

        setTitle("Sistema de Gestión Escolar - Reportes y Roles");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // --- BARRA SUPERIOR DE NAVEGACIÓN ---
        JPanel panelTabs = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelTabs.setBackground(CIAN_PRINCIPAL);
        String[] tabs = {"Estudiantes", "Cursos", "Inscripciones", "Calificaciones", "Reportes"};
        for (int i = 0; i < tabs.length; i++) {
            panelTabs.add(crearBotonTab(tabs[i], i == 4)); // Reportes activo (index 4)
        }
        add(panelTabs, BorderLayout.NORTH);

        // --- CONTENEDOR CENTRAL ---
        JPanel contenedorCentral = new JPanel(new GridLayout(1, 2, 20, 0));
        contenedorCentral.setBackground(Color.WHITE);
        contenedorCentral.setBorder(new EmptyBorder(20, 20, 20, 20));

        // PANEL IZQUIERDO: REPORTES POR PROMEDIO (6.1)
        JPanel panelPromedios = new JPanel(new BorderLayout(10, 10));
        panelPromedios.setBackground(Color.WHITE);
        panelPromedios.setBorder(new TitledBorder(new LineBorder(CIAN_PRINCIPAL), "Estudiantes Ordenados por Promedio", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), CIAN_PRINCIPAL));

        String[] columnas = {"Posición", "Matrícula", "Nombre", "Promedio"};
        modeloEstudiantes = new DefaultTableModel(null, columnas);
        JTable tablaPromedios = new JTable(modeloEstudiantes);
        tablaPromedios.setRowHeight(25);
        tablaPromedios.getTableHeader().setBackground(CIAN_PRINCIPAL);
        tablaPromedios.getTableHeader().setForeground(Color.BLACK);

        JScrollPane scrollTabla = new JScrollPane(tablaPromedios);
        panelPromedios.add(scrollTabla, BorderLayout.CENTER);

        JButton btnGenerarReporte = new JButton("Calcular y Ordenar por Promedio");
        estilizarBotonAccion(btnGenerarReporte, CIAN_PRINCIPAL, Color.BLACK);
        btnGenerarReporte.addActionListener(e -> generarReportePromedios());
        panelPromedios.add(btnGenerarReporte, BorderLayout.SOUTH);

        //ROTAR ROL USANDO LISTA CIRCULAR (6.2)
        JPanel panelRoles = new JPanel(new GridBagLayout());
        panelRoles.setBackground(Color.WHITE);
        panelRoles.setBorder(new TitledBorder(new LineBorder(CIAN_PRINCIPAL), "Rotar Rol de Tutor / Líder de Proyecto", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), CIAN_PRINCIPAL));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.gridx = 0;

        // Visualización Líder Actual
        gbc.gridy = 0;
        JLabel lblTituloLider = new JLabel("LÍDER DE PROYECTO / TUTOR ACTUAL:");
        lblTituloLider.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panelRoles.add(lblTituloLider, gbc);

        gbc.gridy = 1;
        lblLiderActual = new JLabel("[ Ningún alumno asignado al rol ]");
        lblLiderActual.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblLiderActual.setForeground(CIAN_OSCURO);
        panelRoles.add(lblLiderActual, gbc);

        // Visualización del Contenido de la Lista Circular
        gbc.gridy = 2;
        JLabel lblListaSiguientes = new JLabel("Estado de la Lista Circular de Roles:");
        lblListaSiguientes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelRoles.add(lblListaSiguientes, gbc);

        gbc.gridy = 3;
        txtAreaListaTutores = new JTextArea(10, 30);
        txtAreaListaTutores.setEditable(false);
        txtAreaListaTutores.setBorder(new LineBorder(Color.LIGHT_GRAY));
        txtAreaListaTutores.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panelRoles.add(new JScrollPane(txtAreaListaTutores), gbc);

        // Botonera de control de roles
        JPanel panelBotonesRoles = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotonesRoles.setBackground(Color.WHITE);

        JButton btnInicializarLista = new JButton("Cargar Estudiantes al Sistema de Roles");
        estilizarBotonAccion(btnInicializarLista, CIAN_CLARO, Color.BLACK);
        btnInicializarLista.addActionListener(e -> inicializarListaRoles());

        JButton btnRotarRol = new JButton("Rotar Líder");
        estilizarBotonAccion(btnRotarRol, CIAN_PRINCIPAL, Color.BLACK);
        btnRotarRol.addActionListener(e -> ejecutarRotacion());

        panelBotonesRoles.add(btnInicializarLista);
        panelBotonesRoles.add(btnRotarRol);

        gbc.gridy = 4;
        panelRoles.add(panelBotonesRoles, gbc);

        contenedorCentral.add(panelPromedios);
        contenedorCentral.add(panelRoles);
        add(contenedorCentral, BorderLayout.CENTER);

        // --- BARRA INFERIOR ---
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.setBackground(Color.WHITE);
        JButton btnSalir = new JButton("Regresar al Inicio");
        btnSalir.setPreferredSize(new Dimension(150, 35));
        estilizarBotonAccion(btnSalir, Color.LIGHT_GRAY, Color.BLACK);
        btnSalir.addActionListener(e -> {
            this.dispose();
            new PantallaInicioSesion(arbolEstudiantes, diccionarioCursos, colaSolicitudes, manejadorAcciones).setVisible(true);
        });
        panelInferior.add(btnSalir);
        add(panelInferior, BorderLayout.SOUTH);

        actualizarVistaRoles();
    }

    //REPORTES POR PROMEDIO USANDO TU ARBOL AVL
    private void generarReportePromedios() {
        modeloEstudiantes.setRowCount(0);
        ArregloDinamico<Alumno> listaAlumnosABB = new ArregloDinamico<>();

        // Volcamos los alumnos desde tu estructura principal por matrícula (ABB)
        arbolEstudiantes.obtenerTodos(listaAlumnosABB);

        if (listaAlumnosABB.size() == 0) {
            JOptionPane.showMessageDialog(this, "No hay estudiantes registrados en el sistema.", "Reporte Vacío", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Instanciamos tu ArbolAVL para que use su lógica interna de ordenamiento y balanceo
        ArbolAVL arbolOrdenador = new ArbolAVL();

        // Poblamos el AVL pasándole la clave de ordenamiento (promedio) y el objeto
        for (int i = 0; i < listaAlumnosABB.size(); i++) {
            Alumno al = listaAlumnosABB.get(i);
            // Se usa el método recursivo de promedio de tu negocio
            arbolOrdenador.insertar(al.getPromedioRecursivo(), al);
        }

        // Recuperamos los datos ordenados en un ArregloDinamico por InOrder (Menor a Mayor)
        ArregloDinamico<Alumno> listaOrdenadaAVL = arbolOrdenador.obtenerOrdenadosPorPromedio();

        // Renderizamos en la tabla de Mayor a Menor recorriendo el arreglo de atrás hacia adelante
        int posicion = 1;
        for (int i = listaOrdenadaAVL.size() - 1; i >= 0; i--) {
            Alumno al = listaOrdenadaAVL.get(i);
            modeloEstudiantes.addRow(new Object[]{
                posicion + "°",
                al.getMatricula(),
                al.getNombre(),
                String.format("%.2f", al.getPromedioRecursivo())
            });
            posicion++;
        }
    }

    // LÓGICA 6.2: ROTACIÓN CON TU LISTA ENLAZADA CIRCULAR
    private void inicializarListaRoles() {
        if (!listaTutores.estaVacia()) {
            JOptionPane.showMessageDialog(this, "La lista circular de roles ya contiene estudiantes activos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ArregloDinamico<Alumno> listaAlumnos = new ArregloDinamico<>();
        arbolEstudiantes.obtenerTodos(listaAlumnos);

        if (listaAlumnos.size() == 0) {
            JOptionPane.showMessageDialog(this, "Debe registrar alumnos primero en la pestaña 'Estudiantes'.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Poblamos tu estructura cíclica nativa
        for (int i = 0; i < listaAlumnos.size(); i++) {
            listaTutores.add(listaAlumnos.get(i));
        }

        JOptionPane.showMessageDialog(this, "Se cargaron exitosamente " + listaAlumnos.size() + " estudiantes a la lista circular.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        actualizarVistaRoles();
    }

    private void ejecutarRotacion() {
        if (listaTutores.estaVacia()) {
            JOptionPane.showMessageDialog(this, "La lista de roles está vacía. Cárguela primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String mensajeResultado = listaTutores.rotarRol();

        JOptionPane.showMessageDialog(this, mensajeResultado, "Cambio de Rol Exitoso", JOptionPane.INFORMATION_MESSAGE);
        actualizarVistaRoles();
    }

    private void actualizarVistaRoles() {
        if (listaTutores.estaVacia()) {
            lblLiderActual.setText("[ Ningún alumno asignado al rol ]");
            txtAreaListaTutores.setText("Lista vacía.\nPresione 'Cargar Estudiantes al Sistema de Roles' para iniciar.");
            return;
        }

        // Mostramos el tutor apuntado actualmente por el puntero interno
        Alumno actual = listaTutores.getActual();
        if (actual != null) {
            lblLiderActual.setText(actual.getNombre() + " (" + actual.getMatricula() + ")");
        } else {
            lblLiderActual.setText("Presione 'Rotar Líder' para inicializar el puntero");
        }

        // Mostramos el mapa completo de la lista circular 
        txtAreaListaTutores.setText(listaTutores.getContenido());
    }

    // --- MANEJADOR DE PESTAÑAS (TABS) ---
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
                this.dispose();
                if (texto.equals("Estudiantes")) {
                    new PantallaEstudiantes(arbolEstudiantes, diccionarioCursos, colaSolicitudes, manejadorAcciones).setVisible(true);
                } else if (texto.equals("Inscripciones")) {
                    new PantallaInscripciones(arbolEstudiantes, diccionarioCursos, colaSolicitudes, manejadorAcciones).setVisible(true);
                } else if (texto.equals("Calificaciones")) {
                    new PantallaCalificaciones(arbolEstudiantes, diccionarioCursos, colaSolicitudes, manejadorAcciones).setVisible(true);
                } else if (texto.equals("Cursos")) {
                    new PantallaCursos(arbolEstudiantes, diccionarioCursos, colaSolicitudes, manejadorAcciones).setVisible(true);
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
    }
}
