package logica;


import dominio.*;
import java.io.*;
import java.util.*;

public class RegistroManager {
 private List<RegistroCertificacion> registros;
 
 public RegistroManager() {
     registros = new ArrayList<>();
 }
 
 public void cargarRegistros(String rutaArchivo) {
     try {
         BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
         String linea;
         while ((linea = br.readLine()) != null) {
             String[] datos = linea.split(";");
             if (datos.length >= 5) {
                 RegistroCertificacion registro = new RegistroCertificacion(
                     datos[0], datos[1], datos[2], datos[3], 
                     Integer.parseInt(datos[4])
                 );
                 registros.add(registro);
             }
         }
         br.close();
     } catch (IOException e) {
         System.err.println("Error al cargar registros: " + e.getMessage());
     }
 }
 
 public List<RegistroCertificacion> getRegistrosPorEstudiante(String rut) {
     List<RegistroCertificacion> registrosEst = new ArrayList<>();
     for (RegistroCertificacion reg : registros) {
         if (reg.getRutEstudiante().equals(rut)) {
             registrosEst.add(reg);
         }
     }
     return registrosEst;
 }
 
 public List<RegistroCertificacion> getRegistrosPorCertificacion(String idCert) {
     List<RegistroCertificacion> registrosCert = new ArrayList<>();
     for (RegistroCertificacion reg : registros) {
         if (reg.getIdCertificacion().equals(idCert)) {
             registrosCert.add(reg);
         }
     }
     return registrosCert;
 }
 
 public boolean registrarEstudiante(String rut, String idCert) {
     // Verificar si ya está registrado y activo
     for (RegistroCertificacion reg : registros) {
         if (reg.getRutEstudiante().equals(rut) && 
             reg.getIdCertificacion().equals(idCert) &&
             reg.getEstado().equals("Activo")) {
             return false; // Ya está registrado
         }
     }
     
     // Crear nueva fecha (formato dd/MM/yyyy)
     java.util.Date fecha = new java.util.Date();
     java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
     String fechaStr = sdf.format(fecha);
     
     // Crear nuevo registro
     RegistroCertificacion nuevoReg = new RegistroCertificacion(
         rut, idCert, fechaStr, "Activo", 0
     );
     registros.add(nuevoReg);
     return true;
 }
 
 // Método para contar estadísticas simple
 public void mostrarEstadisticasCertificacion(String idCert) {
     int activos = 0, completados = 0, suspendidos = 0;
     
     for (RegistroCertificacion reg : registros) {
         if (reg.getIdCertificacion().equals(idCert)) {
             if (reg.getEstado().equals("Activo")) activos++;
             else if (reg.getEstado().equals("Completado")) completados++;
             else if (reg.getEstado().equals("Suspendido")) suspendidos++;
         }
     }
     
     System.out.println("Estadísticas certificación " + idCert + ":");
     System.out.println("  Activos: " + activos);
     System.out.println("  Completados: " + completados);
     System.out.println("  Suspendidos: " + suspendidos);
     System.out.println("  Total: " + (activos + completados + suspendidos));
 }
}