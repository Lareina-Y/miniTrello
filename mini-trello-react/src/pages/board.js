import { useParams, useNavigate } from "react-router-dom";
import React, {useEffect, useState} from "react";
import { Button } from "reactstrap";
import { useLogged } from '../assets/appStatus/loggedin';
import TodoForm from "../components/board/Todo";
import InProgress from "../components/board/Inprogress";
import CompleteForm from "../components/board/completed";
import SearchForm from "../components/board/Searchbar";
import FilterBar from "../components/board/filterBar";

function BoardPage () {
	
	const logined = useLogged();
	const {workspace_id} = useParams();
    const {board_id} = useParams();
	const {board_name} = useParams();
	const navigate = useNavigate();
	
	const deleteBoard = (boardId) => {
		fetch(`http://localhost:8080/board/delete/${boardId}`, 
		{ method: 'DELETE' })
		.then(() => navigate(`/workspace/${workspace_id}`))
	};

	const [taskData, setTaskData] = useState([]);

	function getTasksByBoardID(board_id) {
			fetch(`http://localhost:8080/board/get_all_tasks/${board_id}`)
		.then(response => response.json())
		.then(task => { setTaskData(task);
		
		})
	}

	async function searchHandler(title){
		if (title !== ""){
			fetch(`http://localhost:8080/task/get_by_name/${board_id}/${title}`)
			.then(response => response.json())
			.then(task => { setTaskData(task);
			})
		} else {
			getTasksByBoardID(board_id);
		}
	}

	async function filterHandler(filter){
		if (filter !== "All"){
			fetch(`http://localhost:8080/task/get_filtered_tasks/${board_id}/${filter}`)
			.then(response => response.json())
			.then(task => { setTaskData(task);
			})
		} else {
			getTasksByBoardID(board_id);
		}
	}

	function checkLogin(){
		if(!logined){
			navigate(`/login`)
		}
	}

	useEffect (function () {
		checkLogin();
		getTasksByBoardID(board_id);
	}, []) // eslint-disable-line react-hooks/exhaustive-deps

	return (
		<div>
			<div className="d-flex justify-content-between">
				<h3 className="mb-3">Board name : {board_name}</h3>
				<SearchForm search={searchHandler} />
			</div>

			<FilterBar filter={filterHandler}/>

			<div className="container text-center">
			<div className="row">
				<TodoForm Todo={taskData}/>
				<InProgress InProgress={taskData}/>
				<CompleteForm Complete={taskData}/>
			</div>
			</div>
			<Button className="btn-danger" onClick={() => deleteBoard(board_id)}>Delete</Button>
		</div>
	);
}

export default BoardPage;
