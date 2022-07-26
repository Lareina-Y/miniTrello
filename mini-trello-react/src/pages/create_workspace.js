import React, {useEffect} from "react";
import { useNavigate } from "react-router-dom";
import CreateWorkspaceForm from "../components/workspace/createWorkspaceForm";
import JoinWorkspaceForm from "../components/workspace/JoinWorkspaceForm";
import { userID, useLogged } from '../assets/appStatus/loggedin';

function CreateWorkspacePage () {

    const user_id = userID();
	const logined = useLogged();
	const navigate = useNavigate();
	
	function checkLogin(){
		if(!logined){
			navigate(`/login`)
		}
	}

	function createWorkspaceHandler(workspace) {
		fetch(`http://localhost:8080/workspace/save/${user_id}`, {
			method: 'POST',
			body: JSON.stringify(workspace),
			headers: {
				'Content-Type': 'application/json',
			}
		}).then(response => response.json())
		.then(() => navigate(`/home/${user_id}`));
	}

	function joinWorkspaceHandler(workspace_id) {
		fetch(`http://localhost:8080/user/addUserInWorkspace/${user_id}?workspaceId=${workspace_id}`, {
			method: 'PUT'
		}).then(response => response.json())
		.then(() => navigate(`/home/${user_id}`));
	}

	useEffect(function(){
		checkLogin();
	}, []) // eslint-disable-line react-hooks/exhaustive-deps

	return (
		<div>
			<CreateWorkspaceForm createWorkspace={createWorkspaceHandler}/>
			
			<h3 className="mb-3 mt-3">Join Workspaces :</h3>
			<JoinWorkspaceForm joinWorkspace={joinWorkspaceHandler}/>
		</div>
	);

}

export default CreateWorkspacePage;