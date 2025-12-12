package dominio;

import logica.*;

public class SistemaCertificaciones {
 private static SistemaCertificaciones instance;
 
 private UsuarioManager usuarioManager;
 private CursoManager cursoManager;
 private CertificacionManager certificacionManager;
 private NotaManager notaManager;
 private RegistroManager registroManager;
 
 private Usuario usuarioActual;
 
 private SistemaCertificaciones() {
     // Inicializar managers
     this.usuarioManager = new UsuarioManager();
     this.cursoManager = new CursoManager();
     this.certificacionManager = new CertificacionManager();
     this.notaManager = new NotaManager();
     this.registroManager = new RegistroManager();
     
     // Cargar datos iniciales
     cargarDatosIniciales();
 }
 
 public static SistemaCertificaciones getInstance() {
     if (instance == null) {
         instance = new SistemaCertificaciones();
     }
     return instance;
 }
 
 private void cargarDatosIniciales() {
     usuarioManager.cargarUsuarios("usuarios.txt");
     usuarioManager.cargarEstudiantes("estudiantes.txt");
     cursoManager.cargarCursos("cursos.txt");
     certificacionManager.cargarCertificaciones("certificaciones.txt");
     certificacionManager.cargarAsignaturasCertificaciones("asignaturas_certificaciones.txt");
     notaManager.cargarNotas("notas.txt");
     registroManager.cargarRegistros("registros.txt");
 }
 
 // Getters para los managers
 public UsuarioManager getUsuarioManager() { return usuarioManager; }
 public CursoManager getCursoManager() { return cursoManager; }
 public CertificacionManager getCertificacionManager() { return certificacionManager; }
 public NotaManager getNotaManager() { return notaManager; }
 public RegistroManager getRegistroManager() { return registroManager; }
 
 public Usuario getUsuarioActual() { return usuarioActual; }
 public void setUsuarioActual(Usuario usuario) { this.usuarioActual = usuario; }
 
 // Métodos de autenticación
 public Usuario autenticar(String username, String password) {
     return usuarioManager.autenticar(username, password);
 }
}