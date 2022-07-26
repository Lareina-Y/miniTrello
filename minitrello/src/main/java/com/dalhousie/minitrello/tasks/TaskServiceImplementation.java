package com.dalhousie.minitrello.tasks;
import com.dalhousie.minitrello.boards.BoardsModel;
import com.dalhousie.minitrello.user.UserModel;
import com.dalhousie.minitrello.user.UserRepository;
import com.dalhousie.minitrello.boards.BoardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.time.LocalDate;

@Service
public class TaskServiceImplementation implements TaskService{

    @Autowired
    private TaskRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardsRepository boardsRepository;

    private static final int WEEK = 7;
    private static final int DUETODAY = 0;
    private static final int DUEINWEEK = 1;
    private static final int OVERDUE = 2;
    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate NEXTWEEK = LocalDate.now().plusDays(WEEK + 1);

    @Override
    public TaskModel createTask(TaskModel taskModel){
        return repository.save(taskModel);
    }

    @Override
    public void deleteTask(Long id){
        repository.deleteById(id);
    }

    @Override
    public TaskModel updateTitle(Long id, String name) throws Exception{
        TaskModel updatedTask = null;
        Optional<TaskModel> optionalTask = repository.findById(id);

        if (optionalTask.isPresent()){
            TaskModel task = optionalTask.get();
            task.setTitle(name);
            updatedTask = repository.save(task);
            return updatedTask;
        } else{
            throw new Exception("Cannot find task");
        }
    }

    @Override
    public TaskModel updateDescription(Long id, String description) throws Exception{
        TaskModel updatedTask = null;
        Optional<TaskModel> optionalTask = repository.findById(id);

        if (optionalTask.isPresent()){
            TaskModel task = optionalTask.get();
            task.setDescription(description);
            updatedTask = repository.save(task);
            return updatedTask;
        } else{
            throw new Exception("Cannot find task");
        }
    }

    @Override
    public TaskModel getTaskByID(Long id) throws Exception{
        TaskModel task = null;
        Optional<TaskModel> optionalTask = repository.findById(id);

        if (optionalTask.isPresent()){
            task = optionalTask.get();
            return task;
        } else{
            throw new Exception("Cannot find task");
        }
    }

    @Override
    public TaskModel updateStatus(Long id, Integer status) throws Exception{
        TaskModel updatedTask = null;
        Optional<TaskModel> optionalTask = repository.findById(id);

        if (optionalTask.isPresent()){
            TaskModel task = optionalTask.get();
            task.setStatus(status);
            updatedTask = repository.save(task);
            return updatedTask;
        } else{
            throw new Exception("Cannot find task");
        }
    }

    @Override
    public TaskModel updateDeadline(Long id, LocalDate date) throws Exception{
        TaskModel updatedTask = null;
        Optional<TaskModel> optionalTask = repository.findById(id);

        if (optionalTask.isPresent()){
            TaskModel task = optionalTask.get();
            task.setDeadline(date);
            updatedTask = repository.save(task);
            return updatedTask;
        } else{
            throw new Exception("Cannot find task");
        }
    }

    @Override
    public TaskModel updateAssignee(Long id, Long userID) throws Exception{
        TaskModel updatedTask = null;
        Optional<TaskModel> optionalTask = repository.findById(id);
        Optional<UserModel> optionalUser = userRepository.findById(userID);

        if (optionalTask.isPresent() && optionalUser.isPresent()){
            UserModel user = optionalUser.get();
            TaskModel task = optionalTask.get();
            List<UserModel> users = task.getUsers();

            if (!users.contains(user)) {
                users.add(user);
            }

            task.setUsers(users);
            updatedTask = repository.save(task);
            return updatedTask;
        } else{
            throw new Exception("Cannot find task");
        }
    }

    @Override
    public TaskModel deleteAssignee(Long id, Long assigneeID) throws Exception{
        TaskModel updatedTask = null;
        Optional<TaskModel> optionalTask = repository.findById(id);
        Optional<UserModel> optionalUser = userRepository.findById(assigneeID);

        if (optionalTask.isPresent() && optionalUser.isPresent()){

            UserModel user = optionalUser.get();
            TaskModel task = optionalTask.get();

            List<UserModel> users = task.getUsers();
            users.remove(user);
            task.setUsers(users);
            updatedTask = repository.save(task);
            return updatedTask;
        } else {
            throw new Exception("Cannot find task");
        }
    }

    @Override
    public List<TaskModel> getTasksByName(Long id, String name){
        List<TaskModel> nameTasks = new ArrayList<>();
        Optional<BoardsModel> optionalBoard = boardsRepository.findById(id);

        if(optionalBoard.isPresent()){
            BoardsModel board = optionalBoard.get();
            List<TaskModel> tasks = board.getTasks();

            for (TaskModel task : tasks) {
                if (task.getTitle().toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT))) {
                    nameTasks.add(task);
                }
            }
        }
        return nameTasks;
    }

    @Override
    public List<TaskModel> filterTasks(Long id, Integer filter){
        List<TaskModel> filteredTasks = new ArrayList<>();
        Optional<BoardsModel> optionalBoard = boardsRepository.findById(id);

        if(optionalBoard.isPresent()) {
            BoardsModel board = optionalBoard.get();
            List<TaskModel> tasks = board.getTasks();

            if (filter == DUETODAY){
                filterDueToday(tasks, filteredTasks);
            } else if (filter == DUEINWEEK) {
                filterDueInWeek(tasks, filteredTasks);
            } else if (filter == OVERDUE){
                filterOverDue(tasks, filteredTasks);
            }
        }
        return filteredTasks;
    }

    public void filterDueToday(List<TaskModel> tasks, List<TaskModel> filteredTasks){
        for (TaskModel task : tasks) {
            LocalDate deadline = task.getDeadline();

            if (deadline != null && deadline.equals(TODAY)) {
                filteredTasks.add(task);
            }
        }
    }

    public void filterDueInWeek(List<TaskModel> tasks, List<TaskModel> filteredTasks){
        for (TaskModel task : tasks) {
            LocalDate deadline = task.getDeadline();

            if (deadline != null) {
                if (deadline.isAfter(TODAY) && deadline.isBefore(NEXTWEEK)) {
                    filteredTasks.add(task);
                }
            }
        }
    }

    public void filterOverDue(List<TaskModel> tasks, List<TaskModel> filteredTasks){
        for (TaskModel task : tasks) {
            LocalDate deadline = task.getDeadline();

            if (deadline != null && deadline.isBefore(TODAY)) {
                filteredTasks.add(task);
            }
        }
    }

}
