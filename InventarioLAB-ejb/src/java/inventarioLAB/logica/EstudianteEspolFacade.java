/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.logica;

import inventarioLAB.logica.beansAdicionales.InformacionEstudianteESPOL;
import inventarioLAB.entidades.EstudianteEspol;
import inventarioLAB.webServicesClient.ESPOL_SAAC.InformacionAcademicaEstudianteGetResponse.InformacionAcademicaEstudianteGetResult;
import inventarioLAB.webServicesClient.ESPOL_SAAC.WsSAAC;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.ws.WebServiceRef;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Edward J. Holguín Holguín
 */
@Stateless
public class EstudianteEspolFacade extends AbstractFacade<EstudianteEspol> implements EstudianteEspolFacadeLocal {
    @WebServiceRef(wsdlLocation = "META-INF/wsdl/www.academico.espol.edu.ec/Services/wsSAAC.asmx.wsdl")
    private WsSAAC service;
    @PersistenceContext(unitName = "InventarioLAB-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstudianteEspolFacade() {
        super(EstudianteEspol.class);
    }
    
    private InformacionEstudianteESPOL obtenerEstudianteEspol(EstudianteEspol estudiante) {
        InformacionEstudianteESPOL info = new InformacionEstudianteESPOL();
        return info;
    }
    private InformacionAcademicaEstudianteGetResult informacionAcademicaEstudianteGet(java.lang.String identificacion, java.lang.String matricula) {
        inventarioLAB.webServicesClient.ESPOL_SAAC.WsSAACSoap port = service.getWsSAACSoap12();
        return port.informacionAcademicaEstudianteGet(identificacion, matricula);
    }
    
    private void procesarInformacionAcademicaEstudiante(InformacionAcademicaEstudianteGetResult resultado){
        //RESULTADO EN DOM
        //Recodar que todo lo que tenemos son objetos por eso da null al leer
        for(Object rset: resultado.getAny())
            System.out.println("Resultado: " + rset);


        //ASI SE DEBE LEER
        //LEEMOS PRIMERO LA TAG <xs:schema>
        Element tagSchema = (Element)resultado.getAny().get(0); //TAG <SCHEMA>
        System.out.println("RESPUESTA: ");
        System.out.println("TAG<SCHEMA>: " + tagSchema.getTagName());
        System.out.println("TAG-ATTRIBUTE: " + tagSchema.getAttribute("id"));//EJEMPLO DE UN ATRIBUTO
        System.out.println("-----------");

        //xs:element name="NewDataSet"
        NodeList schema_elemets = tagSchema.getElementsByTagName("xs:element");
        Element newDataSet_elemet = (Element)schema_elemets.item(0);//xs:element name="NewDataSet"
        System.out.println(newDataSet_elemet.getAttribute("name"));
        System.out.println("-----------");

        //xs:element name="DATOS_USUARIO"
        NodeList newDataSet_elemets = newDataSet_elemet.getElementsByTagName("xs:element");
        Element datos_usuario_element = (Element)newDataSet_elemets.item(0);//xs:element name="DATOS_USUARIO"
        System.out.println(datos_usuario_element.getAttribute("name"));
        System.out.println("-----------");

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


            System.out.println(attribute_tag + ": " + tag.getTextContent());
       }
    }
}
