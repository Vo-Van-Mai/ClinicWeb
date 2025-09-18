import { useContext, useEffect, useState } from "react";
import { Alert, Button, Form, Modal, Spinner } from "react-bootstrap";
import Apis, { authApis, endpoints } from "../../configs/Apis";
import { MyUserContext } from "../../configs/MyContext";
import { Link } from "react-router-dom";

const BookingDetailModal = ({ show, onHide, doctor, schedule }) => {
    const [user] = useContext(MyUserContext);

    const [availableSlots, setAvailableSlots] = useState([]);
    const [services, setServices] = useState([]);

    const [selectedSlot, setSelectedSlot] = useState(null);
    const [selectedService, setSelectedService] = useState('');
    const [selectType, setSelectType] = useState("");

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const [paymentMethod, setPaymentMethod] = useState('VNPAY');

    useEffect(() => {
        if (show && doctor && schedule) {
            const loadServicesAndSlots = async () => {
                setLoading(true);
                setError('');
                setSuccess('');
                setSelectedSlot(null);
                setSelectedService('');

                try {
                    if (doctor.specializes && doctor.specializes.length > 0) {
                        const servicePromises = doctor.specializes.map(spec =>
                            Apis.get(endpoints['services'], { params: { specializeId: spec.id } })
                        );
                        const serviceResponses = await Promise.all(servicePromises);
                        const allServices = serviceResponses.flatMap(res => res.data);
                        const uniqueServices = Array.from(new Map(allServices.map(s => [s.id, s])).values());
                        setServices(uniqueServices);
                    }

                    const resSlots = await Apis.get(endpoints['appointmentslotsBySchedule'](schedule.id));
                    setAvailableSlots(resSlots.data.filter(slot => !slot.isBooked));

                } catch (ex) {
                    setError("Không thể tải dữ liệu đặt lịch. Vui lòng thử lại.");
                    console.error(ex);
                } finally {
                    setLoading(false);
                }
            };
            loadServicesAndSlots();
        }
    }, [show, doctor, schedule]);

    const handleBookingAndPay = async () => {
        if (!selectedSlot || !selectedService) {
            setError("Vui lòng chọn khung giờ và dịch vụ khám.");
            return;
        }
        setLoading(true);
        try {
            let url = endpoints['bookAppointment'](selectedSlot.id);
            const res = await authApis().post(url, {
                "serviceId": {"id": selectedService},
                "online": selectType,
            });
            console.log(res);
            if(res.status === 201){
                let urlPay = endpoints['bookAndPay'];
                const resPay = await authApis().post(urlPay, {
                        "serviceId": selectedService,
                        "slotId": selectedSlot.id,
                        "paymentMethod": "VNPAY"
                });
                console.log("respay", resPay)
                window.location.href = resPay.data.paymentUrl;
            }


        } catch (ex) {
            setError(ex.message);
            console.error(ex.message);
        } finally {
            setLoading(false);
        }
    };

    if (!doctor || !schedule) return null;

    return (
        <Modal show={show} onHide={onHide} size="lg">
            <Modal.Header closeButton>
                <Modal.Title>
                    Đặt lịch khám ngày: <span className="text-success">{schedule.dateWork}</span>
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {error && <Alert variant="danger">{error}</Alert>}
                {success && <Alert variant="success">{success}</Alert>}

                {loading ? <div className="text-center"><Spinner /></div> : <>
                    <Form.Group className="mb-4">
                        <Form.Label className="fw-bold">1. Chọn dịch vụ khám</Form.Label>
                        <Form.Select value={selectedService} onChange={(e) => setSelectedService(e.target.value)} disabled={!user}>
                            <option value="">-- Vui lòng chọn dịch vụ --</option>
                            {services.map(s => <option key={s.id} value={s.id}>{s.name} - {s.price.toLocaleString('vi-VN')} VNĐ</option>)}
                        </Form.Select>
                    </Form.Group>


                    <Form.Group>
                        <Form.Label className="fw-bold">2. Chọn khung giờ có sẵn</Form.Label>
                        {availableSlots.length > 0 ? (
                            <div className="d-flex flex-wrap">
                                {availableSlots.map(slot => (
                                    <Button
                                        key={slot.id}
                                        variant={selectedSlot?.id === slot.id ? "primary" : "outline-secondary"}
                                        className="me-2 mb-2"
                                        onClick={() => setSelectedSlot(slot)}
                                        disabled={!user}
                                    >
                                        {slot.startTime} - {slot.endTime}
                                    </Button>
                                ))}
                            </div>
                        ) : <Alert variant="warning">Không còn lịch trống trong ngày này.</Alert>}
                    </Form.Group>

                    <Form.Group className="mb-4">
                        <Form.Label className="fw-bold">3. Chọn dịch hình thức khám</Form.Label>
                        <Form.Select value={selectType} onChange={(e) => setSelectType(e.target.value === "true")} disabled={!user}>
                            <option value="">-- Vui lòng chọn hình thức khám --</option>
                            <option value="true">-- Khám trực tuyến --</option>
                            <option value="false">-- Khám trực tiếp --</option>
                        </Form.Select>
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label className="fw-bold">4. Chọn phương thức thanh toán</Form.Label>
                        <Form.Select value={paymentMethod} onChange={e => setPaymentMethod(e.target.value)}>
                            <option value="VNPAY">VNPAY</option>
                            <option value="MOMO" disabled>MOMO (Bảo trì)</option>
                        </Form.Select>
                    </Form.Group>

                    {!user && <Alert variant="info" className="d-flex mt-3">Vui lòng <Link className="ms-1 me-1" to="/login">đăng nhập</Link> để đặt lịch.</Alert>}
                </>}
            </Modal.Body>

            <Modal.Footer>
                <Button variant="secondary" onClick={onHide}>Đóng</Button>
                <Button
                    variant="primary"
                    onClick={handleBookingAndPay}
                    disabled={loading || !selectedSlot || !selectedService || !user}
                >
                    {loading ? "Đang xử lý..." : "Tiến hành thanh toán"}
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default BookingDetailModal;