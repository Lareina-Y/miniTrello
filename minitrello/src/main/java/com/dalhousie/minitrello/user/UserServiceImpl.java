package com.dalhousie.minitrello.user;

import com.dalhousie.minitrello.workspace.WorkspaceModel;
import com.dalhousie.minitrello.workspace.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Override
    public UserModel addUser(UserModel userModel) throws Exception {

        UserModel user = searchUserByEmail(userModel.getEmail());

        if(user != null) {
            throw new Exception("This email is already linked");
        } else if (!passwordCheck(userModel.getPassword())){
            throw new Exception("Invalid Password!");
        }

        return userRepository.save(userModel);
    }

    @Override
    public UserModel getUserById(Long userId) {
        UserModel user = null;
        Optional<UserModel> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent())
        {
            user = optionalUser.get();
        }
        return user;
    }

    @Override
    public List<UserModel> getAllUsers()
    {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(@PathVariable Long userId)
    {
        userRepository.deleteById(userId);
    }

    @Override
    public UserModel searchUserByEmail(String email) {
        UserModel userByEmail = null;
        List<UserModel> allUsers = getAllUsers();
        for(UserModel user: allUsers) {
            if (user.getEmail().equals(email)){
                userByEmail = user;
                break;
            }
        }
        return userByEmail;
    }

    @Override
    public String getSecurityQuestion(String email) throws Exception {
        UserModel user = searchUserByEmail(email);
        if(user == null) {
            throw new Exception("This email is not registered");
        }
        return user.getQuestion();
    }

    @Override
    public boolean confirmAnswer(String email, String answer) throws Exception {
        UserModel user = searchUserByEmail(email);
        if(user == null) {
            throw new Exception("This email is not registered");
        }

        return user.getAnswer().equals(answer);
    }

    @Override
    public UserModel updatePassword(String email, String newPassword) throws Exception {
        UserModel updatedUser = null;
        if(passwordCheck(newPassword)) {
            try {
                UserModel user = searchUserByEmail(email);
                if (user != null) {
                    user.setPassword(newPassword);
                    updatedUser = userRepository.save(user);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return updatedUser;
        } else{
            throw new Exception("Invalid Password!");
        }
    }

    @Override
    public UserModel login(LoginModel loginModel){
        UserModel user = searchUserByEmail(loginModel.getEmail());
        if(user != null) {
            if (user.getPassword().equals(loginModel.getPassword())) {
                return user;
            }
        }

        return new UserModel();
    }

    @Override
    public UserModel updateWorkspace(Long userId, Long workspaceId) {
        UserModel updatedUser = null;
        Optional<UserModel> user = null;
        try {
            user = userRepository.findById(userId);
            if(user.isPresent()) {
                UserModel userModel = user.get();
                Optional<WorkspaceModel> workspaceModel = workspaceRepository.findById(workspaceId);
                if(workspaceModel.isPresent()) {
                    WorkspaceModel workspace = workspaceModel.get();
                    List<WorkspaceModel> workspaces = userModel.getWorkspaces();
                    if (!workspaces.contains(workspace)) {
                        workspaces.add(workspace);
                    }
                    userModel.setWorkspaces(workspaces);

                    updatedUser = userRepository.save(userModel);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return updatedUser;
    }

    @Override
    public UserModel removeWorkspace(Long userId, Long workspaceId) {
        UserModel updatedUser = null;
        Optional<UserModel> user = null;
        try {
            user = userRepository.findById(userId);
            if(user.isPresent()) {
                UserModel userModel = user.get();
                Optional<WorkspaceModel> workspaceModel = workspaceRepository.findById(workspaceId);

                if(workspaceModel.isPresent()) {
                    WorkspaceModel workspace = workspaceModel.get();
                    List<WorkspaceModel> workspaces = userModel.getWorkspaces();
                    workspaces.remove(workspace);
                    userModel.setWorkspaces(workspaces);

                    updatedUser = userRepository.save(userModel);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return updatedUser;
    }

    @Override
    public List<WorkspaceModel> getWorkspacesByUserId(Long userId) {
        Optional<UserModel> user = null;
        List<WorkspaceModel> workspaces = null;
        try {
            user = userRepository.findById(userId);
            if(user.isPresent()) {
                UserModel userModel = user.get();
                workspaces = userModel.getWorkspaces();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return workspaces;
    }

    public boolean passwordCheck(String password){
        String regex = "^(?=.*?\\d+)(?=.*?[A-Z])(?=.*?[a-z])(?=.*?\\W).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher((password));
        return matcher.matches();
    }

}
