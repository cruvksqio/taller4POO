package presentacion;


import dominio.*;
import java.util.Scanner;

public class LoginConsole {
 private SistemaCertificaciones sistema;
 private Scanner scanner;
 
 public LoginConsole() {
     this.sistema = SistemaCertificaciones.getInstance();
     this.scanner = new Scanner(System.in);
 }
 
 public void mostrarLogin() {
     System.out.println("=======================================");
     System.out.println("   SISTEMA DE CERTIFICACIONES ACADÉMICAS");
     System.out.println("=======================================");
     
     int intentos = 0;
     
     while (intentos < 3) {
         System.out.println("\n--- INICIO DE SESIÓN ---");
         System.out.print("Usuario/RUT: ");
         String username = scanner.nextLine().trim();
         
         System.out.print("Contraseña: ");
         String password = scanner.nextLine().trim();
         
         Usuario usuario = sistema.autenticar(username, password);
         
         if (usuario != null) {
             sistema.setUsuarioActual(usuario);
             System.out.println("\n¡Bienvenido " + usuario.getUsername() + "!");
             
             // Redirigir según rol
             if (usuario.getRol().equals("Administrador")) {
                 mostrarMenuAdmin();
             } else if (usuario.getRol().equals("Coordinador")) {
                 mostrarMenuCoordinador((Coordinador) usuario);
             } else if (usuario.getRol().equals("Estudiante")) {
                 mostrarMenuEstudiante((Estudiante) usuario);
             }
             return;
         } else {
             intentos++;
             System.out.println("Credenciales incorrectas. Intento " + intentos + " de 3");
         }
     }
     
     System.out.println("\nDemasiados intentos fallidos. Saliendo...");
 }
 
 // Métodos de menús básicos (solo estructura)
 private void mostrarMenuAdmin() {
     System.out.println("\n=== MENÚ ADMINISTRADOR ===");
     System.out.println("1. Crear cuenta de estudiante");
     System.out.println("2. Crear cuenta de coordinador");
     System.out.println("3. Modificar cuenta");
     System.out.println("4. Eliminar cuenta");
     System.out.println("5. Restablecer contraseña");
     System.out.println("0. Cerrar sesión");
     
     System.out.print("\nSeleccione opción: ");
     int opcion = Integer.parseInt(scanner.nextLine());
     
     // Tu compañero implementará la GUI aquí
     System.out.println("Opción seleccionada: " + opcion);
 }
 
 private void mostrarMenuCoordinador(Coordinador coordinador) {
     System.out.println("\n=== MENÚ COORDINADOR ===");
     System.out.println("Área: " + coordinador.getArea());
     System.out.println("1. Gestión de Certificaciones");
     System.out.println("2. Panel de Métricas");
     System.out.println("3. Gestión de Estudiantes");
     System.out.println("0. Cerrar sesión");
     
     System.out.print("\nSeleccione opción: ");
     int opcion = Integer.parseInt(scanner.nextLine());
     
     System.out.println("Opción seleccionada: " + opcion);
 }
 
 private void mostrarMenuEstudiante(Estudiante estudiante) {
     System.out.println("\n=== MENÚ ESTUDIANTE ===");
     System.out.println("Bienvenido: " + estudiante.getNombre());
     System.out.println("1. Ver perfil");
     System.out.println("2. Ver malla curricular");
     System.out.println("3. Ver promedios");
     System.out.println("4. Inscribirse en certificación");
     System.out.println("5. Ver progreso");
     System.out.println("0. Cerrar sesión");
     
     System.out.print("\nSeleccione opción: ");
     int opcion = Integer.parseInt(scanner.nextLine());
     
     System.out.println("Opción seleccionada: " + opcion);
 }
 
 public static void main(String[] args) {
     LoginConsole sistema = new LoginConsole();
     sistema.mostrarLogin();
 }
}