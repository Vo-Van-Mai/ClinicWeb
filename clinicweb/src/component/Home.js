import { useContext, useEffect, useState } from "react";
import Apis, { endpoints } from "../configs/Apis";
import { Link, useSearchParams } from "react-router-dom";
import { Alert, Button, Card, Col, Row, Spinner } from "react-bootstrap";
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

  const loadDoctor = async () => {
    try {
      setLoading(true);
      let url = endpoints["doctors"] + `?page=${page}`;
      console.log(url);
      const res = await Apis.get(url);
      if (res.data.length > 0) {
        if (page === 1) {
          setDoctors(res.data);
        }
        else {
          setDoctors([...doctos, ...res.data]);
        }
      }
      else {
        setPage(0);
      }
    } catch (error) {
      console.error("Lỗi", error);
    }
    finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadDoctor();
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
    <>
      <h1 className="text-center text-primary m-3">
        CHÀO MỪNG ĐẾN VỚI CLINIC WEBSITE
      </h1>
      {loading && <Spinner animation="border" />}
      {doctos.length === 0 && !loading && <Alert variant="danger">
        Chưa có bác sĩ nào!
      </Alert>
      }
      <Row>
        {doctos.map(d =>
          <Col className="p-1" key={d.id} md={3} xs={6}>
            <Card border="danger" style={{ width: '18rem' }}>
              <Card.Img variant="top" src={d.user.avatar} />
              <Card.Body>
                <Card.Title className="text-truncate">BS: {d.user.lastName} {d.user.firstName}</Card.Title>
                <Card.Text>
                  Kinh nghiệm: {d.yearOfExperience} năm
                </Card.Text>
                <Button variant="primary me-2">Xem</Button>
                {user ? (
                   <Button variant="success" onClick={() => handleShowModal(d)}>Đặt lịch</Button>
                ) : (
                  <Link to="/login" className="btn btn-secondary">Đăng nhập để đặt lịch</Link>
                )}
              </Card.Body>
            </Card>
          </Col>)
        }
      </Row>
      {page > 0 && !loading ? <div className="text-center">
        <Button onClick={() => setPage(page + 1)}>Xem thêm</Button>
      </div> : <Alert className="text-center mt-3" variant="info">Hết bác sĩ rồi bạn ơi!</Alert>}

      <BookingModal show={showModal} onHide={handleCloseModal} doctor={selectedDoctor} />
    </>
  );
}
export default Home;