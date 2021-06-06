package com.goddrinksjava.prep.filter;

import com.goddrinksjava.prep.model.bean.database.Privilegio;
import com.goddrinksjava.prep.model.bean.database.Usuario;
import com.goddrinksjava.prep.model.dao.UsuarioDAO;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

@WebFilter("/*")
public class UsuarioFilter implements Filter {
    @Inject
    UsuarioDAO usuarioDAO;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        Principal principal = request.getUserPrincipal();

        if (principal != null) {
            if (request.getAttribute("usuario") == null) {
                try {
                    Usuario usuario = usuarioDAO.findByEmail(principal.getName());
                    List<String> privilegioList = usuarioDAO.getPrivileges(usuario.getId());
                    request.setAttribute("usuario", usuario);
                    request.setAttribute("usuarioPrivilegios", privilegioList);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    throw new ServletException();
                }
            }
        }

        chain.doFilter(req, res);
    }
}