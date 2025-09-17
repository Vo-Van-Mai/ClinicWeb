import { useContext, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Card, Row, Col, Badge, ListGroup, Spinner, Image, Table, Button, Container } from "react-bootstrap";
import Apis, { endpoints } from "../../configs/Apis";
import { MyUserContext } from "../../configs/MyContext";
import ScheduleBookingModal from "../booking/ScheduleBookingModal";

const DoctorView = () => {
    const { doctorId } = useParams();
    const [doctor, setDoctor] = useState({});
    const [loading, setLoading] = useState(false);
    const [workschedule, setWorkSchedule] = useState([]);
    const [user] = useContext(MyUserContext);

    const [showModal, setShowModal] = useState(false);
    const [selectedSchedule, setSelectedSchedule] = useState(null);

    const handleShowModal = (schedule) => {
        setSelectedSchedule(schedule);
        setShowModal(true);
    };

    const loadDoctor = async () => {
        try {
            setLoading(true);
            let url = endpoints['doctorsDetail'](doctorId);
            const resDoctor = await Apis.get(url);
            setDoctor(resDoctor.data);
        } catch (error) {
            console.log(error);
            setDoctor(null);
        } finally {
            setLoading(false);
        }
    };

    const loadSchedule = async () => {
        try {
            setLoading(true);
            let url = endpoints['workschedules'](doctorId);
            const res = await Apis.get(url);
            setWorkSchedule(res.data);
        } catch (error) {
            console.log(error);
            setWorkSchedule(null);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadDoctor();
        loadSchedule();
    }, [doctorId]);

    if (loading) {
        return <div className="text-center my-5"><Spinner animation="border" /></div>;
    }

    if (!doctor) {
        return <h3 className="text-center text-danger my-5">Không tìm thấy bác sĩ!</h3>;
    }

    return (
        <Container className="my-4">
            <Card className="mx-auto my-4 p-3 container" style={{ maxWidth: "90%" }}>
                <Card.Header className="text-center">
                    <Image src={doctor.doctor?.user?.avatar} rounded style={{maxWidth: "50%"}} />
                    <h2>{doctor.doctor?.user?.lastName +" " + doctor.doctor?.user?.firstName || "Bác sĩ"}</h2>
                    <Badge bg={doctor.doctor?.isVerified ? "success" : "warning"}>
                        {doctor.doctor?.isVerified ? "Đã xác thực" : "Chưa xác thực"}
                    </Badge>
                </Card.Header>
                <Card.Body>
                    <Row className="mb-3">
                        <Col md={6}>
                            <strong>License Number:</strong> {doctor.doctor?.licenseNumber}
                        </Col>
                        <Col md={6}>
                            <strong>Năm kinh nghiệm:</strong> {doctor.doctor?.yearOfExperience || 0} năm
                        </Col>
                    </Row>
                    <Row className="mb-3">
                        <Col>
                            <strong>Kinh nghiệm và lĩnh vực:</strong>
                            <p>{doctor.doctor?.experience || "Chưa có thông tin"}</p>
                        </Col>
                    </Row>
                    <Row className="mb-3">
                        <Col>
                            <strong>Chuyên môn:</strong>
                            <ListGroup>
                                {doctor.specializes?.length > 0 ? (
                                    doctor.specializes.map((sp, idx) => (
                                        <ListGroup.Item key={idx}>{sp.name}</ListGroup.Item>
                                    ))
                                ) : (
                                    <ListGroup.Item>Chưa có thông tin</ListGroup.Item>
                                )}
                            </ListGroup>
                        </Col>
                    </Row>
                    <Row className="mb-3">
                        <Col>
                            <strong>Đánh giá:</strong> {doctor.doctor?.ratingSet?.length || 0} lượt
                        </Col>
                    </Row>

                    <>
                    <h3 className="text-center mb-4">📋 Danh sách lịch khám</h3>
                <Table striped bordered hover responsive>
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Ngày</th>
                        <th>Bắt đầu</th>
                        <th>Kết thúc</th>
                        {user && user.role === 'PATIENT' && <th>Hành động</th>}
                    </tr>
                    </thead>
                    <tbody>
                    {workschedule?.map((s, index) => (
                        <tr key={s.id}>
                            <td>{index + 1}</td>
                            <td>{s.dateWork}</td>
                            <td>{s.startTime}</td>
                            <td>{s.endTime}</td>
                            {user && user.role === 'PATIENT' && (
                                <td>
                                    <Button variant="info" size="sm" onClick={() => handleShowModal(s)}>
                                        Chọn Lịch
                                    </Button>
                                </td>
                            )}
                        </tr>
                    ))}
                    {workschedule?.length === 0 && (
                        <tr>
                        <td colSpan="5" className="text-center text-muted">
                            Chưa có lịch làm nào
                        </td>
                        </tr>
                    )}
                    </tbody>
                </Table>
                    </>
                </Card.Body>
            </Card>

            <ScheduleBookingModal
                show={showModal}
                onHide={() => setShowModal(false)}
                schedule={selectedSchedule}
                specializes={doctor.specializes}
                doctor={doctor.doctor}
            />
        </Container>
    );
};

export default DoctorView;
