package com.my.lab.dao.db;

import com.my.lab.dao.entity.AuthorJPAEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.Map;

@Stateless
@LocalBean
public class DbAuthorDao extends AbstractDbDao<AuthorJPAEntity> {

    @Override
    public Class<AuthorJPAEntity> getEntityClass() {
        return AuthorJPAEntity.class;
    }

    @Override
    public Boolean contains(Integer id) {
        return executeQuerySingleResult(Queries.AUTHOR_CHECK_BYID_QUERYNAME,
                getSingleParamMap(Queries.QUERY_PARAM_ID, id)) != null;
    }

    @Override
    public AuthorJPAEntity getEntityByNaturalId(Map<String, ?> naturalId) {
        return executeQuerySingleResult(Queries.BOOK_CHECK_BYNATURALID_QUERYNAME, naturalId);
    }
}
