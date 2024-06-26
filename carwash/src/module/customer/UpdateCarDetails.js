import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function UpdateCarDetails(){

    const userData= JSON.parse(localStorage.getItem("userData"))
    const [selectedCarData, setSelectedCarData] = useState(JSON.parse(localStorage.getItem("selectedCarData"))||[])

    const [brand, setBrand] = useState('');
    const [model, setModel] = useState('');
    const [colour, setColour] = useState('');
    const [mfgYear, setMfgYear] = useState('');
    const [length, setLength] = useState('');
    const [numberPlate, setNumberPlate] = useState('');

    useEffect(() => {
        axios.get(`http://localhost:8088/customer/viewCarDetails/${userData.phoneNumber}/${selectedCarData.numberPlate}`).then((response) => {
          setNumberPlate(response.data.numberPlate);
          setBrand(response.data.brand);
          setModel(response.data.model);
          setColour(response.data.colour);
          setMfgYear(response.data.mfgYear);
          setLength(response.data.lengthInMM);
          console.log(response.data);
    })
    }, []);

    const handleBrandChange = (e) => {
        setBrand(e.target.value);
    };

    const handleModelChange = (e) => {
        setModel(e.target.value);
    };

    const handleColourChange = (e) => {
        setColour(e.target.value);
    };

    const handleMfgYearChange = (e) => {
        setMfgYear(e.target.value);
    };

    const handleLengthChange = (e) => {
        setLength(e.target.value);
    };

    const handleNumberPlateChange = (e) => {
        setNumberPlate(e.target.value);
    };

    const navigate = useNavigate()

    const submit = () => {
        const payload = {
            brand: brand,
            model: model,
            colour: colour,
            mfgYear: mfgYear,
            lengthInMM : length,
            numberPlate: numberPlate
        }
        axios.put(`http://localhost:8088/customer/updateCarDetails/${userData.phoneNumber}/${selectedCarData.numberPlate}`, payload).then((response) => {
            console.log('Updation Successful:', response.data);
            setSelectedCarData(response.data)
            const updatedUser = {...userData,
                carsList: userData.carsList.map(car => {
                    if (car.numberPlate === selectedCarData.numberPlate) {
                      return response.data; // Update the selected car in the user's cars array
                    } else {
                      return car;
                    }
                  }),}
            localStorage.setItem("userData",JSON.stringify(updatedUser))
            navigate("/cardetails")    
            alert("Details updated.")
        }).catch((error) => {
            console.error("Login error:", error);
            alert(error.response.data);
        })
    }

    return(
        
<form>
            <div className="relative z-0 w-full mb-6 group">
                <input
                    type="text"
                    name="numberPlate"
                    id="numberPlate"
                    value={numberPlate}
                    onChange={handleNumberPlateChange}
                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                    placeholder=" "
                    required
                />
                <label
                    for="numberPlate"
                    className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
                >
                    Car Number
                </label>
                <span className="text-xs text-red-500">sample format: 'MH04AA8888'</span>
            </div>
            <div className="relative z-0 w-full mb-6 group">
                <input
                    type="text"
                    name="brand"
                    id="brand"
                    value={brand}
                    onChange={handleBrandChange}
                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                    placeholder=" "
                    required
                />
                <label
                    for="brand"
                    className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
                >
                    Brand
                </label>
            </div>
            <div className="relative z-0 w-full mb-6 group">
                <input
                    type="text"
                    name="model"
                    id="model"
                    value={model}
                    onChange={handleModelChange}
                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                    placeholder=" "
                    required
                />
                <label
                    for="model"
                    className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
                >
                    Model
                </label>
            </div>
            <div className="relative z-0 w-full mb-6 group">
                <input
                    type="text"
                    name="colour"
                    id="colour"
                    value={colour}
                    onChange={handleColourChange}
                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                    placeholder=" "
                    required
                />
                <label
                    for="colour"
                    className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
                >
                    Colour
                </label>
            </div>
            <div className="relative z-0 w-full mb-6 group">
                <input
                    type="text"
                    name="mfgYear"
                    id="mfgYear"
                    value={mfgYear}
                    onChange={handleMfgYearChange}
                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                    placeholder=" "
                    required
                />
                <label
                    for="mfgYear"
                    className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
                >
                    Manufacturing Year
                </label>
                <span className="text-xs text-red-500">Min year: 2008</span>
            </div>
            <div className="relative z-0 w-full mb-6 group">
                <input
                    type="text"
                    name="length"
                    id="length"
                    value={length}
                    onChange={handleLengthChange}
                    className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                    placeholder=" "
                    required
                />
                <label
                    for="length"
                    className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
                >
                    Car Length
                </label>
                <span className="text-xs text-red-500">between 2000mm and 6000mm</span>
            </div>
            <button
                type="button"
                className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover-bg-blue-700 dark:focus:ring-blue-800"
                onClick={submit}
            >
                Submit
            </button>
        </form>
    )
}

export default UpdateCarDetails;