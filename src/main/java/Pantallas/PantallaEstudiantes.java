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
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DANIEL
 */
public class PantallaEstudiantes extends JFrame {

    private ArbolBinarioBusqueda<Alumno> arbolEstudiantes;
    private DiccionarioHash<String, Curso> diccionarioCursos;
    private ManejadorAcciones manejadorAcciones;
    private Cola<SolicitudCalificacion> colaSolicitudes;

    // Colores de la paleta
    private final Color CIAN_PRINCIPAL = new Color(74, 158, 188);
    private final Color CIAN_CLARO = new Color(235, 245, 250);
    private final Color CIAN_OSCURO = new Color(42, 107, 132);
    private final Color GRIS_TEXTO = new Color(70, 70, 70);

    public PantallaEstudiantes(ArbolBinarioBusqueda<Alumno> arbol, DiccionarioHash<String, Curso> diccionario, Cola<SolicitudCalificacion> cola, ManejadorAcciones manejador) {

        this.arbolEstudiantes = arbol;
        this.diccionarioCursos = diccionario;
        this.manejadorAcciones = manejador;
        this.colaSolicitudes = cola;

        setTitle("Sistema de Gestión Escolar - Estudiantes");
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
            panelTabs.add(crearBotonTab(tabs[i], i == 0));
        }
        add(panelTabs, BorderLayout.NORTH);

        // CONTENEDOR PRINCIPAL
        JPanel contenedorCentral = new JPanel(new GridBagLayout());
        contenedorCentral.setBackground(Color.WHITE);
        contenedorCentral.setBorder(new EmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 0, 10, 0);

        // --- BUSCADOR ---
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBackground(Color.WHITE);
        panelBusqueda.add(new JLabel("Buscar por Matrícula: "));
        JTextField txtBusqueda = new JTextField(15);
        panelBusqueda.add(txtBusqueda);

        JButton btnBuscar = new JButton("Buscar");
        estilizarBotonAccion(btnBuscar, CIAN_CLARO, Color.BLACK);
        panelBusqueda.add(btnBuscar);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.05;
        contenedorCentral.add(panelBusqueda, gbc);

        // --- TABLA DE ESTUDIANTES ---
        String[] columnas = {"Matrícula", "Nombre", "Teléfono", "Email", "Ciudad"};
        Object[][] datos = {};
        DefaultTableModel model = new DefaultTableModel(datos, columnas);
        JTable tabla = new JTable(model);
        tabla.setRowHeight(25);
        tabla.getTableHeader().setBackground(CIAN_PRINCIPAL);
        tabla.getTableHeader().setForeground(CIAN_OSCURO);
        tabla.setSelectionBackground(CIAN_CLARO);

        tabla.setSelectionBackground(CIAN_OSCURO);
        tabla.setSelectionForeground(Color.WHITE);

        tabla.setCellSelectionEnabled(true);
        tabla.setRowSelectionAllowed(true);

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setPreferredSize(new Dimension(800, 200));
        scrollTabla.setBorder(new LineBorder(CIAN_PRINCIPAL, 1));

        gbc.gridy = 1;
        gbc.weighty = 0.4;
        contenedorCentral.add(scrollTabla, gbc);

