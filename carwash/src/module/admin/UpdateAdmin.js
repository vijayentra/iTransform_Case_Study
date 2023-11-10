import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function UpdateAdmin(){

    const userData= JSON.parse(localStorage.getItem("userData"))

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    useEffect(() => {
        axios.get(`http://localhost:8084/admin/viewAdmin/${userData.username}`).then((response) => {
          setUsername(response.data.username);
          setPassword(response.data.password);
          console.log(response.data);
        });
      }, []);

    const navigate = useNavigate()

    const submit = () => {
        const payload = {
            username: username,
            password: password,
        }
        console.log(payload);
        axios.put(`http://localhost:8084/admin/updateAdmin/${userData.username}`, payload).then((response) => {
            const updatedUserData = { ...userData, ...response.data };
            localStorage.setItem('userData', JSON.stringify(updatedUserData));
            alert("Updated successfully")
            navigate("/admindashboard")
        }).catch((error) => {
            console.error("Login error:", error);
            alert("Invalid input.");
        })
    }

    return(
        <div>
            <section class="text-gray-600 body-font">
                <div class="container px-5 py-24 mx-auto flex flex-wrap items-center">
                    <div class="lg:w-2/6 md:w-1/2 bg-gray-100 rounded-lg p-8 flex flex-col mx-auto my-auto w-full">
                        <h2 class="text-gray-900 text-lg font-medium title-font mb-5">UPDATE DETAILS</h2>
                        <div class="relative mb-4">
                            <label for="phone no" class="leading-7 text-sm text-gray-600">USERNAME</label>
                            <input type="text" id="username" name="username" placeholder="Username" value={username} onChange={(e)=>setUsername(e.target.value)} class="w-full bg-white rounded border border-gray-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out" />
                        </div>
                        <div class="relative mb-4">
                            <label for="email" class="leading-7 text-sm text-gray-600">PASSWORD</label>
                            <input type="password" id="password" name="password" placeholder="Password" value={password} onChange={(e)=>setPassword(e.target.value)} class="w-full bg-white rounded border border-gray-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out" />
                        </div>
                        <button class="text-white bg-indigo-500 border-0 py-2 px-8 focus:outline-none hover:bg-indigo-600 rounded text-lg" onClick={submit}>Submit</button>
                    </div>
                </div>
            </section>
        </div>
    )
}

export default UpdateAdmin;