/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
    @UniqueConstraint(columnNames = {"codigo"}),
    @UniqueConstraint(columnNames = {"serie"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipo.findAll", query = "SELECT e FROM Equipo e"),
    @NamedQuery(name = "Equipo.findByIdEquipo", query = "SELECT e FROM Equipo e WHERE e.idEquipo = :idEquipo"),
    @NamedQuery(name = "Equipo.findBySerie", query = "SELECT e FROM Equipo e WHERE e.serie = :serie"),
    @NamedQuery(name = "Equipo.findByCodigo", query = "SELECT e FROM Equipo e WHERE e.codigo = :codigo"),
    @NamedQuery(name = "Equipo.findByNombre", query = "SELECT e FROM Equipo e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Equipo.findByMarca", query = "SELECT e FROM Equipo e WHERE e.marca = :marca"),
    @NamedQuery(name = "Equipo.findByModelo", query = "SELECT e FROM Equipo e WHERE e.modelo = :modelo"),
    @NamedQuery(name = "Equipo.findByPrecio", query = "SELECT e FROM Equipo e WHERE e.precio = :precio"),
    @NamedQuery(name = "Equipo.findByUbicacion", query = "SELECT e FROM Equipo e WHERE e.ubicacion = :ubicacion"),
    @NamedQuery(name = "Equipo.findByEstado", query = "SELECT e FROM Equipo e WHERE e.estado = :estado")})
public class Equipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_equipo", nullable = false)
    private Integer idEquipo;
    @Size(max = 15)
    @Column(length = 15)
    private String serie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(nullable = false, length = 15)
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(nullable = false, length = 20)
    private String nombre;
    @Size(max = 20)
    @Column(length = 20)
    private String marca;
    @Size(max = 20)
    @Column(length = 20)
    private String modelo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 10, scale = 2)
    private BigDecimal precio;
    @Size(max = 255)
    @Column(length = 255)
    private String ubicacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(nullable = false, length = 3)
    private String estado;
    @JoinColumn(name = "id_tipo_equipo", referencedColumnName = "id_tipo_equipo", nullable = false)
    @ManyToOne(optional = false)
    private TipoEquipo idTipoEquipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipoCodigo")
    private List<DetalleSolicitudPrestamo> detalleSolicitudPrestamoList;

    public Equipo() {
    }

    public Equipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public Equipo(Integer idEquipo, String codigo, String nombre, String estado) {
        this.idEquipo = idEquipo;
        this.codigo = codigo;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public TipoEquipo getIdTipoEquipo() {
        return idTipoEquipo;
    }

    public void setIdTipoEquipo(TipoEquipo idTipoEquipo) {
        this.idTipoEquipo = idTipoEquipo;
    }

    @XmlTransient
    public List<DetalleSolicitudPrestamo> getDetalleSolicitudPrestamoList() {
        return detalleSolicitudPrestamoList;
    }

    public void setDetalleSolicitudPrestamoList(List<DetalleSolicitudPrestamo> detalleSolicitudPrestamoList) {
        this.detalleSolicitudPrestamoList = detalleSolicitudPrestamoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEquipo != null ? idEquipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equipo)) {
            return false;
        }
        Equipo other = (Equipo) object;
        if ((this.idEquipo == null && other.idEquipo != null) || (this.idEquipo != null && !this.idEquipo.equals(other.idEquipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "inventarioLAB.entidades.Equipo[ idEquipo=" + idEquipo + " ]";
    }
    
}
