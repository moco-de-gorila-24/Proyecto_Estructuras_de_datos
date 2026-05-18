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
import javax.swing.table.*;

/**
 *
 * @author DANIEL
 */
public class PantallaCursos extends JFrame {

    private ArbolBinarioBusqueda<Alumno> arbolEstudiantes;
    private DiccionarioHash<String, Curso> diccionarioCursos;
    private Cola<SolicitudCalificacion> colaSolicitudes;
    private ManejadorAcciones manejadorAcciones;
    private DefaultTableModel model;
    private JTable tabla;

    // Colores de la paleta
    private final Color CIAN_PRINCIPAL = new Color(74, 158, 188);
    private final Color CIAN_CLARO = new Color(235, 245, 250);
    private final Color CIAN_OSCURO = new Color(42, 107, 132);

    public PantallaCursos(ArbolBinarioBusqueda<Alumno> arbol, DiccionarioHash<String, Curso> diccionario, Cola<SolicitudCalificacion> cola, ManejadorAcciones manejador) {
        this.arbolEstudiantes = arbol;
        this.diccionarioCursos = diccionario;

        setTitle("Sistema de Gestión Escolar - Catálogo de Cursos");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // BARRA SUPERIOR
        JPanel panelTabs = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelTabs.setBackground(CIAN_PRINCIPAL);
        String[] tabs = {"Estudiantes", "Cursos", "Inscripciones", "Calificaciones", "Reportes"};
        for (int i = 0; i < tabs.length; i++) {
            panelTabs.add(crearBotonTab(tabs[i], i == 1));
        }
        add(panelTabs, BorderLayout.NORTH);

        // CONTENEDOR PRINCIPAL
        JPanel contenedorCentral = new JPanel(new GridBagLayout());
        contenedorCentral.setBackground(Color.WHITE);
        contenedorCentral.setBorder(new EmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 0, 10, 0);

        // --- TABLA DE CURSOS (Muestra la lista de registrados) ---
        String[] columnas = {"Clave Única (ID)", "Nombre del Curso", "Capacidad Máxima"};
        model = new DefaultTableModel(null, columnas);
        tabla = new JTable(model);
        tabla.setRowHeight(25);
        tabla.getTableHeader().setBackground(CIAN_PRINCIPAL);
        tabla.getTableHeader().setForeground(CIAN_OSCURO);
        tabla.setSelectionBackground(CIAN_OSCURO);
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setRowSelectionAllowed(true);

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setPreferredSize(new Dimension(800, 200));
        scrollTabla.setBorder(new LineBorder(CIAN_PRINCIPAL, 1));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        contenedorCentral.add(scrollTabla, gbc);

        // Renderizar los datos iniciales
        actualizarTabla();

        // --- FORMULARIO DE REGISTRO Y ELIMINACIÓN ---
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(new TitledBorder(new LineBorder(CIAN_PRINCIPAL), "Administración del Catálogo", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), CIAN_PRINCIPAL));

        GridBagConstraints gbf = new GridBagConstraints();
        gbf.insets = new Insets(10, 15, 10, 15);
        gbf.anchor = GridBagConstraints.WEST;

        // Campos de texto y numéricos
        agregarCampo(panelFormulario, "Clave Única del Curso (ID):", 0, 0, gbf);
        JTextField txtClave = new JTextField(12);
        agregarComponente(panelFormulario, txtClave, 1, 0, gbf);

        agregarCampo(panelFormulario, "Nombre del Curso:", 0, 1, gbf);
        JTextField txtNombreCurso = new JTextField(25);
        agregarComponente(panelFormulario, txtNombreCurso, 1, 1, gbf);

        agregarCampo(panelFormulario, "Capacidad Máxima:", 0, 2, gbf);
        // JSpinner restringido para aceptar solo números enteros positivos 
        SpinnerModel spinnerModel = new SpinnerNumberModel(30, 1, 500, 1);
        JSpinner spCapacidad = new JSpinner(spinnerModel);
        spCapacidad.setPreferredSize(new Dimension(80, 22));
        agregarComponente(panelFormulario, spCapacidad, 1, 2, gbf);

        // Botones de acción del formulario
        JPanel panelBotonesAccion = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelBotonesAccion.setBackground(Color.WHITE);

        JButton btnAgregar = new JButton("Agregar Curso");
        estilizarBotonAccion(btnAgregar, CIAN_PRINCIPAL, Color.BLACK);
        panelBotonesAccion.add(btnAgregar);

