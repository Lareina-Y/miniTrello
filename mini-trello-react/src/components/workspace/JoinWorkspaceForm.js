import React, {useEffect, useState } from "react";
import { Card, CardBody, Button, Form, FormGroup, Label, Input } from "reactstrap";

const JoinWorkspaceForm = (props) => {
    const [allWorkspaces, setAllWorkspacesData] = useState([]);
    const [workspaceId, setWorkspaceId] = useState([]);

    const handleChange = (event) => {
        setWorkspaceId(event.target.value)
    }

    const getAllWorkspaces = () => {
		fetch(`http://localhost:8080/workspace/get_all`)
        .then(response => response.json())
		.then(allWorkspaces => {
			setAllWorkspacesData(allWorkspaces);
		})
	};

    function joinWorkspace (e) {
		e.preventDefault();
        props.joinWorkspace(workspaceId);
	};

    useEffect(function(){
        getAllWorkspaces();
	}, []); // eslint-disable-line react-hooks/exhaustive-deps

    return (
        <Card>
            <CardBody>
            <Form onSubmit={joinWorkspace}>
                <FormGroup>
                <Label>Select Workspace Name:</Label>
                <Input name="select" type="select" onChange={handleChange}>
                    <option>None</option>
                    {allWorkspaces.map((tdata) => (
                        <option value={tdata.id}>{tdata.name} (id:{tdata.id})</option>
                    ))}
                </Input>
                </FormGroup>
                <Button color="primary">Join</Button>
            </Form>
            </CardBody>
       </Card>
    );
};

export default JoinWorkspaceForm;
