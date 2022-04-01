package com.kh.reading_fly.domain.booktest.svc;

import com.kh.reading_fly.domain.booktest.dao.BookTestDAO;
import com.kh.reading_fly.domain.booktest.dto.BookTestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BookTestSVCImpl implements BookTestSVC{

  private final BookTestDAO bookTestDAO;

  @Override
  public List<BookTestDTO> selectAll() {
    return bookTestDAO.selectAll();
  }

  @Override
  public BookTestDTO selectOne(Long isbn) {
    BookTestDTO bookTestDTO =  bookTestDAO.selectOne(isbn);

    return bookTestDTO;
  }

  @Override
  public BookTestDTO create(BookTestDTO bookTestDTO) {
    return bookTestDAO.create(bookTestDTO);
  }

  @Override
  public BookTestDTO update(BookTestDTO bookTestDTO) {
    return bookTestDAO.update(bookTestDTO);
  }
}
