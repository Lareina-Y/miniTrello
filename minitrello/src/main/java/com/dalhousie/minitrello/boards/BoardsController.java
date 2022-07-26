package com.dalhousie.minitrello.boards;
import com.dalhousie.minitrello.workspace.WorkspaceService;
import com.dalhousie.minitrello.tasks.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@CrossOrigin
public class BoardsController {

    @Autowired
    private BoardsService service;

    @Autowired
    private WorkspaceService workspaceService;

    @PostMapping("/create/{id}")
    public BoardsModel createBoard(@RequestBody BoardsModel boardsModel, @PathVariable("id") Long workspace_ID) {
        BoardsModel newBoard = service.createBoard(boardsModel);
        workspaceService.addBoardsInWorkspace(workspace_ID, newBoard.getBoardId());
        return newBoard;
    }

    @PutMapping("/update/{id}/{name}")
    public BoardsModel updateBoard(@PathVariable("id") Long id, @PathVariable("name") String name) {
        return service.changeBoardName(id, name);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBoard(@PathVariable("id") Long id) {
        service.deleteBoard(id);
    }

    @GetMapping("/filter/{name}")
    public List<BoardsModel> filterBoards(@PathVariable("name") String name) {
        return service.filter(name);
    }

    @GetMapping("/get_board/{id}")
    public BoardsModel getBoardByID(@PathVariable("id") Long boardID) throws Exception {
        BoardsModel board = service.getBoardByID(boardID);
        return board;
    }

    @GetMapping("/get_all_tasks/{id}")
    public List<TaskModel> get_all_tasks(@PathVariable("id") Long boardID) {
        List<TaskModel> tasks = service.getTasksByBoardID(boardID);
        return tasks;
    }

}
