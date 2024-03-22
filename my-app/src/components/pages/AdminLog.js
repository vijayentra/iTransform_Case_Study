import React from "react";
import { useNavigate } from "react-router-dom";
import Footer from "./Footer";
import Navbar from "../Navbar";
const AdminLog = () => {
  let navigate = useNavigate();
  return (
    <div>
      <Navbar />
      <h1 className="text-center">ADMIN LOGIN</h1>
      <div className="formDetails">
        <input type="text" placeholder="name" />
        <br />
        <br />
        <input type="password" placeholder="password" />
        <br />
        <button
          className="Btn"
          onClick={() => {
            navigate("/admin_home");
          }}
        >
          <i className="fas fa-arrow-right rightArw"></i>Login
        </button>
      </div>
      <Footer />
    </div>
  );
};

export default AdminLog;
