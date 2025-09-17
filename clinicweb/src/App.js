import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./component/layout/Header";
import Footer from "./component/layout/Footer";
import Home from "./component/Home";
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container } from "react-bootstrap";
import ListService from "./component/service/ListService";
import Register from "./component/user/Register";
import Login from "./component/user/Login";

import AddDoctorProfile from "./component/user/AddDoctorProfile";

import { useReducer } from "react";
import { MyUserContext } from "./configs/MyContext";
import { MyUserReducer } from "./reducer/MyUserReducer";
import { MyCartContext } from "./configs/MyCartContext";
import { MyCartReducer } from "./reducer/MyCartReducer";

import DoctorView from "./component/doctor/DoctorView";
import CreateWorkSchedule from "./component/doctor/CreateWorkSchedule";
import ListWorkSchedule from "./component/doctor/ListWorkSchedule";

const App = () => {
  const [user, dispatch] = useReducer(MyUserReducer, null);
  const [cart, cartDispatch] = useReducer(MyCartReducer, []); 

  return (
    <MyUserContext.Provider value={[user, dispatch]}>
      <MyCartContext.Provider value={[cart, cartDispatch]}>
        <BrowserRouter>
          <Header />
          <Container>

          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/service" element={<ListService />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/addDoctorProfile/:userId" element={<AddDoctorProfile />} />
            <Route path="/doctorView/:doctorId" element={<DoctorView />} />
            <Route path="/listWorkSchedule/:doctorId" element={<ListWorkSchedule />} />
          </Routes>
          </Container>

          <Footer />
        </BrowserRouter>
      </MyCartContext.Provider>
    </MyUserContext.Provider>
  );
}

export default App;