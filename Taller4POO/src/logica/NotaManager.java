package logica;


import dominio.*;
import java.io.*;
import java.util.*;

public class NotaManager {
 private List<Nota> todasLasNotas; // List en vez de Map
 
 public NotaManager() {
     todasLasNotas = new ArrayList<>();
 }
 
 public void cargarNotas(String rutaArchivo) {
     try {
         BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
         String linea;
         while ((linea = br.readLine()) != null) {
             String[] datos = linea.split(";");
             if (datos.length >= 5) {
                 Nota nota = new Nota(
                     datos[0], datos[1], 
                     Double.parseDouble(datos[2]), datos[3], datos[4]
                 );
                 todasLasNotas.add(nota);
             }
         }
         br.close();
     } catch (IOException e) {
         System.err.println("Error al cargar notas: " + e.getMessage());
     }
 }
 
 public List<Nota> getNotasPorEstudiante(String rut) {
     List<Nota> notasEstudiante = new ArrayList<>();
     for (Nota nota : todasLasNotas) {
         if (nota.getRutEstudiante().equals(rut)) {
             notasEstudiante.add(nota);
         }
     }
     return notasEstudiante;
 }
 
 public double calcularPromedioGeneral(String rut) {
     List<Nota> notas = getNotasPorEstudiante(rut);
     if (notas.isEmpty()) return 0.0;
     
     double suma = 0;
     int count = 0;
     for (Nota nota : notas) {
         if (nota.getEstado().equals("Aprobada")) {
             suma += nota.getCalificacion();
             count++;
         }
     }
     return count > 0 ? suma / count : 0.0;
 }
 
 // Método simplificado para promedios por semestre
 public void mostrarPromediosPorSemestre(String rut) {
     List<Nota> notas = getNotasPorEstudiante(rut);
     
     // Arreglo para sumar por semestre (semestres 1-8)
     double[] sumas = new double[9]; // índice 0 no usado
     int[] conteos = new int[9];
     
     for (Nota nota : notas) {
         if (nota.getEstado().equals("Aprobada")) {
             // Extraer número de semestre (ej: "2024-1" -> semestre 1)
             String semestreStr = nota.getSemestre();
             int guionIndex = semestreStr.lastIndexOf('-');
             if (guionIndex != -1) {
                 try {
                     int semestreNum = Integer.parseInt(semestreStr.substring(guionIndex + 1));
                     if (semestreNum >= 1 && semestreNum <= 8) {
                         sumas[semestreNum] += nota.getCalificacion();
                         conteos[semestreNum]++;
                     }
                 } catch (NumberFormatException e) {
                     // Ignorar si no es número
                 }
             }
         }
     }
     
     System.out.println("Promedios por semestre:");
     for (int i = 1; i <= 8; i++) {
         if (conteos[i] > 0) {
             System.out.printf("Semestre %d: %.2f%n", i, sumas[i] / conteos[i]);
         }
     }
 }
}