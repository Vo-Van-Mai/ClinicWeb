import { useEffect, useState } from "react";
import { Alert, Button, FloatingLabel, Form } from "react-bootstrap";
import Apis, { endpoints } from "../../configs/Apis";
import { useNavigate, useParams } from "react-router-dom";

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
        setDoctor({ ...doctor, [field]: value });
    }

    const { userId } = useParams();
    const [check, setCheck] = useState(false);
    const [loading, setLoading] = useState(false);
    const [msg, setMsg] = useState("");
    const [specialize, setSpecialize] = useState([]);
    const [specializeChoice, setSpecializeChoice] = useState(null);
    const nav = useNavigate();

    const loadSpecialize = async () => {
        try {
            const res = await Apis.get(endpoints['specializes']);
            setSpecialize(res.data);
        } catch (error) {
            setMsg(`Xảy ra lỗi: ${error}`);
        }
    }

    useEffect(() => {
        loadSpecialize();
    }, [])

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

    const register = async (e) => {
        e.preventDefault();
        setMsg('');
        if (validate() === true) {
            try {
                setLoading(true);
                let url = endpoints['addDoctorProfile'](userId);
                console.log(url);
                const res = await Apis.post(url, {
                    ...doctor
                })
                if (res.status === 201) {
                    setCheck(true)

                }
                else {
                    setMsg(`Có lỗi ${res.status}`);
                }
            } catch (error) {
                setMsg(`Đã xãy ra lỗi ${error}`);
            } finally {
                setLoading(false);
            }
        }
    }

    const choice = async(e) => {
        e.preventDefault();
        if(specializeChoice.specialize===null){
            setMsg("Vui lòng chọn khoa!");
            return false;
        }
        else{
            setMsg("");
            try {
                setLoading(true);
                let url = endpoints["choiceSpecialize"](userId);
                console.log(url);
                const res = await Apis.post(url, {
                    "specializeIds": specializeChoice
                })
                
                if(res.status === 201){
                    nav("/login");
                }
                else{
                    setMsg(`Lỗi ${res.status}`);
                }
            } catch (error) {
                console.log({
  specializeIds: [specializeChoice.id]
});
                setMsg(`Xảy ra lỗi ${error.response.data}`)
                
            } finally{
                setLoading(false);
            }
        }
    }

    return (
        <div className="container mt-4">
            <div className="row justify-content-center">
                <div className="col-md-8 col-lg-6">
                    <div className="card shadow">
                        <div className="card-body p-4">

                            {check === false ? (
                                <>
                                    <h1 className="text-center text-suc cess mb-4">Đăng kí thông tin bác sĩ</h1>
                                    <Form onSubmit={register}>
                                        <div className="row">
                                            {info.map((item, index) => (
                                                <div key={item.field} className="mb-3">
                                                    <FloatingLabel controlId={item.field} label={item.title}>
                                                        <Form.Control
                                                            as={item.as || "input"}
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
                                </>) : (<>
                                    <h1 className="text-center text-suc cess mb-4">Chọn chuyên khoa của bạn</h1>

                                    <p>Giữ Ctrl (Windows) hoặc Command (Mac) và click để chọn nhiều khoa</p>
                                    <Form onSubmit={choice}>
                                        <div className="row">
                                            <FloatingLabel >
                                                <div className="mb-3">
                                                    <Form.Select
                                                    multiple
                                                        value={specializeChoice}
                                                        onChange={(e) => {
                                                            const values = Array.from(e.target.selectedOptions, option => parseInt(option.value));
                                                            setSpecializeChoice(values);
                                                        }}
                                                        >
                                                        {specialize.map((s) => (
                                                            <option key={s.id} value={s.id}>{s.name}</option>
                                                        ))}
                                                        </Form.Select>
                                                </div>
                                            </FloatingLabel>
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
                                </>)}

                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AddDoctorProfile;