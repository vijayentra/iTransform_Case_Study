import React, { useState } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";

function RateWasher() {

    const navigate = useNavigate();

    const { bookingId } = useParams();

    const [rating, setRating] = useState("")

    console.log(bookingId)

    const handleRateWasher = () => {

        const payload = {
            rating: rating
        }

        axios.put("http://localhost:8082/booking/rateWasher/" + bookingId, payload).then((response) => {
            setRating(response.data)
            alert("Rating Given. Thank you.");
            navigate("/customerdashboard")
        }).catch((error) => {
            console.error("Login error:", error);
            alert("Wash booking exists for this car. Cancel it before deleting the car details. ");
        })
    };

    const handleRateLater = () => {
        navigate("/customerdashboard")
    }

    return (
        <div>
            <section class="text-gray-600 body-font">
                <div class="container mx-auto flex px-5 py-24 md:flex-row flex-col items-center">
                    <div class="lg:flex-grow md:w-1/2 lg:pr-24 md:pr-16 flex flex-col md:items-start md:text-left mb-16 md:mb-0 items-center text-center">
                        <h1 class="title-font sm:text-4xl text-3xl mb-4 font-medium text-gray-900">RATE YOUR WASH</h1>
                        <p class="mb-8 leading-relaxed">Help us improve our service quality. Please rate your experience.</p>
                        <div class="flex w-full md:justify-start justify-center items-end">
                            <div class="relative mr-4 md:w-full lg:w-full xl:w-1/2 w-2/4">
                                <label for="hero-field" class="leading-7 text-sm text-gray-600">Rating</label>
                                <select id="rating-dropdown" value={rating} onChange={(e)=>setRating(e.target.value)} name="rating-dropdown" class="w-full bg-gray-100 rounded border bg-opacity-50 border-gray-300 focus:ring-2 focus:ring-indigo-200 focus:bg-transparent focus:border-indigo-500 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                </select>

                            </div>
                            <div className="space-x-2">
                            <button class="inline-flex text-white bg-indigo-500 border-0 py-2 px-6 focus:outline-none hover:bg-indigo-600 rounded text-lg" onClick={()=>handleRateWasher()}>Rate</button>
                            <button class="inline-flex text-white bg-indigo-500 border-0 py-2 px-6 focus:outline-none hover:bg-indigo-600 rounded text-lg" onClick={() => handleRateLater()}>Rate Later</button>
                            </div>
                        </div>
                    </div>
                    <div class="lg:max-w-lg lg:w-full md:w-1/2 w-5/6">
                        <img class="object-cover object-center rounded" alt="hero" src="https://creativepeople.gr/wp-content/uploads/2019/10/shutterstock_373081525.jpg" />
                    </div>
                </div>
            </section>
        </div>
    )
}
export default RateWasher;