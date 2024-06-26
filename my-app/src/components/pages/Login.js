import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./login.css";
import Navbar from "../Navbar";
import Footer from "./Footer";
import Signup from "./SignUp";

import {
  FacebookLoginButton,
  GoogleLoginButton,
} from "react-social-login-buttons";

import { createButton } from "react-social-login-buttons";
const config = {
  text: "Log in with Facebook",
  icon: "facebook",
  iconFormat: (name) => `fa fa-${name}`,
  style: { background: "#3b5998" },
  activeStyle: { background: "#293e69" },
};
/** My Facebook login button. */
const MyFacebookLoginButton = createButton(config);

const config1 = {
  activeStyle: { background: "#EFF0EE" },
  icon: "google",
  style: { background: "white", color: "black" },
  text: "Log in with Google",
};

const Login = () => {
  let navigate = useNavigate();
  const [name, setName] = useState("");
  const [pass, setPss] = useState("");
  const [nameErr, setNameErr] = useState(false);
  const [passErr, setpassErr] = useState(false);

  const handleName = (e) => {
    setName(e.target.value);
    setNameErr(false);
  };
  const handlePass = (e) => {
    setPss(e.target.value);
    setpassErr(false);
  };
  const handleClick = () => {
    if (name && pass) {
      const data = {
        username: name,
        password: pass,
      };
      if (name === "admin" && pass === "1234") {
        axios
          .post("http://localhost:8088/user/login", data)
          .then(function (response) {
            if (response && response.data) {
              if (response.data === "logged in") {
                navigate("/Admin");
              } else {
                alert("wrong credentials.");
              }
            }
          })
          .catch(function (error) {
            console.log(error);
          });
      } else {
        axios
          .post("http://localhost:8088/user/login", data)
          .then(function (response) {
            if (response && response.data) {
              if (response.data === "logged in") {
                navigate("/Userhome");
              } else {
                alert("wrong credentials.");
              }
            }
          })
          .catch(function (error) {
            console.log(error);
          });
      }
    } else if (name) {
      setpassErr(true);
      setNameErr(false);
    } else if (pass) {
      setNameErr(true);
      setpassErr(false);
    } else {
      setpassErr(true);
      setNameErr(true);
    }
  };

  return (
    <>
      <Navbar />
      <div>
        <h1 className="title1a">LOGIN</h1>
        <div
          className="loginForm"
          style={{
            backgroundImage: "url('C:\Users\moghansh\Downloads\carsignup.jpg');",
          }}
        >
          <div className="name">
            <label>Username</label>
            <input
              type="text"
              placeholder="  Enter Username"
              value={name}
              onChange={handleName}
            />
            <br></br>
            {nameErr && <span>Enter valid name</span>}
            <br />
            <label>Password</label>
            <input
              type="password"
              placeholder="password"
              value={pass}
              onChange={handlePass}
            />
            <br></br>
            {passErr && <span>Enter valid password</span>}
          </div>
          <br />

          <button className="Btn" onClick={handleClick}>
            <i className="fas fa-arrow-right rightArw"></i> Login
          </button>

          <div className="text-center pt-3">Or</div>
          {/* <FacebookLoginButton className="mt-3 mb-3">
            <span>Sign up with Facebook</span>
          </FacebookLoginButton>
          <GoogleLoginButton className="mt-3 mb-3">
            <span>Sign up with Google</span>
          </GoogleLoginButton> */}

          <button
            className="Btn"
            onClick={() => {
              navigate("/Signup");
            }}
          >
            SignUp
          </button>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default Login;
