import { useState } from "react";
import { Alert, Button, FloatingLabel, Form } from "react-bootstrap";

const AddDoctorProfile = () => {
    const info = [{
        title: "Kĩ năng của bản thân",
        field: "experience",
        type: "text",
        as: "textarea",
        rows: 4
    }, {
        title: "Số năm kinh nghiệm",
        field: "yearOfExperience",
        type: "number",
    }, {
        title: "Mã số bác sĩ",
        field: "licenseNumber",
        type: "text",
    }]
    const [doctor, setDoctor] = useState({
        experience: "",
        yearOfExperience: "",
        licenseNumber: ""
    });
    const setState = (value, field) => {
        setDoctor({...doctor, [field]: value});
    }
    const [loading, setLoading] = useState(false);
    const [msg, setMsg] = useState("");

    const validate = () => {
        if (Object.values(doctor).length === 0) {
            setMsg("Vui lòng nhập đầy đủ thông tin!");
            return false;
        }

        for (let item of info) {
            if (!doctor[item.field] || doctor[item.field].trim().length === 0) {
                setMsg(`Vui lòng điền thông tin ${item.title}`);
                return false;
            }
        }

        setMsg("");
        return true;
    }

    const register = (e) => {
        e.preventDefault();
        setMsg('');
        if (validate() === true){
            setMsg("validate thành công")
        }
    }

    return (
        <div className="container mt-4">
            <div className="row justify-content-center">
                <div className="col-md-8 col-lg-6">
                    <div className="card shadow">
                        <div className="card-body p-4">
                            <h1 className="text-center text-suc cess mb-4">Đăng kí thông tin bác sĩ</h1>

                            <Form onSubmit={register}>
                                <div className="row">
                                    {info.map((item, index) => (
                                        <div key={item.field} className="mb-3">
                                            <FloatingLabel controlId={item.field} label={item.title}>
                                                <Form.Control
                                                    as={item.as || "input"}    // 👈 nếu có as thì dùng, không thì mặc định input
                                                    type={item.type}
                                                    rows={item.rows}
                                                    placeholder=""
                                                    value={doctor[item.field] || ''}
                                                    onChange={e => setState(e.target.value, item.field)}
                                                />
                                            </FloatingLabel>
                                        </div>
                                    ))}
                                </div>

                                {msg && (
                                    <Alert
                                        className="mt-2 text-center"
                                        variant={msg.includes('thành công') ? 'success' : 'danger'}
                                    >
                                        {msg}
                                    </Alert>
                                )}

                                <div className="d-grid">
                                    <Button
                                        variant="primary"
                                        type="submit"
                                        disabled={loading}
                                        size="lg"
                                    >
                                        {loading ? 'Đang xử lý...' : 'Đăng ký'}
                                    </Button>
                                </div>
                            </Form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AddDoctorProfile;