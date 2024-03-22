import React from "react";
import "./Style.css";

import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./module/Home";


function App() {
  return (
    <>
      <Router>
        <Routes>
          <Route path="/" element={<Home />} />
          
        </Routes>
      </Router>
      <Router></Router>
    </>
  );
}

export default App;
