package biz.manager;

import biz.exception.CategoryDoesNotExistException;
import biz.exception.CategoryAlreadyExistingException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Category;

@Stateless
public class CategoryManager {
    
    @PersistenceContext(unitName="BankAppPU")
    private EntityManager em;

    private final List<Category> categories = new ArrayList<>();

    public CategoryManager() {}
    
    @Lock(LockType.WRITE) // pas obligé, LockType par défault
    public  Category save(String label) throws CategoryAlreadyExistingException {
        for (Category category : categories) {
            if (category.getLabel().equals(label)) {
                throw new CategoryAlreadyExistingException();
            }
        }
        
        Category newCategory = new Category(null,label);//"CategoryManagerL33");
        em.persist(newCategory);
        
        categories.add(newCategory);
        return newCategory;
    }

    @Lock(LockType.READ)
    public Category getByLabel(String label) throws CategoryDoesNotExistException {
        for (Category category : categories) {
            if (category.getLabel().equals(label)) {
                return category;
            }
        }
        throw new CategoryDoesNotExistException();
    }

}

/*

    @Lock(LockType.WRITE)
    public CountryCode save(String label) throws CategoryAlreadyExistingException {
        for (CountryCode countrycode : countrycodes) {
            if (countrycode.getCode().equals(label)) {
                throw new CategoryAlreadyExistingException();
            }
        }

        CountryCode newCountryCode = new CountryCode(null, label);
        this.entityManager.persist(newCountryCode);

        this.countrycodes.add(newCountryCode);
        return newCountryCode;
    }

    @Lock(LockType.READ)
    public CountryCode getByCode(String code) throws CategoryDoesNotExistException {
        try {
            return this.entityManager.createQuery("SELECT c FROM CountryCode c WHERE c.code = :code", CountryCode.class)
                    .setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            throw new CategoryDoesNotExistException();
        }
    }
}
*/