package dominio;

public class Certificacion {
 private String id;
 private String nombre;
 private String descripcion;
 private int creditosRequeridos;
 private int validezAnios;
 
 public Certificacion(String id, String nombre, String descripcion, 
                     int creditosRequeridos, int validezAnios) {
     this.id = id;
     this.nombre = nombre;
     this.descripcion = descripcion;
     this.creditosRequeridos = creditosRequeridos;
     this.validezAnios = validezAnios;
 }
 
 // Getters
 public String getId() {
	 return id; }
 public String getNombre() {
	 return nombre; }
 public String getDescripcion() { 
	 return descripcion; }
 public int getCreditosRequeridos() {
	 return creditosRequeridos; }
 public int getValidezAnios() {
	 return validezAnios; }
}