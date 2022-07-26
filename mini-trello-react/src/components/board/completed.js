import React from "react";
import { useParams } from "react-router-dom";
import { Row, Card, CardBody, Col } from "reactstrap";
import TaskCardTemplete from "../templates/TaskCardTemplete";

function Completed ({Complete}){

    const {workspace_id} = useParams();
    const {board_id} = useParams();
	  const {board_name} = useParams();

    return (
        <Col>
        <Card>
            <CardBody>
            <h5 className="card text-white bg-danger mb-3">Done</h5>
              <Row>
                {Complete.map((blg)=>{
                    if(blg.status === 2) {
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

export default Completed;
