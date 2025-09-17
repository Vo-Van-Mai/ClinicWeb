import { Container, Row, Col, Card, Table, Button, Spinner } from "react-bootstrap";
import CreateWorkSchedule from "./CreateWorkSchedule";
import { useEffect, useState } from "react";
import Apis, { endpoints } from "../../configs/Apis";
import { useParams } from "react-router-dom";

const ListWorkSchedule = () => {
    const [schedules, setSchedules] = useState([]);
    const [loading, setLoading] = useState(false);    
    const { doctorId } = useParams();
    const [appointmentSlots, setAppointmentSlots] = useState([]);
    const [loadingAppointments, setLoadingAppointments] = useState(false);

  
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

    const loadAppointments = async () => {
        try {
            setLoadingAppointments(true);
            let url = endpoints['appointmentslots'](doctorId); // endpoint lấy danh sách appointment của bác sĩ
            const res = await Apis.get(url);
            setAppointmentSlots(res.data);
        } catch (error) {
            console.log(error);
            setAppointmentSlots([]);
        } finally {
            setLoadingAppointments(false);
        }
        };

    const handleDelete = (id) => {
        if (window.confirm("Bạn có chắc muốn xoá lịch này?")) {
        setSchedules(schedules.filter((s) => s.id !== id));
        }
    };

    useEffect(() => {
        loadSchedule();
        loadAppointments();
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
            <CreateWorkSchedule onCreated={loadSchedule} />
            </Col>
        </Row>
        <Row className="mt-4">
    <Col md={5}>
        <Card className="shadow-lg rounded-3 mb-4">
        <Card.Body>
            <h3 className="text-center mb-4">📅 Danh sách lịch trống</h3>
            {loadingAppointments ? (
            <div className="text-center my-3">
                <Spinner animation="border" variant="primary" />
            </div>
            ) : (
            <Table striped bordered hover responsive>
                <thead>
                <tr>
                    <th>#</th>
                    <th>Trạng thái </th>
                    <th>Ngày</th>
                    <th>Bắt đầu</th>
                    <th>Kết thúc</th>
                </tr>
                </thead>
                <tbody>
                {appointmentSlots?.map((a, index) => (
                    <tr key={a.id}>
                    <td>{index + 1}</td>
                    <td>{a.isBooked === false ? "Còn trống" : "đã được đặt" }</td>
                    <td>{a?.scheduleId?.dateWork}</td>
                    <td>{a.startTime}</td>
                    <td>{a.endTime}</td>
                    </tr>
                ))}
                {appointmentSlots.length === 0 && (
                    <tr>
                    <td colSpan="5" className="text-center text-muted">
                        Chưa có lịch hẹn nào
                    </td>
                    </tr>
                )}
                </tbody>
            </Table>
            )}
        </Card.Body>
        </Card>
    </Col>
    </Row>
        </Container>
    );
};

export default ListWorkSchedule;
