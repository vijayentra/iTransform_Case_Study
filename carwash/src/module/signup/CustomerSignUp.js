import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function CustomerSignUp() {

    const [pNumber, setPNumber] = useState("");
    const [password, setPassword] = useState("");
    const [firstname, setFirstname] = useState("");
    const [lastname, setLastname] = useState("");
    const [pCode, setPCode] = useState("");

    const navigate = useNavigate()

    const submit = () => {
        const payload = {
            phoneNumber: pNumber,
            password: password,
            firstName: firstname,
            lastName: lastname,
            pinCode: pCode
        }
        axios.post("http://localhost:8088/customer/add", payload).then(() => {
            alert("Sign in successful")
            navigate("/logincustomer")
        }).catch((error) => {
            console.error("Login error:", error);
            alert("Invalid input.");
        })
    }

    return (
        <div>
            <section class="text-gray-600 body-font">
                <div class="container px-5 py-24 mx-auto flex flex-wrap items-center">
                    <div class="lg:w-3/5 md:w-1/2 md:pr-16 lg:pr-0 pr-0">
                        <h1 class="title-font font-medium text-3xl text-gray-900">Get ready to unlock the best in the business with just one more step! Join us today and experience excellence like never before.</h1>
                    </div>
                    <div class="lg:w-2/6 md:w-1/2 bg-gray-100 rounded-lg p-8 flex flex-col md:ml-auto w-full mt-10 md:mt-0">
                        <h2 class="text-gray-900 text-lg font-medium title-font mb-5">Customer Sign Up</h2>
                        <div class="relative mb-4">
                            <label for="phone no" class="leading-7 text-sm text-gray-600">PhoneNumber</label>
                            <input type="text" id="phoneno" name="pNumber" placeholder="PhoneNumber" value={pNumber} onChange={(e)=>setPNumber(e.target.value)} class="w-full bg-white rounded border border-gray-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out" />
                        </div>
                        <div class="relative mb-4">
                            <label for="email" class="leading-7 text-sm text-gray-600">Firstname</label>
                            <input type="text" id="firstname" name="firstname" placeholder="Firstname" value={firstname} onChange={(e)=>setFirstname(e.target.value)} class="w-full bg-white rounded border border-gray-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out" />
                        </div>
                        <div class="relative mb-4">
                            <label for="full-name" class="leading-7 text-sm text-gray-600">Lastname</label>
                            <input type="text" id="lastname" name="lastname" placeholder="Lastname" value={lastname} onChange={(e)=>setLastname(e.target.value)} class="w-full bg-white rounded border border-gray-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out" />
                        </div>
                        <div class="relative mb-4">
                            <label for="full-name" class="leading-7 text-sm text-gray-600">Password</label>
                            <input type="password" id="password" name="password" placeholder="Password" value={password} onChange={(e)=>setPassword(e.target.value)} class="w-full bg-white rounded border border-gray-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out" />
                        </div>
                        <div class="relative mb-4">
                            <label for="full-name" class="leading-7 text-sm text-gray-600">Pincode</label>
                            <input type="text" id="pcode" name="pCode" placeholder="Pincode" value={pCode} onChange={(e)=>setPCode(e.target.value)} class="w-full bg-white rounded border border-gray-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out" />
                        </div>
                        <button class="text-white bg-indigo-500 border-0 py-2 px-8 focus:outline-none hover:bg-indigo-600 rounded text-lg" onClick={submit}>Sign up</button>
                        {/* <p class="text-xs text-gray-500 mt-3">Literally you probably haven't heard of them jean shorts.</p> */}
                    </div>
                </div>
            </section>
        </div>
    )
}

export default CustomerSignUp;
