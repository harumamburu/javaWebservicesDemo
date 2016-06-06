package com.my.lab.web.service;

import com.my.lab.core.adapter.BookAdapter;
import com.my.lab.core.dto.BookDTO;
import com.my.lab.web.entity.BookWebEntity;
import com.my.lab.web.entity.mapper.frommapper.BookWebFromDTOMapper;
import com.my.lab.web.entity.mapper.tomapper.BookWebToDTOMapper;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class BookService implements Service<BookWebEntity> {

    @EJB
    private BookAdapter bookAdapter;

    @Override
    public BookWebEntity onGet(Integer id) {
        return bookFromDTO(bookAdapter.getEntity(id));
    }

    @Override
    public BookWebEntity onPost(BookWebEntity book) {
        return bookFromDTO(bookAdapter.saveEntity(bookToDTO(book)));
    }

    @Override
    public BookWebEntity onPut(BookWebEntity book) {
        return bookFromDTO(bookAdapter.updateEntity(bookToDTO(book)));
    }

    @Override
    public BookWebEntity onDelete(Integer id) {
        return bookFromDTO(bookAdapter.deleteEntity(id));
    }

    private BookDTO bookToDTO(BookWebEntity book) {
        return BookWebToDTOMapper.INSTANCE.bookToDTO(book);
    }

    private BookWebEntity bookFromDTO(BookDTO bookDTO) {
        return BookWebFromDTOMapper.INSTANCE.bookFromDTO(bookDTO);
    }
}
