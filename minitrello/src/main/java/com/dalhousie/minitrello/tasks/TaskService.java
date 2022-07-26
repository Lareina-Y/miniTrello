package com.dalhousie.minitrello.tasks;
import java.time.LocalDate;
import java.util.List;

public interface TaskService {

    TaskModel createTask(TaskModel task);
    void deleteTask(Long id);
    TaskModel updateTitle(Long id, String name) throws Exception;
    TaskModel updateDescription(Long id, String description) throws Exception;
    TaskModel getTaskByID(Long id) throws Exception;
    TaskModel updateStatus(Long id, Integer status) throws Exception;
    TaskModel updateDeadline(Long id, LocalDate date) throws Exception;
    TaskModel updateAssignee(Long id, Long assigneeID) throws Exception;
    TaskModel deleteAssignee(Long id, Long assigneeID) throws Exception;
    List<TaskModel> getTasksByName(Long id, String name);
    List<TaskModel> filterTasks(Long id, Integer filter);
}
