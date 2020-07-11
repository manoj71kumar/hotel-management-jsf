package com.hotels.filter;

import com.hotels.domain.Users;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Users session = (Users) request.getSession().getAttribute("user");
        String url = request.getRequestURI();
        if(session == null || !session.isLogged) {
            if (url.indexOf("createbooking.xhtml") >= 0)
                response.sendRedirect(request.getServletContext().getContextPath() + "/login.xhtml");
            else
                filterChain.doFilter(request, response);
        }
        else {
            if(url.indexOf("login.xhtml")>=0){
                response.sendRedirect(request.getServletContext().getContextPath()+"/index.xhtml");
            }
            else if(url.indexOf("logout.xhtml")>=0){
                request.getSession().removeAttribute("user");
                response.sendRedirect((request.getServletContext().getContextPath()+"/index.xhtml"));
            }
            else
                filterChain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {

    }

}
