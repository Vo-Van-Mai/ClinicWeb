import { useContext, useEffect, useState } from "react";
import Apis, { endpoints } from "../configs/Apis";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import { Alert, Button, Card, Col, Container, Row, Spinner } from "react-bootstrap";
import BookingModal from "./booking/BookingModal";
import { MyUserContext } from "../configs/MyContext";

const Home = () => {
  const [doctos, setDoctors] = useState([]);
  const [loading, setLoading] = useState(false);
  const [page, setPage] = useState(1);
  const [q] = useSearchParams();

  const [user] = useContext(MyUserContext);
  const [showModal, setShowModal] = useState(false);
  const [selectedDoctor, setSelectedDoctor] = useState(null);
  const nav = useNavigate();

  const loadDoctor = async () => {
    try {
      setLoading(true);
      let url = endpoints["doctors"] + `?page=${page}`;
      const res = await Apis.get(url);
      if (res.data.length > 0) {
        if (page === 1) {
          setDoctors(res.data);
        } else {
          setDoctors([...doctos, ...res.data]);
        }
      } else {
        setPage(0);
      }
    } catch (error) {
      console.error("Lỗi", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadDoctor();
    // eslint-disable-next-line
  }, [q, page]);

  const handleShowModal = (doctor) => {
    setSelectedDoctor(doctor);
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setSelectedDoctor(null);
  };

  return (
    <Container className="mt-4">
      <h1 className="text-center text-primary fw-bold mb-4">
        CHÀO MỪNG ĐẾN VỚI <span className="text-success">CLINIC WEBSITE</span>
      </h1>
      {loading && (
        <div className="text-center my-3">
          <Spinner animation="border" variant="primary" />
        </div>
      )}

      {doctos.length === 0 && !loading && (
        <Alert variant="warning" className="text-center">
          Chưa có bác sĩ nào!
        </Alert>
      )}

      <Row className="g-4">
        {doctos.map((d) => (
          <Col key={d.id} md={3} sm={6} xs={12}>
            <Card className="h-100 shadow-sm border-0">
              <Card.Img
                variant="top"
                src={d.user.avatar}
                alt="Doctor Avatar"
                style={{ height: "220px", objectFit: "cover", borderRadius: "10px" }}
              />
              <Card.Body className="d-flex flex-column">
                <Card.Title className="text-truncate text-center fw-bold">
                  👨‍⚕️ BS: {d.user.lastName} {d.user.firstName}
                </Card.Title>
                <Card.Text className="text-muted text-center mb-3">
                  Kinh nghiệm: {d.yearOfExperience} năm
                </Card.Text>
                <div className="d-flex justify-content-center gap-2 mt-auto">
                  <Button
                    onClick={() => nav(`/doctorView/${d.id}`)}
                    variant="outline-primary"
                  >
                    Xem
                  </Button>
                  {user ? (
                    user?.role === "PATIENT" && (
                      <Button variant="success" onClick={() => handleShowModal(d)}>
                        Đặt lịch
                      </Button>
                    )
                  ) : (
                    <Link to="/login" className="btn btn-secondary">
                      Đăng nhập
                    </Link>
                  )}
                </div>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>
      <div className="text-center mt-4">
        {page > 0 && !loading ? (
          <Button onClick={() => setPage(page + 1)} variant="outline-dark">
            Xem thêm
          </Button>
        ) : (
          doctos.length > 0 && (
            <Alert className="mt-3 text-center" variant="info">
              Hết bác sĩ rồi bạn ơi! 🎉
            </Alert>
          )
        )}
      </div>

      <BookingModal show={showModal} onHide={handleCloseModal} doctor={selectedDoctor} />
    </Container>
  );
};

export default Home;
