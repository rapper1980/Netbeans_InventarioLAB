/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Entity
@Table(name = "estudiante_espol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstudianteEspol.findAll", query = "SELECT e FROM EstudianteEspol e"),
    @NamedQuery(name = "EstudianteEspol.findByIdPersona", query = "SELECT e FROM EstudianteEspol e WHERE e.idPersona = :idPersona"),
    @NamedQuery(name = "EstudianteEspol.findByMatricula", query = "SELECT e FROM EstudianteEspol e WHERE e.matricula = :matricula"),
    @NamedQuery(name = "EstudianteEspol.findByCodUnidad", query = "SELECT e FROM EstudianteEspol e WHERE e.codUnidad = :codUnidad"),
    @NamedQuery(name = "EstudianteEspol.findByCodDivision", query = "SELECT e FROM EstudianteEspol e WHERE e.codDivision = :codDivision"),
    @NamedQuery(name = "EstudianteEspol.findByCodCarrera", query = "SELECT e FROM EstudianteEspol e WHERE e.codCarrera = :codCarrera"),
    @NamedQuery(name = "EstudianteEspol.findByCodEspecializacion", query = "SELECT e FROM EstudianteEspol e WHERE e.codEspecializacion = :codEspecializacion"),
    @NamedQuery(name = "EstudianteEspol.findByEmail", query = "SELECT e FROM EstudianteEspol e WHERE e.email = :email")})
public class EstudianteEspol implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_persona")
    private Integer idPersona;
    @Size(max = 10)
    @Column(name = "matricula")
    private String matricula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "cod_unidad")
    private String codUnidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "cod_division")
    private String codDivision;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "cod_carrera")
    private String codCarrera;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "cod_especializacion")
    private String codEspecializacion;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "email")
    private String email;
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Persona persona;

    public EstudianteEspol() {
    }

    public EstudianteEspol(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public EstudianteEspol(Integer idPersona, String codUnidad, String codDivision, String codCarrera, String codEspecializacion, String email) {
        this.idPersona = idPersona;
        this.codUnidad = codUnidad;
        this.codDivision = codDivision;
        this.codCarrera = codCarrera;
        this.codEspecializacion = codEspecializacion;
        this.email = email;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCodUnidad() {
        return codUnidad;
    }

    public void setCodUnidad(String codUnidad) {
        this.codUnidad = codUnidad;
    }

    public String getCodDivision() {
        return codDivision;
    }

    public void setCodDivision(String codDivision) {
        this.codDivision = codDivision;
    }

    public String getCodCarrera() {
        return codCarrera;
    }

    public void setCodCarrera(String codCarrera) {
        this.codCarrera = codCarrera;
    }

    public String getCodEspecializacion() {
        return codEspecializacion;
    }

    public void setCodEspecializacion(String codEspecializacion) {
        this.codEspecializacion = codEspecializacion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersona != null ? idPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstudianteEspol)) {
            return false;
        }
        EstudianteEspol other = (EstudianteEspol) object;
        if ((this.idPersona == null && other.idPersona != null) || (this.idPersona != null && !this.idPersona.equals(other.idPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "inventarioLAB.entidades.EstudianteEspol[ idPersona=" + idPersona + " ]";
    }
    
}
