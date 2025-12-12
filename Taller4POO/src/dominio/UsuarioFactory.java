package dominio;

public class UsuarioFactory {
 public static Usuario crearUsuario(String tipo, String[] datos) {
     switch (tipo.toUpperCase()) {
         case "ADMIN":
             return new Administrador(datos[0], datos[1]);
         case "COORDINADOR":
             return new Coordinador(datos[0], datos[1], datos[3]);
         case "ESTUDIANTE":
             return new Estudiante(
                 datos[0], // rut
                 datos[1], // nombre
                 datos[2], // carrera
                 Integer.parseInt(datos[3]), // semestre
                 datos[4], // email
                 datos[5]  // password
             );
         default:
             throw new IllegalArgumentException("Tipo de usuario no válido: " + tipo);
     }
 }
}