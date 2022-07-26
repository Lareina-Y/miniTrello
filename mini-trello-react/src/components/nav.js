import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { useLogged, logoutAction, userIdAction} from '../assets/appStatus/loggedin';
import { Navbar, Collapse, Nav, NavItem, DropdownToggle, Button,
  DropdownMenu, DropdownItem, Dropdown } from "reactstrap";
import Logo from "./logo";
import user1 from "../assets/images/users/user1.jpg";

function Navigation() {
  const logged = useLogged();
  const navigate = useNavigate();

  const [isOpen, setIsOpen] = React.useState(false);
  const [dropdownOpen, setDropdownOpen] = React.useState(false);
  const toggle = () => setDropdownOpen((prevState) => !prevState);
  const Handletoggle = () => {setIsOpen(!isOpen);};
  function LogOut() {
    logoutAction(false);
		userIdAction(0);
		navigate("/");
  }

  return (
    <Navbar color="primary" dark expand="md" className="fix-header">
      <div className="d-flex align-items-center">
        <div className="d-lg-block me-5 pe-3">
          <Logo />
        </div>
      </div>
      <div className="hstack gap-2">  {/* buger menu */}
        <Button color="primary" size="sm" className="d-sm-block d-md-none" onClick={Handletoggle}>
          {isOpen ? ( <i className="bi bi-x"></i>) : 
            ( <i className="bi bi-three-dots-vertical"></i> )}
        </Button>
      </div>

      <Collapse navbar isOpen={isOpen}>
        {logged?
        <Nav className="me-auto" navbar>
          <NavItem>
            <Link to="/" className="nav-link">Home</Link>
          </NavItem>
          <NavItem>
            <Link to="/create_workspaces" className="nav-link">Create Workspaces</Link>
          </NavItem>     
        </Nav> : 
        <Nav className="me-auto" navbar>
          <NavItem>
            <Link to="/login" className="nav-link">SignIn</Link>
          </NavItem>
          <NavItem>
            <Link to="/register" className="nav-link">SignUp</Link>
          </NavItem>
        </Nav>
        }
        {logged?
          <Dropdown isOpen={dropdownOpen} toggle={toggle}>
            <DropdownToggle color="transparent">
              <img src={user1} alt="profile" className="rounded-circle" width="30"></img>
            </DropdownToggle>
            <DropdownMenu>
              <DropdownItem onClick={LogOut}>
                Logout
              </DropdownItem>
            </DropdownMenu>
          </Dropdown> : ''  
        }
      </Collapse>
    </Navbar>
  );
};

export default Navigation;
