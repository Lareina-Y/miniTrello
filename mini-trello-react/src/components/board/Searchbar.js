import React, {useState} from "react";
import { Button, Form, Input } from "reactstrap";

function SearchBar(props) {

    const [input, setInput] = useState("");

    function search(e){
        e.preventDefault();
        props.search(input);
    }

    return (
    <Form className="d-flex justify-content-between" onSubmit={search}>
        <Input
            type="text"
            id="header-search"
            placeholder="Search Task by Name"
            onChange = {(e) => {setInput(e.target.value)}}
            name="s" 
        />
        <Button color="primary" type="submit" className="btn btn-primary">Search</Button>
    </Form>
);}

export default SearchBar;
