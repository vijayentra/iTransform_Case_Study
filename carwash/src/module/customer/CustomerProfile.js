import React from "react";
import { useNavigate } from "react-router-dom";

function CustomerProfile() {

  const userData = JSON.parse(localStorage.getItem("userData"))

  const navigate = useNavigate();

  const handleUpdateClick = () => {
    navigate('/updatecustomer');
  };

  return (
    <div>
      <section class="text-gray-600 body-font">
        <div class="container px-5 py-24 mx-auto">
          <div class="flex flex-wrap w-full mb-20 flex-col items-center text-center">
            <h1 class="sm:text-3xl text-2xl font-medium title-font mb-2 text-gray-900">{userData.firstName} {userData.lastName}</h1>
          </div>
          <div class="flex flex-wrap -m-4">
            <div class="xl:w-1/3 md:w-1/2 p-4">
              <div class="border border-gray-200 p-6 rounded-lg">
                <p class="leading-relaxed text-base">PHONE NUMBER</p>
                <h2 class="text-lg text-gray-900 font-medium title-font mb-2">{userData.phoneNumber}</h2>

              </div>
            </div>
            <div class="xl:w-1/3 md:w-1/2 p-4">
              <div class="border border-gray-200 p-6 rounded-lg">
                <p class="leading-relaxed text-base">ADDRESS PIN</p>
                <h2 class="text-lg text-gray-900 font-medium title-font mb-2">{userData.pinCode}</h2>

              </div>
            </div>
            <div class="xl:w-1/3 md:w-1/2 p-4">
              <div class="border border-gray-200 p-6 rounded-lg">
                <p class="leading-relaxed text-base">WASHES DONE</p>
                <h2 class="text-lg text-gray-900 font-medium title-font mb-2">{userData.washesDone}</h2>

              </div>
            </div>
            <div class="xl:w-1/3 md:w-1/2 p-4">
              <div class="border border-gray-200 p-6 rounded-lg">
                <p class="leading-relaxed text-base">RATING</p>
                <h2 class="text-lg text-gray-900 font-medium title-font mb-2">{userData.rating} &#9733;</h2>

              </div>
            </div>
          </div>
          <button class="flex mx-auto mt-16 text-white bg-indigo-500 border-0 py-2 px-8 focus:outline-none hover:bg-indigo-600 rounded text-lg" onClick={handleUpdateClick}>Update Personal Details</button>
        </div>
      </section>
    </div>
  )
}
export default CustomerProfile;