        JButton btnEliminar = new JButton("Eliminar Seleccionado");
        estilizarBotonAccion(btnEliminar, new Color(240, 130, 130), Color.BLACK);
        panelBotonesAccion.add(btnEliminar);

        gbf.gridx = 0;
        gbf.gridy = 3;
        gbf.gridwidth = 2;
        gbf.anchor = GridBagConstraints.CENTER;
        gbf.insets = new Insets(20, 0, 10, 0);
        panelFormulario.add(panelBotonesAccion, gbf);

        gbc.gridy = 1;
        gbc.weighty = 0.4;
        contenedorCentral.add(panelFormulario, gbc);

        add(contenedorCentral, BorderLayout.CENTER);

        // AGREGAR CURSO
        btnAgregar.addActionListener(e -> {
            String clave = txtClave.getText().trim();
            String nombre = txtNombreCurso.getText().trim();
            int capacidad = (Integer) spCapacidad.getValue();

            if (clave.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios para registrar un curso.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validación de claves únicas
            if (diccionarioCursos.contiene(clave)) {
                JOptionPane.showMessageDialog(this, "La clave '" + clave + "' ya existe en el catálogo.", "Clave Duplicada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Agregamos al DiccionarioHash
            diccionarioCursos.agregar(clave, new Curso(clave, nombre, capacidad));

            // Refrescar componentes de la UI
            actualizarTabla();
            txtClave.setText("");
            txtNombreCurso.setText("");
            spCapacidad.setValue(30);
            JOptionPane.showMessageDialog(this, "Curso añadido exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });

        // ELIMINAR CURSO
        btnEliminar.addActionListener(e -> {
            int filaSeleccionada = tabla.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un curso de la tabla para proceder a eliminar.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Obtener el ID único de la columna 0 de la JTable
            String IDAEliminar = model.getValueAt(filaSeleccionada, 0).toString();

            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea eliminar el curso [" + IDAEliminar + "] del catálogo?", "Confirmar Acción", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                diccionarioCursos.eliminar(IDAEliminar);

                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Curso eliminado correctamente.", "Eliminado", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // BARRA INFERIOR 
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.setBackground(Color.WHITE);
        JButton btnSalir = new JButton("Salir");
        btnSalir.setPreferredSize(new Dimension(120, 35));
        estilizarBotonAccion(btnSalir, CIAN_CLARO, Color.BLACK);

        btnSalir.addActionListener(e -> {
            this.dispose();
            PantallaInicioSesion login = new PantallaInicioSesion(arbolEstudiantes, diccionarioCursos, colaSolicitudes, manejadorAcciones);
            login.setVisible(true);
        });

        panelInferior.add(btnSalir);
        add(panelInferior, BorderLayout.SOUTH);
    }

    /**
     * Sincroniza y vuelca el contenido actual del DiccionarioHash usando el
     * for-each iterable
     */
    private void actualizarTabla() {
        model.setRowCount(0); // Limpiar la tabla visual

        // Usamos el Iterador nativo del DiccionarioHash
        for (Curso curso : diccionarioCursos) {
            if (curso != null) {
                model.addRow(new Object[]{curso.getId(), curso.getNombre(), curso.getCapacidadMaxima()});
            }
        }
    }

    // --- MÉTODOS AUXILIARES DE DISEÑO ---
    private void agregarCampo(JPanel p, String t, int x, int y, GridBagConstraints c) {
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = 1;
        JLabel lbl = new JLabel(t);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        p.add(lbl, c);
    }

    private void agregarComponente(JPanel p, JComponent comp, int x, int y, GridBagConstraints c) {
        c.gridx = x;
        c.gridy = y;
        p.add(comp, c);
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
                } else if(texto.equals("Calificaciones")){
                 this.dispose();
                 new PantallaCalificaciones(arbolEstudiantes, diccionarioCursos, colaSolicitudes, manejadorAcciones).setVisible(true);
                } else if(texto.equals("Cursos")){
                    this.dispose();
                    new PantallaCursos(arbolEstudiantes, diccionarioCursos, colaSolicitudes, manejadorAcciones).setVisible(true);
                } else if(texto.equals("Reportes")){
                    this.dispose();
                    //new PantallaReportes(arbolEstudiantes, diccionarioCursos, colaSolicitudes, manejadorAcciones).setVisible(true);
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
