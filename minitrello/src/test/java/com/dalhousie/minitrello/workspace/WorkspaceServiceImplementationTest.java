package com.dalhousie.minitrello.workspace;

import com.dalhousie.minitrello.boards.BoardsModel;
import com.dalhousie.minitrello.boards.BoardsRepository;
import com.dalhousie.minitrello.user.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class WorkspaceServiceImplementationTest {

    @Mock
    @Autowired
    private WorkspaceRepository repository;

    @InjectMocks
    private WorkspaceServiceImplementation service = new WorkspaceServiceImplementation();

    @Mock
    @Autowired
    private BoardsRepository boardsRepository;

    private WorkspaceModel workspaceModel;

    @BeforeEach
    public void setUp() {
        workspaceModel = new WorkspaceModel();
        workspaceModel.setId(1L);
        workspaceModel.setCreatedOn(LocalDateTime.now());
        workspaceModel.setName("Workspace name");
        workspaceModel.setDescription("Workspace description");
    }

    @Test
    public void createWorkspace() {
        Mockito.when(repository.save(workspaceModel)).thenReturn(workspaceModel);
        WorkspaceModel savedWorkspace = service.createWorkspace(workspaceModel);
        assertNotNull(savedWorkspace);
    }

    @Test
    public void getWorkspaceByID() throws Exception {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(workspaceModel));
        assertEquals(workspaceModel, service.getWorkspaceByID(1L));
    }

    @Test
    public void getWorkspaceByIDNotExist() {
        assertThrows(Exception.class, ()->service.getWorkspaceByID(1L));
    }

    @Test
    public void deleteWorkspaceByID() {
        Mockito.doNothing().when(repository).deleteById(any());
        service.deleteWorkspaceByID(1L);
    }

    @Test
    public void updateName() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(workspaceModel));
        Mockito.when(repository.save(any())).thenReturn(workspaceModel);
        String actual = service.updateName(1L, "New Workspace name").getName();
        assertEquals("New Workspace name", actual);
    }

    @Test
    public void updateDescription() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(workspaceModel));
        Mockito.when(repository.save(any())).thenReturn(workspaceModel);
        String actual = service.updateDescription(1L, "New Workspace description").getDescription();
        assertEquals("New Workspace description", actual);
    }

    @Test
    public void getAllWorkspaces() {
        List<WorkspaceModel> listOfWorkspaces = service.getAllWorkspaces();
        assertNotNull(listOfWorkspaces);
    }

    @Test
    public void getUsersByWorkspaceId_Null() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(workspaceModel));
        List<UserModel> listOfUsers = service.getUsersByWorkspaceId(1L);
        assertNull(listOfUsers);
    }

    @Test
    public void getUsersByWorkspaceId() {
        List<UserModel> users = new ArrayList<UserModel>();
        workspaceModel.setUsers(users);
        Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(workspaceModel));
        List<UserModel> listOfUsers = service.getUsersByWorkspaceId(1L);
        assertNotNull(listOfUsers);
    }

    @Test
    public void getBoardsByWorkspaceId_Null() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(workspaceModel));
        List<BoardsModel> listOfBoards = service.getBoardsByWorkspaceId(1L);
        assertNull(listOfBoards);
    }

    @Test
    public void getBoardsByWorkspaceId() {
        List<BoardsModel> boards = new ArrayList<BoardsModel>();
        workspaceModel.setBoards(boards);
        Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(workspaceModel));
        List<BoardsModel> listOfBoards = service.getBoardsByWorkspaceId(1L);
        assertEquals(boards, listOfBoards);
    }

    @Test
    public void addBoardsInWorkspace() {
        BoardsModel boardModel = new BoardsModel();
        boardModel.setBoardId(1L);

        List<BoardsModel> boards = new ArrayList<BoardsModel>();
        workspaceModel.setBoards(boards);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(workspaceModel));
        Mockito.when(boardsRepository.findByBoardId(1L)).thenReturn(boardModel);
        Mockito.when(repository.save(any())).thenReturn(workspaceModel);

        WorkspaceModel update = service.addBoardsInWorkspace(1L, 1L);
        assertTrue(update.getBoards().contains(boardModel));
    }
}