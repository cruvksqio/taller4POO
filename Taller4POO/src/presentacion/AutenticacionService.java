package presentacion;

import dominio.*;

public class AutenticacionService {
 private SistemaCertificaciones sistema;
 
 public AutenticacionService() {
     this.sistema = SistemaCertificaciones.getInstance();
 }
 
 /**
  * Autentica un usuario en el sistema
  * @param username Nombre de usuario o RUT
  * @param password Contraseña
  * @return Usuario autenticado o null si falla
  */
 public Usuario autenticar(String username, String password) {
     if (username == null || username.trim().isEmpty() || 
         password == null || password.trim().isEmpty()) {
         throw new IllegalArgumentException("Usuario y contraseña son requeridos");
     }
     
     Usuario usuario = sistema.autenticar(username, password);
     if (usuario != null) {
         sistema.setUsuarioActual(usuario);
         System.out.println("Login exitoso. Rol: " + usuario.getRol());
     } else {
         System.out.println("Credenciales incorrectas");
     }
     
     return usuario;
 }
 
 /**
  * Cierra la sesión del usuario actual
  */
 public void cerrarSesion() {
     sistema.setUsuarioActual(null);
     System.out.println("Sesión cerrada exitosamente");
 }
 
 /**
  * Obtiene el usuario actualmente autenticado
  * @return Usuario actual o null si no hay sesión
  */
 public Usuario getUsuarioActual() {
     return sistema.getUsuarioActual();
 }
 
 /**
  * Verifica si hay un usuario autenticado
  * @return true si hay sesión activa
  */
 public boolean haySesionActiva() {
     return sistema.getUsuarioActual() != null;
 }
 
 /**
  * Verifica el rol del usuario actual
  * @param rol Rol a verificar
  * @return true si el usuario tiene ese rol
  */
 public boolean verificarRol(String rol) {
     Usuario actual = getUsuarioActual();
     return actual != null && actual.getRol().equalsIgnoreCase(rol);
 }
}