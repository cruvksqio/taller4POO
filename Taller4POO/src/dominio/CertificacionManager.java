package logica;

import dominio.*;
import java.io.*;
import java.util.*;

public class CertificacionManager {
 private Map<String, Certificacion> certificaciones;
 private Map<String, List<String>> asignaturasPorCertificacion;
 
 public CertificacionManager() {
     certificaciones = new HashMap<>();
     asignaturasPorCertificacion = new HashMap<>();
 }
 
 public void cargarCertificaciones(String rutaArchivo) {
     try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
         String linea;
         while ((linea = br.readLine()) != null) {
             String[] datos = linea.split(";");
             if (datos.length >= 5) {
                 Certificacion cert = new Certificacion(
                     datos[0], datos[1], datos[2],
                     Integer.parseInt(datos[3]), Integer.parseInt(datos[4])
                 );
                 certificaciones.put(datos[0], cert);
             }
         }
     } catch (IOException e) {
         System.err.println("Error al cargar certificaciones: " + e.getMessage());
     }
 }
 
 public void cargarAsignaturasCertificaciones(String rutaArchivo) {
     try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
         String linea;
         while ((linea = br.readLine()) != null) {
             String[] datos = linea.split(";");
             if (datos.length >= 2) {
                 String idCert = datos[0];
                 String nrc = datos[1];
                 
                 asignaturasPorCertificacion
                     .computeIfAbsent(idCert, k -> new ArrayList<>())
                     .add(nrc);
             }
         }
     } catch (IOException e) {
         System.err.println("Error al cargar asignaturas-certificaciones: " + e.getMessage());
     }
 }
 
 public List<String> getAsignaturasPorCertificacion(String idCertificacion) {
     return asignaturasPorCertificacion.getOrDefault(idCertificacion, new ArrayList<>());
 }
 
 public List<Certificacion> getCertificacionesDisponibles() {
     return new ArrayList<>(certificaciones.values());
 }
 
 public Certificacion getCertificacion(String id) {
     return certificaciones.get(id);
 }
}