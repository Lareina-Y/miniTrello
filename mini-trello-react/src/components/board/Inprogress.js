import React from "react";
import { Row, Card, CardBody, Col } from "reactstrap";
import { useParams } from "react-router-dom";
import TaskCardTemplete from "../templates/TaskCardTemplete";

function Inprogress ({InProgress}){

    const {workspace_id} = useParams();
    const {board_id} = useParams();
    const {board_name} = useParams(); 

    return (
        <Col>
        <Card>
            <CardBody>
            <h5 className="card text-white bg-warning mb-3">Doing</h5>
              <Row>
              {InProgress.map((blg)=>{
                    if(blg.status === 1) {
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
        </Card>
      </Col>
    );
}

export default Inprogress;
