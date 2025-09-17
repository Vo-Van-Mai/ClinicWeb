import { useRef, useState } from "react";
import { Alert, Button, FloatingLabel, Form } from "react-bootstrap";
import Apis, { endpoints } from "../../configs/Apis";
import {useNavigate } from "react-router-dom";

const Register = () => {
    const info = [{
        title: "Tên đăng nhập",
        field: "username",
        type: "text",
    }, {
        title: "Họ và tên lót",
        field: "lastName",
        type: "text",
    }, {
        title: "Tên",
        field: "firstName",
        type: "text",
    }, {
        title: "Ngày tháng năm sinh",
        field: "dateOfBirth",
        type: "date",
    }, {
        title: "Email",
        field: "email",
        type: "email",
    }, {
        title: "Số điện thoại liên lạc",
        field: "phone",
        type: "tel",
    }, {
        title: "Mât khẩu",
        field: "password",
        type: "password",
    }, {
        title: "Xác nhận mật khẩu",
        field: "confirm",
        type: "password",
    }]
    const avatar = useRef();

    const [user, setUser] = useState({
        username: '',
        firstName: '',
        lastName: '',
        dateOfBirth: '',
        email: '',
        phone: '',
        password: '',
        confirm: '',
        role: '',
        gender: ''
    });
    const [msg, setMsg] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const nav = useNavigate();
    const [checkDoctorRole, setCheckDoctorRole] = useState(null);
    
    
    const setState = (value, field) => {
        setUser({ ...user, [field]: value });
        setMsg(''); // Clear message when user types
    }


    const validate = () => {
        const usernameRegex = /^[a-zA-Z0-9_]{3,30}$/;
        const phoneRegex = /^\d{10}$/;
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        
        if (!user.username || !usernameRegex.test(user.username)) {
            setMsg("Tên đăng nhập phải từ 3-30 ký tự, chỉ chứa chữ, số hoặc dấu gạch dưới.");
            return false;
        }

        if (!user.firstName || user.firstName.trim().length === 0 || user.firstName.length > 50) {
            setMsg("Tên không được để trống và tối đa 50 ký tự.");
            return false;
        }
        
        if (!user.lastName || user.lastName.trim().length === 0 || user.lastName.length > 50) {
            setMsg("Họ và tên lót không được để trống và tối đa 50 ký tự.");
            return false;
        }
        
        if (!user.dateOfBirth) {
            setMsg("Ngày sinh không được để trống.");
            return false;
        } else {
            const dob = new Date(user.dateOfBirth);
            const today = new Date();
            if (dob > today) {
                setMsg("Ngày sinh không được là ngày trong tương lai.");
                return false;
            }
        }
        
        if (!user.email || !emailRegex.test(user.email) || user.email.length > 255) {
            setMsg("Email không hợp lệ hoặc vượt quá 255 ký tự.");
            return false;
        }
        
        if (!user.phone|| !phoneRegex.test(user.phone)) {
            setMsg("Số điện thoại phải là 10 chữ số.");
            return false;
        }
        
        if (!user.password || user.password.length < 6) {
            setMsg("Mật khẩu phải có ít nhất 6 ký tự.");
            return false;
        }
        
        if (!user.confirm || user.password !== user.confirm) {
            setMsg("Mật khẩu xác nhận không khớp.");
            return false;
        }

        if (!user.role || user.role === 'null') {
            setMsg("Vui lòng chọn vai trò người dùng.");
            return false;
        }

        if (!user.gender || user.gender === 'null') {
            setMsg("Vui lòng chọn giới tính.");
            return false;
        }

        if (!avatar.current || !avatar.current.files[0]) {
            setMsg("Ảnh đại diện là bắt buộc.");
            return false;
        }

        return true;
    };

    const register = async (e) => {
        e.preventDefault();
        setMsg('');
        
        if (!validate()) {
            return;
        }
        
        try {
            setIsLoading(true);
            const formData = new FormData();
            for(let key in user){
                formData.append(key, user[key]);
            }
            formData.append('avatar', avatar.current.files[0]);
            console.log("Register data:", user);
            let res = await Apis.post(endpoints["users"], formData);
            if(res.status === 201){
                setMsg("Đăng ký thành công!");
                setIsLoading(false);
                if(checkDoctorRole){
                    nav(`/addDoctorProfile/${res.data.id}`);
                }
                else{
                    nav("/login");
                }
            }
            else{
                setMsg("Có lỗi xảy ra khi đăng ký." + res.data.message);
                setIsLoading(false);
            }
           
            
        } catch (error) {
            setMsg("Có lỗi xảy ra khi đăng ký rồi." + error.response.data.message);
            setIsLoading(false);
        }
    }



    return (
        <div className="container mt-4">
            <div className="row justify-content-center">
                <div className="col-md-8 col-lg-6">
                    <div className="card shadow">
                        <div className="card-body p-4">
                            <h1 className="text-center text-suc cess mb-4">Đăng ký người dùng</h1>
                            
                            <Form onSubmit={register}>
                                <div className="row">
                                    {info.map((item, index) => (
                                        <div key={item.field} className="col-md-6 mb-3">
                                            <FloatingLabel controlId={item.field} label={item.title}>
                                                <Form.Control 
                                                    type={item.type} 
                                                    placeholder="" 
                                                    value={user[item.field] || ''}
                                                    onChange={e => setState(e.target.value, item.field)} 
                                                />
                                            </FloatingLabel>
                                        </div>
                                    ))}
                                </div>

                                <div className="row">
                                    <div className="col-md-6 mb-3">
                                            <Form.Select 
                                                aria-label="Vai trò người dùng"
                                                value={user.role || 'null'}
                                                onChange={(e) => {
                                                    setState(e.target.value, 'role');
                                                    if(e.target.value === "DOCTOR"){
                                                        setCheckDoctorRole(true);
                                                    } else{
                                                        setCheckDoctorRole(false);
                                                    }
                                                }}
                                            >
                                            <option value="null">Chọn vai trò người dùng</option>
                                            <option value="PATIENT">Bệnh nhân</option>
                                            <option value="DOCTOR">Bác sĩ</option>
                                        </Form.Select>
                                    </div>
                                    <div className="col-md-6 mb-3">
                                        <Form.Select 
                                            aria-label="Giới tính"
                                            value={user.gender || 'null'}
                                            onChange={(e) => setState(e.target.value, 'gender')}
                                        >
                                            <option value="null">Chọn giới tính</option>
                                            <option value="MALE">Nam</option>
                                            <option value="FEMALE">Nữ</option>
                                        </Form.Select>
                                    </div>
                                </div>

                                <Form.Group className="mb-4" controlId="avatar">
                                    <Form.Label>Ảnh đại diện</Form.Label>
                                    <Form.Control 
                                        type="file" 
                                        ref={avatar}
                                        accept="image/*"
                                        onChange={() => setMsg('')} // Clear message when file is selected
                                    />
                                    <Form.Text className="text-muted">
                                        Chọn ảnh đại diện (JPG, PNG, GIF)
                                    </Form.Text>
                                </Form.Group>

                                {checkDoctorRole===false && <FloatingLabel className="mb-2" label={"Nhập mã bảo hiểm y tế (nếu có):"}>
                                                <Form.Control 
                                                    type="text" 
                                                    placeholder="" 
                                                    value={user["insurance"] || ''}
                                                    onChange={e => setState(e.target.value, "insurance")} 
                                                />
                                            </FloatingLabel>}

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
                                        disabled={isLoading}
                                        size="lg"
                                    >
                                        {isLoading ? 'Đang xử lý...' : 'Đăng ký'}
                                    </Button>
                                </div>
                            </Form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Register;