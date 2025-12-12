package presentacion;

import dominio.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SistemaCertificacionesGUI extends JFrame {

    private final SistemaCertificaciones sistema;
    private final AutenticacionService auth;
    private final AdminService admin;
    private final EstudianteService estudiante;
    private final CoordinadorService coordinador;

    private final CardLayout layout;
    private final JPanel contenedor;

    public SistemaCertificacionesGUI() {
        sistema = SistemaCertificaciones.getInstance();
        auth = new AutenticacionService();
        admin = new AdminService();
        estudiante = new EstudianteService();
        coordinador = new CoordinadorService();

        layout = new CardLayout();
        contenedor = new JPanel(layout);

        contenedor.add(new LoginPanel(), "LOGIN");
        contenedor.add(new AdminPanel(), "ADMIN");
        contenedor.add(new EstudiantePanel(), "ESTUDIANTE");
        contenedor.add(new CoordinadorPanel(), "COORDINADOR");

        setContentPane(contenedor);
        setTitle("Sistema de Certificaciones");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        layout.show(contenedor, "LOGIN");
    }

    private String captureStdOut(Runnable action) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        try {
            System.setOut(ps);
            action.run();
        } finally {
            System.out.flush();
            System.setOut(originalOut);
        }
        return baos.toString();
    }

    /* ===================== LOGIN ===================== */

    class LoginPanel extends JPanel {
        JTextField user;
        JPasswordField pass;
        JLabel estado;

        LoginPanel() {
            setLayout(new GridBagLayout());
            setBorder(new EmptyBorder(40, 40, 40, 40));

            GridBagConstraints g = new GridBagConstraints();
            g.insets = new Insets(5,5,5,5);

            user = new JTextField(20);
            pass = new JPasswordField(20);
            estado = new JLabel(" ");

            JButton btn = new JButton("Ingresar");
            btn.addActionListener(e -> login());

            g.gridx=0; g.gridy=0; add(new JLabel("Usuario / RUT"), g);
            g.gridx=1; add(user, g);
            g.gridx=0; g.gridy=1; add(new JLabel("Contraseña"), g);
            g.gridx=1; add(pass, g);
            g.gridy=2; g.gridwidth=2; add(btn, g);
            g.gridy=3; add(estado, g);
        }

        void login() {
            Usuario u = auth.autenticar(user.getText(), new String(pass.getPassword()));
            if (u == null) {
                estado.setText("Credenciales incorrectas");
                return;
            }

            switch (u.getRol()) {
                case "Administrador": layout.show(contenedor, "ADMIN"); break;
                case "Estudiante": layout.show(contenedor, "ESTUDIANTE"); break;
                case "Coordinador": layout.show(contenedor, "COORDINADOR"); break;
            }
        }
    }

    /* ===================== ADMIN ===================== */

    class AdminPanel extends JPanel {

        DefaultTableModel modelo;

        AdminPanel() {
            setLayout(new BorderLayout(10,10));
            setBorder(new EmptyBorder(10,10,10,10));

            modelo = new DefaultTableModel(new String[]{"Usuario","Rol"},0);
            JTable tabla = new JTable(modelo);

            JButton listar = new JButton("Listar Usuarios");
            listar.addActionListener(e -> cargarUsuarios());

            JButton salir = new JButton("Cerrar sesión");
            salir.addActionListener(e -> volverLogin());

            JPanel top = new JPanel();
            top.add(listar);
            top.add(salir);

            add(top, BorderLayout.NORTH);
            add(new JScrollPane(tabla), BorderLayout.CENTER);
        }

        void cargarUsuarios() {
            modelo.setRowCount(0);
            for (Usuario u : sistema.getUsuarioManager().getTodosUsuarios()) {
                modelo.addRow(new Object[]{u.getUsername(), u.getRol()});
            }
        }
    }

    /* ===================== ESTUDIANTE ===================== */

    class EstudiantePanel extends JPanel {

        JTextArea area;

        EstudiantePanel() {
            setLayout(new BorderLayout(10,10));
            setBorder(new EmptyBorder(10,10,10,10));

            area = new JTextArea();
            area.setEditable(false);

            JButton perfil = new JButton("Mi Perfil");
            perfil.addActionListener(e -> mostrarPerfil());

            JButton malla = new JButton("Malla Curricular");
            malla.addActionListener(e -> mostrarMalla());

            JButton salir = new JButton("Cerrar sesión");
            salir.addActionListener(e -> volverLogin());

            JPanel top = new JPanel();
            top.add(perfil);
            top.add(malla);
            top.add(salir);

            add(top, BorderLayout.NORTH);
            add(new JScrollPane(area), BorderLayout.CENTER);
        }

        void mostrarPerfil() {
            Estudiante e = (Estudiante) auth.getUsuarioActual();
            area.setText("Nombre: " + e.getNombre() +
                    "\nCarrera: " + e.getCarrera() +
                    "\nSemestre: " + e.getSemestre());
        }

        void mostrarMalla() {
            Estudiante e = (Estudiante) auth.getUsuarioActual();
            String out = captureStdOut(() -> estudiante.mostrarMallaCurricular(e));
            area.setText(out.isBlank() ? "(No hay salida para mostrar)" : out);
            area.setCaretPosition(0);
        }
    }

    /* ===================== COORDINADOR ===================== */

    class CoordinadorPanel extends JPanel {

        DefaultTableModel modelo;

        CoordinadorPanel() {
            setLayout(new BorderLayout(10,10));
            setBorder(new EmptyBorder(10,10,10,10));

            modelo = new DefaultTableModel(
                    new String[]{"Certificación", "Activos", "Completados", "Suspendidos", "Total"}, 0);

            JButton stats = new JButton("Ver estadísticas");
            stats.addActionListener(e -> cargarStats());

            JButton salir = new JButton("Cerrar sesión");
            salir.addActionListener(e -> volverLogin());

            JPanel top = new JPanel();
            top.add(stats);
            top.add(salir);

            add(top, BorderLayout.NORTH);
            add(new JScrollPane(tabla), BorderLayout.CENTER);
        }

        void cargarStats() {
            modelo.setRowCount(0);

            RegistroManager rm = sistema.getRegistroManager();

            for (Certificacion c : sistema.getCertificacionManager().getCertificacionesDisponibles()) {

                List<RegistroCertificacion> regs = rm.getRegistrosPorCertificacion(c.getId());

                int activos = 0, completados = 0, suspendidos = 0;

                for (RegistroCertificacion r : regs) {
                    String estado = (r.getEstado() == null) ? "" : r.getEstado().trim().toLowerCase();

                    // tolerante a variantes
                    if (estado.equals("activo")) {
                        activos++;
                    } else if (estado.equals("completado") || estado.equals("completada") || estado.equals("completo")) {
                        completados++;
                    } else if (estado.equals("suspendido") || estado.equals("suspendida")) {
                        suspendidos++;
                    }
                }

                int total = regs.size();

                modelo.addRow(new Object[]{
                        c.getNombre(),
                        activos,
                        completados,
                        suspendidos,
                        total
                });
            }
        }
    }

    /* ===================== UTIL ===================== */

    void volverLogin() {
        auth.cerrarSesion();
        layout.show(contenedor, "LOGIN");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SistemaCertificacionesGUI().setVisible(true));
    }
}
