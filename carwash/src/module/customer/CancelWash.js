import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";

function CancelWash() {

    const userData = JSON.parse(localStorage.getItem("userData"))
    const [bookingHistory, setBookingHistory] = useState([])
    const [selectedBookingId, setSelectedBookingId] = useState("");
    useEffect(() => {
        const history = async () => {
            try {
                const response = await axios.get(`http://localhost:8082/booking/viewCustomerHistory/${userData.phoneNumber}`);
                console.log(response.data);
                setBookingHistory(response.data);
            } catch (error) {
                // Handle the error here
                console.error('Error fetching booking history:', error);
                // You can also set an error state or show a message to the user
            }
        };
        history();
    }, []);

    // Filter bookingDetails with "REQUESTED" status
    const requestedBookings = bookingHistory.filter(
        (detail) => detail.washStatus === "REQUESTED"
    );
    
    const navigate = useNavigate();
    const handleCancel = (id) => {

        axios.put("http://localhost:8082/booking/cancelWash/" + id).then((response) => {
            alert("Wash Cancelled.");
            navigate("/customerdashboard")
        }).catch((error) => {
            console.error("Login error:", error);
            alert("Wash booking exists for this car. Cancel it before deleting the car details. ");
        })
    };


    return (
        <div>
            <section class="text-gray-600 body-font">
                <div class="container mx-auto flex px-5 py-24 md:flex-row flex-col items-center">
                    <div class="lg:max-w-lg lg:w-full md:w-1/2 w-5/6 mb-10 md:mb-0">
                        <img class="object-cover object-center rounded" alt="hero" src="https://media.istockphoto.com/id/1212727027/vector/events-cancelled-concept.jpg?s=612x612&w=0&k=20&c=K3J20qBSTS7UzQL_9pH8sIyzGbUbPbqFFobOPiIgBKQ=" />
                    </div>
                    <div class="lg:flex-grow md:w-1/2 lg:pl-24 md:pl-16 flex flex-col md:items-start md:text-left items-center text-center">
                        <h1 class="title-font sm:text-4xl text-3xl mb-4 font-medium text-gray-900">Cancel Your Booking</h1>
                        <p class="mb-8 leading-relaxed">NOTE : You cannot cancel the booking if already accepted by the washer</p>
                        <div class="flex w-full md:justify-start justify-center items-end">
                            <div class="relative mr-4 lg:w-full xl:w-1/2 w-2/4">
                                <label for="hero-field" class="leading-7 text-sm text-gray-600">Bookings that can be cancelled..</label>
                                {/* <input type="text" id="hero-field" name="hero-field" class="w-full bg-gray-100 bg-opacity-50 rounded border border-gray-300 focus:ring-2 focus:ring-indigo-200 focus:bg-transparent focus:border-indigo-500 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out"/> */}
                                <select
                                    id="booking-dropdown"
                                    name="booking-dropdown"
                                    value={selectedBookingId}
                                    onChange={(e) => setSelectedBookingId(e.target.value)}
                                    class="w-full bg-gray-100 bg-opacity-50 rounded border border-gray-300 focus:ring-2 focus:ring-indigo-200 focus:bg-transparent focus:border-indigo-500 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out"
                                >
                                    <option value="">
    {requestedBookings.length === 0
      ? "No bookings eligible for cancellation"
      : "Select a Booking to Cancel"}
  </option>
  {requestedBookings.map((booking) => (
    <option key={booking.bookingId} value={booking.bookingId}>
      {booking.bookingId}
    </option>
                                    ))}
                                </select>
                            </div>
                            <button class="inline-flex text-white bg-indigo-500 border-0 py-2 px-6 focus:outline-none hover:bg-indigo-600 rounded text-lg" onClick={() => handleCancel(selectedBookingId)}>Cancel Booking</button>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    )
}
export default CancelWash;