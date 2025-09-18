import React, { useEffect, useState } from "react";
import { Alert, Card, Spinner } from "react-bootstrap";
import { useParams } from "react-router-dom";
import { authApis, endpoints } from "../../configs/Apis";

const MedicalRecord = () => {
  const { medicalId } = useParams();
  const [loading, setLoading] = useState(false);
  const [record, setRecord] = useState(null);
  const [error, setError] = useState("");

  const loadRecordDetail = async () => {
    try {
      setLoading(true);
      let url = endpoints["medicalDetail"](medicalId);
      console.log("url", url);
      const res = await authApis().get(url);
      setRecord(res.data);
    } catch (err) {
      console.error(err);
      setError("Không thể tải hồ sơ bệnh án!");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (medicalId) {
      loadRecordDetail();
    }
  }, [medicalId]);

  if (loading)
    return <Spinner animation="border" className="d-block mx-auto mt-4" />;
  if (error) return <Alert variant="danger" className="mt-4">{error}</Alert>;
  if (!medicalId || !record) {
    return <p>Không có hồ sơ bệnh án để hiển thị.</p>;
  }

  return (
    <>
    <div className="container mt-4">
            <div className="row justify-content-center">
                <div className="col-md-8">
                    <div className="card shadow">
                        <div className="card-body p-4">
                            <Card style={{ width: "100%" }} className="mb-4">
                                <Card.Body>
                                <Card.Title >Hồ sơ bệnh án #{record?.id}</Card.Title>
                                <Card.Text>
                                    <strong>Chẩn đoán:</strong> {record?.diagnosis} <br />
                                    <strong>Triệu chứng:</strong> {record?.symptoms} <br />
                                    <strong>Kết quả xét nghiệm:</strong> {record?.testResults} <br />
                                    <strong>Ngày tạo:</strong>{" "}
                                    {new Date(record?.createdDate).toLocaleString()}
                                </Card.Text>
                                </Card.Body>
                            </Card>

                            {/* Thông tin bác sĩ */}
                            <Card style={{ width: "100%" }} className="mb-4">
                                {/* <Card.Img
                                variant="top"
                                src={record?.appointmentId?.appointmentSlot?.scheduleId?.doctorId?.user?.avatar}
                                alt="doctor"
                                /> */}
                                <Card.Body>
                                <Card.Title>Thông tin bác sĩ</Card.Title>
                                <Card.Text>
                                    <strong>Họ tên:</strong>{" "}
                                    {record?.appointmentId?.appointmentSlot?.scheduleId?.doctorId?.user?.firstName}{" "}
                                    {record?.appointmentId?.appointmentSlot?.scheduleId?.doctorId?.user?.lastName} <br />
                                    <strong>Chuyên khoa:</strong>{" "}
                                    {record?.appointmentId?.serviceId?.specializeId?.name} <br />
                                    <strong>Kinh nghiệm:</strong>{" "}
                                    {record?.appointmentId?.appointmentSlot?.scheduleId?.doctorId?.yearOfExperience} năm
                                </Card.Text>
                                </Card.Body>
                            </Card>

                            {/* Thông tin bệnh nhân */}
                            <Card style={{ width: "100%" }} className="mb-4">
                                {/* <Card.Img
                                variant="top"
                                src={record?.appointmentId?.patientId?.user?.avatar}
                                alt="patient"
                                /> */}
                                <Card.Body>
                                <Card.Title>Thông tin bệnh nhân</Card.Title>
                                <Card.Text>
                                    <strong>Họ tên:</strong>{" "}
                                    {record?.appointmentId?.patientId?.user?.firstName}{" "}
                                    {record?.appointmentId?.patientId?.user?.lastName} <br />
                                    <strong>BHYT:</strong> {record?.appointmentId?.patientId?.insurance} <br />
                                    <strong>Điện thoại:</strong> {record?.appointmentId?.patientId?.user?.phone} <br />
                                    <strong>Email:</strong> {record?.appointmentId?.patientId?.user?.email}
                                </Card.Text>
                                </Card.Body>
                            </Card>

                            {/* Lịch hẹn & dịch vụ */}
                            <Card style={{ width: "100%" }} className="mb-4">
                                <Card.Body>
                                <Card.Title>Lịch hẹn & Dịch vụ</Card.Title>
                                <Card.Text>
                                    <strong>Ngày khám:</strong>{" "}
                                    {record?.appointmentId?.appointmentSlot?.scheduleId?.dateWork} <br />
                                    <strong>Giờ:</strong>{" "}
                                    {record?.appointmentId?.appointmentSlot?.startTime} -{" "}
                                    {record?.appointmentId?.appointmentSlot?.endTime} <br />
                                    <strong>Dịch vụ:</strong> {record?.appointmentId?.serviceId?.name} (
                                    {record?.appointmentId?.serviceId?.price} VND) <br />
                                    <strong>Trạng thái:</strong> {record?.appointmentId?.status}
                                </Card.Text>
                                </Card.Body>
                            </Card>
                        </div>
                    </div>
                </div>
            </div>
        </div>
      
    </>
  );
};

export default MedicalRecord;
