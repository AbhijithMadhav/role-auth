package sh.locus.access.service;

import sh.locus.access.dao.InvalidEntityException;
import sh.locus.access.entity.ActionType;

public interface ActionTypeService {
    ActionType get(String value) throws InvalidEntityException;

    void persist(ActionType actionType);
}
