package biz.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class CountryCodeAlreadyExistingException extends Exception {

    private static final long serialVersionUID = 1L;
    
}
