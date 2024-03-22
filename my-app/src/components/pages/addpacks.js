import React, { useState } from "react";
import axios from "axios";

import { toast } from "react-toastify";

import { Card, Form, Button, Col, ButtonGroup } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faUndo,
  faSave,
  faPlusSquare,
} from "@fortawesome/free-solid-svg-icons";
import { CardBody } from "reactstrap";

const Addpacks = () => {
  const [id, setId] = useState("");
  const [packname, setName] = useState("");
  const [cost, setCost] = useState("");
  const [description, setDescription] = useState("");

  const inputidHandler = (e) => {
    setId(e.target.value);
  };
  const inputnameHandler = (e) => {
    setName(e.target.value);
  };
  const inputcostdHandler = (e) => {
    setCost(e.target.value);
  };
  const inputdescriptionHandler = (e) => {
    setDescription(e.target.value);
  };

  const handleSubmit = (e) => {
    toast.success("Submitted Successfully", {
      position: "top-center",
    });
    const data = {
      id: id,
      packname: packname,
      cost: cost,
      description: description,
    };
    axios
      .post("http://localhost:8081/admin/addpack", data)
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });

    e.preventDefault();
  };

  return (
    <>
      <Card className={"border border-dark bg-dark text-white"}>
        <Card.Header>
          <FontAwesomeIcon icon={faPlusSquare} />
          <h2>Add New Packages</h2>
        </Card.Header>
        <Card.Body>
          <Form.Row>
            <form className="form" onSubmit={handleSubmit}>
              <div className="input">
                <label>id: </label>
                <input type="text" value={id} onChange={inputidHandler} />
              </div>
              <div className="input">
                <label>packname: </label>
                <input
                  type="text"
                  value={packname}
                  onChange={inputnameHandler}
                />
              </div>
              <div className="input">
                <label>cost: </label>
                <input type="text" value={cost} onChange={inputcostdHandler} />
              </div>
              <div className="input">
                <label>Descripton: </label>
                <input
                  type="text"
                  value={description}
                  onChange={inputdescriptionHandler}
                />
              </div>

              <div>
                <ButtonGroup>
                  <Button size="sm" variant="success" type="submit">
                    <FontAwesomeIcon icon={faSave} />
                    Submit
                    <br></br>
                  </Button>{" "}
                  <Button size="sm" variant="info" type="reset">
                    <FontAwesomeIcon icon={faUndo} /> Reset
                  </Button>
                </ButtonGroup>
              </div>
            </form>
          </Form.Row>
        </Card.Body>
      </Card>
      <div className="in">
        <div className="container"></div>
      </div>
    </>
  );
};

export default Addpacks;
