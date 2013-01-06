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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Entity
@Table(name = "detalle_solicitud_prestamo", catalog = "inventario_lab", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleSolicitudPrestamo.findAll", query = "SELECT d FROM DetalleSolicitudPrestamo d"),
    @NamedQuery(name = "DetalleSolicitudPrestamo.findByIdDetalle", query = "SELECT d FROM DetalleSolicitudPrestamo d WHERE d.idDetalle = :idDetalle")})
public class DetalleSolicitudPrestamo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle", nullable = false)
    private Integer idDetalle;
    @JoinColumn(name = "id_solicitud_prestamo", referencedColumnName = "id_solicitud_prestamo", nullable = false)
    @ManyToOne(optional = false)
    private SolicitudPrestamo idSolicitudPrestamo;
    @JoinColumn(name = "equipo_codigo", referencedColumnName = "codigo", nullable = false)
    @ManyToOne(optional = false)
    private Equipo equipoCodigo;

    public DetalleSolicitudPrestamo() {
    }

    public DetalleSolicitudPrestamo(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public SolicitudPrestamo getIdSolicitudPrestamo() {
        return idSolicitudPrestamo;
    }

    public void setIdSolicitudPrestamo(SolicitudPrestamo idSolicitudPrestamo) {
        this.idSolicitudPrestamo = idSolicitudPrestamo;
    }

    public Equipo getEquipoCodigo() {
        return equipoCodigo;
    }

    public void setEquipoCodigo(Equipo equipoCodigo) {
        this.equipoCodigo = equipoCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalle != null ? idDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleSolicitudPrestamo)) {
            return false;
        }
        DetalleSolicitudPrestamo other = (DetalleSolicitudPrestamo) object;
        if ((this.idDetalle == null && other.idDetalle != null) || (this.idDetalle != null && !this.idDetalle.equals(other.idDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "inventarioLAB.entidades.DetalleSolicitudPrestamo[ idDetalle=" + idDetalle + " ]";
    }
    
}
