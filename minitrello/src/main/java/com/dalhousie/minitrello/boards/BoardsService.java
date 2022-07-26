package com.dalhousie.minitrello.boards;

import com.dalhousie.minitrello.tasks.TaskModel;

import java.util.List;

public interface BoardsService {
    BoardsModel createBoard(BoardsModel boardsModel);
    void deleteBoard(Long id);
    BoardsModel changeBoardName(Long id, String name);
    List<BoardsModel> filter(String name);
    BoardsModel getBoardByID(Long ID) throws Exception;

    BoardsModel addTasksInBoard(Long boardID, Long taskID) throws Exception;
    List<TaskModel> getTasksByBoardID(Long boardID);


}
