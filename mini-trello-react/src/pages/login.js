import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { userID, loginAction, userIdAction} from '../assets/appStatus/loggedin';
import LoginForm from "../components/login/loginForm";

function LoginPage() {

	const navigate = useNavigate();
	const user_id = userID();
	
	function checkLogin(){
		if(user_id !== 0){
			navigate(`/home/${user_id}`)
		}
	}

	useEffect(function(){
		checkLogin();
	}, []) // eslint-disable-line react-hooks/exhaustive-deps

	async function loginHandler(loginModel){
		await fetch ("http://localhost:8080/user/login", {
		  method:'POST',
		  body: JSON.stringify(loginModel),
		  headers:{
			'Content-Type': 'application/json',
		  }
		})
		.then(response => response.json())
		.then(response => {
			if (response.user_id === null) {
				window.alert("Incorrect email or password. Please try again.")
				console.warn("Incorrect email or password. Please try again.")
			} else {
				loginAction(true);
				userIdAction(response.user_id);
				navigate(`/home/${response.user_id}`);
			}
		})
	}

	return (
		<div>
			<h3 className="mb-3">Sign In :</h3>       
			< LoginForm login={loginHandler}/>
		</div>
	);
}

export default LoginPage;