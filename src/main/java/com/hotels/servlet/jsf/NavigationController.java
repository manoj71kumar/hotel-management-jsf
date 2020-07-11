package com.hotels.servlet.jsf;

import com.hotels.domain.Users;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.security.Principal;

@ManagedBean(name = "navigationController", eager = true)
@RequestScoped
public class NavigationController implements Serializable {

    @ManagedProperty(value = "#{param.pageId}")
    private String pageId;

    public String showPage() {
        if (pageId==null)
            return "/index?faces-redirect=true";

        if (pageId.equals("1"))
            return "/admin?faces-redirect=true";
        if (pageId.equals("2"))
            return "admin/rooms?faces-redirect=true";
        if (pageId.equals("3"))
            return "admin/users?faces-redirect=true";
        if (pageId.equals("4"))
            return "admin/booking?faces-redirect=true";
        if (pageId.equals("10"))
            return "/createuser?faces-redirect=true";
        if (pageId.equals("11"))
            return "/createbooking?faces-redirect=true";
        if (pageId.equals("12"))
            return "/roomuser?faces-redirect=true";
        if (pageId.equals("13"))
            return "/login?faces-redirect=true";
        if (pageId.equals("999")) {
            logout();
            return "/index?faces-redirect=true";
        }
        if (pageId.equals("888")) {
            logout();
            return "/admin?faces-redirect=true";
        }
        return "/index?faces-redirect=true";

    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    private void logout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().invalidateSession();
    }

    public boolean isLogoutRendered() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal pr =request.getUserPrincipal();
        return pr != null;
    }
}
