import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';


const bookingTimes = [
    '07:00', '07:30', '08:00', '08:30', '09:00', '09:30',
    '10:00', '10:30', '11:00', '11:30', '12:00', '12:30',
    '13:00', '13:30', '14:00', '14:30', '15:00', '15:30',
    '16:00', '16:30', '17:00', '17:30', '18:00'
];

function RescheduleWash() {

    const [selectedDate, setSelectedDate] = useState(null);
    const [selectedTime, setSelectedTime] = useState('');
    const [formatDate, setFormatDate] = useState('');

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
                console.error('Error fetching booking history:', error);
            }
        };
        history();
    }, []);

    const requestedBookings = bookingHistory.filter(
        (detail) => detail.washStatus === "REQUESTED"
    );

    const navigate = useNavigate();
    const handleReschedule = (id) => {

        const payload = {
            washDate: formatDate,
            washTime: selectedTime
        }

        axios.put("http://localhost:8082/booking/rescheduleWash/" + id, payload).then((response) => {
            alert("Wash Rescheduled.");
            navigate("/customerdashboard")
        }).catch((error) => {
            console.error("Login error:", error);
        })
    };
    return (
        <div className="booking-form">
            <h2>Reschedule Your Wash</h2>
            <form>
                <div>
                    <label>Booking ID:</label>
                    <select
                        id="booking-dropdown"
                        name="booking-dropdown"
                        value={selectedBookingId}
                        onChange={(e) => setSelectedBookingId(e.target.value)}
                        class="w-full bg-gray-100 bg-opacity-50 rounded border border-gray-300 focus:ring-2 focus:ring-indigo-200 focus:bg-transparent focus:border-indigo-500 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out"
                    >
                        <option value="">
                            {requestedBookings.length === 0
                                ? "NA"
                                : "Click here to select an ID"}
                        </option>
                        {requestedBookings.map((booking) => (
                            <option key={booking.bookingId} value={booking.bookingId}>
                                {booking.bookingId}
                            </option>
                        ))}
                    </select>
                </div>
                <div>
                    <label>
                        Date:
                    </label>
                    <div>
                        <DatePicker
                            placeholderText="Select Date"
                            selected={selectedDate}
                            onChange={(date) => {
                                setSelectedDate(date);
                                // Format and store the selected date
                                if (date) {
                                    const day = date.getDate().toString().padStart(2, '0');
                                    const month = (date.getMonth() + 1).toString().padStart(2, '0');
                                    const year = date.getFullYear();
                                    setFormatDate(`${day}-${month}-${year}`);
                                } else {
                                    setFormatDate(''); // Handle the case where date is cleared
                                }
                            }}
                            minDate={new Date(Date.now() + 24 * 60 * 60 * 1000)}
                        />
                    </div>
                </div>

                <div>
                    <label>
                        Time:
                    </label>
                    <div>
                        <select
                            value={selectedTime}
                            onChange={(e) => setSelectedTime(e.target.value)}
                        >
                            <option value="">Select Time</option>
                            {bookingTimes.map((time) => (
                                <option key={time} value={time}>
                                    {time}
                                </option>
                            ))}
                        </select>
                    </div>
                </div>




                <div className="button-container">
                    <button type="button" onClick={() => handleReschedule(selectedBookingId)}>
                        Reschedule Booking
                    </button>
                </div>
            </form>
        </div>
    )
}
export default RescheduleWash;