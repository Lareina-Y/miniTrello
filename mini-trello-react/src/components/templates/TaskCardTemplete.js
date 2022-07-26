import { Card, CardBody, CardTitle, Button } from "reactstrap";
import { Link } from 'react-router-dom';

const TaskCardTemplete = (props) => {
  return (
    <Card>
      <CardBody className="bg-light mb-3">
        <CardTitle className = "text-primary"tag="h5" >
          {props.title}</CardTitle>
        <Button color={props.color}>
          <Link to={props.path} className="text-decoration-none text-white">
              Read More
          </Link>
        </Button>
      </CardBody>
    </Card>
  );
};

export default TaskCardTemplete;
