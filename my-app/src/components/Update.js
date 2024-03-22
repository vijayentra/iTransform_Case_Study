import axios from "axios";
import { useState } from "react";
import React from "react";
// import { Link } from "react-router-dom";

import { Button } from "@mui/material";
import TextField from "@mui/material/TextField";

function Update() {
  const [name, setname] = useState("");
  const [username, setusername] = useState("");
  const [password, setpassword] = useState("");
  const [confirmpassword, setconfirmpassword] = useState("");
  const [contactno, setcontactno] = useState("");
  const [email, setemail] = useState("");

  async function handleSubmit(event) {
    event.preventDefault();
    try {
      await axios.put("http://localhost:8088/user/updateUser", {
        id: localStorage.getItem("update"),
        name: name,
        username: username,
        password: password,
        confirmpassword: confirmpassword,
        contactno: contactno,
        email: email,
      });
      setname("");
      setusername("");
      setpassword("");
      setconfirmpassword("");
      setcontactno("");
      setemail("");
      console.log(localStorage.getItem("update"));
    } catch (error) {
      console.log(error);
    }
  }
  return (
    <div>
      <form onSubmit={handleSubmit}>
        <h1>Update</h1>
        <p>Fill in the Information Below</p>
        <TextField
          id="outlined-basic"
          label="Outlined"
          variant="outlined"
          type="text"
          name="name"
          required
          placeholder="Name"
          onChange={(event) => {
            setname(event.target.value);
          }}
        />
        <br />
        <TextField
          id="outlined-basic"
          label="Outlined"
          variant="outlined"
          type="text"
          name="username"
          required
          placeholder="username"
          onChange={(event) => {
            setusername(event.target.value);
          }}
        />
        <br />
        <TextField
          id="outlined-basic"
          label="Outlined"
          variant="outlined"
          type="password"
          name="password"
          required
          placeholder="password"
          onChange={(event) => {
            setpassword(event.target.value);
          }}
        />
        <br />
        <TextField
          id="outlined-basic"
          label="Outlined"
          variant="outlined"
          type="password"
          name="confirmpassword"
          required
          placeholder="confirmpassword"
          onChange={(event) => {
            setconfirmpassword(event.target.value);
          }}
        />
        <br />

        <TextField
          id="outlined-basic"
          label="Outlined"
          variant="outlined"
          type="text"
          name="contactno"
          required
          placeholder="contactno"
          onChange={(event) => {
            setcontactno(event.target.value);
          }}
        />
        <br />
        <TextField
          id="outlined-basic"
          label="Outlined"
          variant="outlined"
          type="text"
          name="email"
          required
          placeholder="email"
          onChange={(event) => {
            setemail(event.target.value);
          }}
        />
        <br />

        <Button variant="contained" type="submit">
          Update
        </Button>
      </form>
    </div>
  );
}
export default Update;
