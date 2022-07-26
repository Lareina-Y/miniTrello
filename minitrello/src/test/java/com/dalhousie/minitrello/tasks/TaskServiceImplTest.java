package com.dalhousie.minitrello.tasks;

import com.dalhousie.minitrello.boards.BoardsModel;
import com.dalhousie.minitrello.boards.BoardsRepository;
import com.dalhousie.minitrello.user.UserModel;
import com.dalhousie.minitrello.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    @Autowired
    private TaskRepository taskRepository;

    @Mock
    @Autowired
    private UserRepository userRepository;

    private TaskModel taskModel;
    private BoardsModel boardsModel;

    @Mock
    @Autowired
    private BoardsRepository boardsRepository;

    @InjectMocks
    private TaskServiceImplementation taskService = new TaskServiceImplementation();

    @BeforeEach
    public void setUp() {
        taskModel = new TaskModel();
        taskModel.setTaskID(1L);
        taskModel.setTitle("testTitle");
        taskModel.setStatus(0);
        taskModel.setCreatedOn(LocalDateTime.now());
        taskModel.setDeadline(LocalDate.now());
        taskModel.setDescription("description");

        boardsModel = new BoardsModel();
        boardsModel.setBoardId(1L);
        List<TaskModel> tasks = new ArrayList<>();
        tasks.add(taskModel);
        boardsModel.setTasks(tasks);

        UserModel userModel = new UserModel();
        userModel.setUser_id(1L);
        userModel.setPassword("@qweQE12423");
        userModel.setEmail("123456@163.com");
        userModel.setQuestion("are you ok ?");
        userModel.setAnswer("xxx");
        List<UserModel> userModels = new ArrayList<>();
        userModels.add(userModel);
        taskModel.setUsers(userModels);
    }

    @Test
    public void addTask() {
        Mockito.when(taskRepository.save(taskModel)).thenReturn(taskModel);
        TaskModel taskModel1 = taskService.createTask(taskModel);
        assertNotNull(taskModel1);
    }

    @Test
    public void getTaskByID() throws Exception {
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(taskModel));
        assertEquals(taskModel, taskService.getTaskByID(1L));
    }

    @Test
    public void getTaskById_null() {
        assertThrows(Exception.class, ()->taskService.getTaskByID(1L));
    }

    @Test
    public void deleteTask(){
        Mockito.doNothing().when(taskRepository).deleteById(any());
        taskService.deleteTask(1L);
    }

    @Test
    public void updateTitle() throws Exception {
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(taskModel));
        Mockito.when(taskRepository.save(any())).thenReturn(taskModel);
        String title = taskService.updateTitle(1L, "new Title").getTitle();
        assertEquals("new Title", title);
    }

    @Test
    public void updateDescription() throws Exception {
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(taskModel));
        Mockito.when(taskRepository.save(any())).thenReturn(taskModel);
        String actual = taskService.updateDescription(1L, "This is new Description").getDescription();
        assertEquals("This is new Description", actual);
    }

    @Test
    public void updateStatus() throws Exception {
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(taskModel));
        Mockito.when(taskRepository.save(any())).thenReturn(taskModel);
        int actual = taskService.updateStatus(1L, 1).getStatus();
        assertEquals(1, actual);
    }

    @Test
    public void updateDeadline() throws Exception {
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(taskModel));
        Mockito.when(taskRepository.save(any())).thenReturn(taskModel);
        LocalDate actual = taskService.updateDeadline(1L, LocalDate.parse("2022-07-22")).getDeadline();
        assertEquals(LocalDate.parse("2022-07-22"), actual);
    }

    @Test
    public void updateAssignee() throws Exception {
        UserModel userModel = new UserModel();
        userModel.setUser_id(1L);

        List<UserModel> users = new ArrayList<UserModel>();
        taskModel.setUsers(users);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(userModel));
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(taskModel));
        Mockito.when(taskRepository.save(any())).thenReturn(taskModel);

        TaskModel update = taskService.updateAssignee(1L, 1L);
        assertTrue(update.getUsers().contains(userModel));
    }

    @Test
    public void deleteAssignee() throws Exception{
        UserModel userModel = new UserModel();
        userModel.setUser_id(1L);

        List<UserModel> users = new ArrayList<UserModel>();
        taskModel.setUsers(users);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(userModel));
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(taskModel));
        Mockito.when(taskRepository.save(any())).thenReturn(taskModel);

        TaskModel update = taskService.deleteAssignee(1L, 1L);
        assertFalse(update.getUsers().contains(userModel));
    }

    @Test
    public void getTasksByName() {
        Mockito.when(boardsRepository.findById(1L)).thenReturn(Optional.ofNullable(boardsModel));
        List<TaskModel> actual = taskService.getTasksByName(1L, "test");
        assertEquals("testTitle", actual.get(0).getTitle());
    }

    @Test
    public void filterTask(){
        Mockito.when(boardsRepository.findById(1L)).thenReturn(Optional.ofNullable(boardsModel));
        List<TaskModel> actual = taskService.filterTasks(1L, 0);
        assertEquals(LocalDate.now(), actual.get(0).getDeadline());
    }

    @Test
    public void filterDueToday(){
        List<TaskModel> filteredTasks = new ArrayList<>();
        taskService.filterDueToday(boardsModel.getTasks(), filteredTasks);
        assertEquals(LocalDate.now(), filteredTasks.get(0).getDeadline());
    }

    @Test
    public void filterDueInWeek(){
        List<TaskModel> filteredTasks = new ArrayList<>();
        TaskModel dueInWeek_task = new TaskModel("testTitle2", "description2", LocalDate.now().plusDays(2));
        dueInWeek_task.setTaskID(2L);
        List<TaskModel> tasks = new ArrayList<>();
        tasks.add(dueInWeek_task);
        tasks.add(taskModel);
        boardsModel.setTasks(tasks);

        taskService.filterDueInWeek(boardsModel.getTasks(), filteredTasks);
        assertEquals(2L, filteredTasks.get(0).getTaskID());
    }

    @Test
    public void filterOverDue(){
        List<TaskModel> filteredTasks = new ArrayList<>();
        TaskModel overDue_task = new TaskModel("testTitle3", "description3", LocalDate.now().minusDays(2));
        overDue_task.setTaskID(3L);
        List<TaskModel> tasks = new ArrayList<>();
        tasks.add(overDue_task);
        tasks.add(taskModel);
        boardsModel.setTasks(tasks);

        taskService.filterOverDue(boardsModel.getTasks(), filteredTasks);
        assertEquals(3L, filteredTasks.get(0).getTaskID());
    }

}
