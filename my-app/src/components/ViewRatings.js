import axios from "axios";
import React, { useEffect, useState } from "react";
import { Button } from "@mui/material";
import "./Viewrating.css";
const ViewRatings = () => {
  const [getreview, setreview] = useState([]);
  const [getrating, setrating] = useState([]);
  const [getwashername, setwashername] = useState([]);

  const Tour = async (event) => {
    event.preventDefault();
    try {
      const result = await axios.get("http://localhost:8081/admin/allratings");
      // const newArray2  = result2.data.filter(function(ea){
      //     return ea.userId === localStorage.getItem("userId");
      // })
      const listItems = result.data.map((data1) => <p>{data1.review}</p>);
      const listItems1 = result.data.map((data1) => <p>{data1.rating}</p>);
      const listItems2 = result.data.map((data1) => <p>{data1.washername}</p>);
      setreview(listItems);
      setrating(listItems1);
      setwashername(listItems2);
    } catch (err) {
      console.log(err);
    }
  };
  // setInterval(Tour,100);

  return (
    <div>
      <h3>Ratings</h3>
      <center>
        {/* <label>
            
                   Enter CustomerId
            </label>
            <br/>
            <input type="number" placeholder='customerId'  id="customerId"/> */}
        <Button onClick={Tour} variant="contained">
          get
        </Button>
      </center>
      <div className="hiii">
        <div className="flex shadow border-b mt-3">
          <table id="customers">
            <thead className="bg-gray-200">
              <tr>
                <th className="text-left font-medium text-gray-600 uppercase tracking-wider py-3 px-6">
                  Review
                </th>
                <th className="text-left font-medium text-gray-600 uppercase tracking-wider py-3 px-6">
                  Rating
                </th>
                <th className="text-left font-medium text-gray-600 uppercase tracking-wider py-3 px-6">
                  Washer Name
                </th>
              </tr>
            </thead>
            <tbody className="bg-white">
              <td className="text-left px-6 py-4 whitespace-nowrap">
                <div className="text-1xl  text-gray-500 hover:delay-75">
                  {getreview}
                </div>
              </td>
              {/* <td className="text-left px-6 py-4 whitespace-nowrap">
                  <div className="text-1xl  text-gray-500 hover:delay-75">
                        {gettotalpersons}
                        </div>
                    </td> */}
              <td className="text-left px-6 py-4 whitespace-nowrap">
                <div className="text-1xl  text-gray-500 hover:delay-75">
                  {getrating}
                </div>
              </td>
              <td className="text-left px-6 py-4 whitespace-nowrap">
                <div className="text-1xl  text-gray-500 hover:delay-75">
                  {getwashername}
                </div>
              </td>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};
export default ViewRatings;
