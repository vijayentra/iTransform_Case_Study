import React, { useEffect, useState } from "react";
import axios from "axios";
import {useParams } from "react-router-dom";

function CHistory() {

    const { phoneNumber } = useParams();

    const [History, setHistory] = useState([])
    const[name, setName] = useState([])
    const[rating, setRating] = useState([])

    useEffect(() => {
        axios.get(`http://localhost:8088/customer/viewCustomer/${phoneNumber}`).then((response) => {
            setName(`${response.data.firstName} ${response.data.lastName}`);
            setRating(response.data.rating);
        });
      }, []);

    useEffect(() => {
        const history = async () => {
            try {
                const response = await axios.get("http://localhost:8082/booking/viewCustomerHistory/" + phoneNumber);
                console.log(response.data);
                setHistory(response.data);
            } catch (error) {
                console.error('Error fetching booking history:', error);
            }
        };
        history();
    }, []);


    return (
        <div>
            <div class="flex flex-col text-center w-full mb-20">
            <a class="text-grey-500 inline-flex items-center md:mb-2 lg:mb-0 mt-2 ml-2 underline" href="/customerdetails">Back to ALL CUSTOMERS</a>
                <h1 class="text-3xl font-bold text-center text-black-700 py-4 border-b-4 border-grey-700">Customer - {name}/{phoneNumber} - {rating} &#9733;</h1>
                {History.length === 0 ? (
                <div className="flex items-center justify-center h-32">
                    <p className="text-2xl font-semibold text-gray-900 dark:text-white">Oops. No bookings done.</p>
                </div>
            ) : (
                <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
                    <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                        <tr>

                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm">
                                Booking Date & Time
                            </th>
                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm">
                                Booking Id
                            </th>
                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm">
                                Washer Name
                            </th>
                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm">
                                Washer PhoneNumber
                            </th>
                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm">
                                Status
                            </th>
                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm">
                                Wash Rating-Washer/Customer
                            </th>
                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm">
                                Billing Date & Time
                            </th>
                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm">
                                Bill Amount
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {History.map((detail) => (
                            <tr className="bg-white border-b dark:bg-gray-800 dark:border-gray-700" key={detail.bookingId}>
                                <th scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                    {detail.bookingDateAndTime}
                                </th>
                                <td className="px-6 py-4">
                                    {detail.bookingId}
                                </td>
                                <td className="px-6 py-4">
                                    {detail.washerName}
                                </td>
                                <td className="px-6 py-4">
                                    {detail.washerPhoneNumber}
                                </td>
                                <td className="px-6 py-4">
                                    {detail.washStatus}
                                </td>
                                <td className="px-6 py-4">
                                   W - {detail.washerRatingGiven} &#9733; / C- {detail.customerRatingGiven} &#9733;
                                </td>
                                <td className="px-6 py-4">
                                    {detail.invoice ? `${detail.invoice.billingDateAndTime}` : 'N/A'}
                                </td>
                                <td className="px-6 py-4">
                                    {detail.invoice ? `Rs.${detail.invoice.bill_Amount}.0` : 'N/A'}
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
export default CHistory;