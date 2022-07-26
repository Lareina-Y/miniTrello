import React, {useEffect} from "react";
import { useParams, useNavigate } from "react-router-dom";
import CreateBoardForm from "../components/workspace/createBoardForm";
import { useLogged } from '../assets/appStatus/loggedin';

function CreateBoardPage () {

    const {workspace_id} = useParams();
	const logined = useLogged();
	const navigate = useNavigate();

	function createBoardHandler(board) {
		fetch(`http://localhost:8080/board/create/${workspace_id}`, {
			method: 'POST',
			body: JSON.stringify(board),
			headers: {
				'Content-Type': 'application/json',
			}
		}).then(response => response.json())
		.then(() => navigate(`/workspace/${workspace_id}`));
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
			<CreateBoardForm createBoard={createBoardHandler}/>
		</div>
	);

}

export default CreateBoardPage;