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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Entity
@Table(name = "detalle_solicitud_prestamo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleSolicitudPrestamo.findAll", query = "SELECT d FROM DetalleSolicitudPrestamo d"),
    @NamedQuery(name = "DetalleSolicitudPrestamo.findByIdDetalle", query = "SELECT d FROM DetalleSolicitudPrestamo d WHERE d.idDetalle = :idDetalle"),
    @NamedQuery(name = "DetalleSolicitudPrestamo.findByEquipoSerie", query = "SELECT d FROM DetalleSolicitudPrestamo d WHERE d.equipoSerie = :equipoSerie")})
public class DetalleSolicitudPrestamo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle")
    private Integer idDetalle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "equipo_serie")
    private String equipoSerie;
    @JoinColumn(name = "id_solicitud_prestamo", referencedColumnName = "id_solicitud_prestamo")
    @ManyToOne(optional = false)
    private SolicitudPrestamo idSolicitudPrestamo;

    public DetalleSolicitudPrestamo() {
    }

    public DetalleSolicitudPrestamo(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public DetalleSolicitudPrestamo(Integer idDetalle, String equipoSerie) {
        this.idDetalle = idDetalle;
        this.equipoSerie = equipoSerie;
    }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public String getEquipoSerie() {
        return equipoSerie;
    }

    public void setEquipoSerie(String equipoSerie) {
        this.equipoSerie = equipoSerie;
    }

    public SolicitudPrestamo getIdSolicitudPrestamo() {
        return idSolicitudPrestamo;
    }

    public void setIdSolicitudPrestamo(SolicitudPrestamo idSolicitudPrestamo) {
        this.idSolicitudPrestamo = idSolicitudPrestamo;
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
