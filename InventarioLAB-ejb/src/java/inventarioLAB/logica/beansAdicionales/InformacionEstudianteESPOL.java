/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.logica.beansAdicionales;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Stateless
@LocalBean
public class InformacionEstudianteESPOL {
    private String matricula;
    private String tipo_identif;
    private String identificacion;
    private String estado;
    private String apellidos;
    private String nombres;
    private int factor_p;
    private String nombreCarrera;
    private String email;
    private double promedioCarrera;
    private int num_Matarias_Aprobadas;
    private String unidadAcademica;
    private String division;
    private String carrera;
    private String especializ;
    private String usuario;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getEspecializ() {
        return especializ;
    }

    public void setEspecializ(String especializ) {
        this.especializ = especializ;
    }
    private String fecha_nacimiento;
    private String registrado;

    public InformacionEstudianteESPOL() {
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTipo_identif() {
        return tipo_identif;
    }

    public void setTipo_identif(String tipo_identif) {
        this.tipo_identif = tipo_identif;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public int getFactor_p() {
        return factor_p;
    }

    public void setFactor_p(int factor_p) {
        this.factor_p = factor_p;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPromedioCarrera() {
        return promedioCarrera;
    }

    public void setPromedioCarrera(double promedioCarrera) {
        this.promedioCarrera = promedioCarrera;
    }

    public int getNum_Matarias_Aprobadas() {
        return num_Matarias_Aprobadas;
    }

    public void setNum_Matarias_Aprobadas(int num_Matarias_Aprobadas) {
        this.num_Matarias_Aprobadas = num_Matarias_Aprobadas;
    }

    public String getUnidadAcademica() {
        return unidadAcademica;
    }

    public void setUnidadAcademica(String unidadAcademica) {
        this.unidadAcademica = unidadAcademica;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getRegistrado() {
        return registrado;
    }

    public void setRegistrado(String registrado) {
        this.registrado = registrado;
    }
    
    @Override
    public String toString() {
        return "InformacionEstudianteESPOL{" + "matricula=" + matricula + ", tipo_identif=" + tipo_identif + ", identificacion=" + identificacion + ", estado=" + estado + ", apellidos=" + apellidos + ", nombres=" + nombres + ", factor_p=" + factor_p + ", nombreCarrera=" + nombreCarrera + ", email=" + email + ", promedioCarrera=" + promedioCarrera + ", num_Matarias_Aprobadas=" + num_Matarias_Aprobadas + ", unidadAcademica=" + unidadAcademica + ", fecha_nacimiento=" + fecha_nacimiento + ", registrado=" + registrado + '}';
    }
    
}
