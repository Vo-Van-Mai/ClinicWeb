import { useState, useEffect } from "react";
import { Form, Button, Alert, Spinner } from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis";
import { useNavigate, useParams } from "react-router-dom";

const CreateMedicalRecord = () => {
  const [diagnosis, setDiagnosis] = useState("");
  const [symptoms, setSymptoms] = useState("");
  const [testResults, setTestResults] = useState("");
  const nav = useNavigate();
  const appointments = useParams();
  console.log("appintmentId", appointments.appointmentId)
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
        setLoading(true);
        const payload = {
            diagnosis: diagnosis ,
            symptoms: symptoms,
            testResults: testResults,
        };
        let url = endpoints["createMedicalRecord"](appointments.appointmentId);
        console.log("urrl", url)
        const res = await authApis().post(url, payload);
        if(res.status === 201){
            nav(-1);
        }
        setSuccess("Tạo hồ sơ bệnh án thành công!");
        setError("");
        setDiagnosis("");
        setSymptoms("");
        setTestResults("");
    } catch (ex) {
      setError("Lỗi khi tạo hồ sơ bệnh án.");
      console.error(ex.message);
      setSuccess("");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="mt-4">
      <h3>Tạo hồ sơ bệnh án</h3>
      {error && <Alert variant="danger">{error}</Alert>}
      {success && <Alert variant="success">{success}</Alert>}

      <Form onSubmit={handleSubmit}>
       

        <Form.Group className="mb-3">
          <Form.Label>Triệu chứng</Form.Label>
          <Form.Control
            as="textarea"
            rows={3}
            value={symptoms}
            onChange={(e) => setSymptoms(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Chẩn đoán</Form.Label>
          <Form.Control
            as="textarea"
            rows={3}
            value={diagnosis}
            onChange={(e) => setDiagnosis(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Kết quả xét nghiệm</Form.Label>
          <Form.Control
            as="textarea"
            rows={3}
            value={testResults}
            onChange={(e) => setTestResults(e.target.value)}
          />
        </Form.Group>

        <Button variant="primary" type="submit" disabled={loading}>
          {loading ? <Spinner animation="border" size="sm" /> : "Tạo hồ sơ"}
        </Button>
      </Form>
    </div>
  );
};

export default CreateMedicalRecord;
