package com.dyrs.smjj.itracker.util;

/**
 * Created by wangcl on 16-9-3.
 */
import com.dyrs.smjj.itracker.entity.Department;

import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * @author <a href="http://community.jboss.org/people/bleathem">Brian Leathem</a>
 */
@FacesConverter("PickConverter")
public class PickConverter implements Converter {
    private Department department;

    public Object getAsObject(FacesContext facesContext, UIComponent component, String s) {

        return s;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object o) {
        if (o == null) return null;
        if(o.getClass().equals(String.class)){
            return o.toString();
        }
        return ((Department) o).getName();
    }
}
