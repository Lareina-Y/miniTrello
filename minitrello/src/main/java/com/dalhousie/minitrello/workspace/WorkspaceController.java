package com.dalhousie.minitrello.workspace;

import com.dalhousie.minitrello.boards.BoardsModel;
import com.dalhousie.minitrello.user.UserModel;
import com.dalhousie.minitrello.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workspace")
@CrossOrigin
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private UserService userService;

    @PostMapping("/save/{id}")
    public WorkspaceModel createWorkspace(@PathVariable("id")Long userID, @RequestBody WorkspaceModel workspaceModel){
        WorkspaceModel newWorkspace = workspaceService.createWorkspace(workspaceModel);
        userService.updateWorkspace(userID, newWorkspace.getId());
        return newWorkspace;
    }

    @GetMapping("/get_workspace/{id}")
    public WorkspaceModel getWorkspaceByID(@PathVariable("id")Long workspaceID) throws Exception{
        WorkspaceModel workspace = workspaceService.getWorkspaceByID(workspaceID);
        return workspace;
    }

    @GetMapping("/get_users/{id}")
    public List<UserModel> getUsersByWorkspaceId(@PathVariable("id") Long workspaceID) {
        List<UserModel> users =  workspaceService.getUsersByWorkspaceId(workspaceID);
        return users;
    }

    @GetMapping("/get_boards/{id}")
    public List<BoardsModel> getBoardsByWorkspaceId(@PathVariable("id") Long workspaceID) {
        List<BoardsModel> boards =  workspaceService.getBoardsByWorkspaceId(workspaceID);
        return boards;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteWorkSpace(@PathVariable("id")Long workspaceID){
        workspaceService.deleteWorkspaceByID(workspaceID);
    }

    @PutMapping("/update/name/{id}")
    public WorkspaceModel updateName(@PathVariable("id")Long workspaceID, @RequestBody Map<String, String> name){
        return workspaceService.updateName(workspaceID, name.get("name"));
    }

    @PutMapping("/update/description/{id}")
    public WorkspaceModel updateDescription(@PathVariable("id")Long workspaceID, @RequestBody Map<String, String>description){
        return workspaceService.updateDescription(workspaceID, description.get("description"));
    }

    @GetMapping("/get_all")
    public List<WorkspaceModel> getAllWorkspaces() {
        return workspaceService.getAllWorkspaces();
    }

    @PutMapping("/addBoardInWorkspace/{workspaceId}")
    public WorkspaceModel addUserInWorkspace(@PathVariable Long workspaceId, @RequestParam Long boardId) {
        return workspaceService.addBoardsInWorkspace(workspaceId, boardId);
    }

}
