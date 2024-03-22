import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function WasherSignUp() {

    const [pNumber, setPNumber] = useState("");
    const [password, setPassword] = useState("");
    const [firstname, setFirstname] = useState("");
    const [lastname, setLastname] = useState("");
    const [age, setAge] = useState("");

    const navigate = useNavigate()

    const submit = () => {
        const payload = {
            phoneNumber: pNumber,
            password: password,
            firstName: firstname,
            lastName: lastname,
            age: age
        }
        axios.post("http://localhost:8086/washer/add", payload).then(() => {
            navigate("/loginwasher")    
            alert("Sign in successful")
        }).catch((error) => {
            console.error("Login error:", error);
            alert(error.response.data);
        })
    }

    return (
        <div>
            <section class="text-gray-600 body-font">
                <div class="container px-5 py-24 mx-auto flex flex-wrap items-center">
                    <div class="lg:w-3/5 md:w-1/2 md:pr-16 lg:pr-0 pr-0">
                        <h1 class="title-font font-medium text-3xl text-gray-900">Drive into a world of opportunity at our car wash! Join our team of dedicated washers and be a part of the crew that keeps vehicles shining and customers smiling.</h1>
                    </div>
                    <div class="lg:w-2/6 md:w-1/2 bg-gray-100 rounded-lg p-8 flex flex-col md:ml-auto w-full mt-10 md:mt-0">
                        <h2 class="text-gray-900 text-lg font-medium title-font mb-5">Washer Sign Up</h2>
                        <label for="phone no" class="leading-6 text-sm text-gray-600">*All Fields are mandatory</label>
                        <div class="relative mb-4">
                            <label for="phone no" class="leading-7 text-sm text-gray-600">PhoneNumber</label>
                            <input type="text" id="phoneno" name="pNumber" placeholder="PhoneNumber" value={pNumber} onChange={(e)=>setPNumber(e.target.value)} class="w-full bg-white rounded border border-gray-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out" 
                            required/>
                            <span class="text-xs text-red-500">10 digit number starting with digits from 6-9</span>
                        </div>
                        <div class="relative mb-4">
                            <label for="email" class="leading-7 text-sm text-gray-600">Firstname</label>
                            <input type="text" id="firstname" name="firstname" placeholder="Firstname" value={firstname} onChange={(e)=>setFirstname(e.target.value)} class="w-full bg-white rounded border border-gray-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out" 
                            required/>
                            <span class="text-xs text-red-500">First Letter capital</span>
                        </div>
                        <div class="relative mb-4">
                            <label for="full-name" class="leading-7 text-sm text-gray-600">Lastname</label>
                            <input type="text" id="lastname" name="lastname" placeholder="Lastname" value={lastname} onChange={(e)=>setLastname(e.target.value)} class="w-full bg-white rounded border border-gray-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out" 
                            required/>
                            <span class="text-xs text-red-500">First letter capital</span>
                        </div>
                        <div class="relative mb-4">
                            <label for="full-name" class="leading-7 text-sm text-gray-600">Password</label>
                            <input type="password" id="password" name="password" placeholder="Password" value={password} onChange={(e)=>setPassword(e.target.value)} class="w-full bg-white rounded border border-gray-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out"
                            required/>
                            <span class="text-xs text-red-500">Min 8 characters, alteast one capital, number and special charater</span>
                        </div>
                        <div class="relative mb-4">
                            <label for="full-name" class="leading-7 text-sm text-gray-600">Age</label>
                            <input type="text" id="age" name="age" placeholder="Age in years" value={age} onChange={(e)=>setAge(e.target.value)} class="w-full bg-white rounded border border-gray-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out" 
                            required/>
                            <span class="text-xs text-red-500">Min age should be 18. Proof will be verified later.</span>
                        </div>
                        <button class="text-white bg-indigo-500 border-0 py-2 px-8 focus:outline-none hover:bg-indigo-600 rounded text-lg" onClick={submit}>Sign up</button>
                        {/* <p class="text-xs text-gray-500 mt-3">Literally you probably haven't heard of them jean shorts.</p> */}
                    </div>
                </div>
            </section>
        </div>
    )
}

export default WasherSignUp;
