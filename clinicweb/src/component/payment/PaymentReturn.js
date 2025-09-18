import React, { useEffect, useState } from 'react';
import { useSearchParams, useNavigate, Link } from 'react-router-dom';
import { Container, Alert, Spinner, Card, Button } from 'react-bootstrap';

const PaymentReturn = () => {
    const [searchParams] = useSearchParams();
    const navigate = useNavigate();
    const [status, setStatus] = useState('processing');
    const [message, setMessage] = useState('Đang xử lý kết quả thanh toán...');
    const [countdown, setCountdown] = useState(5);

    useEffect(() => {
        const responseCode = searchParams.get('vnp_ResponseCode');
        const transactionRef = searchParams.get('vnp_TxnRef');
        const amount = searchParams.get('vnp_Amount');

        if (responseCode === '00') {
            setStatus('success');
            setMessage(`Thanh toán thành công cho đơn hàng ${transactionRef} với số tiền ${(parseInt(amount) / 100).toLocaleString('vi-VN')} VNĐ.`);
        } else {
            setStatus('failed');
            setMessage(`Thanh toán thất bại. Mã lỗi từ VNPAY: ${responseCode}.`);
        }

        const timer = setInterval(() => {
            setCountdown(prevCountdown => prevCountdown - 1);
        }, 1000);

        const redirectTimeout = setTimeout(() => {
            navigate('/my-bookings');
        }, 5000);

        return () => {
            clearInterval(timer);
            clearTimeout(redirectTimeout);
        };
    }, [searchParams, navigate]);

    const getAlertVariant = () => {
        switch (status) {
            case 'success':
                return 'success';
            case 'failed':
                return 'danger';
            default:
                return 'info';
        }
    };

    return (
        <Container className="d-flex align-items-center justify-content-center" style={{ minHeight: '80vh' }}>
            <Card className="text-center shadow-lg" style={{ maxWidth: '500px', width: '100%' }}>
                <Card.Header as="h5">Kết Quả Thanh Toán</Card.Header>
                <Card.Body className="p-4">
                    {status === 'processing' ? (
                        <Spinner animation="border" variant="primary" />
                    ) : (
                        <Alert variant={getAlertVariant()} className="mb-4">
                            <h4>
                                {status === 'success' ? 'Thành Công!' : 'Thất Bại!'}
                            </h4>
                            <p>{message}</p>
                        </Alert>
                    )}
                    <p className="text-muted">
                        Bạn sẽ được tự động chuyển hướng về trang Lịch đã đặt trong {countdown} giây.
                    </p>
                    <Button as={Link} to="/my-bookings" variant="primary">
                        Về trang Lịch đã đặt ngay
                    </Button>
                </Card.Body>
            </Card>
        </Container>
    );
};

export default PaymentReturn;