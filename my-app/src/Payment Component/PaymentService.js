import axios from "axios";

const FLIGHT_API_BASE_URL = "http://localhost:8083/order/addorder";

class PaymentService {
  createOrder(Data) {
    return axios.post(FLIGHT_API_BASE_URL, Data);
  }
}
export default new PaymentService();
