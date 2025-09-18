import { Button, Card, Col, Row, Spinner, Table } from "react-bootstrap";
import Apis, { endpoints } from "../../configs/Apis";
import { useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { MyUserContext } from "../../configs/MyContext";
import Authorization from "../Error/Authorization";

const AppointmentSlots = () => {
    const [appointmentSlots, setAppointmentSlots] = useState([]);
    const [loadingAppointments, setLoadingAppointments] = useState(false);
    const { doctorId } = useParams();
    const [user, ] = useContext(MyUserContext);
    const nav = useNavigate();
    
    const loadAppointments = async () => {
        try {
            setLoadingAppointments(true);
            let url = endpoints['appointmentslots'](doctorId); 
            const res = await Apis.get(url);
            setAppointmentSlots(res.data);
        } catch (error) {
            console.log(error);
            setAppointmentSlots([]);
        } finally {
            setLoadingAppointments(false);
        }
    };
     useEffect(() => {
        loadAppointments();
    }, []);

    if(!user || user.role !== "DOCTOR" || user.id !== Number(doctorId))
        {
            console.log(user.role, user.id, doctorId);
            nav("/error");
        }

    return(
        <>
            <Row>
                <Col md={12}>
                    <Card className="shadow-lg rounded-3 mb-4">
                    <Card.Body>
                        <h3 className="text-center mb-4">📅 Danh sách lịch hẹn</h3>
                        {loadingAppointments ? (
                        <div className="text-center my-3">
                            <Spinner animation="border" variant="primary" />
                        </div>
                        ) : (
                        <Table striped bordered hover responsive>
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Ngày</th>
                                <th>Bắt đầu</th>
                                <th>Kết thúc</th>
                                <th>Trạng thái </th>
                                <th>Hành động</th>
                            </tr>
                            </thead>
                            <tbody>
                            {appointmentSlots?.map((a, index) => (
                                <tr key={a.id} >
                                    <td>{index + 1}</td>
                                    <td>{a?.scheduleId?.dateWork}</td>
                                    <td>{a.startTime}</td>
                                    <td>{a.endTime}</td>
                                    {a.isBooked === false ?<td >Còn trống</td>:
                                    <td className="text-danger fw-bold">Đã được đặt</td>}
                                    <td>{a.isBooked === true && <Button variant="outline-dark" onClick={() => nav(`/appointment/${a.id}`)} >Xem</Button>}</td>
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
        </>
    );
}

export default AppointmentSlots;