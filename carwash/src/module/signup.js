import React from "react";
import { useNavigate } from "react-router-dom";

function Login() {

    // const navigate = useNavigate();

    // const customerLogin=()=>{
    //     navigate("/logincustomer")
    // }
    // const washerLogin=()=>{
    //     navigate("/loginwasher")
    // }

    return (
        // <div>
        // <div className="login" onClick={customerLogin}>
        //     <button class="inline-flex text-white bg-indigo-500 border-0 py-2 px-6 focus:outline-none hover:bg-indigo-600 rounded text-lg">login as a customer</button>
        // </div>
        // <div className="login" onClick={washerLogin}>
        //     <button class="inline-flex text-white bg-indigo-500 border-0 py-2 px-6 focus:outline-none hover:bg-indigo-600 rounded text-lg">login as a washer</button>
        // </div>
        // </div>
        <div>
            <section class="text-gray-600 body-font">
  <div class="container px-5 py-24 mx-auto flex flex-wrap">
    <div class="lg:w-1/2 w-full mb-10 lg:mb-0 rounded-lg overflow-hidden">
      <img alt="feature" class="object-cover object-center h-full w-full" src="https://img.freepik.com/premium-vector/car-wash-logo-met-klassieke-auto_26638-19.jpg?w=2000"/>
    </div>
    <div class="flex flex-col flex-wrap lg:py-6 -mb-10 lg:w-1/2 lg:pl-12 lg:text-left text-center">
      <div class="flex flex-col mb-10 lg:items-start items-center">
      <div class="w-12 h-12 inline-flex items-center justify-center rounded-full bg-indigo-100 text-indigo-500 mb-5">
          <svg fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" class="w-6 h-6" viewBox="0 0 24 24">
            <path d="M20 21v-2a4 4 0 00-4-4H8a4 4 0 00-4 4v2"></path>
            <circle cx="12" cy="7" r="4"></circle>
          </svg>
        </div>
        <div class="flex-grow">
          <h2 class="text-gray-900 text-lg title-font font-medium mb-3">CUSTOMER LOGIN</h2>
          {/* <p class="leading-relaxed text-base">Blue bottle crucifix vinyl post-ironic four dollar toast vegan taxidermy. Gastropub indxgo juice poutine.</p> */}
          <a class="mt-3 text-indigo-500 inline-flex items-center" href="/logincustomer">Click here
            <svg fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" class="w-4 h-4 ml-2" viewBox="0 0 24 24">
              <path d="M5 12h14M12 5l7 7-7 7"></path>
            </svg>
          </a>
        </div>
      </div>
      <div class="flex flex-col mb-10 lg:items-start items-center">
      <div class="w-12 h-12 inline-flex items-center justify-center rounded-full bg-indigo-100 text-indigo-500 mb-5">
          <svg fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" class="w-6 h-6" viewBox="0 0 24 24">
            <path d="M20 21v-2a4 4 0 00-4-4H8a4 4 0 00-4 4v2"></path>
            <circle cx="12" cy="7" r="4"></circle>
          </svg>
        </div>
        <div class="flex-grow">
          <h2 class="text-gray-900 text-lg title-font font-medium mb-3">WASHER LOGIN</h2>
          {/* <p class="leading-relaxed text-base">Blue bottle crucifix vinyl post-ironic four dollar toast vegan taxidermy. Gastropub indxgo juice poutine.</p> */}
          <a class="mt-3 text-indigo-500 inline-flex items-center" href="/loginwasher">Click here
            <svg fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" class="w-4 h-4 ml-2" viewBox="0 0 24 24">
              <path d="M5 12h14M12 5l7 7-7 7"></path>
            </svg>
          </a>
        </div>
      </div>
      <div class="flex flex-col mb-10 lg:items-start items-center">
        <div class="w-12 h-12 inline-flex items-center justify-center rounded-full bg-indigo-100 text-indigo-500 mb-5">
          <svg fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" class="w-6 h-6" viewBox="0 0 24 24">
            <path d="M20 21v-2a4 4 0 00-4-4H8a4 4 0 00-4 4v2"></path>
            <circle cx="12" cy="7" r="4"></circle>
          </svg>
        </div>
        <div class="flex-grow">
          <h2 class="text-gray-900 text-lg title-font font-medium mb-3">ADMIN LOGIN</h2>
          {/* <p class="leading-relaxed text-base">Blue bottle crucifix vinyl post-ironic four dollar toast vegan taxidermy. Gastropub indxgo juice poutine.</p> */}
          <a class="mt-3 text-indigo-500 inline-flex items-center" href="/loginadmin">Click here
            <svg fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" class="w-4 h-4 ml-2" viewBox="0 0 24 24">
              <path d="M5 12h14M12 5l7 7-7 7"></path>
            </svg>
          </a>
        </div>
      </div>
    </div>
  </div>
</section>
        </div>
    )

}

export default Login;