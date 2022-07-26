import React, {useEffect} from "react";
import { useParams, useNavigate } from "react-router-dom";
import CreateTaskForm from "../components/task/createTaskForm";
import { useLogged } from '../assets/appStatus/loggedin';

function CreateTaskPage () {
	
	const {workspace_id} = useParams();
    const {board_id} = useParams();
	const {board_name} = useParams();
	const logined = useLogged();
	const navigate = useNavigate();

	function createTaskHandler(task) {
		fetch(`http://localhost:8080/task/create/${board_id}`, {
			method: 'POST',
			body: JSON.stringify(task),
			headers: {
				'Content-Type': 'application/json',
			}
		}).then(response => response.json())
		.then(() => navigate(`/board/${workspace_id}/${board_id}/${board_name}`));
	}

	function checkLogin(){
		if(!logined){
			navigate(`/login`)
		}
	}

	useEffect(function(){
		checkLogin();
	}, []) // eslint-disable-line react-hooks/exhaustive-deps

	return (
		<div>
			<CreateTaskForm createTask={createTaskHandler}/>
		</div>
	);

}

export default CreateTaskPage;