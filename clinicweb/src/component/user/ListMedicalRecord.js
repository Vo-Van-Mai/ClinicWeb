import React, { useContext, useEffect, useState } from "react";
import { Table, Spinner, Alert, Button } from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis"; // tuỳ cấu hình của bạn
import { MyUserContext } from "../../configs/MyContext";
import { useNavigate } from "react-router-dom";

const ListMedicalRecord = () => {

    const [records, setRecords] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [user,] = useContext(MyUserContext);
    const nav = useNavigate();
    const loadMedicalRecords = async () => {
        try {
            setLoading(true);
            let url = endpoints['listMedicalRecord'];
            const res = await authApis().get(url);
            console.log("res", res.data);
            setRecords(res.data);

        } catch (error) {
            console.log(error);
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        loadMedicalRecords();
    }, []);

    if (loading) return <Spinner animation="border" className="d-block mx-auto mt-4" />;
    if (error) return <Alert variant="danger" className="mt-4">{error}</Alert>;

    return (
        <div className="container mt-4">
        <h1 className="text-center mb-4">Danh sách hồ sơ bệnh án</h1>
        <Table striped bordered hover responsive>
            <thead>
            <tr>
                <th>ID</th>
                <th>Chẩn đoán</th>
                <th>Triệu chứng</th>
                <th>Kết quả xét nghiệm</th>
                <th>Ngày tạo</th>
                <th>Lịch hẹn</th>
                <th>Thao tác</th>
            </tr>
            </thead>
            <tbody>
            {records.length > 0 ? (
                records.map((r) => (
                <tr key={r.id}>
                    <td>{r.id}</td>
                    <td>{r.diagnosis}</td>
                    <td>{r.symptoms}</td>
                    <td>{r.testResults}</td>
                    <td>{new Date(r.createdDate).toLocaleString()}</td>
                    <td>{r.appointmentId ? `#${r.appointmentId.id}` : "Chưa có"}</td>
                    <td>
                    <Button
                        size="sm"
                        variant="outline-primary"
                        onClick={() => nav(`/medicalRecorđetail/${r.id}`)}
                    >
                        Xem chi tiết
                    </Button>
                    </td>
                </tr>
                ))
            ) : (
                <tr>
                <td colSpan="7" className="text-center">
                    Không có hồ sơ nào.
                </td>
                </tr>
            )}
            </tbody>
        </Table>
        </div>
    );
};

export default ListMedicalRecord;
