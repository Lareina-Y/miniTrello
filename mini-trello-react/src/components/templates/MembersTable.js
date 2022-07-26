import React, {useEffect, useState } from "react";
import { Card, CardBody, Table, Button } from "reactstrap";
import { useParams, useNavigate } from "react-router-dom";
import { userID } from '../../assets/appStatus/loggedin';

const MembersTable = () => {

  // Remember Destructuring here & useParams hooks
  const {workspace_id} = useParams();
  const user_id = userID();
  const navigate = useNavigate();

	const [usersData, setusersData] = useState([]);

	function getWorkspacesDetail(){
		fetch(`http://localhost:8080/workspace/get_users/${workspace_id}`)
		.then(response => response.json())
		.then(users => {
			setusersData(users);
		})
	}

  const deleteUser = (userId) => {
		fetch(`http://localhost:8080/user/removeUserFromWorkspace/${userId}?workspaceId=${workspace_id}`, 
    { method: 'PUT' })
    .then(() => userId === user_id ? navigate(`/home/${user_id}`) : null)
	};

	useEffect(function(){
		getWorkspacesDetail();
	}, []); // eslint-disable-line react-hooks/exhaustive-deps
  
  return (
    <div>
      <Card>
        <CardBody>
          <Table className="no-wrap mt-3 align-middle" responsive borderless>
            <thead>
              <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Remove</th>
              </tr>
            </thead>
            <tbody>
              {usersData.map((tdata, index) => (
                <tr key={index} className="border-top">
                  <td>
                    <div className="d-flex align-items-center">
                      <div>
                        {tdata.user_id === user_id ? (
                          <h6 className="mb-0 text-primary">{tdata.firstname} {tdata.lastname} (me)</h6>
                        ) : (
                          <h6 className="mb-0">{tdata.firstname} {tdata.lastname}</h6>
                        )}
                      </div>
                    </div>
                  </td>
                  <td>{tdata.email}</td>
                  <td>
                    {tdata.user_id === user_id ? (
                      <Button className="btn-danger" onClick={() => deleteUser(tdata.user_id)}>
                        Drop
                      </Button>
                    ) : ''}
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </CardBody>
      </Card>
    </div>
  );
};

export default MembersTable;
