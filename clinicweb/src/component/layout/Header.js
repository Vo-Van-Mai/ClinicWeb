import { useContext, useEffect, useState } from "react";
import { Container, Nav, Navbar, Button, NavDropdown } from "react-bootstrap";
import Apis, { endpoints } from "../../configs/Apis";
import { Link, useNavigate } from "react-router-dom";
import { MyUserContext } from "../../configs/MyContext";

const Header = () => {
  const [user, dispatch] = useContext(MyUserContext);
  const [specialize, setSpecialize] = useState([]);
  const [doctor, setDoctor] = useState({});
  const nav = useNavigate();
  const loadSpecialize = async () => {
    try {
      const res = await Apis.get(endpoints["specializes"]);
      setSpecialize(res.data);
    } catch (error) {
      console.error("Lỗi", error);
    }
  };

  const loadDoctor = async () => {
    try {
      console.log("user load doctor", user)
      if(user && user?.role==="DOCTOR"){
        let url = endpoints["doctorsDetail"](user?.id);
        const res = await Apis.get(url);
        console.log("doctor load: ", res.data);
        setDoctor(res.data);
      }
    } catch (error) {
      console.log(error);
    }
  }

  useEffect(() => {
    loadSpecialize();
  }, []);

  useEffect(() => {
    loadDoctor();
  }, [user]);

  return (
    <Navbar bg="dark" variant="dark" expand="lg" className="shadow-sm mb-3">
      <Container>
        <Navbar.Brand as={Link} to="/">🏥 Clinic Website</Navbar.Brand>
        <Navbar.Toggle aria-controls="navbar-nav" />
        <Navbar.Collapse id="navbar-nav">
          <Nav className="me-auto">
            <Nav.Link as={Link} to="/">Trang chủ</Nav.Link>
            <Nav.Link as={Link} to="#doctors">Bác sĩ</Nav.Link>
            <Nav.Link as={Link} to="#specializes">Khoa</Nav.Link>

            <NavDropdown title="Danh sách khoa" id="nav-dropdown">
              {specialize.length > 0 ? specialize.map(s => (
                <NavDropdown.Item
                  as={Link}
                  to={`/service?specializeName=${s.name}`}
                  key={s.id}
                >
                  {s.name}
                </NavDropdown.Item>
              )) : <NavDropdown.Item disabled>Đang tải...</NavDropdown.Item>}
            </NavDropdown>
          </Nav>

          <Nav className="ms-auto align-items-center">
            {user === null ? (
              <>
                <Button as={Link} to="/login" variant="outline-warning" className="me-2">
                  Đăng nhập
                </Button>
                <Button as={Link} to="/register" variant="outline-danger">
                  Đăng ký
                </Button>
              </>
            ) : (
              <>
                <span className="text-info me-3">Chào {user.username}</span>
                {user?.role === "DOCTOR" && (
                  doctor.doctor?.isVerified ===true ? <Button as={Link} to={`/listWorkSchedule/${user?.id}`} variant="outline-info" className="me-2">
                    Quản lý lịch làm
                  </Button> : <span className="text-warning me-3">Đang chờ xác nhận</span>
                )}
                {user.role === "PATIENT" && (<>
                  <Button as={Link} to={`/listMedicalRecord/${user?.id}`} variant="outline-success" className="me-2">
                    Hồ sơ bệnh án
                  </Button>
                    <Button as={Link} to="/my-bookings" variant="success" className="ms-3 me-3">
                        Lịch đã đặt
                    </Button>
                </>
                )}
                <Button variant="outline-danger" onClick={() => {
                  dispatch({ type: "logout" });
                  nav("/");
                  }}>
                  Đăng xuất
                </Button>
              </>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Header;
