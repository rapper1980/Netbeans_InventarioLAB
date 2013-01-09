/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Entity
@Table(name = "solicitud_prestamo", catalog = "inventario_lab", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SolicitudPrestamo.findAll", query = "SELECT s FROM SolicitudPrestamo s"),
    @NamedQuery(name = "SolicitudPrestamo.findByIdSolicitudPrestamo", query = "SELECT s FROM SolicitudPrestamo s WHERE s.idSolicitudPrestamo = :idSolicitudPrestamo"),
    @NamedQuery(name = "SolicitudPrestamo.findByFechaSolicitud", query = "SELECT s FROM SolicitudPrestamo s WHERE s.fechaSolicitud = :fechaSolicitud"),
    @NamedQuery(name = "SolicitudPrestamo.findByFechaPrestamo", query = "SELECT s FROM SolicitudPrestamo s WHERE s.fechaPrestamo = :fechaPrestamo"),
    @NamedQuery(name = "SolicitudPrestamo.findByFechaDevolucion", query = "SELECT s FROM SolicitudPrestamo s WHERE s.fechaDevolucion = :fechaDevolucion"),
    @NamedQuery(name = "SolicitudPrestamo.findByAutorizacionRequerida", query = "SELECT s FROM SolicitudPrestamo s WHERE s.autorizacionRequerida = :autorizacionRequerida"),
    @NamedQuery(name = "SolicitudPrestamo.findByUsuarioAutorizacion", query = "SELECT s FROM SolicitudPrestamo s WHERE s.usuarioAutorizacion = :usuarioAutorizacion"),
    @NamedQuery(name = "SolicitudPrestamo.findByEstado", query = "SELECT s FROM SolicitudPrestamo s WHERE s.estado = :estado")})
public class SolicitudPrestamo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_solicitud_prestamo", nullable = false)
    private Integer idSolicitudPrestamo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_solicitud", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaSolicitud;
    @Column(name = "fecha_prestamo")
    @Temporal(TemporalType.DATE)
    private Date fechaPrestamo;
    @Column(name = "fecha_devolucion")
    @Temporal(TemporalType.DATE)
    private Date fechaDevolucion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "autorizacion_requerida", nullable = false, length = 1)
    private String autorizacionRequerida;
    @Size(max = 15)
    @Column(name = "usuario_autorizacion", length = 15)
    private String usuarioAutorizacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(nullable = false, length = 3)
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSolicitudPrestamo")
    private List<DetalleSolicitudPrestamo> detalleSolicitudPrestamoList;
    @JoinColumn(name = "usuario", referencedColumnName = "usuario", nullable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "matricula", referencedColumnName = "matricula", nullable = false)
    @ManyToOne(optional = false)
    private Prestatario matricula;

    public SolicitudPrestamo() {
    }

    public SolicitudPrestamo(Integer idSolicitudPrestamo) {
        this.idSolicitudPrestamo = idSolicitudPrestamo;
    }

    public SolicitudPrestamo(Integer idSolicitudPrestamo, Date fechaSolicitud, String autorizacionRequerida, String estado) {
        this.idSolicitudPrestamo = idSolicitudPrestamo;
        this.fechaSolicitud = fechaSolicitud;
        this.autorizacionRequerida = autorizacionRequerida;
        this.estado = estado;
    }

    public Integer getIdSolicitudPrestamo() {
        return idSolicitudPrestamo;
    }

    public void setIdSolicitudPrestamo(Integer idSolicitudPrestamo) {
        this.idSolicitudPrestamo = idSolicitudPrestamo;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getAutorizacionRequerida() {
        return autorizacionRequerida;
    }

    public void setAutorizacionRequerida(String autorizacionRequerida) {
        this.autorizacionRequerida = autorizacionRequerida;
    }

    public String getUsuarioAutorizacion() {
        return usuarioAutorizacion;
    }

    public void setUsuarioAutorizacion(String usuarioAutorizacion) {
        this.usuarioAutorizacion = usuarioAutorizacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<DetalleSolicitudPrestamo> getDetalleSolicitudPrestamoList() {
        return detalleSolicitudPrestamoList;
    }

    public void setDetalleSolicitudPrestamoList(List<DetalleSolicitudPrestamo> detalleSolicitudPrestamoList) {
        this.detalleSolicitudPrestamoList = detalleSolicitudPrestamoList;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Prestatario getMatricula() {
        return matricula;
    }

    public void setMatricula(Prestatario matricula) {
        this.matricula = matricula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSolicitudPrestamo != null ? idSolicitudPrestamo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitudPrestamo)) {
            return false;
        }
        SolicitudPrestamo other = (SolicitudPrestamo) object;
        if ((this.idSolicitudPrestamo == null && other.idSolicitudPrestamo != null) || (this.idSolicitudPrestamo != null && !this.idSolicitudPrestamo.equals(other.idSolicitudPrestamo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "inventarioLAB.entidades.SolicitudPrestamo[ idSolicitudPrestamo=" + idSolicitudPrestamo + " ]";
    }
    
}