        // --- FORMULARIO DE REGISTRO ---
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(new TitledBorder(new LineBorder(CIAN_PRINCIPAL), "Registro de Nuevo Alumno", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), CIAN_PRINCIPAL));

        GridBagConstraints gbf = new GridBagConstraints();
        gbf.insets = new Insets(5, 10, 5, 10);
        gbf.anchor = GridBagConstraints.WEST;

        // Fila 1
        agregarCampo(panelFormulario, "Nombre Completo:", 0, 0, gbf);
        JTextField txtNombre = new JTextField(20);
        agregarComponente(panelFormulario, txtNombre, 1, 0, gbf);

        agregarCampo(panelFormulario, "Matrícula:", 2, 0, gbf);
        JTextField txtMatricula = new JTextField(10);
        agregarComponente(panelFormulario, txtMatricula, 3, 0, gbf);

        // Fila 2
        agregarCampo(panelFormulario, "Teléfono:", 0, 1, gbf);
        JTextField txtTel = new JTextField(12);
        agregarComponente(panelFormulario, txtTel, 1, 1, gbf);

        agregarCampo(panelFormulario, "Email:", 2, 1, gbf);
        JTextField txtEmail = new JTextField(15);
        agregarComponente(panelFormulario, txtEmail, 3, 1, gbf);

        // Fila 3
        agregarCampo(panelFormulario, "Calle:", 0, 2, gbf);
        JTextField txtCalle = new JTextField(12);
        agregarComponente(panelFormulario, txtCalle, 1, 2, gbf);

        agregarCampo(panelFormulario, "Número:", 2, 2, gbf);
        JTextField txtNum = new JTextField(5);
        agregarComponente(panelFormulario, txtNum, 3, 2, gbf);

        // Fila 4
        agregarCampo(panelFormulario, "Colonia:", 0, 3, gbf);
        JTextField txtColonia = new JTextField(12);
        agregarComponente(panelFormulario, txtColonia, 1, 3, gbf);

        agregarCampo(panelFormulario, "Ciudad:", 2, 3, gbf);
        JTextField txtCiudad = new JTextField(10);
        agregarComponente(panelFormulario, txtCiudad, 3, 3, gbf);

        // Botón Registrar 
        JButton btnRegistrar = new JButton("Registrar Alumno");
        estilizarBotonAccion(btnRegistrar, CIAN_PRINCIPAL, Color.BLACK);

        gbf.gridx = 1;
        gbf.gridy = 4;
        gbf.gridwidth = 2;
        gbf.anchor = GridBagConstraints.CENTER;
        gbf.insets = new Insets(15, 0, 10, 0);
        panelFormulario.add(btnRegistrar, gbf);

        gbc.gridy = 2;
        gbc.weighty = 0.4;
        contenedorCentral.add(panelFormulario, gbc);

        add(contenedorCentral, BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> {
            String matriculaABuscar = txtBusqueda.getText().trim();
            if (matriculaABuscar.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor ingrese una matrícula para buscar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Alumno alumnoEncontrado = arbolEstudiantes.buscarAlumno(matriculaABuscar);

            if (alumnoEncontrado != null) {
                // Si existe, se muestran todos los campos en el formulario de la interfaz
                txtMatricula.setText(alumnoEncontrado.getMatricula());
                txtNombre.setText(alumnoEncontrado.getNombre());
                txtTel.setText(alumnoEncontrado.getTelefono());
                txtEmail.setText(alumnoEncontrado.getEmail());
                txtCalle.setText(alumnoEncontrado.getCalle());
                txtNum.setText(alumnoEncontrado.getNumero());
                txtColonia.setText(alumnoEncontrado.getColonia());
                txtCiudad.setText(alumnoEncontrado.getCiudad());

                // Se muestra de forma detallada toda la info
                JOptionPane.showMessageDialog(this, alumnoEncontrado.getInfo(), "Estudiante Encontrado", JOptionPane.INFORMATION_MESSAGE);
                txtMatricula.setText("");
                txtNombre.setText("");
                txtTel.setText("");
                txtEmail.setText("");
                txtCalle.setText("");
                txtNum.setText("");
                txtColonia.setText("");
                txtCiudad.setText("");
                txtBusqueda.setText("");
            } else {
                // Si no existe, se informa “Estudiante no encontrado”.
                JOptionPane.showMessageDialog(this, "Estudiante no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);

            }
        });

        btnRegistrar.addActionListener(e -> {
            // Instanciar la clase de validaciones
            Validaciones.Validaciones validador = new Validaciones.Validaciones();

            // Extraer y limpiar los textos de la interfaz
            String matricula = txtMatricula.getText().trim();
            String nombre = txtNombre.getText().trim();
            String telefono = txtTel.getText().trim();
            String email = txtEmail.getText().trim();
            String calle = txtCalle.getText().trim();
            String numero = txtNum.getText().trim();
            String colonia = txtColonia.getText().trim();
            String ciudad = txtCiudad.getText().trim();

            // SECCIÓN DE VALIDACIONES CRÍTICAS
            if (!validador.validarMatricula(matricula)) {
                JOptionPane.showMessageDialog(this, "Matrícula inválida. Solo se permiten letras y números (4-15 caracteres).", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                txtMatricula.requestFocus();
                return;
            }

            if (!validador.validarNombres(nombre)) {
                JOptionPane.showMessageDialog(this, "Nombre inválido. Use solo letras y espacios.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                txtNombre.requestFocus();
                return;
            }

            if (!validador.validarTelefono(telefono)) {
                JOptionPane.showMessageDialog(this, "El teléfono debe contener exactamente 10 dígitos numéricos.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                txtTel.requestFocus();
                return;
            }

            if (!validador.validarCorreo(email)) {
                JOptionPane.showMessageDialog(this, "El correo electrónico no tiene un formato válido (ejemplo@dominio.com).", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                txtEmail.requestFocus();
                return;
            }

            // Validación simple para que las direcciones no queden vacías
            if (calle.isEmpty() || numero.isEmpty() || colonia.isEmpty() || ciudad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos de la dirección son obligatorios.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // VERIFICAR DUPLICADOS EN EL ÁRBOL BINARIO
            // Evitamos meter dos alumnos con la misma matrícula para no corromper el árbol
            if (arbolEstudiantes.buscarAlumno(matricula) != null) {
                JOptionPane.showMessageDialog(this, "Ya existe un estudiante registrado con la matrícula: " + matricula, "Registro Duplicado", JOptionPane.ERROR_MESSAGE);
                txtMatricula.requestFocus();
                return;
            }

            // SI TODO ESTÁ BIEN -> SE PROCEDE AL REGISTRO REAL
            Alumno nuevoAlumno = new Alumno(matricula, nombre, telefono, email, calle, numero, colonia, ciudad);

            // Inserta en tu estructura lógica
            manejadorAcciones.registrarAccion("REGISTRAR_ESTUDIANTE", nuevoAlumno);
            
            // Actualiza la interfaz visual al instante
            model.addRow(new Object[]{
                nuevoAlumno.getMatricula(),
                nuevoAlumno.getNombre(),
                nuevoAlumno.getTelefono(),
                nuevoAlumno.getEmail(),
                nuevoAlumno.getCiudad()
            });

            JOptionPane.showMessageDialog(this, "Estudiante registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Limpiar cajas de texto para el próximo registro
            txtMatricula.setText("");
            txtNombre.setText("");
            txtTel.setText("");
            txtEmail.setText("");
            txtCalle.setText("");
            txtNum.setText("");
            txtColonia.setText("");
            txtCiudad.setText("");
        });

        //BOTON DESHACER
        JButton btnDeshacer = new JButton("Deshacer");
        btnDeshacer.setPreferredSize(new Dimension(120, 35));
        estilizarBotonAccion(btnDeshacer, CIAN_CLARO, Color.BLACK);

btnDeshacer.addActionListener(e -> {
    // Validar usando tu método nativo
    if (!manejadorAcciones.hayAcciones()) {
        JOptionPane.showMessageDialog(this, "No hay acciones recientes para deshacer.", "Historial Vacío", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    // 2. Ejecutar el desapilado lógico y capturar el mensaje descriptivo
    String mensajeResultado = manejadorAcciones.deshacer();

    //  Sincronizar la interfaz gráfica (JTable) reconstruyendo las filas vivas del árbol
    // Como tu método modificó el árbol real eliminando al alumno, limpiar y repoblar es lo más seguro
    model.setRowCount(0);
    Estructuras.ArregloDinamico<Alumno> listaTemporal = new Estructuras.ArregloDinamico<>();
    arbolEstudiantes.obtenerTodos(listaTemporal);

    for (int i = 0; i < listaTemporal.size(); i++) {
        Alumno al = listaTemporal.get(i);
        model.addRow(new Object[]{al.getMatricula(), al.getNombre(), al.getTelefono(), al.getEmail(), al.getCiudad()});
    }

    // Mostrar la confirmación que programaste en tu clase
    JOptionPane.showMessageDialog(this, mensajeResultado, "Acción Deshecha", JOptionPane.INFORMATION_MESSAGE);
});

        //BARRA INFERIOR
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setBackground(Color.WHITE);
        JButton btnSalir = new JButton("Salir");
        btnSalir.setPreferredSize(new Dimension(120, 35));
        btnSalir.addActionListener(e -> {
            this.dispose();

            PantallaInicioSesion login = new PantallaInicioSesion(arbolEstudiantes, diccionarioCursos, colaSolicitudes, manejadorAcciones);
            login.setVisible(true);
        });

        panelBotones.add(btnDeshacer);
        panelBotones.add(btnSalir);
        add(panelBotones, BorderLayout.SOUTH);

        model.setRowCount(0);

        // Usamos el método del árbol para volcar todos los alumnos en un arreglo dinámico
        Estructuras.ArregloDinamico<Clases.Alumno> listaTemporal = new Estructuras.ArregloDinamico<>();
        arbolEstudiantes.obtenerTodos(listaTemporal);

        // Pasamos los datos del arreglo dinámico a las filas de la tabla
        for (int i = 0; i < listaTemporal.size(); i++) {
            Clases.Alumno al = listaTemporal.get(i);
            model.addRow(new Object[]{
                al.getMatricula(),
                al.getNombre(),
                al.getTelefono(),
                al.getEmail(),
                al.getCiudad()
            });
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

    /**
     * Pestañas superiores: si no están activas, cambian a Azul Oscuro al
     * pasar/presionar el mouse.
     */
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

    /**
     * Estiliza los botones del cuerpo (Buscar, Registrar) con feedbacks
     * dinámicos a azul oscuro.
     */
    private void estilizarBotonAccion(JButton btn, Color fondoOriginal, Color textoOriginal) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setBackground(fondoOriginal);
        btn.setForeground(textoOriginal);
        btn.setBorder(new LineBorder(CIAN_PRINCIPAL, 1, true));

        // Listener dinámico para el comportamiento de presión
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                btn.setBackground(CIAN_OSCURO); // Cambia a azul oscuro inmediatamente al presionar
                btn.setForeground(Color.WHITE);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                btn.setBackground(fondoOriginal); // Regresa a su estado base al soltar
                btn.setForeground(textoOriginal);
            }
        });
    }

}
