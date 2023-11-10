import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function CarDetails() {

    const userData= JSON.parse(localStorage.getItem("userData"))||{}
    // const cardetails = userData.carsList

    const [cardetails, setCarDetails] = useState([])
    useEffect(() => {
        const cars = async()=>{
            try {
                const response = await axios.get(`http://localhost:8088/customer/viewAllCarDetails/${userData.phoneNumber}`);
                console.log(response.data);
                setCarDetails(response.data);
              } catch (error) {
                // Handle any errors that occur during the request
                console.error('Error:', error);
              }
            };
        cars();
    }, []);

    const navigate = useNavigate();

    const handleEditCar = (carData) => {
        // Store the selected car's data in local storage
        localStorage.setItem("selectedCarData", JSON.stringify(carData));

        // Navigate to the "/updatecardetails" page
        navigate("/updatecardetails");
    };

    const handleDeleteCar = (nPlate) => {
        axios.delete(`http://localhost:8088/customer/deleteCarDetails/${userData.phoneNumber}/${nPlate}`).then(() => {
            alert("Car Deleted.");
            window.location.reload();
            // navigate("/cardetails")    
        }).catch((error) => {
            console.error("Login error:", error);
            alert("Wash booking exists for this car. Cancel it before deleting the car details. ");
        })
    };

    const addCar = () => {
        navigate("/addcar");
    };

    if(!cardetails){
        return (
            <div>
                loading...
            </div>
        )
    }

    return(
        
<div className="relative overflow-x-auto">
<div className="flex flex-col items-center mt-4">
    <button
        type="button"
        class="text-white bg-gradient-to-r from-blue-500 via-blue-600 to-blue-700 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-blue-300 dark:focus:ring-blue-800 shadow-lg shadow-blue-500/50 dark:shadow-lg dark:shadow-blue-800/80 font-medium rounded-lg text-sm px-5 py-2.5 text-center mr-2 mb-2"
        onClick={addCar}
        >
        Add New Car Details
    </button>
</div>
{cardetails.length === 0 ? (
    <div className="flex items-center justify-center h-32">
      <p className="text-2xl font-semibold text-gray-900 dark:text-white">No car details available</p>
    </div>
  ) : (
    <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
        <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
            <tr>
                <th scope="col" className="px-6 py-3">
                    Car Number
                </th>
                <th scope="col" className="px-6 py-3">
                    Brand
                </th>
                <th scope="col" className="px-6 py-3">
                    Model
                </th>
                <th scope="col" className="px-6 py-3">
                    Colour
                </th>
                <th scope="col" className="px-6 py-3">
                    Mfg Year
                </th>
                <th scope="col" class="px-6 py-3">
                    Action
                </th>
            </tr>
        </thead>
        <tbody>
        {cardetails.map((car) => (
                        <tr className="bg-white border-b dark:bg-gray-800 dark:border-gray-700" key={car.numberPlate}>
                            <th scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                {car.numberPlate}
                            </th>
                            <td className="px-6 py-4">
                                {car.brand}
                            </td>
                            <td className="px-6 py-4">
                                {car.model}
                            </td>
                            <td className="px-6 py-4">
                                {car.colour}
                            </td>
                            <td className="px-6 py-4">
                                {car.mfgYear}
                            </td>
                            <td class="flex items-center px-6 py-4 space-x-3">
                    <a href="/updatecardetails" class="font-medium text-blue-600 dark:text-blue-500 hover:underline" onClick={() => handleEditCar(car)} >Edit</a>
                    <a href="#" class="font-medium text-red-600 dark:text-red-500 hover:underline" onClick={() => handleDeleteCar(car.numberPlate)}>Remove</a>
                </td>
                </tr>
                    ))}
        </tbody>
    </table>
  )}
</div>

    )
}
export default CarDetails;