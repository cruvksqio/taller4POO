package dominio;


public interface VisitorCertificacion {
 void visit(Certificacion certificacion);
}

//Visitors concretos
class GeneradorCertificadoVisitor implements VisitorCertificacion {
 public void visit(Certificacion certificacion) {
     System.out.println("Generando certificado para: " + certificacion.getNombre());
     // Lógica de generación de certificado
 }
}

class ValidadorRequisitosVisitor implements VisitorCertificacion {
 public void visit(Certificacion certificacion) {
     System.out.println("Validando requisitos para: " + certificacion.getNombre());
     // Lógica de validación
 }
}