import { useContext, useEffect, useState } from "react";
import { Alert, Card, Col, Container, Row, Spinner, Table, Badge, Button } from "react-bootstrap";
import { MyUserContext } from "../../configs/MyContext";
import Apis, { authApis, endpoints } from "../../configs/Apis";
import { Navigate } from "react-router-dom";

const MyBookings = () => {
    const [user] = useContext(MyUserContext);
    const [bookings, setBookings] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const loadBookings = async () => {
        if (user && user.id) {
            try {
                setLoading(true);
                const res = await authApis().get(endpoints['myAppointments'], {
                    params: { patientId: user.id }
                });
                setBookings(res.data);
            } catch (error) {
                console.error("Không thể tải lịch đã đặt:", error);
                setBookings([]);
            } finally {
                setLoading(false);
            }
        }
    };

    useEffect(() => {
        loadBookings();
    }, [user]);

    const handleCancelBooking = async (appointmentId) => {
        if (window.confirm("Bạn có chắc chắn muốn hủy lịch hẹn này?")) {
            try {
                const res = await authApis().delete(endpoints['deleteAppointment'](appointmentId));
                if (res.status === 204) {
                    alert("Hủy lịch thành công!");
                    loadBookings();
                }
            } catch (ex) {
                setError(ex.response?.data || "Đã có lỗi xảy ra khi hủy lịch.");
                console.error(ex);
            }
        }
    };

    const handlePayment = async (appointmentId) => {
        if (window.confirm("Bạn sẽ được chuyển đến cổng thanh toán VNPAY. Tiếp tục?")) {
            try {
                const res = await authApis().post(endpoints['createPayment'], {
                    "appointmentId": appointmentId,
                    "paymentMethod": "VNPAY"
                });

                if (res.data.paymentUrl) {
                    window.location.href = res.data.paymentUrl;
                }
            } catch (ex) {
                setError(ex.response?.data || "Đã có lỗi xảy ra khi tạo thanh toán.");
                console.error(ex);
            }
        }
    };

    if (!user) {
        return <Navigate to="/" />;
    }

    if (loading) {
        return <div className="text-center my-5"><Spinner animation="border" variant="primary" /></div>;
    }

    return (
        <Container className="my-4">
            <h1 className="text-center text-primary mb-4">Lịch hẹn của bạn</h1>
            {error && <Alert variant="danger" onClose={() => setError("")} dismissible>{error}</Alert>}

            {bookings === null || bookings.length === 0 ? (
                <Alert variant="info" className="mt-4 text-center">Bạn chưa có lịch hẹn nào.</Alert>
            ) : (
                <Row>
                    <Col>
                        <Card className="shadow-sm">
                            <Card.Body>
                                <Table striped bordered hover responsive>
                                    <thead>
                                        <tr className="text-center">
                                            <th>#</th>
                                            <th>Ngày tạo</th>
                                            <th>Dịch vụ</th>
                                            <th>Bác sĩ</th>
                                            <th>Ngày hẹn</th>
                                            <th>Giờ hẹn</th>
                                            <th>Trạng thái</th>
                                            <th>Hành động</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {bookings.map((b, index) => {
                                            const doctor = b.appointmentSlot.scheduleId.doctorId.user;
                                            return (
                                                <tr key={b.id}>
                                                    <td>{index + 1}</td>
                                                    <td>{b.createdDate}</td>
                                                    <td>{b.serviceId.name}</td>
                                                    <td>{`BS. ${doctor.lastName} ${doctor.firstName}`}</td>
                                                    <td>{b.appointmentSlot.scheduleId.dateWork}</td>
                                                    <td>{`${b.appointmentSlot.startTime} - ${b.appointmentSlot.endTime}`}</td>
                                                    <td className="text-center">
                                                        <Badge bg={b.status === "PENDING" ? "warning" : (b.status === "CONFIRM" ? "success" : "danger")}>
                                                            {b.status}
                                                        </Badge>
                                                    </td>
                                                    <td className="text-center">
                                                        {b.status === "PENDING" && (
                                                            <>
                                                                <Button
                                                                    variant="success"
                                                                    size="sm"
                                                                    className="me-2"
                                                                    onClick={() => handlePayment(b.id)}>
                                                                    Thanh toán
                                                                </Button>

                                                                <Button
                                                                    variant="outline-danger"
                                                                    size="sm"
                                                                    onClick={() => handleCancelBooking(b.id)}>
                                                                    Hủy lịch
                                                                </Button>
                                                            </>
                                                        )}
                                                    </td>
                                                </tr>
                                            )
                                        })}
                                    </tbody>
                                </Table>
                                
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>)}
        </Container>
    );
};

export default MyBookings;