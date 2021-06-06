package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.model.dao.UsuarioDAO;
import com.goddrinksjava.prep.model.bean.database.Usuario;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@ApplicationScoped
public class PrepIdentityStore implements IdentityStore {
    @Inject
    UsuarioDAO usuarioDAO;

    public CredentialValidationResult validate(UsernamePasswordCredential credential) {

        try {
            Usuario usuario = usuarioDAO.findByEmail(credential.getCaller());
            System.out.println("PrepIdentityStore found user:" + usuario + ", caller: " + credential.getCaller());

            if (
                    usuario != null &&
                            usuario.getPassword().equals(credential.getPasswordAsString())
            ) {
                Set<String> groups = new HashSet<>(usuarioDAO.getPrivileges(usuario.getId()));

                return new CredentialValidationResult(usuario.getEmail(), groups);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return INVALID_RESULT;
    }

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        return validationResult.getCallerGroups();
    }
}
