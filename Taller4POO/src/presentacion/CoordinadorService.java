package presentacion;


import dominio.*;
import java.util.*;

public class CoordinadorService {
 private SistemaCertificaciones sistema;
 
 public CoordinadorService() {
     this.sistema = SistemaCertificaciones.getInstance();
 }
 
 // Método para mostrar estadísticas de certificación
 public void mostrarEstadisticasCertificacion(String idCertificacion) {
     RegistroManager registroManager = sistema.getRegistroManager();
     List<RegistroCertificacion> registros = registroManager.getRegistrosPorCertificacion(idCertificacion);
     
     int activos = 0, completados = 0, suspendidos = 0;
     
     for (RegistroCertificacion reg : registros) {
         if (reg.getEstado().equals("Activo")) {
             activos++;
         } else if (reg.getEstado().equals("Completado")) {
             completados++;
         } else if (reg.getEstado().equals("Suspendido")) {
             suspendidos++;
         }
     }
     
     System.out.println("=== ESTADÍSTICAS CERTIFICACIÓN " + idCertificacion + " ===");
     System.out.println("Estudiantes activos: " + activos);
     System.out.println("Estudiantes completados: " + completados);
     System.out.println("Estudiantes suspendidos: " + suspendidos);
     System.out.println("Total inscritos: " + registros.size());
 }
 
 // Método para analizar asignaturas críticas
 public void analizarAsignaturasCriticas(String idCertificacion) {
     CertificacionManager certManager = sistema.getCertificacionManager();
     NotaManager notaManager = sistema.getNotaManager();
     UsuarioManager usuarioManager = sistema.getUsuarioManager();
     
     List<String> asignaturas = certManager.getAsignaturasPorCertificacion(idCertificacion);
     List<Usuario> todosUsuarios = usuarioManager.getTodosUsuarios();
     
     System.out.println("=== ANÁLISIS DE ASIGNATURAS CRÍTICAS ===");
     System.out.println("Certificación: " + idCertificacion);
     
     for (String nrc : asignaturas) {
         int totalEstudiantes = 0;
         int reprobados = 0;
         
         // Contar cuántos estudiantes han cursado y reprobado esta asignatura
         for (Usuario usuario : todosUsuarios) {
             if (usuario instanceof Estudiante) {
                 Estudiante est = (Estudiante) usuario;
                 List<Nota> notasEst = notaManager.getNotasPorEstudiante(est.getRut());
                 
                 for (Nota nota : notasEst) {
                     if (nota.getCodigoCurso().equals(nrc)) {
                         totalEstudiantes++;
                         if (nota.getEstado().equals("Reprobada")) {
                             reprobados++;
                         }
                         break;
                     }
                 }
             }
         }
         
         if (totalEstudiantes > 0) {
             double porcentajeReprobacion = (reprobados * 100.0) / totalEstudiantes;
             System.out.println("Asignatura " + nrc + ": " + 
                              reprobados + "/" + totalEstudiantes + 
                              " reprobados (" + String.format("%.1f", porcentajeReprobacion) + "%)");
             
             if (porcentajeReprobacion > 30) {
                 System.out.println("  ¡ASIGNATURA CRÍTICA! (Más del 30% de reprobación)");
             }
         }
     }
 }
 
 // Método para validar avances de estudiantes
 public void validarAvancesEstudiantes(String idCertificacion) {
     RegistroManager registroManager = sistema.getRegistroManager();
     EstudianteService estService = new EstudianteService();
     
     List<RegistroCertificacion> registros = registroManager.getRegistrosPorCertificacion(idCertificacion);
     UsuarioManager usuarioManager = sistema.getUsuarioManager();
     
     System.out.println("=== VALIDACIÓN DE AVANCES ===");
     
     for (RegistroCertificacion reg : registros) {
         if (reg.getEstado().equals("Activo")) {
             Estudiante estudiante = usuarioManager.getEstudiantePorRut(reg.getRutEstudiante());
             if (estudiante != null) {
                 int progresoCalculado = estService.calcularProgresoCertificacion(estudiante, idCertificacion);
                 
                 System.out.println("Estudiante: " + estudiante.getNombre() + 
                                  " | Progreso actual: " + reg.getProgreso() + "%" +
                                  " | Progreso calculado: " + progresoCalculado + "%");
                 
                 if (progresoCalculado >= 100) {
                     System.out.println("  ¡ESTUDIANTE LISTO PARA CERTIFICAR!");
                 } else if (progresoCalculado < 30) {
                     System.out.println("  Atención: Progreso bajo");
                 }
             }
         }
     }
 }
}