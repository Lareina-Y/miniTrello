package com.dalhousie.minitrello.workspace;

import com.dalhousie.minitrello.user.UserModel;
import com.fasterxml.jackson.annotation.*;
import com.dalhousie.minitrello.boards.BoardsModel;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "workspace")
public class WorkspaceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(targetEntity = BoardsModel.class)
    @JoinColumn(name="workspace_id")
    private List<BoardsModel> Boards;


    @CreationTimestamp
    private LocalDateTime createdOn;

    @ManyToMany(mappedBy = "Workspaces", targetEntity = UserModel.class)
    private List<UserModel> Users;

    public WorkspaceModel(String name, String description){
        this.name = name;
        this.description = description;
    }

    public WorkspaceModel(){}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @JsonIgnore
    public List<UserModel> getUsers() {
        return Users;
    }

    public void setUsers(List<UserModel> users) {
        Users = users;
    }

    public List<BoardsModel> getBoards() {
        return Boards;
    }

    public void setBoards(List<BoardsModel> boards) {Boards = boards;}
}
