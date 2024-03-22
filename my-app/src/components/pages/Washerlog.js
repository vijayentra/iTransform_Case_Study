import React from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "../Navbar";
import Footer from "./Footer";

const WasherLogin = () => {
  let navigate = useNavigate();
  return (
    <>
      <Navbar />
      <div>
        <h1 className="text-center">WASHER LOGIN</h1>
        <div className="formDetails">
          <input type="text" placeholder="name" />
          <br />
          <br />
          <input type="password" placeholder="password" />
          <br />
          <button
            className="Btn"
            onClick={() => {
              navigate("/washerhome");
            }}
          >
            <i className="fas fa-arrow-right rightArw"></i>Login
          </button>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default WasherLogin;
