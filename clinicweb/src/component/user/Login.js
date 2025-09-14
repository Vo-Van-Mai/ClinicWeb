import { useContext, useState } from "react";
import { Alert, Button, FloatingLabel, Form } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import Apis, { authApis, endpoints } from "../../configs/Apis";
import cookie from 'react-cookies'
import { MyUserContext } from "../../configs/MyContext";

const Login = () => {
    const navigate = useNavigate();
    const [user, setUser] = useState({
        username: '',
        password: ''
    });
    const [msg, setMsg] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [, dispatch] = useContext(MyUserContext);

    const setState = (value, field) => {
        setUser({ ...user, [field]: value });
        setMsg(''); 
    }

    const validate = () => {
        if (!user.username || user.username.trim().length === 0) {
            setMsg("Vui lòng nhập tên đăng nhập.");
            return false;
        }

        if (!user.password || user.password.length === 0) {
            setMsg("Vui lòng nhập mật khẩu.");
            return false;
        }
        return true;
    };

    const login = async (e) => {
        e.preventDefault();
        setMsg('');
        if (!validate()) {
            return;
        }
        try {
            setIsLoading(true);
           
            const res = await Apis.post(endpoints["login"], {
                "username": user.username,
                "password": user.password
            });
            console.log("Login data:", res.data);
            cookie.save('token', res.data.token);
            const u = await authApis().get(endpoints["profile"]);
            console.log("Profile data:", u.data);
            dispatch({
                "type": "login",
                "payload": u.data
            })
            navigate("/");
            
        } catch (error) {
            console.error("Login error:", error.response.data);
            setMsg(error.response.data);
        } finally {
            setIsLoading(false);
        }
    }

    return (
        <div className="container mt-4">
            <div className="row justify-content-center">
                <div className="col-md-6 col-lg-4">
                    <div className="card shadow">
                        <div className="card-body p-4">
                            <h1 className="text-center text-primary mb-4">Đăng nhập</h1>
                            
                            <Form onSubmit={login}>
                                <FloatingLabel controlId="username" label="Tên đăng nhập" className="mb-3">
                                    <Form.Control 
                                        type="text" 
                                        placeholder="" 
                                        value={user.username}
                                        onChange={e => setState(e.target.value, 'username')}
                                        disabled={isLoading}
                                    />
                                </FloatingLabel>

                                <FloatingLabel controlId="password" label="Mật khẩu" className="mb-3">
                                    <Form.Control 
                                        type="password" 
                                        placeholder="" 
                                        value={user.password}
                                        onChange={e => setState(e.target.value, 'password')}
                                        disabled={isLoading}
                                    />
                                </FloatingLabel>

                                {msg && (
                                    <Alert 
                                        className="mt-2 text-center" 
                                        variant={msg.includes('thành công') ? 'success' : 'danger'}
                                    >
                                        {msg}
                                    </Alert>
                                )}
                                
                                <div className="d-grid gap-2">
                                    <Button 
                                        variant="primary" 
                                        type="submit" 
                                        disabled={isLoading}
                                        size="lg"
                                    >
                                        {isLoading ? 'Đang đăng nhập...' : 'Đăng nhập'}
                                    </Button>
                                    
                                    <Button 
                                        variant="outline-secondary" 
                                        type="button"
                                        onClick={() => navigate('/register')}
                                        disabled={isLoading}
                                    >
                                        Chưa có tài khoản? Đăng ký ngay
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

export default Login;
