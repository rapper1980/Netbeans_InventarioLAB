/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "historial_equipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistorialEquipo.findAll", query = "SELECT h FROM HistorialEquipo h"),
    @NamedQuery(name = "HistorialEquipo.findById", query = "SELECT h FROM HistorialEquipo h WHERE h.id = :id"),
    @NamedQuery(name = "HistorialEquipo.findByIdEquipo", query = "SELECT h FROM HistorialEquipo h WHERE h.idEquipo = :idEquipo"),
    @NamedQuery(name = "HistorialEquipo.findBySerie", query = "SELECT h FROM HistorialEquipo h WHERE h.serie = :serie"),
    @NamedQuery(name = "HistorialEquipo.findByCodigo", query = "SELECT h FROM HistorialEquipo h WHERE h.codigo = :codigo"),
    @NamedQuery(name = "HistorialEquipo.findByNombre", query = "SELECT h FROM HistorialEquipo h WHERE h.nombre = :nombre"),
    @NamedQuery(name = "HistorialEquipo.findByMarca", query = "SELECT h FROM HistorialEquipo h WHERE h.marca = :marca"),
    @NamedQuery(name = "HistorialEquipo.findByModelo", query = "SELECT h FROM HistorialEquipo h WHERE h.modelo = :modelo"),
    @NamedQuery(name = "HistorialEquipo.findByPrecio", query = "SELECT h FROM HistorialEquipo h WHERE h.precio = :precio"),
    @NamedQuery(name = "HistorialEquipo.findByUbicacion", query = "SELECT h FROM HistorialEquipo h WHERE h.ubicacion = :ubicacion"),
    @NamedQuery(name = "HistorialEquipo.findByIdTipoEquipo", query = "SELECT h FROM HistorialEquipo h WHERE h.idTipoEquipo = :idTipoEquipo"),
    @NamedQuery(name = "HistorialEquipo.findByEstado", query = "SELECT h FROM HistorialEquipo h WHERE h.estado = :estado"),
    @NamedQuery(name = "HistorialEquipo.findByAutorizacion", query = "SELECT h FROM HistorialEquipo h WHERE h.autorizacion = :autorizacion"),
    @NamedQuery(name = "HistorialEquipo.findByObservacion", query = "SELECT h FROM HistorialEquipo h WHERE h.observacion = :observacion")})
public class HistorialEquipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_equipo")
    private int idEquipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "serie")
    private String serie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 20)
    @Column(name = "marca")
    private String marca;
    @Size(max = 20)
    @Column(name = "modelo")
    private String modelo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio")
    private BigDecimal precio;
    @Size(max = 255)
    @Column(name = "ubicacion")
    private String ubicacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tipo_equipo")
    private int idTipoEquipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "autorizacion")
    private String autorizacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "observacion")
    private String observacion;

    public HistorialEquipo() {
    }

    public HistorialEquipo(Integer id) {
        this.id = id;
    }

    public HistorialEquipo(Integer id, int idEquipo, String serie, String codigo, String nombre, int idTipoEquipo, String estado, String autorizacion, String observacion) {
        this.id = id;
        this.idEquipo = idEquipo;
        this.serie = serie;
        this.codigo = codigo;
        this.nombre = nombre;
        this.idTipoEquipo = idTipoEquipo;
        this.estado = estado;
        this.autorizacion = autorizacion;
        this.observacion = observacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
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

    public int getIdTipoEquipo() {
        return idTipoEquipo;
    }

    public void setIdTipoEquipo(int idTipoEquipo) {
        this.idTipoEquipo = idTipoEquipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
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
        if (!(object instanceof HistorialEquipo)) {
            return false;
        }
        HistorialEquipo other = (HistorialEquipo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "inventarioLAB.entidades.HistorialEquipo[ id=" + id + " ]";
    }
    
}
