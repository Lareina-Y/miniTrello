package com.dalhousie.minitrello.tasks;
import com.dalhousie.minitrello.boards.BoardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
@CrossOrigin
public class TaskController {

    @Autowired
    private TaskService service;

    @Autowired
    private BoardsService boardsService;

    @PostMapping("/create/{board_id}")
    public TaskModel createTask(@RequestBody TaskModel taskModel, @PathVariable("board_id") Long boardID) throws Exception{
        TaskModel task = service.createTask(taskModel);
        boardsService.addTasksInBoard(boardID,task.getTaskID());
        return task;
    }

    @PutMapping("/update/{id}/{title}")
    public TaskModel updateTitle(@PathVariable("id") Long id, @PathVariable("title") String title) throws Exception{
        return service.updateTitle(id,title);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTask(@PathVariable("id") Long id){
        service.deleteTask(id);
    }

    @GetMapping("/get_task/{id}")
    public TaskModel getTaskByID(@PathVariable("id")Long id) throws Exception{
        return service.getTaskByID(id);
    }

    @PutMapping("/update/description/{id}")
    public TaskModel updateDescription(@PathVariable("id") Long id, @RequestBody Map<String, String> description) throws Exception{
        return service.updateDescription(id,description.get("description"));
    }

    @PutMapping("/update/status/{id}")
    public TaskModel updateStatus(@PathVariable("id") Long id, @RequestBody Map<String, Integer> status) throws Exception{
        return service.updateStatus(id,status.get("status"));
    }

    @PutMapping("/update/deadline/{id}")
    public TaskModel updateDeadline(@PathVariable("id") Long id, @RequestBody Map<String, String> deadline) throws Exception{
        //convert date string into date
        LocalDate date = LocalDate.parse(deadline.get("deadline"));
        return service.updateDeadline(id, date);
    }

    @PutMapping("/assignee/{id}/{assigneeID}")
    public TaskModel updateAssignee(@PathVariable("id") Long id, @PathVariable("assigneeID")Long assigneeID) throws Exception{
        return service.updateAssignee(id,assigneeID);
    }
    @DeleteMapping("/assignee/delete/{id}/{assigneeID}")
    public TaskModel deleteAssignee(@PathVariable("id") Long id, @PathVariable("assigneeID")Long assigneeID) throws Exception{
        return service.deleteAssignee(id, assigneeID);
    }

    @GetMapping("/get_by_name/{id}/{name}")
    public List<TaskModel> getTaskByName(@PathVariable("id") Long boardID, @PathVariable("name")String name){
        return service.getTasksByName(boardID, name);
    }

    @GetMapping("/get_filtered_tasks/{id}/{filter}")
    public List<TaskModel> filterTasks(@PathVariable("id")Long boardID, @PathVariable("filter")Integer filter){
        return service.filterTasks(boardID, filter);
    }

}
