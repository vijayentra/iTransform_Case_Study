import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function LoginWasher() {

    const [phoneNo,setPhoneNo] = useState("");
    const [password,setPassword] = useState("");

    const navigate = useNavigate();
    const submit=()=>{
        
        const payload={
            phoneNumber:phoneNo,
            password:password
        }
        console.log(payload)
        axios.get("http://localhost:8086/washer/loginWasher/"+phoneNo+"/"+password).then((response)=>{
            localStorage.setItem("userData",JSON.stringify(response.data))
            navigate("/washerdashboard")
            alert("Logged in successfully.")
            console.log(response.data)
        }).catch((error) => {
            alert("Invalid Credentials")
    
        })
    }

    return (
        <div>
            <section class="text-gray-600 body-font relative">
                <div class="container px-5 py-24 mx-auto">
                    <div class="flex flex-col text-center w-full mb-12">
                        <h1 class="sm:text-3xl text-2xl font-medium title-font mb-4 text-gray-900">Washer Login</h1>
                    </div>
                    <div class="lg:w-1/2 md:w-2/3 mx-auto">
                        <div class="flex flex-wrap -m-2">
                            <div class="p-2 w-1/2">
                                <div class="relative">
                                    <label for="name" class="leading-7 text-sm text-gray-600">Mobile Number</label>
                                    <input type="text" id="phoneNo" name="phoneNo" placeholder="Mobile Number" value={phoneNo} class="w-full bg-gray-100 bg-opacity-50 rounded border border-gray-300 focus:border-indigo-500 focus:bg-white focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out" onChange={(e)=>setPhoneNo(e.target.value)}/>
                                </div>
                            </div>
                            <div class="p-2 w-1/2">
                                <div class="relative">
                                    <label for="password" class="leading-7 text-sm text-gray-600">Password</label>
                                    <input type="password" id="password" name="password" placeholder="Password" value={password} class="w-full bg-gray-100 bg-opacity-50 rounded border border-gray-300 focus:border-indigo-500 focus:bg-white focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out" onChange={(e)=>setPassword(e.target.value)}/>
                                </div>
                            </div>
                            <div class="p-2 w-full">
                                <button class="flex mx-auto text-white bg-indigo-500 border-0 py-2 px-8 focus:outline-none hover:bg-indigo-600 rounded text-lg" onClick={submit}>Login</button>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    )
}

export default LoginWasher;