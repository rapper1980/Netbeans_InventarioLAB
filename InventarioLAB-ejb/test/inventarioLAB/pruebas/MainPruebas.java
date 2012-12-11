/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarioLAB.pruebas;

import inventarioLAB.logica.beansAdicionales.InformacionEstudianteESPOL;
import inventarioLAB.webServicesClient.ESPOL.DatosUsuarioResponse.DatosUsuarioResult;
import inventarioLAB.webServicesClient.ESPOL_SAAC.InformacionAcademicaEstudianteGetResponse.InformacionAcademicaEstudianteGetResult;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Edward J. Holguín Holguín
 */
public class MainPruebas {
    public static void main(String args[]){
        InformacionEstudianteESPOL infoEstudiantes;
        String user = "edwjholg";
        String password = "gp07jkh4";
        System.out.println("Autenticacion: " + autenticacion(user, password));
        System.out.println("Datos Usuario: ");
        //procesarDatosUsuario(datosUsuario(user, password));
        infoEstudiantes = procesarInformacionAcademicaEstudiante(informacionAcademicaEstudianteGet("", "200733517"));
        System.out.println(infoEstudiantes);
        
    }

    private static boolean autenticacion(java.lang.String varUser, java.lang.String varContrasenia) {
        inventarioLAB.webServicesClient.ESPOL.DirectorioEspol service = new inventarioLAB.webServicesClient.ESPOL.DirectorioEspol();
        inventarioLAB.webServicesClient.ESPOL.DirectorioEspolSoap port = service.getDirectorioEspolSoap12();
        return port.autenticacion(varUser, varContrasenia);
    }

    private static DatosUsuarioResult datosUsuario(java.lang.String varUser, java.lang.String varContrasenia) {
        inventarioLAB.webServicesClient.ESPOL.DirectorioEspol service = new inventarioLAB.webServicesClient.ESPOL.DirectorioEspol();
        inventarioLAB.webServicesClient.ESPOL.DirectorioEspolSoap port = service.getDirectorioEspolSoap12();
        return port.datosUsuario(varUser, varContrasenia);
    }
    
    private static InformacionAcademicaEstudianteGetResult informacionAcademicaEstudianteGet(java.lang.String identificacion, java.lang.String matricula) {
        inventarioLAB.webServicesClient.ESPOL_SAAC.WsSAAC service = new inventarioLAB.webServicesClient.ESPOL_SAAC.WsSAAC();
        inventarioLAB.webServicesClient.ESPOL_SAAC.WsSAACSoap port = service.getWsSAACSoap12();
        return port.informacionAcademicaEstudianteGet(identificacion, matricula);
    }
    
    private static void procesarDatosUsuario(DatosUsuarioResult resultado){
        //RESULTADO EN DOM
        //Recodar que todo lo que tenemos son objetos por eso da null al leer
        for(Object rset: resultado.getAny()) {
            System.out.println("Resultado: " + rset);
        }


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
    
    private static InformacionEstudianteESPOL procesarInformacionAcademicaEstudiante(InformacionAcademicaEstudianteGetResult resultado){
        InformacionEstudianteESPOL info;
        info = new InformacionEstudianteESPOL();
        //RESULTADO EN DOM
        //Recodar que todo lo que tenemos son objetos por eso da null al leer
//        for(Object rset: resultado.getAny()) {
//            System.out.println("Resultado: " + rset);
//        }


        //ASI SE DEBE LEER
        //LEEMOS PRIMERO LA TAG <xs:schema>
        Element tagSchema = (Element)resultado.getAny().get(0); //TAG <SCHEMA>
//        System.out.println("RESPUESTA: ");
//        System.out.println("TAG<SCHEMA>: " + tagSchema.getTagName());
//        System.out.println("TAG-ATTRIBUTE: " + tagSchema.getAttribute("id"));//EJEMPLO DE UN ATRIBUTO
//        System.out.println("-----------");

        //xs:element name="NewDataSet"
        NodeList schema_elemets = tagSchema.getElementsByTagName("xs:element");
        Element newDataSet_elemet = (Element)schema_elemets.item(0);//xs:element name="NewDataSet"
//        System.out.println(newDataSet_elemet.getAttribute("name"));
//        System.out.println("-----------");

        //xs:element name="DATOS_USUARIO"
        NodeList newDataSet_elemets = newDataSet_elemet.getElementsByTagName("xs:element");
        Element datos_usuario_element = (Element)newDataSet_elemets.item(0);//xs:element name="DATOS_USUARIO"
//        System.out.println(datos_usuario_element.getAttribute("name"));
//        System.out.println("-----------");

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


//            System.out.println(attribute_tag + ": " + tag.getTextContent());
            
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
