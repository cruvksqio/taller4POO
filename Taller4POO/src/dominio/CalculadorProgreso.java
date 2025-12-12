package dominio;

public interface CalculadorProgreso {
 int calcularProgreso(Estudiante estudiante, Certificacion certificacion);
}

//Estrategias concretas
class CalculadorProgresoSimple implements CalculadorProgreso {
 public int calcularProgreso(Estudiante estudiante, Certificacion certificacion) {
     // Implementación simple basada en créditos aprobados
     return 0; // Tu compañero implementará esto
 }
}

class CalculadorProgresoAvanzado implements CalculadorProgreso {
 public int calcularProgreso(Estudiante estudiante, Certificacion certificacion) {
     // Implementación avanzada considerando prerrequisitos
     return 0; // Tu compañero implementará esto
 }
}