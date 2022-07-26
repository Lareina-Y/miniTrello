package com.dalhousie.minitrello.workspace;

import com.dalhousie.minitrello.user.UserModel;
import com.dalhousie.minitrello.boards.BoardsModel;

import java.util.List;

public interface WorkspaceService {
    WorkspaceModel createWorkspace(WorkspaceModel workspaceModel);
    WorkspaceModel getWorkspaceByID(Long id) throws Exception;
    void deleteWorkspaceByID(Long id);
    WorkspaceModel updateName(Long id, String name);
    WorkspaceModel updateDescription(Long id, String description);
    List<WorkspaceModel> getAllWorkspaces();
    WorkspaceModel addBoardsInWorkspace(Long workspace_ID, Long board_ID);
    List<UserModel> getUsersByWorkspaceId(Long workspaceID);
    List<BoardsModel> getBoardsByWorkspaceId(Long workspaceID);
}
