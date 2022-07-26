package com.dalhousie.minitrello.boards;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardsRepository extends JpaRepository<BoardsModel, Long> {
    BoardsModel findByBoardId(Long id);
    List<BoardsModel> findByNameContaining(String name);
}
