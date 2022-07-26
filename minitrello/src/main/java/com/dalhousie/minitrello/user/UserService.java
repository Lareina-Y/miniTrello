package com.dalhousie.minitrello.user;

import com.dalhousie.minitrello.workspace.WorkspaceModel;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

public interface UserService {
    UserModel addUser(UserModel userModel) throws Exception;
    UserModel getUserById(Long userId);
    List<UserModel> getAllUsers();
    void deleteUser(@PathVariable Long userId);
    UserModel searchUserByEmail(String email);
    String getSecurityQuestion(String email) throws Exception;
    boolean confirmAnswer(String email, String answer) throws Exception;
    UserModel updatePassword(String email, String newPassword) throws Exception;
    UserModel login(LoginModel loginModel);
    UserModel updateWorkspace(Long userId, Long workspaceId);
    UserModel removeWorkspace(Long userId, Long workspaceId);
    List<WorkspaceModel> getWorkspacesByUserId(Long userId);
}
