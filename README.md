Sistema de Certificaciones Académicas – Taller 4 POO

📌 Descripción general

Este proyecto corresponde a un Sistema de Certificaciones Académicas, desarrollado en Java como parte del Taller de Programación Orientada a Objetos (POO).
El sistema permite la gestión de usuarios académicos, certificaciones y seguimiento de avances estudiantiles, integrando una interfaz gráfica Swing (JFrame / JPanel) y una arquitectura en capas bien definida.

El sistema soporta distintos roles de usuario:

Administrador
Estudiante
Coordinador

Cada rol accede a funcionalidades específicas mediante una interfaz gráfica intuitiva.

🧱 Arquitectura del sistema

presentacion  →  Servicios + Interfaz Gráfica (Swing)
dominio       →  Lógica de negocio y modelo
datos (.txt)  →  Persistencia simple de información

Capas principales

Dominio

Entidades: Usuario, Estudiante, Curso, Certificacion, etc.
Managers: UsuarioManager, CursoManager, CertificacionManager, etc.
Patrón Singleton: SistemaCertificaciones

Presentación
Servicios: AdminService, EstudianteService, CoordinadorService
GUI Swing: SistemaCertificacionesGUI

🎨 Interfaz gráfica

La aplicación cuenta con una interfaz gráfica desarrollada con Swing, utilizando:

JFrame como ventana principal
JPanel + CardLayout para navegación entre vistas
JTable para visualización estructurada de datos
JTextArea para reportes

La navegación y funcionalidades se adaptan automáticamente según el rol autenticado.

👤 Funcionalidades por rol

🔑 Administrador

Listar usuarios del sistema
Crear estudiantes y coordinadores
Eliminar usuarios
Restablecer contraseñas

🎓 Estudiante

Ver información personal
Visualizar malla curricular
Inscribirse en certificaciones
Consultar progreso y asignaturas pendientes

📊 Coordinador

Visualizar estadísticas de certificaciones
Monitorear estudiantes activos, completados y suspendidos
Análisis académico basado en registros reales

▶️ Ejecución del proyecto

Requisitos

Java JDK 11 o superior
IDE recomendado: Eclipse

📂 Archivos de datos

El sistema carga información desde archivos .txt ubicados en el directorio raíz del proyecto:

usuarios.txt
estudiantes.txt
cursos.txt
certificaciones.txt
asignaturas_certificaciones.txt
notas.txt
registros.txt

🛠️ Tecnologías utilizadas

Java
Swing (java.desktop)
Javadoc
PlantUML
Git / GitHub

👨‍💻 Autores

Nombre: Fernando Lagos y Constantino Bekios

Asignatura: Programasion orientada a objetos

Institución: Universidad catolica del norte

Fecha: 12/12/2025
