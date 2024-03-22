import React from "react";
import "./Footer.css";
import { Link } from "react-router-dom";

function Footer() {
  return (
    <div className="footer-container" id="about">
      <section className="footer-subscription">
        <p className="footer-subscription-heading">
          For You Excellence is our best habit. We wash cars with modern
          Detailing Technology
        </p>
      </section>
      <div className="footer-links">
        <div className="footer-link-wrapper">
          <div className="footer-link-items">
            <h2>About Us</h2>

            <Link to="/">
              Making cars clean, shiny and glossy is our main specialty! On par
              with dedicated and thorough exterior and interior washing
              services, we also offer such detailing help as interior vacuuming,
              polishing, waxing and many others...
            </Link>
          </div>
        </div>

        <div className="footer-link-items">
          <h2>Need Help ?</h2>
          <Link to="/">We are here to help you</Link>
        </div>

        <div className="footer-link-items" id="Contact">
          <h2>Contact Us</h2>
          <Link to="/">We are open</Link>
          <Link to="/"> Mon-sat 09:00 am - 09:00 pm</Link>
          <Link to="/"> Mohanish Khotele</Link>
          <Link to="/">Contact (+91)8668431993</Link>

          <Link to="/">
            We're here near Thane-Belapur Road, TTC Industrial Area, Airoli, Navi Mumbai, Maharashtra 400708
          </Link>
        </div>
      </div>
      <section className="social-media">
        <div className="social-media-wrap">
          <div className="footer-logo">
            <Link to="/" className="social-logo">
              CarWash-Centre
              {/* <i class="fab fa-swift"></i> */}
            </Link>
          </div>
          <small className="website-rights"> </small>
          <div className="social-icons">
            <Link
              className="social-icon-link facebook"
              to="/"
              target="_blank"
              aria-label="Facebook"
            >
              <i className="fab fa-facebook-f" />
            </Link>
            <Link
              className="social-icon-link instagram"
              to="/"
              target="_blank"
              aria-label="Instagram"
            >
              <i className="fab fa-instagram" />
            </Link>
            <Link
              className="social-icon-link youtube"
              to="/"
              target="_blank"
              aria-label="Youtube"
            >
              <i className="fab fa-youtube" />
            </Link>
            {/* <Link
              className="social-icon-link twitter"
              to="/"
              target="_blank"
              aria-label="Twitter"
            >
              <i className="fab fa-twitter" />
            </Link> */}
          </div>
        </div>
      </section>
    </div>
  );
}

export default Footer;
