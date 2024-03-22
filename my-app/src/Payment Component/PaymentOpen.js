import React, { Component } from "react";
import PaymentService from "./PaymentService";

export default class PaymentOpen extends Component {
  state = {
    amount: "",
  };

  onHandleChange = (event) => {
    this.setState({
      [event.target.name]: event.target.value,
    });
  };

  componentDidMount() {
    const script = document.createElement("script");
    script.src = "https://checkout.razorpay.com/v1/checkout.js";
    script.async = true;
    document.body.appendChild(script);
  }

  onSubmit = (e) => {
    e.preventDefault();
    let orderDetail = { amount: this.state.amount };

    PaymentService.createOrder(orderDetail).then((res) => {
      console.log("PAYMENT ORDER RESPONSE", res);
      var options = {
        key: "rzp_test_m2u4cY6xcCJXiV", // Enter the Key ID generated from the Dashboard
        amount: "50000", // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
        currency: "INR",
        name: "On Demand Car Wash",
        description: "Test Transaction",
        image: "https://example.com/your_logo",
        order_id: "order_KQlF4GRSPPh8Ci", //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
        callback_url: "https://eneqd3r9zrjok.x.pipedream.net/",
        prefill: {
          name: " Hari Krishna",
          email: "harikrishnasai5678@gmail.com",
          contact: "8309375377",
        },
        notes: {
          address: "Razorpay Corporate Office",
        },
        theme: {
          color: "#3399cc",
        },
      };
      var rzp1 = new window.Razorpay(options);
      rzp1.open();
    });
  };

  render() {
    return (
      <div>
        <form
          style={{
            backgroundColor: "#488AC7",
            width: "500px",
            height: "400px",
            marginLeft: "500px",
            marginTop: "150px",
          }}
        >
          <br />
          <br />
          <div className="form-outline mb-4">
            <label className="form-label" htmlFor="form2Example1">
              <b>Enter Amount</b>
            </label>
            <input
              type="text"
              id="form2Example1"
              className="form-control"
              placeholder="In Rupees"
              name="amount"
              value={this.state.amount}
              onChange={this.onHandleChange}
            />
          </div>
          <button
            type="button"
            className="btn btn-success btn-block mb-4"
            onClick={this.onSubmit}
          >
            Procced To Pay
          </button>
        </form>
      </div>
    );
  }
}
