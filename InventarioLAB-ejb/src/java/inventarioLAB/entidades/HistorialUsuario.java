/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Entity
@Table(name = "historial_usuario", catalog = "inventario_lab", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistorialUsuario.findAll", query = "SELECT h FROM HistorialUsuario h"),
    @NamedQuery(name = "HistorialUsuario.findById", query = "SELECT h FROM HistorialUsuario h WHERE h.id = :id"),
    @NamedQuery(name = "HistorialUsuario.findByUsuario", query = "SELECT h FROM HistorialUsuario h WHERE h.usuario = :usuario"),
    @NamedQuery(name = "HistorialUsuario.findByEmail", query = "SELECT h FROM HistorialUsuario h WHERE h.email = :email"),
    @NamedQuery(name = "HistorialUsuario.findByClave", query = "SELECT h FROM HistorialUsuario h WHERE h.clave = :clave"),
    @NamedQuery(name = "HistorialUsuario.findByIdPersona", query = "SELECT h FROM HistorialUsuario h WHERE h.idPersona = :idPersona"),
    @NamedQuery(name = "HistorialUsuario.findByEstado", query = "SELECT h FROM HistorialUsuario h WHERE h.estado = :estado"),
    @NamedQuery(name = "HistorialUsuario.findByModoAutenticacion", query = "SELECT h FROM HistorialUsuario h WHERE h.modoAutenticacion = :modoAutenticacion"),
    @NamedQuery(name = "HistorialUsuario.findByRol", query = "SELECT h FROM HistorialUsuario h WHERE h.rol = :rol"),
    @NamedQuery(name = "HistorialUsuario.findByFechaIngreso", query = "SELECT h FROM HistorialUsuario h WHERE h.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "HistorialUsuario.findByFechaModificacion", query = "SELECT h FROM HistorialUsuario h WHERE h.fechaModificacion = :fechaModificacion"),
    @NamedQuery(name = "HistorialUsuario.findByUsuarioModificacion", query = "SELECT h FROM HistorialUsuario h WHERE h.usuarioModificacion = :usuarioModificacion"),
    @NamedQuery(name = "HistorialUsuario.findByObservacion", query = "SELECT h FROM HistorialUsuario h WHERE h.observacion = :observacion")})
public class HistorialUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(nullable = false, length = 15)
    private String usuario;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(length = 50)
    private String email;
    @Size(max = 255)
    @Column(length = 255)
    private String clave;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_persona", nullable = false)
    private int idPersona;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(nullable = false, length = 1)
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "modo_autenticacion", nullable = false, length = 3)
    private String modoAutenticacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(nullable = false, length = 3)
    private String rol;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ingreso", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_modificacion", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaModificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "usuario_modificacion", nullable = false, length = 15)
    private String usuarioModificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(nullable = false, length = 150)
    private String observacion;

    public HistorialUsuario() {
    }

    public HistorialUsuario(Integer id) {
        this.id = id;
    }

    public HistorialUsuario(Integer id, String usuario, int idPersona, String estado, String modoAutenticacion, String rol, Date fechaIngreso, Date fechaModificacion, String usuarioModificacion, String observacion) {
        this.id = id;
        this.usuario = usuario;
        this.idPersona = idPersona;
        this.estado = estado;
        this.modoAutenticacion = modoAutenticacion;
        this.rol = rol;
        this.fechaIngreso = fechaIngreso;
        this.fechaModificacion = fechaModificacion;
        this.usuarioModificacion = usuarioModificacion;
        this.observacion = observacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getModoAutenticacion() {
        return modoAutenticacion;
    }

    public void setModoAutenticacion(String modoAutenticacion) {
        this.modoAutenticacion = modoAutenticacion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistorialUsuario)) {
            return false;
        }
        HistorialUsuario other = (HistorialUsuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "inventarioLAB.entidades.HistorialUsuario[ id=" + id + " ]";
    }
    
}
