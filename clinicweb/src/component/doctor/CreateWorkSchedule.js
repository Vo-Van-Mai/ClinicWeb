import { useState } from "react";
import { Alert, Button, Card, Col, Container, Form, Row } from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis";

const CreateWorkSchedule = () => {
  const [date, setDate] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [msg, setMsg] = useState("");
  const [loading, setLoading] = useState(false);

  const validate = () => {
    const today = new Date();
    const selectedDate = new Date(date);

    if (selectedDate.setHours(0, 0, 0, 0) < today.setHours(0, 0, 0, 0)) {
      setMsg("Ngày phải là hiện tại hoặc tương lai!");
      return false;
    }

    const start = new Date(`${date}T${startTime}`);
    const end = new Date(`${date}T${endTime}`);

    if (start >= end) {
      setMsg("Giờ bắt đầu phải nhỏ hơn giờ kết thúc!");
      return false;
    }

    setMsg(""); 
    return true;
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (validate()===true) {
        try {
            setLoading(true);
            let url = endpoints['createWorkSchedules'];
            const res = await authApis().post(url, {
                "dateWork": date,
                "startTime": startTime,
                "endTime": endTime
            })
            console.log(res.data);
           

        } catch (error) {
            setMsg(`Lỗi ${error.response.data   }`);
        } finally{
            setLoading(false);
        }
    }
  };

  return (
    <Container >
      
          <Card className="shadow-lg p-4 rounded-3">
            <Card.Body>
              <h3 className="text-center mb-4">Tạo lịch làm</h3>
              {msg && <Alert variant="danger">{msg}</Alert>}
              <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3">
                  <Form.Label>Ngày làm</Form.Label>
                  <Form.Control
                    type="date"
                    value={date}
                    onChange={(e) => setDate(e.target.value)}
                    required
                  />
                </Form.Group>

                <Form.Group className="mb-3">
                  <Form.Label>Giờ bắt đầu</Form.Label>
                  <Form.Control
                    type="time"
                    value={startTime}
                    onChange={(e) => setStartTime(e.target.value)}
                    required
                  />
                </Form.Group>

                <Form.Group className="mb-3">
                  <Form.Label>Giờ kết thúc</Form.Label>
                  <Form.Control
                    type="time"
                    value={endTime}
                    onChange={(e) => setEndTime(e.target.value)}
                    required
                  />
                </Form.Group>

                <div className="d-grid">
                  <Button type="submit" variant="primary" size="lg">
                    Lưu lịch làm
                  </Button>
                </div>
              </Form>
            </Card.Body>
          </Card>
   
    </Container>
  );
};

export default CreateWorkSchedule;
