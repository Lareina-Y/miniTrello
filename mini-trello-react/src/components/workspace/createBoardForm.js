import React, { useRef } from "react";
import { Card, CardBody, Button, Form, FormGroup, Label, Input } from "reactstrap";

function CreateBoardForm(props) {

    const boardNameRef = useRef();

	function createBoard (e) {
		e.preventDefault();
		const BoardName = boardNameRef.current.value;

		const newBoard = {
			name: BoardName
		}

        props.createBoard(newBoard);
	};

    return (
		<section>
			<h3 className="mb-3">Create Board:</h3>
			<Card>
			<CardBody>
				<Form onSubmit={createBoard}>
				<FormGroup>
					<Label for="workspace_name">New Board Name:</Label>
					<Input type="text" innerRef={boardNameRef} required/>
				</FormGroup>
				<Button type="submit" color="primary">Create Board</Button>
				</Form>
			</CardBody>
			</Card>
		</section>
	  );
};

export default CreateBoardForm;