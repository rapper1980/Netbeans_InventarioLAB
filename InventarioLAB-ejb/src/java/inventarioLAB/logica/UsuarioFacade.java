/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.logica;

import inventarioLAB.entidades.Usuario;
import inventarioLAB.logica.beansAdicionales.InformacionEstudianteESPOL;
import inventarioLAB.webServicesClient.ESPOL.DirectorioEspol;
import inventarioLAB.webServicesClient.ESPOL_SAAC.InformacionAcademicaEstudianteGetResponse;
import inventarioLAB.webServicesClient.ESPOL_SAAC.InformacionAcademicaEstudianteGetResponse.InformacionAcademicaEstudianteGetResult;
import inventarioLAB.webServicesClient.ESPOL_SAAC.WsSAAC;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.ws.WebServiceRef;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @WebServiceRef(wsdlLocation = "META-INF/wsdl/www.academico.espol.edu.ec/Services/wsSAAC.asmx.wsdl")
    private WsSAAC service_1;
    @WebServiceRef(wsdlLocation = "META-INF/wsdl/www.academico.espol.edu.ec/services/directorioEspol.asmx.wsdl")
    private DirectorioEspol service;
    @PersistenceContext(unitName = "InventarioLAB-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    /**
     * Inactiva un usuario a la Base de Datos. Cambia de estado <tt>Activo</tt>
     * (A) a estado <tt>Inactivo</tt> (I).
     *
     * @param usuario
     */
    @Override
    public void inactiva(Usuario usuario) {
        usuario.setEstado("I");
        this.em.merge(usuario);
    }

    /**
     * Obtine todos los usuarios de la Base de Datos, ya sea activos o inactivos
     *
     * @return una lista con todos los Usuarios.
     */
    @Override
    public List<Usuario> obtenerUsuarios() {
        Query query = this.em.createNamedQuery("Usuario.findAll");
        return (List<Usuario>) query.getResultList();

    }

    /**
     * Obtine todos los usuarios de la Base de Datos, pero filtramos por estado
     *
     * @param estado puede ser <tt>A</tt> para activos y <tt>I</tt> para
     * inactivos
     * @return una lista de los Usuarios segun el estado.
     */
    @Override
    public List<Usuario> obtenerUsuarios(String estado) {
        Query query = this.em.createNamedQuery("Usuario.findByEstado");
        query.setParameter("estado", estado);
        return (List<Usuario>) query.getResultList();
    }

    /**
     * Obtine todos los usuarios de la Base de Datos, pero filtramos por rol y
     * por estado Roles: <tt>SPU</tt> Usuarios con todos los privilegios.
     * <tt>SSE</tt> para los ayudantes. <tt>PUB</tt> Usuario para consultas.
     *
     * @param rol, rol de los usuario.
     * @param estado
     * @return
     */
    @Override
    public List<Usuario> obtenerUsuarios(String rol, String estado) {
        Query query = this.em.createNamedQuery("Usuario.findByRolEstado");
        query.setParameter("rol", rol);
        query.setParameter("estado", estado);
        return (List<Usuario>) query.getResultList();
    }

    /**
     * Verifica que un usuario exista en la Base de Datos.
     *
     * @param usuario
     * @return <tt>ture</tt> Si el usuario existe, <tt>false</tt> si no existe.
     */
    private boolean existeUsuario(String usuario) {
        boolean resultado = false;
        List<Usuario> lista = null;
        Query query = this.em.createNamedQuery("Usuario.findByUsuario");
        query.setParameter("usuario", usuario);

        return resultado;
    }

    /**
     * Inactiva un usuario a la Base de Datos. Cambia de estado <tt>Activo</tt>
     * (A) a estado <tt>Inactivo</tt> (I).
     *
     * @param usuario
     */
    @Override
    public Usuario obtenerUsuario(String usuario, String estado) {
        Usuario resultado = null;
        Query query = this.em.createNamedQuery("Usuario.findByUsuario");
        query.setParameter("usuario", usuario);
        query.setParameter("estado", estado);
        try {
            resultado = (Usuario) query.getResultList().get(0);
        } catch (Exception e) {
            resultado = null;
        }

        return resultado;
    }
    
    private Usuario obtenerUsuarioINT(String usuario, String clave, String estado){
        Usuario resultado = null;
        Query query = this.em.createNamedQuery("Usuario.findByUsuarioClave");
        query.setParameter("usuario", usuario);
        query.setParameter("clave", usuario);
        query.setParameter("estado", estado);
        try {
            resultado = (Usuario) query.getResultList().get(0);
        } catch (Exception e) {
            resultado = null;
        }

        return resultado;
    }

    /**
     *
     * @param usuario
     * @return
     */
    @Override
    public Usuario autenticar(String usuario, String clave, String modoAutenticacion) {
        Usuario resultado = null;

        if ("WSE".equals(modoAutenticacion)) {
            if (autenticacion(usuario, clave)) {
                resultado = obtenerUsuario(usuario, "A");
            }
        } else {
            resultado = obtenerUsuario(usuario, "A");
        }



        return resultado;
    }

    @Override
    public boolean existeSuperUsuario() {
        boolean resultado = false;
        List<Usuario> lista = null;
        Query query = this.em.createNamedQuery("Usuario.findByRolEstado");
        query.setParameter("rol", "SUPER");
        query.setParameter("estado", "A");
        try {
            lista = query.getResultList();

            if (lista != null) {
                if (!lista.isEmpty()) {
                    resultado = true;
                }
            }
        } catch (Exception e) {
            resultado = false;
        }

        return resultado;
    }

    @Override
    public InformacionEstudianteESPOL obtenerInformacionAcademicaEstudianteESPOL(String identificacion, String matricula) {
        return procesarInformacionAcademicaEstudiante(informacionAcademicaEstudianteGet(identificacion, matricula));
    }

    private boolean autenticacion(java.lang.String varUser, java.lang.String varContrasenia) {
        inventarioLAB.webServicesClient.ESPOL.DirectorioEspolSoap port = service.getDirectorioEspolSoap12();
        return port.autenticacion(varUser, varContrasenia);
    }

    private InformacionAcademicaEstudianteGetResult informacionAcademicaEstudianteGet(java.lang.String identificacion, java.lang.String matricula) {
        inventarioLAB.webServicesClient.ESPOL_SAAC.WsSAACSoap port = service_1.getWsSAACSoap12();
        return port.informacionAcademicaEstudianteGet(identificacion, matricula);
    }

    private InformacionEstudianteESPOL procesarInformacionAcademicaEstudiante(InformacionAcademicaEstudianteGetResponse.InformacionAcademicaEstudianteGetResult resultado) {
        InformacionEstudianteESPOL info;
        info = new InformacionEstudianteESPOL();
        //RESULTADO EN DOM
        //Recodar que todo lo que tenemos son objetos por eso da null al leer

        //ASI SE DEBE LEER
        //LEEMOS PRIMERO LA TAG <xs:schema>
        Element tagSchema = (Element) resultado.getAny().get(0); //TAG <SCHEMA>

        //xs:element name="NewDataSet"
        NodeList schema_elemets = tagSchema.getElementsByTagName("xs:element");
        Element newDataSet_elemet = (Element) schema_elemets.item(0);//xs:element name="NewDataSet"

        //xs:element name="DATOS_USUARIO"
        NodeList newDataSet_elemets = newDataSet_elemet.getElementsByTagName("xs:element");
        Element datos_usuario_element = (Element) newDataSet_elemets.item(0);//xs:element name="DATOS_USUARIO"

        //xs:element dentro de xs:element name="DATOS_USUARIO"
        NodeList elementos_TAG = datos_usuario_element.getElementsByTagName("xs:element");

        //----------
        //LEEMOS PRIMERO LA TAG <diffgr:diffgram>
        Element tag_diffgr = (Element) resultado.getAny().get(1); //TAG <diffgr>

        //SCHEMA, las tag xs:element dentro de xs:element name="DATOS_USUARIO",
        //en el attributo name tiene la informacion(tag) de respuesta
        //es decir que, el atributo es el nombre de la tag que tiene la data =)
        //lo hago asi para hacer lo dinamico ya que no sabremos que nueva etiquea aparecera alguna dia
        //si la ESPOL cambia la respuesta del WS
        for (int i = 0; i < elementos_TAG.getLength(); i++) {
            String attribute_tag = ((Element) elementos_TAG.item(i)).getAttribute("name");
            Element tag = (Element) tag_diffgr.getElementsByTagName(attribute_tag).item(0);

            if ("COD_ESTUDIANTE".equals(attribute_tag)) {
                info.setMatricula(tag.getTextContent().trim());
            } else if ("IDENTIFICACION".equals(attribute_tag)) {
                info.setIdentificacion(tag.getTextContent().trim());
            } else if ("TIPO_IDENTIF".equals(attribute_tag)) {
                info.setTipo_identif(tag.getTextContent().trim());
            } else if ("ESTADO_ESTUDIANTE".equals(attribute_tag)) {
                info.setEstado(tag.getTextContent().trim());
            } else if ("APELLIDOS".equals(attribute_tag)) {
                info.setApellidos(tag.getTextContent().trim());
            } else if ("NOMBRES".equals(attribute_tag)) {
                info.setNombres(tag.getTextContent().trim());
            } else if ("FACTOR_P".equals(attribute_tag)) {
                info.setFactor_p(Integer.parseInt(tag.getTextContent().trim()));
            } else if ("NOMBRE_CARRERA".equals(attribute_tag)) {
                info.setNombreCarrera(tag.getTextContent().trim());
            } else if ("EMAIL".equals(attribute_tag)) {
                info.setEmail(tag.getTextContent().trim());
                info.setUsuario(info.getEmail().substring(0, info.getEmail().indexOf("@")));
            } else if ("NUMAPROBADAS".equals(attribute_tag)) {
                info.setNum_Matarias_Aprobadas(Integer.parseInt(tag.getTextContent().trim()));
            } else if ("PROMEDIOCARRERA".equals(attribute_tag)) {
                info.setPromedioCarrera(Double.parseDouble(tag.getTextContent().trim()));
            } else if ("ESTA_REGISTRADO".equals(attribute_tag)) {
                info.setRegistrado(tag.getTextContent().trim());
            } else if ("COD_UNIDAD".equals(attribute_tag)) {
                info.setUnidadAcademica(tag.getTextContent().trim());
            } else if ("FECHA_NACIM".equals(attribute_tag)) {
                info.setFecha_nacimiento(tag.getTextContent().trim().substring(0, tag.getTextContent().trim().indexOf("T")));
            } else if ("COD_DIVISION".equals(attribute_tag)) {
                info.setDivision(tag.getTextContent().trim());
            } else if ("COD_CARRERA".equals(attribute_tag)) {
                info.setCarrera(tag.getTextContent().trim());
            } else if ("COD_ESPECIALIZ".equals(attribute_tag)) {
                info.setEspecializ(tag.getTextContent().trim());
            }
        }
        return info;
    }
}
