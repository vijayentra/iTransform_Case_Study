import { Routes, Route } from "react-router-dom";
import React from "react";
import Footer from "./component/Footer";
import CommonHeader from "./component/Header/CommonHeader";
import CustomerHeader from "./component/Header/CustomerHeader";
import WasherHeader from "./component/Header/WasherHeader";
import Home from './module/Home';
import LoginCustomer from "./module/login/loginCustomer";
import LoginWasher from "./module/login/loginWasher";
import LoginAdmin from "./module/login/loginAdmin";
import CustomerSignUp from "./module/signup/CustomerSignUp";
import SignUp from "./module/signup/signup";
import Login from "./module/login/login";
import WasherSignUp from "./module/signup/WasherSignUp";
import BookWash from "./module/customer/BookWash";

import CustomerDashboard from "./module/customer/CustomerDashboard";
import CarDetails from "./module/customer/CarDetails";
import UpdateCarDetails from "./module/customer/UpdateCarDetails";
import CustomerProfile from "./module/customer/CustomerProfile";
import UpdateCustomer from "./module/customer/UpdateCustomer";
import AddCar from "./module/customer/AddCar";
import RateWasher from "./module/customer/RateWasher";
import CancelWash from "./module/customer/CancelWash";
import RescheduleWash from "./module/customer/RescheduleWash";
import WasherDashboard from "./module/washer/WasherDashboard";
import WasherProfile from "./module/washer/WasherProfile";
import UpdateWasher from "./module/washer/UpdateWasher";
import RateCustomer from "./module/washer/RateCustomer";
import RespondWash from "./module/washer/RespondWash";
import Services from "./module/Home/Services";
import AdminHeader from "./component/Header/AdminHeader";
import AdminDashboard from "./module/admin/AdminDashboard";
import UpdateAdmin from "./module/admin/UpdateAdmin";
import CustomerDetails from "./module/admin/CustomerDetails";
import WasherDetails from "./module/admin/WasherDetails";
import CHistory from "./module/admin/CHistory";
import WHistory from "./module/admin/WHistory";
import AllBooking from "./module/admin/AllBooking";

function App() {
  return (
    <div>
      <Routes>
        <Route path="/" element={<CommonHeader />} />
        <Route path="/services" element={<CommonHeader />} />
        <Route path="/signup" element={<CommonHeader />} />
        <Route path="/customersignup" element={<CommonHeader />} />
        <Route path="/washersignup" element={<CommonHeader />} />
        <Route path="/login" element={<CommonHeader />} />
        <Route path="/logincustomer" element={<CommonHeader />} />
        <Route path="/loginwasher" element={<CommonHeader />} />
        <Route path="/loginadmin" element={<CommonHeader />} />

        <Route path="/bookwash" element={<CustomerHeader />} />
        <Route path="/customerdashboard" element={<CustomerHeader />} />
        <Route path="/cardetails" element={<CustomerHeader />} />
        <Route path="/updatecardetails" element={<CustomerHeader />} />
        <Route path="/customerprofile" element={<CustomerHeader />} />
        <Route path="/updatecustomer" element={<CustomerHeader />} />
        <Route path="/addcar" element={<CustomerHeader/>} />
        <Route path={"/ratewasher/:bookingId"} element={<CustomerHeader/>} />
        <Route path={"/cancelwash"} element={<CustomerHeader/>} />
        <Route path={"/reschedulewash"} element={<CustomerHeader/>} />

        <Route path={"/washerdashboard"} element={<WasherHeader/>} />
        <Route path={"/washerprofile"} element={<WasherHeader/>} />
        <Route path={"/updatewasher"} element={<WasherHeader/>} />
        <Route path={"/ratecustomer/:bookingId"} element={<WasherHeader/>} />
        <Route path={"/respondwash"} element={<WasherHeader/>} />

        <Route path="/admindashboard" element={<AdminHeader />} />
        <Route path="/updateadmin" element={<AdminHeader />} />
        <Route path="/customerdetails" element={<AdminHeader />} />
        <Route path="/washerdetails" element={<AdminHeader />} />
        <Route path={"/chistory/:phoneNumber"} element={<AdminHeader/>} />
        <Route path={"/whistory/:phoneNumber"} element={<AdminHeader/>} />
        <Route path={"/allbookings"} element={<AdminHeader/>} />

      </Routes>
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path="/services" element={<Services />} />
        <Route path ="/signup" element={<SignUp/>}></Route>
        <Route path ="/customersignup" element={<CustomerSignUp/>}></Route>
        <Route path ="/washersignup" element={<WasherSignUp/>}></Route>
        <Route path ="/login" element={<Login/>}></Route>
        <Route path ="/logincustomer" element={<LoginCustomer/>}></Route>
        <Route path ="/loginwasher" element={<LoginWasher/>}></Route>
        <Route path ="/loginadmin" element={<LoginAdmin/>}></Route>
        <Route path ="/bookwash" element={<BookWash/>}></Route>
        <Route path ="/customerdashboard" element={<CustomerDashboard/>}></Route>
        <Route path ="/cardetails" element={<CarDetails/>}></Route>
        <Route path ="/updatecardetails" element={<UpdateCarDetails/>}></Route>
        <Route path="/customerprofile" element={<CustomerProfile />} />
        <Route path="/updatecustomer" element={<UpdateCustomer />} />
        <Route path="/addcar" element={<AddCar />} />
        <Route path={"/ratewasher/:bookingId"} element={<RateWasher/>} />
        <Route path={"/cancelwash"} element={<CancelWash/>} />
        <Route path={"/reschedulewash"} element={<RescheduleWash/>} />

        <Route path={"/washerdashboard"} element={<WasherDashboard/>} />
        <Route path={"/washerprofile"} element={<WasherProfile/>} />
        <Route path={"/updatewasher"} element={<UpdateWasher/>} />
        <Route path={"/ratecustomer/:bookingId"} element={<RateCustomer/>} />
        <Route path={"/respondwash"} element={<RespondWash/>} />

        <Route path="/admindashboard" element={<AdminDashboard />} />
        <Route path="/updateadmin" element={<UpdateAdmin />} />
        <Route path="/customerdetails" element={<CustomerDetails />} />
        <Route path="/washerdetails" element={<WasherDetails />} />
        <Route path={"/chistory/:phoneNumber"} element={<CHistory/>} />
        <Route path={"/whistory/:phoneNumber"} element={<WHistory/>} />
        <Route path={"/allbookings"} element={<AllBooking/>} />
      </Routes>
      <Footer/>
    </div>
   
        
  );
}

export default App;




