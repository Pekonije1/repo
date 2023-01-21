package com.utils;

import com.office.exceptions.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;


public class ValidationUtils {
    public static <T> void checkIfObjectExists(T object, String errorMessage) throws ServiceException {
        if (object == null)
            throw new ServiceException(errorMessage);
        if (object instanceof String && StringUtils.isBlank((String) object))
            throw new ServiceException(errorMessage);
    }

    public static void checkStringLength(String str, Integer length, String errorMessage) throws ServiceException {
        if (str.length() > length)
            throw new ServiceException(errorMessage);
    }

    public static void checkUnnecessaryStringFieldLength(String str, Integer length, String errorMessage) throws ServiceException {
        if (str != null && str.length() > length)
            throw new ServiceException(errorMessage);
    }

    public static void checkIfEmailIsValid(String email, String errorMessage) throws ServiceException {
        if (!EmailValidator.getInstance().isValid(email))
            throw new ServiceException(errorMessage);
    }

}
