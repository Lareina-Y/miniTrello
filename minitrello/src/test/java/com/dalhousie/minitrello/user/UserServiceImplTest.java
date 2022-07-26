package com.dalhousie.minitrello.user;

import com.dalhousie.minitrello.workspace.WorkspaceModel;
import com.dalhousie.minitrello.workspace.WorkspaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    @Autowired
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl = new UserServiceImpl();

    @Mock
    @Autowired
    private WorkspaceRepository workspaceRepository;

    private UserModel userModel;

    private LoginModel loginModel;

    @BeforeEach
    public void setUp() {
        userModel = new UserModel();
        userModel.setUser_id(1L);
        userModel.setPassword("@qweQE12423");
        userModel.setEmail("123456@163.com");
        userModel.setQuestion("are you ok ?");
        userModel.setAnswer("xxx");

        loginModel = new LoginModel();
        loginModel.setPassword("@qweQE12423");
        loginModel.setEmail("123456@163.com");
    }

    @Test
    public void addUser() throws Exception {
        Mockito.when(userRepository.save(userModel)).thenReturn(userModel);
        UserModel userModel1 = userServiceImpl.addUser(userModel);
        assertNotNull(userModel1);
    }

    @Test
    public void getUserById(){
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(userModel));
        assertEquals(userModel, userServiceImpl.getUserById(1L));
    }

    @Test
    public void getUserById_null(){
        assertNull(userServiceImpl.getUserById(1L));
    }

    @Test
    public void  getAllUsers() {
        List<UserModel> allUsers = userServiceImpl.getAllUsers();
        assertNotNull(allUsers);
    }

    @Test
    public void deleteUser(){
        Mockito.doNothing().when(userRepository).deleteById(any());
        userServiceImpl.deleteUser(1L);
    }

    @Test
    public void searchUserByEmail_null(){
        UserModel userModel1 = userServiceImpl.searchUserByEmail(userModel.getEmail());
        assertNull(userModel1);
    }

    @Test
    public void getSecurityQuestion_null() {
        assertThrows(Exception.class, ()->userServiceImpl.getSecurityQuestion(userModel.getEmail()));
    }

    @Test
    public void getSecurityQuestion() throws Exception {
        List<UserModel> users = new ArrayList<UserModel>();
        users.add(userModel);

        Mockito.when(userRepository.findAll()).thenReturn(users);
        String question = userServiceImpl.getSecurityQuestion(userModel.getEmail());
        assertEquals(userModel.getQuestion(), question);
    }

    @Test
    public void confirmAnswer_null () {
        assertThrows(Exception.class, ()->userServiceImpl.confirmAnswer(userModel.getEmail(), userModel.getAnswer()));
    }

    @Test
    public void confirmAnswer() throws Exception {
        List<UserModel> users = new ArrayList<UserModel>();
        users.add(userModel);

        Mockito.when(userRepository.findAll()).thenReturn(users);
        boolean actual = userServiceImpl.confirmAnswer(userModel.getEmail(), userModel.getAnswer());
        assertTrue(actual);
    }

    @Test
    public void updatePassword() throws Exception {
        List<UserModel> users = new ArrayList<UserModel>();
        users.add(userModel);

        Mockito.when(userRepository.findAll()).thenReturn(users);
        Mockito.when(userRepository.save(any())).thenReturn(userModel);
        String actual = userServiceImpl.updatePassword(userModel.getEmail(), "@qweQWE1234").getPassword();
        assertEquals("@qweQWE1234", actual);
    }

    @Test
    public void login(){
        List<UserModel> users = new ArrayList<UserModel>();
        users.add(userModel);

        Mockito.when(userRepository.findAll()).thenReturn(users);
        UserModel login = userServiceImpl.login(loginModel);
        assertEquals(userModel.getUser_id(), login.getUser_id());
    }

    @Test
    public void updateWorkspace(){
        WorkspaceModel workspaceModel = new WorkspaceModel();
        workspaceModel.setId(1L);

        List<WorkspaceModel> workspaces = new ArrayList<WorkspaceModel>();
        userModel.setWorkspaces(workspaces);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(userModel));
        Mockito.when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspaceModel));
        Mockito.when(userRepository.save(any())).thenReturn(userModel);

        UserModel update = userServiceImpl.updateWorkspace(1L, 1L);
        assertTrue(update.getWorkspaces().contains(workspaceModel));
    }

    @Test
    public void removeWorkspace(){
        WorkspaceModel workspaceModel = new WorkspaceModel();
        workspaceModel.setId(1L);

        List<WorkspaceModel> workspaces = new ArrayList<WorkspaceModel>();
        workspaces.add(workspaceModel);
        userModel.setWorkspaces(workspaces);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(userModel));
        Mockito.when(workspaceRepository.findById(1L)).thenReturn(Optional.of(workspaceModel));
        Mockito.when(userRepository.save(any())).thenReturn(userModel);

        UserModel update = userServiceImpl.removeWorkspace(1L, 1L);
        assertTrue(!update.getWorkspaces().contains(workspaceModel));
    }

    @Test
    public void getWorkspacesByUserId() {
        List<WorkspaceModel> workspaces = new ArrayList<WorkspaceModel>();
        userModel.setWorkspaces(workspaces);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(userModel));
        List<WorkspaceModel> listOfWorkspaces = userServiceImpl.getWorkspacesByUserId(1L);
        assertEquals(workspaces, listOfWorkspaces);
    }

    @Test
    public void passwordCheck() {
        assertTrue(userServiceImpl.passwordCheck("@qww123QQWW"));
    }

    @Test
    public void passwordCheck_missNumber() {
        assertFalse(userServiceImpl.passwordCheck("@qwwafQQWW"));
    }

    @Test
    public void passwordCheck_missSpecialSymbol() {
        assertFalse(userServiceImpl.passwordCheck("1234qwwafQQWW"));
    }

    @Test
    public void passwordCheck_missCapital() {
        assertFalse(userServiceImpl.passwordCheck("1234qwwaf!!df"));
    }

    @Test
    public void passwordCheck_missSmall() {
        assertFalse(userServiceImpl.passwordCheck("1234QFGH!!Q"));
    }

    @Test
    public void passwordCheck_notLong() {
        assertFalse(userServiceImpl.passwordCheck("1!qQ"));
    }
}
