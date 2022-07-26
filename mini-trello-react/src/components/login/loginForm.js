import React, { useState }from "react";
import { Card, CardBody, Button, Form, FormGroup, Label, Input } from "reactstrap";
import { Link } from "react-router-dom";

function LoginForm(props) {
  
    const [emailReg, setemailreg] = useState("");
    const [passwordReg, setpasswordreg] = useState("");

    function login(e){
        e.preventDefault();
    
        const loginModel = {
            "email": emailReg,
            "password": passwordReg
        }
    
        props.login(loginModel);
    }
    
    return (
        <Card>
        <CardBody>
        <Form onSubmit={login}>
        <FormGroup>
            <Label>Email :</Label>
            <Input 
                type="email" 
                className="form-control" 
                onChange = {(e) => {setemailreg(e.target.value)}}
                placeholder="Enter email" 
                required
                />
        </FormGroup>                            
        <FormGroup>
            <Label>Password :</Label>
            <Input
                type="password" 
                className="form-control"
                autoComplete="on"
                onChange = {(e) => {setpasswordreg(e.target.value)}}
                placeholder="Enter password" 
                required/>
            <p className="mt-2"><Link to="/forget_password"> Forgot password?</Link></p>
        </FormGroup>     

        <div className="d-grid">
            <Button 
                color="primary"
                type="submit" 
                className="btn btn-primary">
                    Log In
            </Button>
        </div>   
        
        <p className="mt-3">New User <Link to="/register"> Sign Up?</Link></p>
        </Form>
        </CardBody>
        </Card>
    );
    
}

export default LoginForm;
