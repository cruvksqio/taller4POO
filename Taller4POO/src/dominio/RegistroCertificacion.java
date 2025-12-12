package dominio;


public class RegistroCertificacion {
 private String rutEstudiante;
 private String idCertificacion;
 private String fechaRegistro; // String en vez de LocalDate
 private String estado;
 private int progreso;
 
 public RegistroCertificacion(String rutEstudiante, String idCertificacion, 
                             String fechaStr, String estado, int progreso) {
     this.rutEstudiante = rutEstudiante;
     this.idCertificacion = idCertificacion;
     this.fechaRegistro = fechaStr; // Guardamos como String
     this.estado = estado;
     this.progreso = progreso;
 }
 
 // Getters y setters
 public String getRutEstudiante() { return rutEstudiante; }
 public String getIdCertificacion() { return idCertificacion; }
 public String getFechaRegistro() { return fechaRegistro; }
 public String getEstado() { return estado; }
 public int getProgreso() { return progreso; }
 
 public void setEstado(String estado) { this.estado = estado; }
 public void setProgreso(int progreso) { this.progreso = progreso; }
}