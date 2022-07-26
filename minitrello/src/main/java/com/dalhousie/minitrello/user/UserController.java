package com.dalhousie.minitrello.user;

import com.dalhousie.minitrello.workspace.WorkspaceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController
{
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserModel addUser(@RequestBody UserModel userModel) throws Exception {
        return userService.addUser(userModel);
    }

    @GetMapping("/get_user/{id}")
    public UserModel getUserById(@PathVariable("id") Long userId) {
        UserModel user =  userService.getUserById(userId);
        return user;
    }

    @GetMapping("/get_workspaces/{id}")
    public List<WorkspaceModel> getWorkspacesByUserId(@PathVariable("id") Long userId) {
        List<WorkspaceModel> workspaces =  userService.getWorkspacesByUserId(userId);
        return workspaces;
    }

    @GetMapping("/get_all_users")
    public List<UserModel> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/forget_password/{email}")
    public String getSecurityQuestion(@PathVariable("email") String email) throws Exception {
        return userService.getSecurityQuestion(email);
    }

    @GetMapping("/confirm_answer/{email}/{answer}")
    public boolean confirmAnswer(
            @PathVariable("email") String email, @PathVariable("answer") String answer) throws Exception {
        return userService.confirmAnswer(email, answer);
    }

    @PutMapping("/update_password/{email}")
    public UserModel updatePassword(@PathVariable("email") String email, @RequestBody Map<String, String> password) throws Exception{
        return userService.updatePassword(email, password.get("password"));
    }

    @PostMapping("/login")
    public UserModel login(@RequestBody LoginModel loginModel){
        return userService.login(loginModel);
    }

    @PutMapping("/addUserInWorkspace/{userId}")
    public UserModel addUserInWorkspace(@PathVariable Long userId, @RequestParam Long workspaceId) {
        return userService.updateWorkspace(userId, workspaceId);
    }

    @PutMapping("/removeUserFromWorkspace/{userId}")
    public UserModel removeUserFromWorkspace(@PathVariable Long userId, @RequestParam Long workspaceId) {
        return userService.removeWorkspace(userId, workspaceId);
    }

}
