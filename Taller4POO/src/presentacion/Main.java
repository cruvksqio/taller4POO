package presentacion;

import javax.swing.SwingUtilities;
import dominio.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new SistemaCertificacionesGUI().setVisible(true)
        );
    }
}