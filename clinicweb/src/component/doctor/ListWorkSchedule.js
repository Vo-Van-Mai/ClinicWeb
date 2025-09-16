import { Container, Row, Col, Card, Table, Button, Spinner } from "react-bootstrap";
import CreateWorkSchedule from "./CreateWorkSchedule";
import { useEffect, useState } from "react";
import Apis, { endpoints } from "../../configs/Apis";
import { useParams } from "react-router-dom";

const ListWorkSchedule = () => {
  const [schedules, setSchedules] = useState([]);
  const [loading, setLoading] = useState(false);    
  const { doctorId } = useParams();
  
  const loadSchedule = async () => {
          try {
              setLoading(true);
              let url = endpoints['workschedules'](doctorId);
              const res = await Apis.get(url);
              setSchedules(res.data);
          } catch (error) {
              console.log(error);
              setSchedules(null);
          } finally {
              setLoading(false);
          }
      };

  const handleDelete = (id) => {
    if (window.confirm("Bạn có chắc muốn xoá lịch này?")) {
      setSchedules(schedules.filter((s) => s.id !== id));
    }
  };

  useEffect(() => {
    loadSchedule();
  }, []);

  return (
    <Container className="mt-5">
        {loading && (
        <div className="text-center my-3">
          <Spinner animation="border" variant="primary" />
        </div>
      )}
      <Row>
        {/* Cột trái: Danh sách lịch */}
        <Col md={7}>
          <Card className="shadow-lg rounded-3 mb-4">
            <Card.Body>
              <h3 className="text-center mb-4">📋 Danh sách lịch làm</h3>
              <Table striped bordered hover responsive>
                <thead>
                  <tr>
                    <th>#</th>
                    <th>Ngày</th>
                    <th>Bắt đầu</th>
                    <th>Kết thúc</th>
                    <th>Hành động</th>
                  </tr>
                </thead>
                <tbody>
                  {schedules?.map((s, index) => (
                    <tr key={s.id}>
                      <td>{index + 1}</td>
                      <td>{s.dateWork}</td>
                      <td>{s.startTime}</td>
                      <td>{s.endTime}</td>
                      <td>
                        <Button
                          variant="danger"
                          size="sm"
                          onClick={() => handleDelete(s.id)}
                        >
                          Xoá
                        </Button>
                      </td>
                    </tr>
                  ))}
                  {schedules.length === 0 && (
                    <tr>
                      <td colSpan="5" className="text-center text-muted">
                        Chưa có lịch làm nào
                      </td>
                    </tr>
                  )}
                </tbody>
              </Table>
            </Card.Body>
          </Card>
        </Col>

        {/* Cột phải: Form tạo lịch */}
        <Col md={5}>
          <CreateWorkSchedule />
        </Col>
      </Row>
    </Container>
  );
};

export default ListWorkSchedule;
