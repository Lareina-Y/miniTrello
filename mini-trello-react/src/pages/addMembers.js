import React, {useEffect} from "react";
import { useParams, useNavigate } from "react-router-dom";
import AddMoreMembers from "../components/workspace/addMembersForm"
import { useLogged } from '../assets/appStatus/loggedin';

function AddMembersPage () {

    const {workspace_id} = useParams();
	const logined = useLogged();
	const navigate = useNavigate();

	function addMoreMembersHandler(user_id) {
		fetch(`http://localhost:8080/user/addUserInWorkspace/${user_id}?workspaceId=${workspace_id}`, {
			method: 'PUT'
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
			<h3 className="mb-3">Add More Members :</h3>
			<AddMoreMembers addMoreMembers={addMoreMembersHandler}/>
		</div>
	);

}

export default AddMembersPage;