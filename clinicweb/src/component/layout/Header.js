import { useContext, useEffect, useState } from "react";
import { Container, Dropdown, Nav, Navbar } from "react-bootstrap";
import Apis, { endpoints } from "../../configs/Apis";
import { Link } from "react-router-dom";
import { MyUserContext } from "../../configs/MyContext";

const Header = () => {
  const [user, dispatch] = useContext(MyUserContext);
  const [specialize, setSpecialize] = useState([]);

  const loadSpecialize = async () => {
    try {
      const res = await Apis.get(endpoints["specializes"]);
      setSpecialize(res.data);
    } catch (error) {
      console.error("Lỗi", error);
    }
  };

  useEffect(() => {
    loadSpecialize();
  }, []);
  return (
    <>
      <Navbar bg="dark" data-bs-theme="dark">
        <Container>
          <Navbar.Brand >Clinic Website </Navbar.Brand>
          <Nav className="me-auto">
            <Nav.Link href="/">Trang chủ</Nav.Link>
            <Dropdown>
              <Dropdown.Toggle variant="secondary" id="dropdown-basic">
                Danh sách khoa
              </Dropdown.Toggle>

              <Dropdown.Menu>
                {specialize.map(s => (
                  <Link className="dropdown-item" to={`/service?specializeName=${s.name}`} key={s.id} >
                    {s.name}
                  </Link>
                ))}
              </Dropdown.Menu>
            </Dropdown>
            <Nav.Link href="#features">Bác sĩ</Nav.Link>
            <Nav.Link href="#pricing">Khoa</Nav.Link>
            {user === null ? (
              <>
                <Nav.Link href="login" className="text-warning" >Đăng nhập</Nav.Link>
                <Nav.Link href="register" className="text-danger">Đăng kí</Nav.Link>
              </>
            ) : (
              <>
                <Nav.Link href="#" className="text-info" >Chào {user.username}</Nav.Link>
                <Nav.Link href="#" className="text-danger" onClick={() => dispatch({ "type": "logout" })}>Đăng xuất</Nav.Link>
              </>
            )}
          </Nav>
        </Container>
      </Navbar>
    </>
  );
}
export default Header;