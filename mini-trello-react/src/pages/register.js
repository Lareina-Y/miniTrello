import React, { useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import SignUp from "../components/register/signUpForm";
import { userID } from '../assets/appStatus/loggedin';

function RegisterPage(){
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

	async function newUserHandler(newUser){
		let result = await fetch ("http://localhost:8080/user/register", {
		  method:'POST',
		  body: JSON.stringify(newUser),
		  headers:{
			'Content-Type': 'application/json',
			'Accept': 'application/json'
		  }
		})
	  
		let response = await result.json()
		if (response.message === undefined) {
			navigate("/login")
		} else {
			window.alert(response.message)
		}
	}

    return (
        <div>    
			<h3 className="mb-3">Sign Up :</h3>       
            < SignUp register={newUserHandler}/>
        </div>
    );

}

export default RegisterPage;
