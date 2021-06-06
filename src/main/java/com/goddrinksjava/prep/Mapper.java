package com.goddrinksjava.prep;

import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

@ApplicationScoped
public class Mapper {
    @Inject
    Validator validator;

    public <T> T getDTO(Class<T> tClass, HttpServletRequest request) throws ServletException {
        T t;
        try {
            t = tClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new ServletException();
        }

        try {
            BeanUtils.populate(t, request.getParameterMap());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }

        Set<ConstraintViolation<T>> violations = validator.validate(t);

        if (violations.isEmpty()) {
            return t;
        }

        return null;
    }
}
