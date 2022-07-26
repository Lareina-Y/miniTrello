import { useParams, Link } from "react-router-dom";
import { Row, Col, Card, CardBody, Input } from "reactstrap";
import React, {useEffect, useState} from "react";

function AssigneeUpdate ({assigneesList, reset}) {
    
    const {task_id} = useParams();
    const {workspace_id} = useParams();
    const [members, setMembers] = useState([]);
    const [assigneeUpdate, setAssigneeUpdate] = useState(false);
    const toggle_Assignee = () => setAssigneeUpdate((assigneeUpdate) => !assigneeUpdate);

    const [newAssignee, setnewAssignee] = useState("0");
  	const onChange_Assignee= (event) => setnewAssignee(event.target.value);

    async function Add() {
        if (newAssignee === "0") {
            toggle_Assignee()
        } else {
            await fetch(`http://localhost:8080/task/assignee/${task_id}/${newAssignee}`, { 
                method: 'PUT'
            }).then(response => response.json())
            .then(() => {
                toggle_Assignee()
                reset();
            })
        }
	}

    async function deleteUser (delete_id) {
        await fetch(`http://localhost:8080/task/assignee/delete/${task_id}/${delete_id}`, { 
			method: 'DELETE'
		}).then(response => response.json())
		.then(() => {
            reset();
		})
	}

    async function getMembers(){
        await fetch(`http://localhost:8080/workspace/get_users/${workspace_id}`)
		.then(response => response.json())
		.then(users => {
			setMembers(users)
		})
	}

    useEffect(function(){
		getMembers()
	}, []) // eslint-disable-line react-hooks/exhaustive-deps

    return (
        <div>
        <div className="d-flex justify-content-between">
            <h3 className="mb-3">Assignee :</h3>
            {assigneeUpdate? <Link to="" onClick={Add}>Add</Link> :
            <Link to="" onClick={toggle_Assignee}>Edit</Link>}
        </div>

        <Row>
            <Col lg="12">
            <Card>
                <CardBody>
                    {assigneesList.map((user) => {
                        return (
                            <div className="mb-2 d-flex justify-content-between">
                                - {user.firstname} {user.lastname} ( {user.email} ) 
                                {assigneeUpdate? <Link to="" onClick={() => deleteUser(user.user_id)}>Remove</Link> : null}
                            </div>
                        );
                    })
                    }
                    {assigneeUpdate? 
                        <Input type="select" className= "mt-2" onChange={onChange_Assignee}>
                            <option value="0">None</option>
                            {members.map((member) => {
                                return (
                                    <option value={member.user_id}>{member.firstname} {member.lastname} ({member.email})</option>
                                );
                            })}
                        </Input> : null
                    }
                </CardBody>
            </Card>
            </Col>
        </Row>
        </div>
    );
}

export default AssigneeUpdate;

