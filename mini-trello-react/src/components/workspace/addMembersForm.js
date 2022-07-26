import React, {useEffect, useState } from "react";
import { Card, CardBody, Button, Form, FormGroup, Label, Input } from "reactstrap";
import { userID } from '../../assets/appStatus/loggedin';

const AddMembersForm = (props) => {
    const [allUsers, setAllUsersData] = useState([]);
    const [userId, setUserId] = useState([]);
    const user_id = userID();

    const handleChange = (event) => {
        setUserId(event.target.value)
    }

	const getAllUser = () => {
		fetch(`http://localhost:8080/user/get_all_users`)
        .then(response => response.json())
		.then(allUsers => {
			setAllUsersData(allUsers);
		})
	};

	useEffect(function(){
        getAllUser();
	}, []); // eslint-disable-line react-hooks/exhaustive-deps

    function addMembers (e) {
		e.preventDefault();
        props.addMoreMembers(userId);
	};

    return (
        <Card>
            <CardBody>
            <Form onSubmit={addMembers}>
                <FormGroup>
                <Label>Select User Name:</Label>
                <Input name="select" type="select" onChange={handleChange}>
                    <option>None</option>
                    {allUsers.map((tdata) => (
                        tdata.user_id === user_id ? '' :
                        <option value={tdata.user_id}>{tdata.firstname} {tdata.lastname} ({tdata.email})</option>
                    ))}
                </Input>
                </FormGroup>
                <Button color="primary">Add</Button>
            </Form>
            </CardBody>
       </Card>
    );
};

export default AddMembersForm;
