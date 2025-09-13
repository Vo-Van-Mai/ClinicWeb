import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./component/layout/Header";
import Footer from "./component/layout/Footer";
import Home from "./component/Home";
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container } from "react-bootstrap";
import ListService from "./component/service/ListService";
import Register from "./component/user/Register";
const App = () => {

  return (
    <BrowserRouter>
      <Header />
      <Container>

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/service" element={<ListService />} />
        <Route path="/login" element={<Home />} />
        <Route path="/register" element={<Register />} />
      </Routes>
      </Container>

      <Footer />
    </BrowserRouter>
  );
}

export default App;