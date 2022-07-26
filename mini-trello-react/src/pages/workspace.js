import React, {useEffect, useState} from "react";
import { Row, Col, Card, CardBody, CardTitle, Input } from "reactstrap";
import { Link, useParams, useNavigate } from "react-router-dom";
import MembersTables from "../components/templates/MembersTable";
import CardTemplate from "../components/templates/CardTemplate";
import { useLogged } from '../assets/appStatus/loggedin';
import bg1 from "../assets/images/bg/bg1.jpg";
import bg2 from "../assets/images/bg/bg2.jpg";
import bg3 from "../assets/images/bg/bg3.jpg";
import bg4 from "../assets/images/bg/bg4.jpg";

const image = [bg1, bg2, bg3, bg4]
  
const WorkspacePage = () => {

    const {workspace_id} = useParams();
	const [boardsList, setboardsList] = useState([]);
	const [createBoardsLink, setcreateBoardsLink] = useState([]);
	const [addMembersLink, setaddMembersLink] = useState([]);
	const [detailUpdate, setdetailUpdate] = useState([]);
	
	const [workspaceName, setWorkspaceName] = useState([]);
  	const onChange_workspaceName = (event) => setWorkspaceName(event.target.value);

	const [workspaceD, setWorkspaceD] = useState([]);
  	const onChange_workspaceD = (event) => setWorkspaceD(event.target.value);

	const logined = useLogged();
	const navigate = useNavigate();
	
	function checkLogin(){
		if(!logined){
			navigate(`/login`)
		}
	}
	  
	function getWorkspacesDetail(){
		fetch(`http://localhost:8080/workspace/get_workspace/${workspace_id}`)
		.then(response => response.json())
		.then(workspace => {
			setWorkspaceName(workspace.name)
			setWorkspaceD(workspace.description)
			setboardsList(workspace.boards)
		})
	}

	useEffect(function(){
		checkLogin();
		getWorkspacesDetail();
		setcreateBoardsLink(`/create_board/${workspace_id}`)
		setaddMembersLink(`/addMembers/${workspace_id}`)
		setdetailUpdate(false)
	}, []) // eslint-disable-line react-hooks/exhaustive-deps

	const toggle = () => setdetailUpdate((detailUpdate) => !detailUpdate);

	async function update() {
		await fetch(`http://localhost:8080/workspace/update/name/${workspace_id}`, { 
			method: 'PUT',
			body: JSON.stringify({name: workspaceName}),
			headers: {
				'Content-Type': 'application/json',
			} 
		}).then(response => response.json())
		.then(workspace => {
			setWorkspaceName(workspace.name)
		})

		await fetch(`http://localhost:8080/workspace/update/description/${workspace_id}`, { 
			method: 'PUT',
			body: JSON.stringify({description: workspaceD}),
			headers: {
				'Content-Type': 'application/json',
			}  
		})
		.then(response => response.json())
		.then(workspace => {
			setWorkspaceD(workspace.description)
			toggle()
		})
	}

	return (
		<div>
		<div className="d-flex justify-content-between">
			<h3 className="mb-3">WORKSPACE DETAIL :</h3>
			{detailUpdate? <Link to="" onClick={update}>Update</Link> :
			<Link to="" onClick={toggle}>Edit</Link>}
		</div>
		<Row>
			<Col lg="12">
				<Card>
					<CardTitle tag="h6" className="border-bottom p-3 mb-0">
						Name: {detailUpdate? 
						<Input type="text" value={workspaceName} onChange={onChange_workspaceName}>
						</Input>: workspaceName}
					</CardTitle>
					<CardBody>
						Description: {detailUpdate? 
						<Input type="textarea" value={workspaceD} onChange={onChange_workspaceD}>
						</Input>: workspaceD}
					</CardBody>
				</Card>
			</Col>
		</Row>
		<div className="d-flex justify-content-between">
			<h3 className="mb-3">BOARDS : </h3>
			<Link to={createBoardsLink}>Add more Boards</Link>
		</div>
		<Row>
			{boardsList.map((blg, index) => (
			<Col sm="6" lg="6" xl="3" key={index}>
				<CardTemplate
				image={image[index%4]}
				title={blg.name}
				text=''
				color="primary"
				path={`/board/${workspace_id}/${blg.boardId}/${blg.name}`}
				/>
			</Col>
			))}
		</Row>
		<div className="d-flex justify-content-between">
			<h3 className="mb-3">MEMBERS :</h3>
			<Link to={addMembersLink}>Add more Members</Link>
		</div>
		<Col lg="12">
			<MembersTables />
		</Col>
		</div>
	);
}

export default WorkspacePage;
