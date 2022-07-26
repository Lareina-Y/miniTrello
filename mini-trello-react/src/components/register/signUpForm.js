import {React, useState} from "react";
import { Card, CardBody, Button, Form, FormGroup, Label, Input } from "reactstrap";
import { Link } from "react-router-dom";

function SignupForm(props) {
    const [firstnameReg, setfirstnameReg] = useState('')
    const [lastnameReg, setlastnameReg] = useState('')
    const [emailReg, setemailReg] = useState('')
    const [passwordReg, setpasswordReg] = useState('')
    const [questionReg, setquestionReg] = useState('')
    const [answerReg, setanswerReg] = useState('')
  
    function registerUser(e){
      e.preventDefault();

      const newUser = {
          "answer": answerReg,
          "email": emailReg,
          "password": passwordReg,
          "firstname": firstnameReg,
          "lastname": lastnameReg,
          "question": questionReg
      }

      props.register(newUser);
    }

    return (
      <Card>
        <CardBody>
        <Form onSubmit={registerUser}>
        <FormGroup>
          <Label>First name :</Label>
          <Input
            type="text"
            className="form-control"
            placeholder="First name"
            onChange = {(e)=> {setfirstnameReg(e.target.value)}} 
            required/>
        </FormGroup>
        <FormGroup>
          <Label>Last name :</Label>
          <Input
            type="text"        
            className="form-control" 
            placeholder="Last name" 
            onChange = {(e)=> {setlastnameReg(e.target.value)}} 
            required/>
        </FormGroup>
        <FormGroup>
          <Label>Email address :</Label>
          <Input
            type="email"
            className="form-control"
            placeholder="Enter email"
            onChange = {(e)=> {setemailReg(e.target.value)}} 
            required/>
        </FormGroup>
        <FormGroup>
          <Label>Password : * </Label>
          <Input
            type="password"        
            className="form-control"
            autoComplete="on"
            placeholder="Enter password"
            onChange = {(e)=> {setpasswordReg(e.target.value)}} 
            required/>
        </FormGroup>
        <FormGroup>
          <Label>Security Question : </Label>
          <Input
            type="text"           
            className="form-control"
            placeholder="Question"
            onChange = {(e)=> {setquestionReg(e.target.value)}} 
            required/>
        </FormGroup>
        <FormGroup>
          <Label>Answer : </Label>
          <Input
            type="text"
            className="form-control"
            placeholder="Answer "
            onChange = {(e)=> {setanswerReg(e.target.value)}} 
            required/>
        </FormGroup>
        <div className="d-grid">
          <Button
            color="primary"
            type="submit" 
            className="btn btn-primary d-grid" >
              Sign Up
          </Button>
        </div>

        <p className="mt-3">Already registered <Link to="/login"> Sign in?</Link></p>
        <p className="mt-3">
          * Password has to be a minimum length of 8 characters, 
          include at least 1 uppercase character, 1 lowercase character, 
          1 number and 1 special character.
        </p>
        </Form>
        </CardBody>
        </Card>
    );
}

export default SignupForm;
