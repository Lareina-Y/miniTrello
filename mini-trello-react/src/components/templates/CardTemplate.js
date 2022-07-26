import { Card, CardBody, CardImg, CardText, CardTitle, Button } from "reactstrap";
import { Link } from 'react-router-dom';

const CardTemplate = (props) => {
  return (
    <Card>
      <CardImg alt="Card image cap" src={props.image} />
      <CardBody className="p-4">
        <CardTitle tag="h5">{props.title}</CardTitle>
        <CardText className="mt-3">{props.text}</CardText>
        <Button color={props.color}>
          <Link to={props.path} className="text-decoration-none text-white">
              Read More
          </Link>
        </Button>
      </CardBody>
    </Card>
  );
};

export default CardTemplate;
