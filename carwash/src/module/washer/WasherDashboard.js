import React, { useEffect, useState } from "react";
import WasherHistory from "../../component/BookingHistory/WasherHistory";
import axios from "axios";

function WasherDashboard() {
    const userData = JSON.parse(localStorage.getItem("userData"))
    const [bookingHistory, setBookingHistory] = useState([])
    console.log(userData)
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
                <div class="container px-5 py-24 mx-auto flex flex-col">
                    <div class="lg:w-4/6 mx-auto">
                        <div class="rounded-lg h-64 overflow-hidden">
                            <img alt="content" class="object-cover object-center h-full w-full" src="https://detailinggeelong.com/wp-content/uploads/2020/11/GD-quality-car-cleaning-package-customer-service.jpg" />
                        </div>
                        <div class="flex flex-col sm:flex-row mt-10">
                            <div class="sm:w-1/3 text-center sm:pr-8 sm:py-8">
                                <div class="w-20 h-20 rounded-full inline-flex items-center justify-center bg-gray-200 text-gray-400">
                                    <svg fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" class="w-10 h-10" viewBox="0 0 24 24">
                                        <path d="M20 21v-2a4 4 0 00-4-4H8a4 4 0 00-4 4v2"></path>
                                        <circle cx="12" cy="7" r="4"></circle>
                                    </svg>
                                </div>
                                <div class="flex flex-col items-center text-center justify-center">
                                    <h2 class="font-medium title-font mt-4 text-gray-900 text-lg">{userData.firstName} {userData.lastName}</h2>
                                    <div class="w-12 h-1 bg-indigo-500 rounded mt-2 mb-4"></div>
                                    <p class="text-base">Keep your eyes on the road. Look out for more car wash orders!</p>
                                    <a class="text-indigo-500 inline-flex items-center" href="respondwash">CLICK HERE 
                                    <svg fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" class="w-4 h-4 ml-2" viewBox="0 0 24 24">
                                        <path d="M5 12h14M12 5l7 7-7 7"></path>
                                    </svg>
                                </a>
                                </div>
                            </div>
                            <div class="sm:w-2/3 sm:pl-8 sm:py-8 sm:border-l border-gray-200 sm:border-t-0 border-t mt-4 pt-4 sm:mt-0 text-center sm:text-left">
                                <h2 class="text-2xl font-bold mb-4">WE ARE THE BEST!!!</h2>
                                <ul class="list-disc pl-6 leading-relaxed text-lg mb-4">
                                    <li>Quality Products</li>
                                    <li>Politeness</li>
                                    <li>Safety</li>
                                    <li>Time Management</li>
                                    <li>Equipment Maintenance</li>
                                    <li>Building a Reputation</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <WasherHistory bookingDetails={bookingHistory} />
            </section>
            
        </div>
    )
}

export default WasherDashboard;