package com.dalhousie.minitrello.boards;

import com.dalhousie.minitrello.tasks.TaskModel;
import com.dalhousie.minitrello.tasks.TaskRepository;
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
class BoardsServiceImplementationTest {

    @Mock
    @Autowired
    private BoardsRepository boardsRepository;

    @Mock
    @Autowired
    private TaskRepository taskRepository;

    @InjectMocks
    private BoardsService boardsService = new BoardsServiceImplementation();

    private BoardsModel boardsModel;

    @BeforeEach
    public void setUp() {
        boardsModel = new BoardsModel("Board Name");
        boardsModel.setBoardId(1L);
        boardsModel.setCreateTime(LocalDateTime.now());
    }

    @Test
    void createBoard() {
        Mockito.when(boardsRepository.save(boardsModel)).thenReturn(boardsModel);
        BoardsModel savedBoard = boardsService.createBoard(boardsModel);
        assertNotNull(savedBoard);
    }

    @Test
    void deleteBoard() {
        Mockito.doNothing().when(boardsRepository).deleteById(any());
        boardsService.deleteBoard(1L);
    }

    @Test
    void changeBoardName() {
        Mockito.when(boardsRepository.findByBoardId(1L)).thenReturn(boardsModel);
        Mockito.when(boardsRepository.save(any())).thenReturn(boardsModel);
        String actual = boardsService.changeBoardName(1L, "New Board name").getName();
        assertEquals("New Board name", actual);
    }

    @Test
    void filter() {
        String title = "Board Name";
        List<BoardsModel> boardsModels = boardsService.filter(title);
        assertNotNull(boardsModels);
    }

    @Test
    public void getBoardByIDNotExist() {
        assertThrows(Exception.class, ()->boardsService.getBoardByID(1L));
    }

    @Test
    public void getBoardById() throws Exception {
        Mockito.when(boardsRepository.findById(1L)).thenReturn(Optional.ofNullable(boardsModel));
        assertEquals(boardsModel, boardsService.getBoardByID(1L));
    }

    @Test
    public void addTasksInBoard() throws Exception {
        TaskModel taskModel = new TaskModel();
        taskModel.setTaskID(1L);

        List<TaskModel> tasks = new ArrayList<TaskModel>();
        boardsModel.setTasks(tasks);

        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(taskModel));
        Mockito.when(boardsRepository.findById(1L)).thenReturn(Optional.ofNullable(boardsModel));
        Mockito.when(boardsRepository.save(any())).thenReturn(boardsModel);

        BoardsModel update = boardsService.addTasksInBoard(1L, 1L);
        assertTrue(update.getTasks().contains(taskModel));
    }

    @Test
    public void getTasksByBoardID() {
        TaskModel taskModel = new TaskModel();
        taskModel.setTaskID(1L);

        List<TaskModel> tasks = new ArrayList<TaskModel>();
        tasks.add(taskModel);
        boardsModel.setTasks(tasks);

        Mockito.when(boardsRepository.findById(1L)).thenReturn(Optional.ofNullable(boardsModel));
        assertEquals(tasks, boardsService.getTasksByBoardID(1L));
    }

}
