package com.dalhousie.minitrello.boards;

import com.dalhousie.minitrello.tasks.TaskModel;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "board")
public class BoardsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;
    private String name;

    @OneToMany(targetEntity = TaskModel.class)
    @JoinColumn(name = "board_id")
    private List<TaskModel> Tasks;

    @CreationTimestamp
    private LocalDateTime createTime;

    public BoardsModel() {}

    public BoardsModel(String name) {
        this.name = name;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }


    public List<TaskModel> getTasks() {return Tasks;}

    public void setTasks(List<TaskModel> tasks) {Tasks = tasks;}
}
