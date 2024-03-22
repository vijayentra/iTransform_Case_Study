import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Table } from "react-bootstrap";
import Footer from "./Footer";
import { Card, Form, Button, Col, ButtonGroup } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
//import { useNavigate } from 'react-router-dom';
import { useHistory } from "react-router-dom";

import {
  faUsers,
  faEdit,
  faTrash,
  faSave,
  faPlusSquare,
} from "@fortawesome/free-solid-svg-icons";
import axios from "axios";

export default class Profile extends Component {
  // Start
  // Constructor

  constructor(props) {
    super(props);

    this.state = {
      items: [],
      DataisLoaded: false,
    };
  }

  // ComponentDidMount is used to
  // execute the code
  componentDidMount() {
    fetch("http://localhost:8088/user/allusers")
      .then((res) => res.json())
      .then((json) => {
        this.setState({
          items: json,
          DataisLoaded: true,
        });
      });
  }

  //End

  render() {
    // const navigate=useNavigate();
    // const GotoNext = () => {
    //   navigate("/update");
    // };
    const { DataisLoaded, items } = this.state;
    const abc = (event, id) => {
      event.preventDefault();
      localStorage.setItem("update", id);
      // navigate('/update')
      // GotoNext();
    };

    const dlt = async (event, id) => {
      alert(id);
      await axios.delete(
        "http://localhost:8088/user/deleteUser/{id}?id=" + id.toString()
      );
    };
    return (
      <>
        <Card className={"border border-dark bg-dark text-white"}>
          <Card.Header>
            <FontAwesomeIcon icon={faUsers} />
            user Profile View
          </Card.Header>
          <Card.Body>
            <Table bordered hover striped variant="dark">
              <thead>
                <tr align="center">
                  <th>id</th>
                  <th>Name</th>
                  <th>Username</th>
                  <th>Email</th>
                  <th>Contact</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {items.map((item) => (
                  <tr align="center">
                    <td>{item.id}</td>
                    <td>{item.name}</td>
                    <td>{item.username}</td>
                    <td>{item.email}</td>
                    <td>{item.contactno}</td>
                    <ButtonGroup>
                      <a href="/update" className="outline-primary">
                        Update
                      </a>
                      <Button
                        size="sm"
                        variant="outline-danger"
                        onClick={(event, id) => dlt(event, item.id)}
                      >
                        <FontAwesomeIcon icon={faTrash} />
                      </Button>
                    </ButtonGroup>
                  </tr>
                ))}
              </tbody>
            </Table>
          </Card.Body>
        </Card>
        <div>
          <Footer />
        </div>
      </>
    );
  }
}
