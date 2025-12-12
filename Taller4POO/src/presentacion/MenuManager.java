package presentacion;

//src/presentation/MenuManager.java

import dominio.*;
import java.util.Scanner;

public class MenuManager {
 private Scanner scanner;
 private AutenticacionService authService;
 private SistemaCertificaciones sistema;
 private AdminService adminService;
 private CoordinadorService coordService;
 private EstudianteService estudianteService;
 
 public MenuManager() {
     this.scanner = new Scanner(System.in);
     this.authService = new AutenticacionService();
     this.sistema = SistemaCertificaciones.getInstance();
     this.adminService = new AdminService();
     this.coordService = new CoordinadorService();
     this.estudianteService = new EstudianteService();
 }
 
 // Los métodos de menús estarán en el COMMIT 3
 // Este es solo el esqueleto
 
 public void mostrarMenuAdmin() {
     System.out.println("=== MENÚ ADMINISTRADOR ===");
     // Implementación en COMMIT 3
 }
 
 public void mostrarMenuCoordinador(Coordinador coordinador) {
     System.out.println("=== MENÚ COORDINADOR ===");
     System.out.println("Área: " + coordinador.getArea());
     // Implementación en COMMIT 3
 }
 
 public void mostrarMenuEstudiante(Estudiante estudiante) {
     System.out.println("=== MENÚ ESTUDIANTE ===");
     System.out.println("Bienvenido: " + estudiante.getNombre());
     // Implementación en COMMIT 3
 }
 
 /**
  * Muestra un menú genérico con opciones numeradas
  * @param titulo Título del menú
  * @param opciones Array de opciones
  * @return Opción seleccionada (1..n)
  */
 protected int mostrarMenu(String titulo, String[] opciones) {
     System.out.println("\n=== " + titulo + " ===");
     for (int i = 0; i < opciones.length; i++) {
         System.out.println((i + 1) + ". " + opciones[i]);
     }
     System.out.println("0. Volver / Salir");
     System.out.print("\nSeleccione una opción: ");
     
     try {
         int opcion = Integer.parseInt(scanner.nextLine());
         if (opcion >= 0 && opcion <= opciones.length) {
             return opcion;
         } else {
             System.out.println("Opción inválida. Intente nuevamente.");
             return mostrarMenu(titulo, opciones);
         }
     } catch (NumberFormatException e) {
         System.out.println("Por favor, ingrese un número.");
         return mostrarMenu(titulo, opciones);
     }
 }
 
 /**
  * Pide confirmación al usuario
  * @param mensaje Mensaje a mostrar
  * @return true si confirma (S/s)
  */
 protected boolean confirmar(String mensaje) {
     System.out.print(mensaje + " (S/N): ");
     String respuesta = scanner.nextLine().trim().toUpperCase();
     return respuesta.equals("S");
 }
 
 /**
  * Muestra un mensaje y espera Enter para continuar
  */
 protected void pausa() {
     System.out.print("\nPresione Enter para continuar...");
     scanner.nextLine();
 }
}