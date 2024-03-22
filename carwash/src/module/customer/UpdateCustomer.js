import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function UpdateCustomer(){

    const userData= JSON.parse(localStorage.getItem("userData"))

    const [pNumber, setPNumber] = useState("");
    const [password, setPassword] = useState("");
    const [firstname, setFirstname] = useState("");
    const [lastname, setLastname] = useState("");
    const [pCode, setPCode] = useState("");

    useEffect(() => {
        axios.get(`http://localhost:8088/customer/viewCustomer/${userData.phoneNumber}`).then((response) => {
          setFirstname(response.data.firstName);
          setLastname(response.data.lastName);
          setPassword(response.data.password);
          setPNumber(response.data.phoneNumber);
          setPCode(response.data.pinCode);
          console.log(response.data);
        });
      }, []);

    const navigate = useNavigate()

    const submit = () => {
        const payload = {
            phoneNumber: pNumber,
            password: password,
            firstName: firstname,
            lastName: lastname,
            pinCode: pCode
        }
        console.log(payload);
        axios.put(`http://localhost:8088/customer/updateCustomer/${userData.phoneNumber}`, payload).then((response) => {
            const updatedUserData = { ...userData, ...response.data };
            localStorage.setItem('userData', JSON.stringify(updatedUserData));
            alert("Updated successfully")
            navigate("/customerprofile")
        }).catch((error) => {
            console.error("Login error:", error);
            alert(error.response.data);
        })
    }

    return (
        <div>
            <section class="text-gray-600 body-font">
                <div class="container px-5 py-24 mx-auto flex flex-wrap items-center">
                    <div class="lg:w-2/6 md:w-1/2 bg-gray-100 rounded-lg p-8 flex flex-col mx-auto my-auto w-full">
                        <h2 class="text-gray-900 text-lg font-medium title-font mb-5">UPDATE DETAILS</h2>
                        <div class="relative mb-4">
                            <label for="phone no" class="leading-7 text-sm text-gray-600">PhoneNumber</label>
                            <input type="text" id="phoneno" name="pNumber" placeholder="PhoneNumber" value={pNumber} onChange={(e)=>setPNumber(e.target.Value)} class="w-full bg-white rounded border border-gray-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out" />
                            <span class="text-xs text-red-500">10 digit number starting with digits from 6-9</span>
                        </div>
                        <div class="relative mb-4">
                            <label for="email" class="leading-7 text-sm text-gray-600">Firstname</label>
                            <input type="text" id="firstname" name="firstname" placeholder="Firstname" value={firstname} onChange={(e)=>setFirstname(e.target.value)} class="w-full bg-white rounded border border-gray-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out" />
                            <span class="text-xs text-red-500">First letter capital</span>
                        </div>
                        <div class="relative mb-4">
                            <label for="full-name" class="leading-7 text-sm text-gray-600">Lastname</label>
                            <input type="text" id="lastname" name="lastname" placeholder="Lastname" value={lastname} onChange={(e)=>setLastname(e.target.value)} class="w-full bg-white rounded border border-gray-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out" />
                            <span class="text-xs text-red-500">First letter capital</span>
                        </div>
                        <div class="relative mb-4">
                            <label for="full-name" class="leading-7 text-sm text-gray-600">Password</label>
                            <input type="password" id="password" name="password" placeholder="Password" value={password} onChange={(e)=>setPassword(e.target.value)} class="w-full bg-white rounded border border-gray-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out" />
                            <span class="text-xs text-red-500">Min 8 characters, alteast one capital, number and special charater</span>
                        </div>
                        <div class="relative mb-4">
                            <label for="full-name" class="leading-7 text-sm text-gray-600">Pincode</label>
                            <input type="text" id="pcode" name="pCode" placeholder="Pincode" value={pCode} onChange={(e)=>setPCode(e.target.value)} class="w-full bg-white rounded border border-gray-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out" />
                            <span class="text-xs text-red-500">6 digit number starts with '400'</span>
                        </div>
                        <button class="text-white bg-indigo-500 border-0 py-2 px-8 focus:outline-none hover:bg-indigo-600 rounded text-lg" onClick={submit}>Finish</button>
                        {/* <p class="text-xs text-gray-500 mt-3">Literally you probably haven't heard of them jean shorts.</p> */}
                    </div>
                </div>
            </section>
        </div>
    )
}

export default UpdateCustomer;