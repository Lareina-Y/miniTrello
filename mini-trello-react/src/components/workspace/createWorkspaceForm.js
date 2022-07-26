import React, { useRef } from "react";
import { Card, CardBody, Button, Form, FormGroup, Label, Input } from "reactstrap";

function CreateWorkspaceForm(props) {

    const workspaceNameRef = useRef();
	const descriptionRef = useRef();

	function createWorkspace (e) {
		e.preventDefault();
		const workspaceName = workspaceNameRef.current.value;
		const description = descriptionRef.current.value;

		const newWorkspace = {
			name: workspaceName,
			description: description
		}

        props.createWorkspace(newWorkspace);
	};

    return (
		<section>
			<h3 className="mb-3">Create Workspace:</h3>
			<Card>
			<CardBody>
				<Form onSubmit={createWorkspace}>
				<FormGroup>
					<Label for="workspace_name">New Workspace Name:</Label>
					<Input type="text" innerRef={workspaceNameRef} required/>
				</FormGroup>
				<FormGroup>
					<Label for="workspace_description">Description:</Label>
					<Input type="textarea" innerRef={descriptionRef} required/>
				</FormGroup>
				<Button type="submit" color="primary">Create Workspace</Button>
				</Form>
			</CardBody>
			</Card>
		</section>
	  );
};

export default CreateWorkspaceForm;