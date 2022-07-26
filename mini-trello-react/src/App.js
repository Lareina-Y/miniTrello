import React from "react";
import {Routes, Route, BrowserRouter} from "react-router-dom";
import Navigation from "./components/nav";
import RegisterPage from "./pages/register";
import LoginPage from "./pages/login";
import ForgetPasswordPage from "./pages/forget_pw";
import HomePage from "./pages/home";
import WorkspacePage from "./pages/workspace";
import BoardPage from "./pages/board";
import CreateWorkspacePage from "./pages/create_workspace";
import CreateBoardPage from "./pages/create_board";
import AddMembersPage from "./pages/addMembers";
import TaskPage from "./pages/task";
import { Container } from "reactstrap";
import CreateTaskPage from "./pages/create_task";

function App() {
	return (
    <div className="App">
      <BrowserRouter>
        <Navigation />
        <div className="pageWrapper d-lg-flex">
          <div className="contentArea">
            <Container className="p-4" fluid>
              <Routes>
                <Route path="/" element={<LoginPage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route path="/forget_password" element={ <ForgetPasswordPage />} />
                <Route path="/home/:user_id" element={<HomePage />} />
                <Route path="/workspace/:workspace_id" element={<WorkspacePage />} />
                <Route path="/board/:workspace_id/:board_id/:board_name" element={<BoardPage />} />
                <Route path="/create_workspaces" element={<CreateWorkspacePage />} /> 
                <Route path="/create_board/:workspace_id" element={<CreateBoardPage />} /> 
                <Route path="/create_task/:workspace_id/:board_id/:board_name" element={<CreateTaskPage />} /> 
                <Route path="/addMembers/:workspace_id" element={<AddMembersPage />} /> 
                <Route path="/task/:task_id/:workspace_id/:board_id/:board_name" element={<TaskPage />} />
              </Routes>
            </Container>
          </div>
        </div>
      </BrowserRouter> 
    </div>
	);
}

export default App;
