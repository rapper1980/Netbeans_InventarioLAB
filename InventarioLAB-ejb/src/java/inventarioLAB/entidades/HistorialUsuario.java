/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Entity
@Table(name = "historial_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistorialUsuario.findAll", query = "SELECT h FROM HistorialUsuario h"),
    @NamedQuery(name = "HistorialUsuario.findById", query = "SELECT h FROM HistorialUsuario h WHERE h.id = :id"),
    @NamedQuery(name = "HistorialUsuario.findByUsuario", query = "SELECT h FROM HistorialUsuario h WHERE h.usuario = :usuario"),
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
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "usuario")
    private String usuario;
    @Size(max = 255)
    @Column(name = "clave")
    private String clave;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_persona")
    private int idPersona;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private int estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modo_autenticacion")
    private int modoAutenticacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rol")
    private int rol;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ingreso")
    private int fechaIngreso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_modificacion")
    private int fechaModificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "usuario_modificacion")
    private int usuarioModificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "observacion")
    private String observacion;

    public HistorialUsuario() {
    }

    public HistorialUsuario(Integer id) {
        this.id = id;
    }

    public HistorialUsuario(Integer id, String usuario, int idPersona, int estado, int modoAutenticacion, int rol, int fechaIngreso, int fechaModificacion, int usuarioModificacion, String observacion) {
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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getModoAutenticacion() {
        return modoAutenticacion;
    }

    public void setModoAutenticacion(int modoAutenticacion) {
        this.modoAutenticacion = modoAutenticacion;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public int getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(int fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public int getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(int fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(int usuarioModificacion) {
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
