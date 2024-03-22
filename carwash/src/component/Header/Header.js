import React from "react";
import { useLocation } from "react-router-dom";
import CustomerHeader from "./CustomerHeader";
import CommonHeader from "./CommonHeader";

function Header() {
  const location = useLocation();

  // Check the route and conditionally render the appropriate header
  if (location.pathname.includes("/customerdashboard")) {
    return <CustomerHeader />;
  } else {
    return <CommonHeader />;
  }
}

export default Header;