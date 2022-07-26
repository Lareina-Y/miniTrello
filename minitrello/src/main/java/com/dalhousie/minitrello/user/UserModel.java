package com.dalhousie.minitrello.user;

import com.dalhousie.minitrello.tasks.TaskModel;
import com.dalhousie.minitrello.workspace.WorkspaceModel;
import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "user")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String email;
    private String password;
    private String question;
    private String firstname;
    private String lastname;
    private String answer;

    @ManyToMany(targetEntity = WorkspaceModel.class)
    @JoinTable(name="user_workspace", joinColumns=@JoinColumn(name="user_id"))
    private List<WorkspaceModel> Workspaces;

    @JsonIgnore
    @ManyToMany(targetEntity = TaskModel.class, mappedBy = "users")
    private List<TaskModel> tasks;

    @CreationTimestamp
    private LocalDateTime create_time;

    public UserModel(String email, String password, String question, String answer) {
        this.email = email;
        this.password = password;
        this.question = question;
        this.answer = answer;
    }

    public UserModel() {}

    public List<TaskModel> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskModel> tasks) {
        this.tasks = tasks;
    }

    public Long getUser_id() {
        return user_id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setUser_id(Long id) {
        this.user_id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @JsonIgnore
    public List<WorkspaceModel> getWorkspaces() {
        return Workspaces;
    }

    public void setWorkspaces(List<WorkspaceModel> workspaces) {
        Workspaces = workspaces;
    }
}
