import React from "react";

function AdminDashboard() {
    return (
        <div>

            <section class="text-gray-600 body-font">
                <div class="container px-5 py-24 mx-auto">
                    <div class="flex flex-wrap -mx-4 -mb-10 text-center">
                        <div class="sm:w-1/2 mb-10 px-4">
                            <div class="rounded-lg h-64 overflow-hidden">
                                <img alt="content" class="object-cover object-center h-full w-full" src="https://ctcgulf.com/wp-content/uploads/2016/04/customerservice3.jpg" />
                            </div>
                            <h2 class="title-font text-2xl font-medium text-gray-900 mt-6 mb-3">KNOW YOUR CUSTOMERS</h2>
                            <a class="text-indigo-500 inline-flex items-center md:mb-2 lg:mb-0" href="/customerdetails">Click here
                                <svg class="w-4 h-4 ml-2" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" fill="none" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M5 12h14"></path>
                                    <path d="M12 5l7 7-7 7"></path>
                                </svg>
                            </a>
                        </div>
                        <div class="sm:w-1/2 mb-10 px-4">
                            <div class="rounded-lg h-64 overflow-hidden">
                                <img alt="content" class="object-cover object-center h-full w-full" src="https://emphasishr.com/wp-content/uploads/2019/12/iStock-937843262-1568x1046.jpg" />
                            </div>
                            <h2 class="title-font text-2xl font-medium text-gray-900 mt-6 mb-3">KNOW YOUR EMPLOYEES</h2>
                            <a class="text-indigo-500 inline-flex items-center md:mb-2 lg:mb-0" href="/washerdetails">Click here
                                <svg class="w-4 h-4 ml-2" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" fill="none" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M5 12h14"></path>
                                    <path d="M12 5l7 7-7 7"></path>
                                </svg>
                            </a>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    )
}

export default AdminDashboard;