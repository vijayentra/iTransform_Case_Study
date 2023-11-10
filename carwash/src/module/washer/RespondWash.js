import React, { useState, useEffect } from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";

function RespondWash() {

    const userData = JSON.parse(localStorage.getItem("userData"))
    const [bookingHistory, setBookingHistory] = useState([])
    const [selectedBookingId, setSelectedBookingId] = useState("");
    useEffect(() => {
        const history = async () => {
            try {
                const response = await axios.get(`http://localhost:8082/booking/viewWasherHistory/${userData.phoneNumber}`);
                console.log(response.data);
                setBookingHistory(response.data);
            } catch (error) {
                console.error('Error fetching booking history:', error);
            }
        };
        history();
    }, []);

    // Filter bookingDetails with "REQUESTED" status
    const requestedBookings = bookingHistory.filter(
        (detail) => detail.washStatus === "REQUESTED"
    );

    const requestedBookingIds = requestedBookings.map((booking) => booking.bookingId);

    const navigate = useNavigate();
    const [response, setResponse] = useState("")

    const handleResponse = () => {
        const payload = {
            response: response
        }

        axios.put("http://localhost:8082/booking/respondWash/" + selectedBookingId, payload).then((response) => {
            alert("Response Updated.");
            setResponse(response.data)
            window.location.reload();
            navigate("/respondwash")
        }).catch((error) => {
            console.error("Login error:", error);
            alert("error ");
            navigate("/respondwash")
        })
    };

    return (
        <div>
            <section class="text-gray-600 body-font">
                <div class="container px-5 py-24 mx-auto">
                    <div class="flex flex-col text-center w-full mb-12">
                        <h1 class="sm:text-3xl text-2xl font-medium title-font mb-4 text-gray-900">More Orders, More Smiles! We're Eager to Keep You Busy.</h1>
                        <p class="lg:w-2/3 mx-auto leading-relaxed text-base">Your work is going to fill a large part of your life, and the only way to be truly satisfied is to do what you believe is great work. And the only way to do great work is to love what you do.</p>
                    </div>
                    <div class="flex lg:w-2/3 w-full sm:flex-row flex-col mx-auto px-8 sm:space-x-4 sm:space-y-0 space-y-4 sm:px-0 items-end">
                        <div class="relative flex-grow w-full">
                            <label for="booking-id" class="leading-7 text-sm text-gray-600">Select Booking</label>
                            <select
                                id="booking-id"
                                name="booking-id"
                                value={selectedBookingId}
                                onChange={(e) => setSelectedBookingId(e.target.value)}
                                class="w-full bg-gray-100 bg-opacity-50 rounded border border-gray-300 focus:border-indigo-500 focus:bg-transparent focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out"
                            >
                                {requestedBookingIds.length === 0 ? (
                                    <option value="" disabled>
                                        No bookings
                                    </option>
                                ) : (
                                    <>
                                        <option value="">Select Booking</option>
                                        {requestedBookingIds.map((bookingId) => (
                                            <option key={bookingId} value={bookingId}>
                                                {bookingId}
                                            </option>
                                        ))}
                                    </>
                                )}
                            </select>
                        </div>
                        <div class="relative flex-grow w-full">
                            <label for="response" class="leading-7 text-sm text-gray-600">Response</label>
                            <select
                                id="response"
                                name="response"
                                value={response}
                                onChange={(e) => setResponse(e.target.value)}
                                class="w-full bg-gray-100 bg-opacity-50 rounded border border-gray-300 focus:border-indigo-500 focus:bg-transparent focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out"
                            >
                                <option value="">Select Response</option>
                                <option value="ACCEPT">ACCEPT</option>
                                <option value="DECLINE">DECLINE</option>
                            </select>
                        </div>
                        <button class="text-white bg-indigo-500 border-0 py-2 px-8 focus:outline-none hover:bg-indigo-600 rounded text-lg" onClick={() => handleResponse(selectedBookingId)}>UPDATE</button>
                    </div>
                </div>
            </section>
        </div>
    )
}

export default RespondWash;