import React, { useEffect, useState } from "react";
import axios from "axios";

function CustomerDetails() {

    const userData = JSON.parse(localStorage.getItem("userData"))
    const [bookingHistory, setBookingHistory] = useState([])
    console.log(userData)
    useEffect(() => {
        const history = async () => {
            try {
                const response = await axios.get(`http://localhost:8084/admin/viewCustomerOverview`);
                console.log(response.data);
                setBookingHistory(response.data);
            } catch (error) {
                console.error('Error fetching booking history:', error);
            }
        };
        history();
    }, []);

    return (
        <div>
            <div class="flex flex-col text-center w-full mb-20">
                <h1 class="text-3xl font-bold text-center text-black-700 py-4 border-b-4 border-grey-700">ALL CUSTOMERS</h1>
                {bookingHistory.length === 0 ? (
                <div className="flex items-center justify-center h-32">
                    <p className="text-2xl font-semibold text-gray-900 dark:text-white">CUSTOMERS ARE YET TO FIND YOU.</p>
                </div>
            ) : (
                <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
                    <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                        <tr>

                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm">
                                Phone Number
                            </th>
                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm">
                                Name
                            </th>
                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm">
                                No of Cars
                            </th>
                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm">
                                PinCode
                            </th>
                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm">
                                Rating
                            </th>
                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm">
                                Washes Done
                            </th>
                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm">
                                Wash History
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {bookingHistory.map((detail) => (
                            <tr className="bg-white border-b dark:bg-gray-800 dark:border-gray-700" key={detail.phoneNumber}>
                                <th scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                    {detail.phoneNumber}
                                </th>
                                <td className="px-6 py-4">
                                    {detail.name}
                                </td>
                                <td className="px-6 py-4">
                                    {detail.noOfCars}
                                </td>
                                <td className="px-6 py-4">
                                    {detail.pinCode}
                                </td>
                                <td className="px-6 py-4">
                                    {detail.rating} &#9733;
                                </td>
                                <td className="px-6 py-4">
                                    {detail.washesDone}
                                </td>
                                <td className="px-6 py-4 align-left">
                                    <a
                                        href={`/chistory/${detail.phoneNumber}`}
                                        className="font-medium text-blue-600 dark:text-blue-500 hover:underline"
                                    >
                                        View history
                                    </a>
                                </td>


                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
            </div>
            
        </div>
    )
}
export default CustomerDetails;