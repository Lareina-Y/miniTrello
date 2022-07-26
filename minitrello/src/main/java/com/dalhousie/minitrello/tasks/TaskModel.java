package com.dalhousie.minitrello.tasks;

import com.dalhousie.minitrello.user.UserModel;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "task")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskID;

    @ManyToMany(targetEntity = UserModel.class)
    @JoinTable(name = "task_users", joinColumns = @JoinColumn(name = "taskid"))
    private List<UserModel> users;

    private String title;
    private String description;
    private Integer status = 0;
    private LocalDate deadline;
    @CreationTimestamp
    private LocalDateTime createdOn;

    public TaskModel(String title, String description, LocalDate deadline){
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }

    public TaskModel(){}

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }

    public Long getTaskID() {
        return taskID;
    }

    public void setTaskID(Long taskID) {
        this.taskID = taskID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
