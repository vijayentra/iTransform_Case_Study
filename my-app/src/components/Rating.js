import axios from "axios";
import { useState } from "react";
import React from "react";
// import { Link } from "react-router-dom";

import { Button } from "@mui/material";
import TextField from "@mui/material/TextField";

function Rating() {
  const [review, setreview] = useState("");
  const [rating, setrating] = useState("");
  const [washername, setwashername] = useState("");

  async function handleSubmit(event) {
    event.preventDefault();
    try {
      await axios.post("http://localhost:8081/admin/addratings", {
        rating: rating,
        review: review,
        washername: washername,
      });
      setrating("");
      setreview("");
      setwashername("");
      alert("success");
    } catch (error) {
      console.log(error);
    }
  }
  return (
    <div>
      <form onSubmit={handleSubmit}>
        <h1>washerRating</h1>
        <p>Fill in the Information Below</p>
        <TextField
          id="outlined-basic"
          label="Outlined"
          variant="outlined"
          type="number"
          name="rating"
          required
          placeholder="rating"
          onChange={(event) => {
            setrating(event.target.value);
          }}
        />
        <br />
        <TextField
          id="outlined-basic"
          label="Outlined"
          variant="outlined"
          type="text"
          name="review"
          required
          placeholder="review"
          onChange={(event) => {
            setreview(event.target.value);
          }}
        />
        <br />
        <TextField
          id="outlined-basic"
          label="Outlined"
          variant="outlined"
          type="text"
          name="washername"
          required
          placeholder="washername"
          onChange={(event) => {
            setwashername(event.target.value);
          }}
        />

        <br />

        <Button variant="contained" type="submit">
          submit
        </Button>
      </form>
    </div>
  );
}
export default Rating;
