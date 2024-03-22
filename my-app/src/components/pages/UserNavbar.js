import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
// import './Navbar.css';

function UserNavbar() {
  const [click, setClick] = useState(false);
  const [button, setButton] = useState(true);

  const handleClick = () => setClick(!click);
  const closeMobileMenu = () => setClick(false);

  const showButton = () => {
    if (window.innerWidth <= 960) {
      setButton(false);
    } else {
      setButton(true);
    }
  };

  useEffect(() => {
    showButton();
  }, []);

  window.addEventListener("resize", showButton);

  return (
    <>
      <div className="ms-auto pe-md-5 navbar-nav">
        <li className="nav-item">
          <Link to="/" className="nav-link">
            Logout
          </Link>
        </li>
      </div>
    </>
  );
}
export default UserNavbar;
