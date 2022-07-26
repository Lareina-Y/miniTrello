import React from "react";
import { Row, Card, CardBody, Col } from "reactstrap";
import { Link, useParams } from "react-router-dom";
import TaskCardTemplete from "../templates/TaskCardTemplete";

function Todo ({Todo}){

    const {workspace_id} = useParams();
    const {board_id} = useParams();
    const {board_name} = useParams();

    const link = `/create_task/${workspace_id}/${board_id}/${board_name}`

    return (
        <Col>
          <Card>
              <CardBody>
                <h5 className="card text-white bg-success mb-3">To-Do</h5>
                <Row>
                  {Todo.map((blg)=>{
                      if(blg.status === 0) {
                        return <TaskCardTemplete
                        title ={blg.title}
                        text ={blg.description}
                        color = "primary"
                        path = {'/task/' + blg.taskID + '/' + workspace_id + '/' + board_id + '/' + board_name}
                      />
                      } else {
                        return null
                      }
                  })}
                </Row>              
              </CardBody>
              <Link to={link} className="btn btn-primary">Creat Task</Link>
          </Card>
        </Col>
    );
}

export default Todo;
