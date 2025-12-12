package logica;

/* Constantino Demostenes Bekios Canales - 21761616-6 - cruvksqio
Fernando Lagos Barahona - 22060830-1 - elthefa */



import dominio.*;
import java.io.*;
import java.util.*;

public class UsuarioManager {
 private List<Usuario> usuarios; // List en vez de Map
 
 public UsuarioManager() {
     usuarios = new ArrayList<>();
 }
 
 public void cargarUsuarios(String rutaArchivo) {
     try {
         BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
         String linea;
         while ((linea = br.readLine()) != null) {
             String[] datos = linea.split(";");
             if (datos.length >= 3) {
                 String tipo = datos[2].toUpperCase();
                 Usuario usuario = UsuarioFactory.crearUsuario(tipo, datos);
                 usuarios.add(usuario);
             }
         }
         br.close();
     } catch (IOException e) {
         System.err.println("Error al cargar usuarios: " + e.getMessage());
     }
 }
 
 public void cargarEstudiantes(String rutaArchivo) {
     try {
         BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
         String linea;
         while ((linea = br.readLine()) != null) {
             String[] datos = linea.split(";");
             if (datos.length >= 6) {
                 Estudiante estudiante = new Estudiante(
                     datos[0], datos[1], datos[2], 
                     Integer.parseInt(datos[3]), datos[4], datos[5]
                 );
                 usuarios.add(estudiante);
             }
         }
         br.close();
     } catch (IOException e) {
         System.err.println("Error al cargar estudiantes: " + e.getMessage());
     }
 }
 
 public Usuario autenticar(String username, String password) {
     for (Usuario usuario : usuarios) {
         if (usuario.getUsername().equals(username) && 
             usuario.getPassword().equals(password)) {
             return usuario;
         }
     }
     return null;
 }
 
 public List<Estudiante> getEstudiantesPorArea(String area) {
     List<Estudiante> estudiantesArea = new ArrayList<>();
     for (Usuario usuario : usuarios) {
         if (usuario instanceof Estudiante) {
             estudiantesArea.add((Estudiante) usuario);
         }
     }
     return estudiantesArea;
 }
 
 public Estudiante getEstudiantePorRut(String rut) {
     for (Usuario usuario : usuarios) {
         if (usuario instanceof Estudiante) {
             Estudiante est = (Estudiante) usuario;
             if (est.getRut().equals(rut)) {
                 return est;
             }
         }
     }
     return null;
 }
 
 public boolean agregarUsuario(Usuario usuario) {
     // Verificar si ya existe
     for (Usuario u : usuarios) {
         if (u.getUsername().equals(usuario.getUsername())) {
             return false;
         }
     }
     usuarios.add(usuario);
     guardarUsuarios();
     return true;
 }
 
 public boolean eliminarUsuario(String username) {
     for (int i = 0; i < usuarios.size(); i++) {
         if (usuarios.get(i).getUsername().equals(username)) {
             usuarios.remove(i);
             guardarUsuarios();
             return true;
         }
     }
     return false;
 }
 
 public boolean modificarUsuario(String username, String nuevoPassword) {
     for (Usuario usuario : usuarios) {
         if (usuario.getUsername().equals(username)) {
             usuario.setPassword(nuevoPassword);
             guardarUsuarios();
             return true;
         }
     }
     return false;
 }
 
 private void guardarUsuarios() {
     // Implementar si es necesario
 }
}