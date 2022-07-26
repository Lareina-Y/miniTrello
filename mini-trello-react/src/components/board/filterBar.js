import React, {useState} from "react";
import { Form, Row, Col, Card, CardBody, Input, Label } from "reactstrap";

function FilterBar(props) {

    const [ratioStatus, setratioStatus] = useState("All")

    function handleChange (e) {
        e.preventDefault();
        setratioStatus(e.target.value)
        props.filter(e.target.value);
    }
    
    return (
        <Row className="mt-3">
            <Col lg="12">
            <Card className="bg-light mb-3">
            <CardBody>
                <Form className="radio-buttons d-flex justify-content-between" onChange={handleChange}>
                    <Label>Filter :</Label>
                    <Label><Input type="radio" name="due" value="All" checked={ratioStatus === "All"} /> All</Label>
                    <Label><Input type="radio" name="due" value="0" checked={ratioStatus === "0"}/> Due today</Label>
                    <Label><Input type="radio" name="due" value="1" checked={ratioStatus === "1"}/> Due in this week</Label>
                    <Label><Input type="radio" name="due" value="2" checked={ratioStatus === "2"}/> Overdue</Label>
                </Form>
            </CardBody>
            </Card>
            </Col>
        </Row>
    );
}

export default FilterBar;
