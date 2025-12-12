package dominio;

public class Nota {
 private String rutEstudiante;
 private String codigoCurso;
 private double calificacion;
 private String estado; // Aprobada, Reprobada, Cursando
 private String semestre;
 
 public Nota(String rutEstudiante, String codigoCurso, double calificacion, 
            String estado, String semestre) {
     this.rutEstudiante = rutEstudiante;
     this.codigoCurso = codigoCurso;
     this.calificacion = calificacion;
     this.estado = estado;
     this.semestre = semestre;
 }
 
 // Getters
 public String getRutEstudiante() { 
	 return rutEstudiante; }
 public String getCodigoCurso() { 
	 return codigoCurso; }
 public double getCalificacion() { 
	 return calificacion; }
 public String getEstado() { 
	 return estado; }
 public String getSemestre() { 
	 return semestre; }
}