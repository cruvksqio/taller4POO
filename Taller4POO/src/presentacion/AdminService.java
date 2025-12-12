package presentacion;


import dominio.*;
import java.util.List;

public class AdminService {
 private SistemaCertificaciones sistema;
 
 public AdminService() {
     this.sistema = SistemaCertificaciones.getInstance();
 }
 
 // Método para crear estudiante
 public String crearEstudiante(String rut, String nombre, String carrera, 
                               int semestre, String email, String password) {
     UsuarioManager usuarioManager = sistema.getUsuarioManager();
     
     // Verificar si ya existe
     if (usuarioManager.getEstudiantePorRut(rut) != null) {
         return "Error: Ya existe un estudiante con ese RUT";
     }
     
     Estudiante nuevoEst = new Estudiante(rut, nombre, carrera, semestre, email, password);
     if (usuarioManager.agregarUsuario(nuevoEst)) {
         return "Estudiante creado exitosamente";
     }
     return "Error al crear estudiante";
 }
 
 // Método para crear coordinador
 public String crearCoordinador(String username, String password, String area) {
     UsuarioManager usuarioManager = sistema.getUsuarioManager();
     
     // Verificar si ya existe
     for (Usuario usuario : usuarioManager.getTodosUsuarios()) {
         if (usuario.getUsername().equals(username)) {
             return "Error: Ya existe un usuario con ese username";
         }
     }
     
     Coordinador nuevoCoord = new Coordinador(username, password, area);
     if (usuarioManager.agregarUsuario(nuevoCoord)) {
         return "Coordinador creado exitosamente";
     }
     return "Error al crear coordinador";
 }
 
 // Método para listar todos los usuarios
 public void listarTodosUsuarios() {
     UsuarioManager usuarioManager = sistema.getUsuarioManager();
     List<Usuario> usuarios = usuarioManager.getTodosUsuarios();
     
     System.out.println("=== LISTADO DE USUARIOS ===");
     System.out.println("Total: " + usuarios.size() + " usuarios\n");
     
     for (Usuario usuario : usuarios) {
         System.out.println("Username/RUT: " + usuario.getUsername());
         System.out.println("Rol: " + usuario.getRol());
         
         if (usuario instanceof Estudiante) {
             Estudiante est = (Estudiante) usuario;
             System.out.println("Nombre: " + est.getNombre());
             System.out.println("Carrera: " + est.getCarrera());
             System.out.println("Semestre: " + est.getSemestre());
         } else if (usuario instanceof Coordinador) {
             Coordinador coord = (Coordinador) usuario;
             System.out.println("Área: " + coord.getArea());
         }
         
         System.out.println("------------------------");
     }
 }
 
 // Método para eliminar usuario
 public String eliminarUsuario(String username) {
     UsuarioManager usuarioManager = sistema.getUsuarioManager();
     
     if (usuarioManager.eliminarUsuario(username)) {
         return "Usuario eliminado exitosamente";
     }
     return "Error: Usuario no encontrado";
 }
 
 // Método para restablecer contraseña
 public String restablecerPassword(String username, String nuevaPassword) {
     UsuarioManager usuarioManager = sistema.getUsuarioManager();
     
     if (usuarioManager.modificarUsuario(username, nuevaPassword)) {
         return "Contraseña restablecida exitosamente";
     }
     return "Error: Usuario no encontrado";
 }
}