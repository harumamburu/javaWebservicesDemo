package com.my.lab.dao.db;

public interface Queries {

    String QUERY_PARAM_ID = "id";
    String QUERY_PARAM_NAME = "name";

    
    String BOOK_DELETE_BYID_QUERYNAME = "BookJPAEntity.DeleteById";
    String BOOK_DELETE_BYID_QUERY = "delete BookJPAEntity b where b.bookId = :" + QUERY_PARAM_ID;
    
    String BOOK_CHECK_BYID_QUERYNAME = "BookJPAEntity.CheckById";
    String BOOK_CHECK_BYID_QUERY = "select 1 from BookJPAEntity b where b.bookId = :" + QUERY_PARAM_ID;

    String BOOK_CHECK_BYNATURALID_QUERYNAME = "BookJPAEntity.CheckByNaturalId";
    String BOOK_CHECK_BYNATURALID_QUERY = "select b from BookJPAEntity b where b.name = :" + QUERY_PARAM_NAME;

    
    String AUTHOR_DELETE_BYID_QUERYNAME = "AuthorJPAEntity.DeleteById";
    String AUTHOR_DELETE_BYID_QUERY = "delete AuthorJPAEntity a where a.authorId = :" + QUERY_PARAM_ID;
    
    String AUTHOR_CHECK_BYID_QUERYNAME = "AuthorJPAEntity.CheckById";
    String AUTHOR_CHECK_BYID_QUERY = "select 1 from  AuthorJPAEntity a where a.authorId = :" + QUERY_PARAM_ID;

    String AUTHOR_CHECK_BYNATURALID_QUERYNAME = "AuthorJPAEntity.CheckByNaturalId";
    String AUTHOR_CHECK_BYNATURALID_QUERY = "select a from  AuthorJPAEntity a where a.name = :" + QUERY_PARAM_NAME;
}
