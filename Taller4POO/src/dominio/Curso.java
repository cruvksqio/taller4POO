package dominio;

import java.util.List;
import java.util.ArrayList;

public class Curso {
	 private String nrc;
	 private String nombre;
	 private int semestre;
	 private int creditos;
	 private String area;
	 private List<String> prerequisitos;
	 
	 public Curso(String nrc, String nombre, int semestre, int creditos, 
	             String area, String prerequisitosStr) {
	     this.nrc = nrc;
	     this.nombre = nombre;
	     this.semestre = semestre;
	     this.creditos = creditos;
	     this.area = area;
	     this.prerequisitos = new ArrayList<>();
	     
	     if (prerequisitosStr != null && !prerequisitosStr.trim().isEmpty()) {
	         String[] pre = prerequisitosStr.split(",");
	         for (String p : pre) {
	             this.prerequisitos.add(p.trim());
	         }
	     }
	 }
	 
	 // Getters
	 public String getNrc() { 
		 return nrc; 
		 }
	 public String getNombre() { 
		 return nombre; 
		 }
	 public int getSemestre() { 
		 return semestre; 
		 }
	 public int getCreditos() { 
		 return creditos; 
		 }
	 public String getArea() { 
		 return area; 
		 }
	 public List<String> getPrerequisitos() {
		 return prerequisitos; 
		 }
	}