import { useContext, useEffect, useState } from "react";
import { Badge, Container, Dropdown, Nav, Navbar } from "react-bootstrap";
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
            <Link className="nav-link" to="/">Trang chủ</Link>
            <Dropdown>
              <Dropdown.Toggle variant="secondary" id="dropdown-basic">
                Danh sách khoa
              </Dropdown.Toggle>

              <Dropdown.Menu>
                {specialize.map(s => (
                  <Link className="nav-link dropdown-item" to={`/service?specializeName=${s.name}`} key={s.id} >
                    {s.name}
                  </Link>
                ))}
              </Dropdown.Menu>
            </Dropdown>
            <Link className="nav-link" to="#features">Bác sĩ</Link>
            <Link className="nav-link" to="#pricing">Khoa</Link>
            {user === null ? (
              <>
                <Link to="login" className="nav-link text-warning" >Đăng nhập</Link>
                <Link to="register" className="nav-link text-danger">Đăng kí</Link>
              </>
            ) : (
              <>
                <Link to="#" className="nav-link text-info" >Chào {user.username}</Link>
                <Link to="#" className="nav-link text-danger" onClick={() => dispatch({ "type": "logout" })}>Đăng xuất</Link>
              </>
            )}
            <Link to="/cart" className="nav-link text-success" >Lich da dat<Badge className="bg-danger" variant="danger">0</Badge></Link>
          </Nav>
        </Container>
      </Navbar>
    </>
  );
}
export default Header;