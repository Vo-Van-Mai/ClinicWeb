import { useContext, useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Alert, Spinner, Card, Button, Row, Col, Image } from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis";
import { MyUserContext } from "../../configs/MyContext";

const AppointmentDetail = () => {
    const { appointmentId } = useParams();
    console.log("appointmentId", appointmentId)
    const [appointment, setAppointment] = useState({});
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [user] = useContext(MyUserContext);
    const nav = useNavigate();
    const [medicalRecord, setMedicalRecord] = useState(null);

    const loadAppointment = async () => {
    try {
      setLoading(true);
      const url = endpoints["appointmentDetail"] + `?slotId=${appointmentId}`;
      const res = await authApis().get(url);

      const appointmentData = res.data[0]; 
      setAppointment(appointmentData);
      console.log("appointment data:", appointmentData);

      return appointmentData?.id;
    } catch (error) {
      setError("Không thể tải thông tin lịch hẹn.");
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const loadMedicalRecords = async (appId) => {
    try {
      if (!appId) return;

      let url = endpoints["medicalRedcords"] + `?appointmentId=${appId}`;
      console.log("MedicalRecord url:", url);

      const res = await authApis().get(url);
      const medicalData = res.data[0];
      setMedicalRecord(medicalData);

      console.log("medicalRecord data:", medicalData);
    } catch (error) {
      setError("Không thể tải hồ sơ bệnh án.");
      console.error(error);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      const appId = await loadAppointment();
      await loadMedicalRecords(appId);
    };
    fetchData();
  }, [appointmentId]);

    if (loading) return <div className="text-center"><Spinner animation="border" /></div>;
    if (error) return <Alert className="text-center" variant="danger">{error}</Alert>;
    if (!appointment) return null;

    const doctor = appointment.appointmentSlot?.scheduleId?.doctorId;

    return (
      <Card className="mt-4">
        <Card.Header>
          <h4>Chi tiết lịch hẹn #{appointment?.id}</h4>
        </Card.Header>
        <Card.Body>
          <Row>
            <Col md={6}>
              <p><strong>Ngày tạo:</strong> {appointment.createdDate}</p>
              <p><strong>Trạng thái:</strong> {appointment.status}</p>
              <p><strong>Dịch vụ:</strong> {appointment.serviceId?.name} - {appointment.serviceId?.price?.toLocaleString('vi-VN')} VNĐ</p>
              <p><strong>Hình thức khám:</strong> {appointment.online ? "Trực tuyến" : "Trực tiếp"}</p>
              {appointment.online && appointment.roomUrl && (
                <p><strong>Phòng khám online:</strong> <a href={appointment.roomUrl} target="_blank" rel="noreferrer">{appointment.roomUrl}</a></p>
              )}
              <p><strong>Khung giờ:</strong> {appointment.appointmentSlot?.startTime} - {appointment.appointmentSlot?.endTime}</p>
              <br/>
              {user?.role === "DOCTOR" ? <>
              {medicalRecord?.length === 0 ?<Button onClick={() => nav(`/createmedical/${appointment?.id}`)}>Tạo hồ sơ bệnh án</Button> 
                : <Button onClick={() => nav(`/medicalRecorđetail/${medicalRecord?.id}`)}>Xem hồ sơ bệnh án</Button>}
              </>: <>
              {medicalRecord?.length === 0 && <Button onClick={() => nav(`/medicalRecorđetail/${medicalRecord?.id}`)}>Xem hồ sơ bệnh án</Button>}
              </>}
            </Col>
            <Col md={6}>
              <h5 className="text-info">Thông tin bác sĩ</h5>
              {doctor && (
                <>
                <Row>
                  <Col md={7}>
                    <p><strong>Họ tên:</strong> {doctor.user?.lastName} {doctor.user?.firstName}</p>
                    <p><strong>Chuyên môn:</strong> {doctor.experience}</p>
                    <p><strong>Năm kinh nghiệm:</strong> {doctor.yearOfExperience}</p>
                    <p><strong>License:</strong> {doctor.licenseNumber}</p>
                    <p><strong>Đã xác minh:</strong> {doctor.isVerified ? "Có" : "Chưa"}</p>
                    
                  </Col> 
                  <Col md={5}>
                  <Image className="mt-0" width={"80%"} rounded src={doctor?.user?.avatar}/>
                  </Col>
                </Row>
                </>
              
              )}
              <div>
                <Row>
                  <Col md={7}>
                      <h5 className="mt-3 text-success">Thông tin bệnh nhân</h5>
                    <p><strong>Họ tên:</strong> {appointment.patientId?.user?.lastName} {appointment.patientId?.user?.firstName}</p>
                    <p><strong>Email:</strong> {appointment.patientId?.user?.email}</p>
                    <p><strong>Phone:</strong> {appointment.patientId?.user?.phone}</p>
                    <p><strong>Insurance:</strong> {appointment.patientId?.insurance}</p>
                  </Col> 
                  <Col md={5}>
                  <Image className="mt-4" width={"80%"} rounded src={appointment.patientId?.user?.avatar}/>
                  </Col>
                </Row>
              </div>
              
            </Col>
          </Row>


          {appointment.paymentSet && appointment.paymentSet.length > 0 && (
            <div className="mt-4">
              <h5>Thông tin thanh toán</h5>
              {appointment.paymentSet.map(payment => (
                <Card key={payment.id} className="mb-2 p-2">
                  <p><strong>Phương thức:</strong> {payment.method}</p>
                  <p><strong>Số tiền:</strong> {payment.totalAmount?.toLocaleString('vi-VN')} VNĐ</p>
                  <p><strong>Trạng thái:</strong> {payment.status}</p>
                  {payment.transactionId && <p><strong>Transaction ID:</strong> {payment.transactionId}</p>}
                </Card>
              ))}
            </div>
          )}
        </Card.Body>
        <Card.Footer>
          <Button variant="secondary" onClick={() => nav(-1)}>Quay lại danh sách</Button>
        </Card.Footer>
      </Card>
    );
};

export default AppointmentDetail;
