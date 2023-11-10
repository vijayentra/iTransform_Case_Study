import React, { useEffect, useState } from "react";
import CustomerHistory from "../../component/BookingHistory/CustomerHistory";
import axios from "axios";

function CustomerDashboard() {
    const userData = JSON.parse(localStorage.getItem("userData"))
    const [bookingHistory, setBookingHistory] = useState([])
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

    if (!userData) {
        return (
            <div>
                loading...
            </div>
        )
    }
    return (

        <div>
            <section class="text-gray-600 body-font">
                <div class="container px-5 py-24 mx-auto">
                    <div class="flex flex-wrap -m-4">
                        <div class="p-4 md:w-1/3">
                            <div class="flex rounded-lg h-full bg-gray-100 p-8 flex-col">
                                <div class="flex items-center mb-3">
                                    <div class="w-8 h-8 mr-3 inline-flex items-center justify-center rounded-full bg-indigo-500 text-white flex-shrink-0">
                                    </div>
                                    <h2 class="text-gray-900 text-lg title-font font-medium">Book Your Wash</h2>
                                </div>
                                <div class="flex-grow">
                                    <a class="mt-3 text-indigo-500 inline-flex items-center" href="/bookwash">Click here to book a carwash
                                        <svg fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" class="w-4 h-4 ml-2" viewBox="0 0 24 24">
                                            <path d="M5 12h14M12 5l7 7-7 7"></path>
                                        </svg>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div class="p-4 md:w-1/3">
                            <div class="flex rounded-lg h-full bg-gray-100 p-8 flex-col">
                                <div class="flex items-center mb-3">
                                    <div class="w-8 h-8 mr-3 inline-flex items-center justify-center rounded-full bg-indigo-500 text-white flex-shrink-0">
                                    </div>
                                    <h2 class="text-gray-900 text-lg title-font font-medium">Reshedule Your Wash</h2>
                                </div>
                                <div class="flex-grow">
                                    <a class="mt-3 text-indigo-500 inline-flex items-center" href="/reschedulewash">Click here to reschedule a booking
                                        <svg fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" class="w-4 h-4 ml-2" viewBox="0 0 24 24">
                                            <path d="M5 12h14M12 5l7 7-7 7"></path>
                                        </svg>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div class="p-4 md:w-1/3">
                            <div class="flex rounded-lg h-full bg-gray-100 p-8 flex-col">
                                <div class="flex items-center mb-3">
                                    <div class="w-8 h-8 mr-3 inline-flex items-center justify-center rounded-full bg-indigo-500 text-white flex-shrink-0">
                                    </div>
                                    <h2 class="text-gray-900 text-lg title-font font-medium">Cancel Your Wash</h2>
                                </div>
                                <div class="flex-grow">
                                    <a class="mt-3 text-indigo-500 inline-flex items-center" href="/cancelwash">Click here to cancel a booking
                                        <svg fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" class="w-4 h-4 ml-2" viewBox="0 0 24 24">
                                            <path d="M5 12h14M12 5l7 7-7 7"></path>
                                        </svg>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                </div>
                <CustomerHistory bookingDetails={bookingHistory} />
            </section>
            
        </div>
    )
}

export default CustomerDashboard;