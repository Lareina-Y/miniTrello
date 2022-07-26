package com.dalhousie.minitrello.workspace;

import com.dalhousie.minitrello.boards.BoardsRepository;
import com.dalhousie.minitrello.user.UserModel;
import com.dalhousie.minitrello.boards.BoardsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkspaceServiceImplementation implements WorkspaceService {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private BoardsRepository boardsRepository;

    @Override
    public WorkspaceModel createWorkspace(WorkspaceModel workspaceModel){
        return workspaceRepository.save(workspaceModel);
    }

    @Override
    public WorkspaceModel getWorkspaceByID(Long id) throws Exception {

        WorkspaceModel workspace = null;
        Optional<WorkspaceModel> optionalWorkspace = workspaceRepository.findById(id);

        if (optionalWorkspace.isPresent()) {
            workspace = optionalWorkspace.get();
            return workspace;
        } else{
            throw new Exception("Workspace does not exist");
        }
    }

    @Override
    public void deleteWorkspaceByID(Long id){
        workspaceRepository.deleteById(id);
    }

    @Override
    public WorkspaceModel updateName(Long id, String name){

        WorkspaceModel workspace = null;
        WorkspaceModel updatedWorkspace = null;
        Optional<WorkspaceModel> optionalWorkspace = workspaceRepository.findById(id);
        if(optionalWorkspace.isPresent()){
            workspace = optionalWorkspace.get();
            workspace.setName(name);
            updatedWorkspace = workspaceRepository.save(workspace);
        }

        return updatedWorkspace;
    }

    @Override
    public WorkspaceModel updateDescription(Long id, String description){
        WorkspaceModel workspace = null;
        WorkspaceModel updatedWorkspace = null;
        Optional<WorkspaceModel> optionalWorkspace = workspaceRepository.findById(id);
        if(optionalWorkspace.isPresent()){
            workspace = optionalWorkspace.get();
            workspace.setDescription(description);
            updatedWorkspace = workspaceRepository.save(workspace);
        }

        return updatedWorkspace;
    }

    @Override
    public List<WorkspaceModel> getAllWorkspaces(){
        return workspaceRepository.findAll();
    }

    @Override
    public List<UserModel> getUsersByWorkspaceId(Long workspaceId) {
        Optional<WorkspaceModel> workspace  = null;
        List<UserModel> users = null;
        try {
            workspace = workspaceRepository.findById(workspaceId);
            if(workspace.isPresent()) {
                WorkspaceModel workspaceModel = workspace.get();
                users = workspaceModel.getUsers();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return users;
    }

    @Override
    public List<BoardsModel> getBoardsByWorkspaceId(Long workspaceId) {
        Optional<WorkspaceModel> workspace  = null;
        List<BoardsModel> boards = null;
        try {
            workspace = workspaceRepository.findById(workspaceId);
            if(workspace.isPresent()) {
                WorkspaceModel workspaceModel = workspace.get();
                boards = workspaceModel.getBoards();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return boards;
    }

    @Override
    public WorkspaceModel addBoardsInWorkspace(Long workspace_ID, Long board_ID){

        WorkspaceModel updatedWorkspace = null;
        Optional<WorkspaceModel> workspace;

        try{
            workspace = workspaceRepository.findById(workspace_ID);
            if(workspace.isPresent()){
                WorkspaceModel workspaceModel = workspace.get();
                BoardsModel boardModel = boardsRepository.findByBoardId(board_ID);

                List<BoardsModel> boards = workspaceModel.getBoards();
                boards.add(boardModel);
                workspaceModel.setBoards(boards);

                updatedWorkspace = workspaceRepository.save(workspaceModel);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        return updatedWorkspace;
    }

}



