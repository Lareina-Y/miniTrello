import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import { Card, CardBody, Button, Form, FormGroup, Label, Input } from "reactstrap";

function ForgotPasswordForm(){
    const navigate = useNavigate();

    const [question, setQuestion] = useState([]);
    const [userFound, setuserFound] = useState(false);
    const [newPassword, setnewPassword] = useState([]);
    const [answer, setAnswer] = useState([]);
    const [email, setEmail] = useState([]);
    
    async function userHandler(event){
        event.preventDefault();

        //get user question from database with email
        let response = await fetch(`http://localhost:8080/user/forget_password/${email}`)
        if(response.ok){
            let question = await response.text()
            setQuestion(question)
            setuserFound(true)
        } else {
            let result = await response.json()
            window.alert(result.message)
        }  
    }
    
    async function verifyUserHandler(event){
        event.preventDefault();

        await fetch(`http://localhost:8080/user/confirm_answer/${email}/${answer}`)
        .then(response => response.text())
        .then(response => {
            if(response === 'true'){
                updatePasswordHandler(newPassword) 
            } else {
                window.alert("Answer is wrong")
            }
        })
    }

    async function updatePasswordHandler(newPassword){
        let response = await fetch(`http://localhost:8080/user/update_password/${email}`, {
            method: `PUT`,
            body: JSON.stringify({"password": newPassword}),
            headers: {'content-type' : 'application/json'}
        })
        
        if(response.ok){
            navigate(`/login`)
        } else {
            let result = await response.json()
            window.alert(result.message)
        } 
    }

    return(
        <Card>
        <CardBody>
            {userFound? 
            <Form onSubmit={verifyUserHandler} id="answerPage">
                <FormGroup>
                    <Label> Answer Security question : {question}</Label>
                    <Input type="text" placeholder="Answer" value = {answer} required 
                    onChange = {(e) => {setAnswer(e.target.value)}}/>
                </FormGroup>
                <FormGroup>
                    <Label>Enter a new password : *</Label>
                    <Input type="password" placeholder="New Password" required 
                    onChange = {(e) => {setnewPassword(e.target.value)}}/>
                </FormGroup>
                <div className="d-grid">
                    <Button 
                        color="primary"
                        type="submit" 
                        className="btn btn-primary">
                            Reset Password
                    </Button>
                </div>
                <p className="mt-3">
                    * Password has to be a minimum length of 8 characters, 
                    include at least 1 uppercase character, 1 lowercase character, 
                    1 number and 1 special character.
                </p>
            </Form>
            :
            <Form onSubmit={userHandler} id="setPasswordPage">
                <FormGroup>
                    <Label>Begin by Entering your email :</Label>
                    <Input type="email" required placeholder="Email" 
                    onChange = {(e) => {setEmail(e.target.value)}}/>
                </FormGroup>
                <div className="d-grid">
                    <Button 
                        color="primary"
                        type="submit" 
                        className="btn btn-primary">
                            Next
                    </Button>
                </div>
            </Form> }
        </CardBody>
        </Card>
    )
}

export default ForgotPasswordForm;
