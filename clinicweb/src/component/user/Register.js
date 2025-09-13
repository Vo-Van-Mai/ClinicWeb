import { useRef, useState } from "react";
import { Button, FloatingLabel, Form } from "react-bootstrap";

const Register = () => {
    const info = [{
        "title": "Tên đăng nhập",
        "field": "username",
        "type": "text",
    }, {
        "title": "Họ và tên lót",
        "field": "lastName",
        "type": "text",
    }, {
        "title": "Tên",
        "field": "firstName",
        "type": "text",
    }, {
        "title": "Ngày tháng năm sinh",
        "field": "dateOfBirth",
        "type": "date",
    }, {
        "title": "Email",
        "field": "email",
        "type": "email",
    }, , {
        "title": "Số điện thoại liên lạc:",
        "field": "phone",
        "type": "number",
    }, {
        "title": "Mât khẩu",
        "field": "password",
        "type": "password",
    }, {
        "title": "Xác nhận mật khẩu",
        "field": "confirm",
        "type": "password",
    }]
    const avatar = useRef();

    const [user, setUser] = useState({});
    const [msg, setMsg] = useState();
    const setState = (value, field) => {
        setUser({ ...user, [field]: value });
        setMsg({ ...msg, [field]: null });
    }


    const validate = () => {
        const newErrors = {};
        const usernameRegex = /^[a-zA-Z0-9_]{3,30}$/;
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,50}$/;
        const emailRegex = /^[A-Za-z0-9+_.-]+@(.+)$/;
        const phoneRegex = /^\d{10}$/;

        if (!user.firstName || user.firstName.length > 50) {
            newErrors.firstName = "Họ không được để trống và tối đa 50 ký tự.";
            return false;
        }
        if (!user.lastName || user.lastName.length > 50) {
            newErrors.lastName = "Tên không được để trống và tối đa 50 ký tự.";
            return false;

        }
        if (!user.email || !emailRegex.test(user.email) || user.email.length > 50) {
            newErrors.email = "Email không hợp lệ hoặc vượt quá 50 ký tự.";
            return false;

        }
        if (!user.username || !usernameRegex.test(user.username)) {
            newErrors.username = "Tên đăng nhập phải từ 3-30 ký tự, chỉ chứa chữ, số hoặc dấu gạch dưới.";
            return false;

        }
        if (!user.password || !passwordRegex.test(user.password)) {
            newErrors.password = "Mật khẩu phải từ 8-50 ký tự, chứa chữ hoa, chữ thường, số và ký tự đặc biệt.";
            return false;

        }
        if (!user.confirm || user.password !== user.confirm) {
            newErrors.confirm = "Mật khẩu xác nhận không khớp.";
            return false;

        }
        if (!user.phoneNumber || !phoneRegex.test(user.phoneNumber)) {
            newErrors.phoneNumber = "Số điện thoại phải là 10 chữ số.";
            return false;

        }

        if (!user.dateOfBirth) {
            newErrors.dateOfBirth = "Ngày sinh không được để trống.";
            return false;

        } else {
            const dob = new Date(user.dateOfBirth);
            const today = new Date();
            if (dob > today) {
                newErrors.dateOfBirth = "Ngày sinh không được là ngày trong tương lai.";
                return false;

            }
        }

        if (!avatar.current || !avatar.current.files[0]) {
            newErrors.avatar = "Ảnh đại diện là bắt buộc.";
            return false;
        }

        return true;

    };

    return (
        <>
            <h1 className="text-center text-success mt-3">Đăng kí người dùng</h1>
            <div>
                {info.map((item, index) => (
                    <FloatingLabel controlId={index.toString()} label={item.title} className="mb-3"
                    >
                        <Form.Control type={item.type} placeholder="" />
                    </FloatingLabel>))}
                <div className="dlex">
                    <Form.Select aria-label="Vai trò người dùng">
                        <option value="null">Chọn vai trò người dùng</option>
                        <option value="DOCTOR">Bệnh nhân</option>
                        <option value="PATIENT">Bác sĩ</option>
                    </Form.Select>
                    <Form.Select className="mt-3" aria-label="Giới tính">
                        <option value="null">Chọn giới tính:</option>
                        <option value="MALE">Nam</option>
                        <option value="FEMALE">Nữ</option>
                    </Form.Select>
                    
                    <Form.Group className="mb-3" controlId="avatar">
                        <Form.Label>Ảnh đại diện</Form.Label>
                        <Form.Control type="file" ref={avatar} />
                    </Form.Group>

                </div>
                <Button variant="primary mt-3" type="submit">
                    Đăng kí
                </Button>
            </div>


        </>
    )
}

export default Register;