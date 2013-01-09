/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Entity
@Table(catalog = "inventario_lab", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"identificacion"}),
    @UniqueConstraint(columnNames = {"matricula"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prestatario.findAll", query = "SELECT p FROM Prestatario p"),
    @NamedQuery(name = "Prestatario.findByIdPrestatario", query = "SELECT p FROM Prestatario p WHERE p.idPrestatario = :idPrestatario"),
    @NamedQuery(name = "Prestatario.findByMatricula", query = "SELECT p FROM Prestatario p WHERE p.matricula = :matricula"),
    @NamedQuery(name = "Prestatario.findByTipoIdetificacion", query = "SELECT p FROM Prestatario p WHERE p.tipoIdetificacion = :tipoIdetificacion"),
    @NamedQuery(name = "Prestatario.findByIdentificacion", query = "SELECT p FROM Prestatario p WHERE p.identificacion = :identificacion"),
    @NamedQuery(name = "Prestatario.findByNombresCompleto", query = "SELECT p FROM Prestatario p WHERE p.nombresCompleto = :nombresCompleto"),
    @NamedQuery(name = "Prestatario.findByEmail", query = "SELECT p FROM Prestatario p WHERE p.email = :email")})
public class Prestatario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_prestatario", nullable = false)
    private Integer idPrestatario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(nullable = false, length = 10)
    private String matricula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "tipo_idetificacion", nullable = false, length = 3)
    private String tipoIdetificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(nullable = false, length = 13)
    private String identificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombres_completo", nullable = false, length = 100)
    private String nombresCompleto;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(nullable = false, length = 50)
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matricula")
    private List<SolicitudPrestamo> solicitudPrestamoList;

    public Prestatario() {
    }

    public Prestatario(Integer idPrestatario) {
        this.idPrestatario = idPrestatario;
    }

    public Prestatario(Integer idPrestatario, String matricula, String tipoIdetificacion, String identificacion, String nombresCompleto, String email) {
        this.idPrestatario = idPrestatario;
        this.matricula = matricula;
        this.tipoIdetificacion = tipoIdetificacion;
        this.identificacion = identificacion;
        this.nombresCompleto = nombresCompleto;
        this.email = email;
    }

    public Integer getIdPrestatario() {
        return idPrestatario;
    }

    public void setIdPrestatario(Integer idPrestatario) {
        this.idPrestatario = idPrestatario;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTipoIdetificacion() {
        return tipoIdetificacion;
    }

    public void setTipoIdetificacion(String tipoIdetificacion) {
        this.tipoIdetificacion = tipoIdetificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombresCompleto() {
        return nombresCompleto;
    }

    public void setNombresCompleto(String nombresCompleto) {
        this.nombresCompleto = nombresCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public List<SolicitudPrestamo> getSolicitudPrestamoList() {
        return solicitudPrestamoList;
    }

    public void setSolicitudPrestamoList(List<SolicitudPrestamo> solicitudPrestamoList) {
        this.solicitudPrestamoList = solicitudPrestamoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrestatario != null ? idPrestatario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prestatario)) {
            return false;
        }
        Prestatario other = (Prestatario) object;
        if ((this.idPrestatario == null && other.idPrestatario != null) || (this.idPrestatario != null && !this.idPrestatario.equals(other.idPrestatario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "inventarioLAB.entidades.Prestatario[ idPrestatario=" + idPrestatario + " ]";
    }
    
}
