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
import java.util.Collection;
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

    @Override
    public boolean autenticar(Usuario usuario) {
        return autenticacion(usuario.getUsuario(), usuario.getClave());
    }

    @Override
    public boolean existeSuperUsuario() {
        boolean resultado = false;
        Collection<Usuario> lista = null;
        Query query = this.em.createNamedQuery("Usuario.findByRolEstado");
        query.setParameter("rol", "SUPER");
        query.setParameter("estado", "A");
        try {
            lista = query.getResultList();
            
            if (lista != null){
                if (!lista.isEmpty()){
                    resultado = true;
                }
            }
        } catch (Exception e) {
            resultado = false;
        }
        
        return resultado;
    }
    
    @Override
    public InformacionEstudianteESPOL obtenerInformacionAcademicaEstudianteESPOL(String identificacion, String matricula){
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
    
    private InformacionEstudianteESPOL procesarInformacionAcademicaEstudiante(InformacionAcademicaEstudianteGetResponse.InformacionAcademicaEstudianteGetResult resultado){
        InformacionEstudianteESPOL info;
        info = new InformacionEstudianteESPOL();
        //RESULTADO EN DOM
        //Recodar que todo lo que tenemos son objetos por eso da null al leer

        //ASI SE DEBE LEER
        //LEEMOS PRIMERO LA TAG <xs:schema>
        Element tagSchema = (Element)resultado.getAny().get(0); //TAG <SCHEMA>

        //xs:element name="NewDataSet"
        NodeList schema_elemets = tagSchema.getElementsByTagName("xs:element");
        Element newDataSet_elemet = (Element)schema_elemets.item(0);//xs:element name="NewDataSet"

        //xs:element name="DATOS_USUARIO"
        NodeList newDataSet_elemets = newDataSet_elemet.getElementsByTagName("xs:element");
        Element datos_usuario_element = (Element)newDataSet_elemets.item(0);//xs:element name="DATOS_USUARIO"

        //xs:element dentro de xs:element name="DATOS_USUARIO"
        NodeList elementos_TAG = datos_usuario_element.getElementsByTagName("xs:element");

        //----------
        //LEEMOS PRIMERO LA TAG <diffgr:diffgram>
        Element tag_diffgr = (Element)resultado.getAny().get(1); //TAG <diffgr>

        //SCHEMA, las tag xs:element dentro de xs:element name="DATOS_USUARIO",
        //en el attributo name tiene la informacion(tag) de respuesta
        //es decir que, el atributo es el nombre de la tag que tiene la data =)
        //lo hago asi para hacer lo dinamico ya que no sabremos que nueva etiquea aparecera alguna dia
        //si la ESPOL cambia la respuesta del WS
        for(int i = 0; i< elementos_TAG.getLength() ;i++){
            String attribute_tag = ((Element)elementos_TAG.item(i)).getAttribute("name");
            Element tag = (Element)tag_diffgr.getElementsByTagName(attribute_tag).item(0);
            
            if ("COD_ESTUDIANTE".equals(attribute_tag)){
                info.setMatricula(tag.getTextContent().trim());
            }
            if ("IDENTIFICACION".equals(attribute_tag)){
                info.setIdentificacion(tag.getTextContent().trim());
            }
            if ("TIPO_IDENTIF".equals(attribute_tag)){
                info.setTipo_identif(tag.getTextContent().trim());
            }
            if ("ESTADO_ESTUDIANTE".equals(attribute_tag)){
                info.setEstado(tag.getTextContent().trim());
            }
            if ("APELLIDOS".equals(attribute_tag)){
                info.setApellidos(tag.getTextContent().trim());
            }
            if ("NOMBRES".equals(attribute_tag)){
                info.setNombres(tag.getTextContent().trim());
            }
            if ("FACTOR_P".equals(attribute_tag)){
                info.setFactor_p(Integer.parseInt(tag.getTextContent().trim()));
            }
            if ("NOMBRE_CARRERA".equals(attribute_tag)){
                info.setNombreCarrera(tag.getTextContent().trim());
            }
            if ("EMAIL".equals(attribute_tag)){
                info.setEmail(tag.getTextContent().trim());
            }
            if ("NUMAPROBADAS".equals(attribute_tag)){
                info.setNum_Matarias_Aprobadas(Integer.parseInt(tag.getTextContent().trim()));
            }
            if ("PROMEDIOCARRERA".equals(attribute_tag)){
                info.setPromedioCarrera(Double.parseDouble(tag.getTextContent().trim()));
            }
            if ("ESTA_REGISTRADO".equals(attribute_tag)){
                info.setRegistrado(tag.getTextContent().trim());
            }
            if ("COD_UNIDAD".equals(attribute_tag)){
                info.setUnidadAcademica(tag.getTextContent().trim());
            }
            if ("FECHA_NACIM".equals(attribute_tag)){
                info.setFecha_nacienmiento(tag.getTextContent().trim());
            }
        }
        return info;
    }

}
