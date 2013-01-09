package inventarioLAB.util;

import inventarioLAB.entidades.Equipo;
import inventarioLAB.entidades.TipoEquipo;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

public class JsfUtil {

    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size-1];
        int i = 0;
//        if (selectOne) {
//            //items[0] = new SelectItem("", "---");
//            i++;
//        }
        for (Object x : entities) {
            items[i] = new SelectItem(x, x.toString());
            i++;
        }
        
        return items;
    }

    public static List<TipoEquipo> getAllItems(List<TipoEquipo> entities, List<TipoEquipo> todosTipos) {
        int size = entities.size();        
        int i=0;
        for (Object x : entities){
            TipoEquipo tmp = new TipoEquipo();
            tmp = (TipoEquipo)x;
            todosTipos.add(tmp);
            i++;
        }
        return todosTipos;
    }
    public static List<Equipo> getAllPrestables(List<Equipo> allEquipos) {
        List<Equipo> todosPrestables = new ArrayList<Equipo>();
        for(Object x : allEquipos){            
            Equipo tmp = new Equipo();
            tmp = (Equipo)x;
            if(tmp.getEstado().equalsIgnoreCase("DIS"))
            {
                todosPrestables.add(tmp);
            }
        }
        return todosPrestables;
    }
    public static List<Equipo> getAllEquipos(List<Equipo> entities, List<Equipo> todosEquipos) {
        int size = entities.size();        
        int i=0;
        for (Object x : entities){
            Equipo tmp = new Equipo();
            tmp = (Equipo)x;
            todosEquipos.add(tmp);
            i++;
        }
        return todosEquipos;
    }
    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);
        }
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }
}