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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Entity
@Table(name = "tipo_equipo", catalog = "inventario_lab", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoEquipo.findAll", query = "SELECT t FROM TipoEquipo t"),
    @NamedQuery(name = "TipoEquipo.findByIdTipoEquipo", query = "SELECT t FROM TipoEquipo t WHERE t.idTipoEquipo = :idTipoEquipo"),
    @NamedQuery(name = "TipoEquipo.findByDescripcion", query = "SELECT t FROM TipoEquipo t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TipoEquipo.findByPrestar", query = "SELECT t FROM TipoEquipo t WHERE t.prestar = :prestar"),
    @NamedQuery(name = "TipoEquipo.findByAutorizacion", query = "SELECT t FROM TipoEquipo t WHERE t.autorizacion = :autorizacion")})
public class TipoEquipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_equipo", nullable = false)
    private Integer idTipoEquipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30)
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(nullable = false, length = 1)
    private String prestar;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(nullable = false, length = 1)
    private String autorizacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoEquipo")
    private List<Equipo> equipoList;

    public TipoEquipo() {
    }

    public TipoEquipo(Integer idTipoEquipo) {
        this.idTipoEquipo = idTipoEquipo;
    }

    public TipoEquipo(Integer idTipoEquipo, String descripcion, String prestar, String autorizacion) {
        this.idTipoEquipo = idTipoEquipo;
        this.descripcion = descripcion;
        this.prestar = prestar;
        this.autorizacion = autorizacion;
    }

    public Integer getIdTipoEquipo() {
        return idTipoEquipo;
    }

    public void setIdTipoEquipo(Integer idTipoEquipo) {
        this.idTipoEquipo = idTipoEquipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrestar() {
        return prestar;
    }

    public void setPrestar(String prestar) {
        this.prestar = prestar;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    @XmlTransient
    public List<Equipo> getEquipoList() {
        return equipoList;
    }

    public void setEquipoList(List<Equipo> equipoList) {
        this.equipoList = equipoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoEquipo != null ? idTipoEquipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEquipo)) {
            return false;
        }
        TipoEquipo other = (TipoEquipo) object;
        if ((this.idTipoEquipo == null && other.idTipoEquipo != null) || (this.idTipoEquipo != null && !this.idTipoEquipo.equals(other.idTipoEquipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "inventarioLAB.entidades.TipoEquipo[ idTipoEquipo=" + idTipoEquipo + " ]";
    }
    
}
