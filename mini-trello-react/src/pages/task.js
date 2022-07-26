import { useParams, useNavigate, Link } from "react-router-dom";
import { Button, Row, Col, Card, CardBody, CardTitle, Input } from "reactstrap";
import React, {useEffect, useState} from "react";
import { useLogged } from '../assets/appStatus/loggedin';
import AssigneeUpdate from "../components/task/assignee";

function TaskPage () {
	const current = new Date();
	const logined = useLogged();
    const {task_id} = useParams();
	const {workspace_id} = useParams();
    const {board_id} = useParams();
	const {board_name} = useParams();
	const navigate = useNavigate();

	const [boardLink, setBoardLink] = useState([]);
	const [detailUpdate, setdetailUpdate] = useState(false);
	const [statusUpdate, setStatusUpdate] = useState(false);
	const [assigneesList, setAssigneesList] = useState([]);

	const [taskTitle, setTaskTitle] = useState([]);
  	const onChange_taskTitle = (event) => setTaskTitle(event.target.value);
	const [taskD, setTaskD] = useState([]);
  	const onChange_taskD = (event) => setTaskD(event.target.value);
	const [deadline, setDeadline] = useState([]);
  	const onChange_deadline = (event) => setDeadline(event.target.value);
	const [status, setStatus] = useState([]);
  	const onChange_status = (event) => setStatus(event.target.value);

	async function getTaskDetail(){
		await fetch(`http://localhost:8080/task/get_task/${task_id}`)
		.then(response => response.json())
		.then(task => {
			setTaskTitle(task.title)
			setTaskD(task.description)
			setAssigneesList(task.users)
			setDeadline(task.deadline)
			setStatus(task.status===0? "To do": task.status===1? "Doing": "Done")
		})
	}
	
	function checkLogin(){
		if(!logined){
			navigate(`/login`)
		}
	}

	useEffect(function(){
		checkLogin();
		getTaskDetail();
		setBoardLink(`/board/${workspace_id}/${board_id}/${board_name}`)
	}, []) // eslint-disable-line react-hooks/exhaustive-deps

	const deleteTask = (taskId) => {
		fetch(`http://localhost:8080/task/delete/${taskId}`, 
		{ method: 'DELETE' })
		.then(() => navigate(boardLink))
	};

	async function update() {
		await fetch(`http://localhost:8080/task/update/${task_id}/${taskTitle}`, { 
			method: 'PUT'
		}).then(response => response.json())
		.then(task => {
			setTaskTitle(task.title)
		})

		await fetch(`http://localhost:8080/task/update/description/${task_id}`, { 
			method: 'PUT',
			body: JSON.stringify({description: taskD}),
			headers: {
				'Content-Type': 'application/json',
			}  
		}).then(response => response.json())
		.then(task => {
			setTaskD(task.description)
		})

		await fetch(`http://localhost:8080/task/update/deadline/${task_id}`, { 
			method: 'PUT',
			body: JSON.stringify({deadline: deadline}),
			headers: {
				'Content-Type': 'application/json',
			}  
		}).then(response => response.json())
		.then(task => {
			setDeadline(task.deadline)
			toggle()
		})
	}

	async function StatusUpdate () {
		var status_integer = (status==="To do"? 0: status==="Doing"? 1: 2)
		await fetch(`http://localhost:8080/task/update/status/${task_id}`, { 
			method: 'PUT',
			body: JSON.stringify({status: status_integer}),
			headers: {
				'Content-Type': 'application/json',
			}  
		}).then(response => response.json())
		.then(
			toggle_Status()
		)
	}

	const toggle = () => setdetailUpdate((detailUpdate) => !detailUpdate);
	const toggle_Status = () => setStatusUpdate((statusUpdate) => !statusUpdate);

	return (
		<div>
			<div className="d-flex justify-content-between">
				<h3 className="mb-3">TASK DETAIL :</h3>
				{detailUpdate? <Link to="" onClick={update}>Update</Link> :
				<Link to="" onClick={toggle}>Edit</Link>}
			</div>

			<Row>
				<Col lg="12">
				<Card>
					<CardTitle tag="h6" className="border-bottom p-3 mb-0">
						Title: {detailUpdate? 
						<Input type="text" value={taskTitle} onChange={onChange_taskTitle}>
						</Input>: taskTitle}
					</CardTitle>
					<CardBody>
						Description: {detailUpdate? 
						<Input type="textarea" value={taskD} onChange={onChange_taskD}>
						</Input>: taskD}
					</CardBody>
					<CardBody>
						Deadline: {detailUpdate? 
						<Input type="date" value={deadline} onChange={onChange_deadline}>
						</Input>: 
						(current < new Date(deadline) || deadline === null)? deadline
						: <span className="text-white bg-danger p-1">{deadline}</span>}
					</CardBody>
				</Card>
				</Col>
			</Row>
			
			<div className="d-flex justify-content-between">
				<h3 className="mb-3">Status :</h3>
				{statusUpdate? <Link to="" onClick={StatusUpdate}>Update</Link> :
				<Link to="" onClick={toggle_Status}>Edit</Link>}
			</div>

			<Row>
				<Col lg="12">
				<Card>
					<CardTitle tag="h6" className="border-bottom p-3 mb-0">
						Status: {statusUpdate? 
						<Input type="select" value={status} onChange={onChange_status}>
							<option value="To do">To do</option>
							<option value="Doing">Doing</option>
							<option value="Done">Done</option>
						</Input>: status}
					</CardTitle>
				</Card>
				</Col>
			</Row>
			<AssigneeUpdate assigneesList={assigneesList} reset={getTaskDetail}/>
			<Button className="btn-danger mb-3" onClick={() => deleteTask(task_id)}>
				Delete
			</Button><br/>
			<Link to={boardLink}>Back Board Page</Link>
		</div>
	);

}

export default TaskPage;