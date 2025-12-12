package presentacion;


import dominio.*;
import java.util.*;

public class EstudianteService {
 private SistemaCertificaciones sistema;
 
 public EstudianteService() {
     this.sistema = SistemaCertificaciones.getInstance();
 }
 
 // Método para verificar prerrequisitos simple
 public boolean puedeInscribirseEnCertificacion(Estudiante estudiante, String idCertificacion) {
     CertificacionManager certManager = sistema.getCertificacionManager();
     NotaManager notaManager = sistema.getNotaManager();
     
     // Obtener asignaturas requeridas
     List<String> asignaturasRequeridas = certManager.getAsignaturasPorCertificacion(idCertificacion);
     List<Nota> notasEstudiante = notaManager.getNotasPorEstudiante(estudiante.getRut());
     
     // Contar cuántas asignaturas requeridas tiene aprobadas
     int aprobadas = 0;
     for (String nrc : asignaturasRequeridas) {
         for (Nota nota : notasEstudiante) {
             if (nota.getCodigoCurso().equals(nrc) && nota.getEstado().equals("Aprobada")) {
                 aprobadas++;
                 break;
             }
         }
     }
     
     // Requiere al menos el 30% de las asignaturas aprobadas
     return aprobadas >= (asignaturasRequeridas.size() * 0.3);
 }
 
 // Método para calcular progreso simple
 public int calcularProgresoCertificacion(Estudiante estudiante, String idCertificacion) {
     CertificacionManager certManager = sistema.getCertificacionManager();
     NotaManager notaManager = sistema.getNotaManager();
     
     List<String> asignaturasRequeridas = certManager.getAsignaturasPorCertificacion(idCertificacion);
     List<Nota> notasEstudiante = notaManager.getNotasPorEstudiante(estudiante.getRut());
     
     if (asignaturasRequeridas.isEmpty()) return 0;
     
     int aprobadas = 0;
     for (String nrc : asignaturasRequeridas) {
         for (Nota nota : notasEstudiante) {
             if (nota.getCodigoCurso().equals(nrc) && nota.getEstado().equals("Aprobada")) {
                 aprobadas++;
                 break;
             }
         }
     }
     
     // Calcular porcentaje
     return (aprobadas * 100) / asignaturasRequeridas.size();
 }
 
 // Método para obtener asignaturas pendientes
 public List<String> getAsignaturasPendientes(Estudiante estudiante, String idCertificacion) {
     CertificacionManager certManager = sistema.getCertificacionManager();
     NotaManager notaManager = sistema.getNotaManager();
     CursoManager cursoManager = sistema.getCursoManager();
     
     List<String> pendientes = new ArrayList<>();
     List<String> asignaturasRequeridas = certManager.getAsignaturasPorCertificacion(idCertificacion);
     List<Nota> notasEstudiante = notaManager.getNotasPorEstudiante(estudiante.getRut());
     
     for (String nrc : asignaturasRequeridas) {
         boolean aprobada = false;
         for (Nota nota : notasEstudiante) {
             if (nota.getCodigoCurso().equals(nrc) && nota.getEstado().equals("Aprobada")) {
                 aprobada = true;
                 break;
             }
         }
         if (!aprobada) {
             Curso curso = cursoManager.getCurso(nrc);
             if (curso != null) {
                 pendientes.add(curso.getNombre() + " (" + nrc + ")");
             }
         }
     }
     
     return pendientes;
 }
 
 // Método para mostrar malla curricular simple
 public void mostrarMallaCurricular(Estudiante estudiante) {
     NotaManager notaManager = sistema.getNotaManager();
     CursoManager cursoManager = sistema.getCursoManager();
     
     List<Nota> notasEstudiante = notaManager.getNotasPorEstudiante(estudiante.getRut());
     
     System.out.println("=== MALLA CURRICULAR DE " + estudiante.getNombre() + " ===");
     
     // Mostrar por semestre del 1 al 8
     for (int semestre = 1; semestre <= 8; semestre++) {
         System.out.println("\n--- SEMESTRE " + semestre + " ---");
         List<Curso> cursosSemestre = cursoManager.getCursosPorSemestre(semestre);
         
         for (Curso curso : cursosSemestre) {
             String estado = "Pendiente";
             double nota = 0.0;
             
             // Buscar si el estudiante tiene nota para este curso
             for (Nota n : notasEstudiante) {
                 if (n.getCodigoCurso().equals(curso.getNrc())) {
                     estado = n.getEstado();
                     nota = n.getCalificacion();
                     break;
                 }
             }
             
             System.out.println(curso.getNrc() + " - " + curso.getNombre() + 
                              " | Créditos: " + curso.getCreditos() + 
                              " | Estado: " + estado + 
                              (nota > 0 ? " | Nota: " + nota : ""));
         }
     }
 }
 
 // Método para mostrar información básica del estudiante
 public void mostrarInformacionEstudiante(Estudiante estudiante) {
     NotaManager notaManager = sistema.getNotaManager();
     
     System.out.println("=== INFORMACIÓN DEL ESTUDIANTE ===");
     System.out.println("RUT: " + estudiante.getRut());
     System.out.println("Nombre: " + estudiante.getNombre());
     System.out.println("Carrera: " + estudiante.getCarrera());
     System.out.println("Semestre actual: " + estudiante.getSemestre());
     System.out.println("Email: " + estudiante.getEmail());
     
     double promedio = notaManager.calcularPromedioGeneral(estudiante.getRut());
     System.out.println("Promedio general: " + String.format("%.2f", promedio));
 }
}