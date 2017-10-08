package sh.locus.access.dao;

import sh.locus.access.entity.ActionType;

public interface ActionTypeDao {
    // TODO : exceptions
    void persist(ActionType actionType);
    void delete(String id) throws InvalidEntityException;
    ActionType get(String id) throws InvalidEntityException;
}
