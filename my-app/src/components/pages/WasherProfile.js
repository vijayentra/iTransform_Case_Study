import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Table } from "react-bootstrap";
import Footer from "./Footer";
import axios from "axios";

import { Card, Form, Button, Col, ButtonGroup } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUsers, faEdit, faTrash } from "@fortawesome/free-solid-svg-icons";

export default class WasherProfile extends Component {
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
    fetch("http://localhost:8082/wash/allwashers")
      .then((res) => res.json())
      .then((json) => {
        this.setState({
          items: json,
          DataisLoaded: true,
        });
      });
  }
  //End
  // deleteWasher = (id) => {
  //   fetch("http://localhost:8082/user/deleteUser/{id}?id=" + id, {
  //     method: "DELETE",
  //     headers: {
  //       "content-type": "application/json",
  //       accept: "application/json",
  //       "Access-Control-Allow-Origin": "*",
  //     },
  //   })
  //     .then((res) => res.json())
  //     .then((json) => {
  //       this.setState({
  //         items: json,
  //         DataisLoaded: true,
  //       });
  //     });
  // };

  render() {
    const { DataisLoaded, items } = this.state;
    const dlt = async (event, id) => {
      alert(id);
      await axios.delete(
        "http://localhost:8082/wash/deletewasher/{id}?id=" + id.toString()
      );
    };

    return (
      <>
        <Card className={"border border-dark bg-dark text-white"}>
          <Card.Header>
            <FontAwesomeIcon icon={faUsers} /> Washer Profile View
          </Card.Header>
          <Card.Body>
            <Table bordered hover striped variant="dark">
              <thead>
                <tr align="center">
                  <th>Name</th>
                  <th>Location</th>
                  <th>Email</th>
                  <th>Contact</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {items.map((item) => (
                  <tr align="center">
                    <td>{item.name}</td>
                    <td>{item.location}</td>
                    <td>{item.email}</td>
                    <td>{item.contanct}</td>

                    <td>
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
                    </td>
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
