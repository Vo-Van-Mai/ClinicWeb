import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./component/layout/Header";
import Footer from "./component/layout/Footer";
import Home from "./component/Home";
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container } from "react-bootstrap";
import ListService from "./component/service/ListService";
import Register from "./component/user/Register";
import Login from "./component/user/Login";
import { MyUserContext } from "./configs/MyContext";
import { MyUserReducer } from "./reducer/MyUserReducer";
import { useReducer } from "react";
import AddDoctorProfile from "./component/user/AddDoctorProfile";
const App = () => {
  const [user, dispatch] = useReducer(MyUserReducer, null);

  return (
    <MyUserContext.Provider value={[user, dispatch]}>

      <BrowserRouter>
        <Header />
        <Container>

        <Routes>
          <Route path="/" element={<AddDoctorProfile />} />
          <Route path="/service" element={<ListService />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/addDoctorProfile" element={<AddDoctorProfile />} />
        </Routes>
        </Container>

        <Footer />
      </BrowserRouter>
    </MyUserContext.Provider>
  );
}

export default App;