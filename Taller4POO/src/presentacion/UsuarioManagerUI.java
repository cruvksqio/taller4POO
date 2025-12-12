package presentacion;

import dominio.*;
import java.io.*;
import java.util.*;

public class UsuarioManagerUI {
 private List<Usuario> usuarios;
 
 public void UsuarioManager() {
     usuarios = new ArrayList<>();
 }
 
 // Método para obtener todos los usuarios (agregar esto)
 public List<Usuario> getTodosUsuarios() {
	    return usuarios;
	}
 
 // Método para obtener estudiante por RUT
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
}