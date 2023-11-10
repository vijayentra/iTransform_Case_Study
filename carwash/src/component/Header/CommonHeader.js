import React from "react";
import { Link } from "react-router-dom";

function CommonHeader() {
    return (
        <div className="mt-10">
        <div>
            <header class="text-gray-600 body-font"
                style={{
                    position: "fixed",
                    top: 0,
                    left: 0,
                    right: 0,
                    zIndex: 100,
                    background: "white", // You can set the background color here
                }}>
                <div class="container mx-auto flex flex-wrap p-5 flex-col md:flex-row items-center shadow-md">
                    <a class="flex title-font font-medium items-center text-gray-900 mb-4 md:mb-0">
                        <img src="https://daxcarcare.com/wp-content/uploads/2022/09/GREEN-WASH-LOGO-01-Solid-Green_pages-to-jpg-0001.jpg" alt="Logo" className="w-11 h-11" />

                        <Link to={'/'}><span class="ml-3 text-xl">GREEN WASH</span></Link>
                    </a>
                    <nav class="md:mr-auto md:ml-4 md:py-1 md:pl-4 md:border-l md:border-gray-400	flex flex-wrap items-center text-base justify-center">
                        <Link to={'/login'} class="mr-5 hover:text-gray-900">LOGIN</Link>
                        <Link to={'/services'} class="mr-5 hover:text-gray-900">OUR SERVICES</Link>
                    </nav>
                    <button class="inline-flex items-center bg-gray-100 border-0 py-1 px-3 focus:outline-none hover:bg-gray-200 rounded text-base mt-4 md:mt-0"><Link to={'/signup'}>SignUp</Link>
                        <svg fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" class="w-4 h-4 ml-1" viewBox="0 0 24 24">
                            <path d="M5 12h14M12 5l7 7-7 7"></path>
                        </svg>
                    </button>
                </div>
            </header>
        </div>
        </div>
    );
}

export default CommonHeader;
