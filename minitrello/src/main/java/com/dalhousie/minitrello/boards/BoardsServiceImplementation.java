package com.dalhousie.minitrello.boards;

import com.dalhousie.minitrello.tasks.TaskModel;
import com.dalhousie.minitrello.tasks.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardsServiceImplementation implements BoardsService {

    @Autowired
    private BoardsRepository repository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public BoardsModel createBoard(BoardsModel boardsModel) {
        return repository.save(boardsModel);
    }

    @Override
    public void deleteBoard(Long id) {
        repository.deleteById(id);
    }

    @Override
    public BoardsModel changeBoardName(Long id, String name) {
        BoardsModel boardsModel = repository.findByBoardId(id);
        boardsModel.setName(name);
        return repository.save(boardsModel);
    }

    @Override
    public List<BoardsModel> filter(String name) {
        return repository.findByNameContaining(name);
    }

    @Override
    public BoardsModel getBoardByID(Long ID) throws Exception{
        BoardsModel board = null;
        Optional<BoardsModel> optionalBoard = repository.findById(ID);

        if(optionalBoard.isPresent()){
            board = optionalBoard.get();
            return board;
        } else{
            throw new Exception("Board does not exist");
        }
    }

    @Override
    public BoardsModel addTasksInBoard(Long boardID, Long taskID) {

        BoardsModel updatedBoard = null;
        Optional<BoardsModel> optionalBoard;
        Optional<TaskModel> optionalTask;

        try{
            optionalBoard = repository.findById(boardID);
            optionalTask = taskRepository.findById(taskID);

            if (optionalBoard.isPresent() && optionalTask.isPresent()){

                BoardsModel boardsModel = optionalBoard.get();
                TaskModel taskModel = optionalTask.get();

                List<TaskModel> tasks = boardsModel.getTasks();
                tasks.add(taskModel);
                boardsModel.setTasks(tasks);

                updatedBoard = repository.save(boardsModel);
            }

        }
        catch (Exception e){

            e.printStackTrace();
        }

        return updatedBoard;

    }

    @Override
    public List<TaskModel> getTasksByBoardID(Long id){

        Optional<BoardsModel> optionalBoard = null;
        List<TaskModel> tasks = null;

        try{
           optionalBoard = repository.findById(id);

           if (optionalBoard.isPresent()){
               BoardsModel boards = optionalBoard.get();
               tasks = boards.getTasks();
           }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return tasks;
    }
}
