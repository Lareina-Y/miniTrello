import React, { useEffect, useState } from "react";
import { Row, Col } from "reactstrap";
import { useParams, useNavigate } from "react-router-dom";
import CardTemplate from "../components/templates/CardTemplate";
import { useLogged } from '../assets/appStatus/loggedin';
import bg1 from "../assets/images/bg/bg1.jpg";
import bg2 from "../assets/images/bg/bg2.jpg";
import bg3 from "../assets/images/bg/bg3.jpg";
import bg4 from "../assets/images/bg/bg4.jpg";

const image = [bg1, bg2, bg3, bg4]

function HomePage () {

    const {user_id} = useParams();
	const [workspaceData, setWorkspacesData] = useState([]);
	const logined = useLogged();
	const navigate = useNavigate();
	
	function checkLogin(){
		if(!logined){
			navigate(`/login`)
		}
	}

	function getAllWorkspaces(){
		fetch(`http://localhost:8080/user/get_workspaces/${user_id}`)
		.then(response => response.json())
		.then(workspaces => {
			setWorkspacesData(workspaces);
		})
	}

	useEffect(function(){
		checkLogin();
		getAllWorkspaces();
	}, []) // eslint-disable-line react-hooks/exhaustive-deps

	return (
		<div>
		<h3 className="mb-3">WORKSPACES :</h3>
		<Row>
			{workspaceData.map((blg, index) => (
				
			<Col sm="6" lg="6" xl="3" key={index}>
				<CardTemplate
				image={image[index%4]}
				title={blg.name}
				text={blg.description}
				color="primary"
				path={`/workspace/` + blg.id}
				/>
			</Col>
			))}
		</Row>
		</div>
	);
};

export default HomePage;