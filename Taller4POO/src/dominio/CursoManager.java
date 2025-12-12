package dominio;

import java.io.*;
import java.util.*;

public class CursoManager {
 private List<Curso> cursos; // List en vez de Map
 
 public CursoManager() {
     cursos = new ArrayList<>();
 }
 
 public void cargarCursos(String rutaArchivo) {
     try {
         BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
         String linea;
         while ((linea = br.readLine()) != null) {
             String[] datos = linea.split(";");
             String prerequisitos = datos.length > 5 ? datos[5] : "";
             Curso curso = new Curso(datos[0], datos[1], 
                 Integer.parseInt(datos[2]), Integer.parseInt(datos[3]), 
                 datos[4], prerequisitos);
             cursos.add(curso);
         }
         br.close();
     } catch (IOException e) {
         System.err.println("Error al cargar cursos: " + e.getMessage());
     }
 }
 
 public Curso getCurso(String nrc) {
     for (Curso curso : cursos) {
         if (curso.getNrc().equals(nrc)) {
             return curso;
         }
     }
     return null;
 }
 
 public List<Curso> getCursosPorSemestre(int semestre) {
     List<Curso> cursosSemestre = new ArrayList<>();
     for (Curso curso : cursos) {
         if (curso.getSemestre() == semestre) {
             cursosSemestre.add(curso);
         }
     }
     return cursosSemestre;
 }
 
 public List<Curso> getCursosPorArea(String area) {
     List<Curso> cursosArea = new ArrayList<>();
     for (Curso curso : cursos) {
         if (curso.getArea().equalsIgnoreCase(area)) {
             cursosArea.add(curso);
         }
     }
     return cursosArea;
 }
}
