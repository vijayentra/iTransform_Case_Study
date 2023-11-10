import React from "react";

function CustomerHistory({ bookingDetails = [] }) {

    const bookingHistory = bookingDetails

    return (
        <div>
            <div class="flex flex-col text-center w-full mb-20">
                <h1 class="text-3xl font-bold text-center text-black-700 py-4 border-b-4 border-grey-700">YOUR CAR WASH HISTORY</h1>
                {bookingHistory.length === 0 ? (
                <div className="flex items-center justify-center h-32">
                    <p className="text-2xl font-semibold text-gray-900 dark:text-white">Make history! Book your first CAR WASH!.</p>
                </div>
            ) : (
                <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
                    <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                        <tr>

                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm text-center">
                                Booking Date and Time
                            </th>
                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm text-center">
                                Booking Id
                            </th>
                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm text-center">
                                Washer Name
                            </th>
                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm text-center">
                                Washer Rating
                            </th>
                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm text-center">
                                Washer PhoneNumber
                            </th>
                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm text-center">
                                Status
                            </th>
                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm text-center">
                                Bill Amount
                            </th>
                            <th scope="col" className="px-6 py-3 font-medium text-gray-700 text-sm text-center">
                                 YOUR EXPERIENCE
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {bookingHistory.map((detail) => (
                            <tr className="bg-white border-b dark:bg-gray-800 dark:border-gray-700" key={detail.bookingId}>
                                <th scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white text-center">
                                    {detail.bookingDateAndTime}
                                </th>
                                <td className="px-6 py-4 text-center">
                                    {detail.bookingId}
                                </td>
                                <td className="px-6 py-4 text-center">
                                    {detail.washerName}
                                </td>
                                <td className="px-6 py-4 text-center">
                                    {detail.washerRating} &#9733;/ 5
                                </td>
                                <td className="px-6 py-4 text-center">
                                    {detail.washerPhoneNumber}
                                </td>
                                <td className="px-6 py-4 text-center">
                                    {detail.washStatus}
                                </td>
                                <td className="px-6 py-4 text-center">
                                    {detail.invoice ? `Rs.${detail.invoice.bill_Amount}.0` : 'N/A'}
                                </td>
                                <td className="flex items-center px-6 py-4 space-x-3 text-center">
                                    {detail.washStatus === "COMPLETED" ? (
                                        detail.washerRatingGiven !== 0 ? (
                                            <td className="px-6 py-4 text-center">
                                                <span>{detail.washerRatingGiven} &#9733;/ 5</span>
                                            </td>
                                        ) : (
                                            <td className="px-6 py-4 text-center">
                                                <a
                                                    href={`/ratewasher/${detail.bookingId}`}
                                                    className="font-medium text-blue-600 dark:text-blue-500 hover:underline"
                                                >
                                                    Click to Rate
                                                </a>
                                            </td>
                                        )
                                    ) : (
                                        <td className="px-6 py-4 align-left">
                                            N/A
                                        </td>
                                    )}
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
export default CustomerHistory;