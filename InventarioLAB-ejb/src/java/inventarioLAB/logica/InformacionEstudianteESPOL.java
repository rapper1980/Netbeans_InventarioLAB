/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.logica;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Stateless
@LocalBean
public class InformacionEstudianteESPOL {
    private String codigoEstudiante;
    private String identificacion;
    private String tipoIdentif;
    private String apellidos;
    private String nombres;
    private String codDivision;
    private String codCarrera;
    private String codEspecializacion;
    private String nombreCarrera;
    private String email;
    private String fechaNacimiento;
    private String registrado;

    public InformacionEstudianteESPOL() {
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCodCarrera() {
        return codCarrera;
    }

    public void setCodCarrera(String codCarrera) {
        this.codCarrera = codCarrera;
    }

    public String getCodDivision() {
        return codDivision;
    }

    public void setCodDivision(String codDivision) {
        this.codDivision = codDivision;
    }

    public String getCodEspecializacion() {
        return codEspecializacion;
    }

    public void setCodEspecializacion(String codEspecializacion) {
        this.codEspecializacion = codEspecializacion;
    }

    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(String codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getRegistrado() {
        return registrado;
    }

    public void setRegistrado(String registrado) {
        this.registrado = registrado;
    }

    public String getTipoIdentif() {
        return tipoIdentif;
    }

    public void setTipoIdentif(String tipoIdentif) {
        this.tipoIdentif = tipoIdentif;
    }
    
        
}
