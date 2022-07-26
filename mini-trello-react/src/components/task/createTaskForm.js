import React, { useRef } from "react";
import { Card, CardBody, Button, Form, FormGroup, Label, Input } from "reactstrap";

function CreateTaskForm(props) {

    const taskTitleRef = useRef();
    const taskDescriptionRef = useRef();
    const taskDeadlineRef = useRef();

	function createTask (e) {
		e.preventDefault();
		const TaskTitle = taskTitleRef.current.value;
        const TaskDescription = taskDescriptionRef.current.value;
        const TaskStatus = document.getElementById("status").value;
        const TaskDeadline = taskDeadlineRef.current.value;

		const newTask = {
			title: TaskTitle,
            description: TaskDescription,
            status: TaskStatus,
            deadline: TaskDeadline
		}
        props.createTask(newTask); 
	};

    return (
		<section>
			<h3 className="mb-3">Create Task:</h3>
			<Card>
			<CardBody>
				<Form onSubmit={createTask}>
				<FormGroup>
					<Label for="task_title">New Task Title:</Label>
					<Input type="text" innerRef={taskTitleRef} required/>
				</FormGroup>
                <FormGroup>
					<Label for="task_description">New Task Description:</Label>
					<Input type="text" innerRef={taskDescriptionRef} required/>
				</FormGroup>
				<FormGroup>
					<Label for="task_status">Task status: *</Label>
					<Input name="select" type="select" id="status">
						<option value={0} style={{padding: "5px", fontSize: "14px"}}>To-do</option>
						<option value={1} style={{padding: "5px", fontSize: "14px"}} >Doing</option>
						<option value={2} style={{padding: "5px", fontSize: "14px"}}>Done</option>
					</Input>
				</FormGroup>
                <FormGroup>
					<Label for="deadline">Deadline: *</Label>
					<Input type="date" innerRef={taskDeadlineRef}/>
				</FormGroup>
				<Button type="submit" color="primary">Create Task</Button>
				<p className="mt-3">
					* Task status will automatically be To-do. <br/>
					* Deadline is not required, can be edited later.
				</p>
				</Form>
			</CardBody>
			</Card>
		</section>
	  );
};

export default CreateTaskForm;