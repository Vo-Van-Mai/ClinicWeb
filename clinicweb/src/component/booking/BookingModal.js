import { useContext, useEffect, useState } from "react";
import { Alert, Button, Form, Modal } from "react-bootstrap";
import Apis, { authApis, endpoints } from "../../configs/Apis";
import { MyUserContext } from "../../configs/MyContext";
import { MyCartContext } from "../../configs/MyCartContext";
import MySpinner from "../layout/MySpinner";

const BookingModal = ({ show, onHide, doctor }) => {
    const [user] = useContext(MyUserContext);
    const [, cartDispatch] = useContext(MyCartContext);
    const [selectedDate, setSelectedDate] = useState('');
    const [availableSlots, setAvailableSlots] = useState([]);
    const [selectedSlot, setSelectedSlot] = useState(null);
    const [services, setServices] = useState([]);
    const [selectedService, setSelectedService] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    useEffect(() => {
        const loadServices = async () => {
            try {
                const res = await Apis.get(endpoints['services']);
                setServices(res.data);
            } catch (ex) {
                console.error(ex);
            }
        };
        loadServices();
    }, []);

    useEffect(() => {
        if (selectedDate && doctor) {
            const loadSlots = async () => {
                setLoading(true);
                setError('');
                setAvailableSlots([]);
                try {
                    const res = await Apis.get(endpoints['available-slots'], {
                        params: {
                            doctorId: doctor.id,
                            date: selectedDate
                        }
                    });
                    setAvailableSlots(res.data);
                } catch (ex) {
                    setError("Không thể tải lịch hẹn.");
                    console.error(ex);
                } finally {
                    setLoading(false);
                }
            };
            loadSlots();
        }
    }, [selectedDate, doctor]);

    const handleBooking = async () => {
        if (!selectedSlot || !selectedService) {
            setError("Vui lòng chọn khung giờ và dịch vụ.");
            return;
        }
        setLoading(true);
        setError('');
        setSuccess('');

        try {
            const api = authApis();
            const res = await api.post(endpoints['appointments'], {
                patientId: user.patientId,
                serviceId: parseInt(selectedService),
                slotId: selectedSlot.id
            });

            if (res.status === 201) {
                cartDispatch({
                    type: "add",
                    payload: {
                        ...res.data,
                        doctorName: `${doctor.user.lastName} ${doctor.user.firstName}`,
                        serviceName: services.find(s => s.id === parseInt(selectedService))?.name
                    }
                });
                setSuccess("Đặt lịch thành công! Vui lòng kiểm tra trong mục 'Lịch đã đặt'.");
                setTimeout(onHide, 2000);
            }
        } catch (ex) {
            setError("Đã có lỗi xảy ra hoặc lịch đã được đặt. Vui lòng thử lại.");
            console.error(ex);
        } finally {
            setLoading(false);
        }
    };

    if (!doctor) return null;

    return (
        <Modal show={show} onHide={onHide}>
            <Modal.Header closeButton>
                <Modal.Title>Đặt lịch khám với BS. {doctor.user.lastName} {doctor.user.firstName}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {error && <Alert variant="danger">{error}</Alert>}
                {success && <Alert variant="success">{success}</Alert>}
                
                <Form.Group className="mb-3">
                    <Form.Label>Chọn ngày khám</Form.Label>
                    <Form.Control type="date" value={selectedDate} onChange={(e) => setSelectedDate(e.target.value)} />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Chọn dịch vụ</Form.Label>
                    <Form.Select value={selectedService} onChange={(e) => setSelectedService(e.target.value)}>
                        <option value="">-- Chọn dịch vụ --</option>
                        {services.map(s => <option key={s.id} value={s.id}>{s.name} - {s.price} VNĐ</option>)}
                    </Form.Select>
                </Form.Group>

                {loading && <div className="text-center"><MySpinner /></div>}

                {availableSlots.length > 0 && (
                    <Form.Group>
                        <Form.Label>Chọn khung giờ</Form.Label>
                        <div>
                            {availableSlots.map(slot => (
                                <Button
                                    key={slot.id}
                                    variant={selectedSlot?.id === slot.id ? "success" : "outline-primary"}
                                    className="me-2 mb-2"
                                    onClick={() => setSelectedSlot(slot)}
                                >
                                    {slot.startTime}
                                </Button>
                            ))}
                        </div>
                    </Form.Group>
                )}
                 {selectedDate && !loading && availableSlots.length === 0 && <Alert variant="warning">Không có lịch trống trong ngày này.</Alert>}
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={onHide}>Đóng</Button>
                <Button variant="primary" onClick={handleBooking} disabled={loading || !selectedSlot || !selectedService}>
                    {loading ? "Đang xử lý..." : "Xác nhận đặt lịch"}
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default BookingModal;