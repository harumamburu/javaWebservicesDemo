package com.my.lab.core.adapter;

import com.my.lab.core.adapter.exception.DataPersistenceException;
import com.my.lab.core.dto.BookDTO;
import com.my.lab.dao.db.DbBookDao;
import com.my.lab.dao.entity.BookJPAEntity;
import com.my.lab.dao.entity.mapper.frommapper.BookJPAFromDTOMapper;
import com.my.lab.dao.entity.mapper.tomapper.BookJPAToDTOMapper;
import com.my.lab.dao.exception.DaoException;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class BookAdapter extends AbstractAdapter<BookDTO> {

    @EJB
    private DbBookDao bookDao;

    @Override
    protected BookDTO saveEntityRoutine(BookDTO bookDTO) throws DaoException {
        return bookToDTO(bookDao.saveEntity(bookFromDTO(bookDTO)));
    }

    @Override
    protected BookDTO updateEntityRoutine(BookDTO bookDTO) throws DaoException {
        return bookToDTO(bookDao.updateEntity(bookFromDTO(bookDTO)));
    }

    @Override
    protected BookDTO getEntityRoutine(Integer id) throws DaoException {
        return bookToDTO(bookDao.getEntity(id));
    }

    @Override
    protected BookDTO deleteEntityRoutine(Integer id) throws DaoException {
        return bookToDTO(bookDao.deleteEntity(id));
    }

    private BookDTO bookToDTO(BookJPAEntity book) throws DataPersistenceException {
        return BookJPAToDTOMapper.INSTANCE.bookToDTO(book);
    }

    private BookJPAEntity bookFromDTO(BookDTO bookDTO) throws DataPersistenceException {
        return BookJPAFromDTOMapper.INSTANCE.bookFromDTO(bookDTO);
    }
}
