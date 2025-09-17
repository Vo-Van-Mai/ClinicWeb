import { useContext, useEffect, useState } from "react";
import { Alert, Button, Form, Modal, Spinner } from "react-bootstrap";
import Apis, { authApis, endpoints } from "../../configs/Apis";
import { MyUserContext } from "../../configs/MyContext";
import { MyCartContext } from "../../configs/MyCartContext";

const ScheduleBookingModal = ({ show, onHide, schedule, specializes, doctor }) => {
    const [user] = useContext(MyUserContext);
    const [, cartDispatch] = useContext(MyCartContext);
    
    const [slots, setSlots] = useState([]);
    const [services, setServices] = useState([]);
    
    const [selectedSlot, setSelectedSlot] = useState(null);
    const [selectedService, setSelectedService] = useState('');
    
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    useEffect(() => {
        const loadData = async () => {
            if (show && schedule) {
                setLoading(true);
                setError('');
                try {
                    const [slotsRes, servicesData] = await Promise.all([
                        Apis.get(endpoints['slotsBySchedule'](schedule.id)),
                        fetchServices()
                    ]);
                    
                    setSlots(slotsRes.data);
                    setServices(servicesData);

                } catch (ex) {
                    setError("Không thể tải dữ liệu lịch hẹn.");
                    console.error(ex);
                } finally {
                    setLoading(false);
                }
            }
        };

        const fetchServices = async () => {
            if (specializes && specializes.length > 0) {
                const servicePromises = specializes.map(spec =>
                    Apis.get(endpoints['services'] + `?specializeName=${spec.name}`)
                );
                const serviceResponses = await Promise.all(servicePromises);
                const allServices = serviceResponses.flatMap(res => res.data);
                return Array.from(new Map(allServices.map(s => [s.id, s])).values());
            }
            return [];
        };

        loadData();
    }, [show, schedule, specializes]);
    
    const handleExited = () => {
        setSlots([]);
        setServices([]);
        setSelectedSlot(null);
        setSelectedService('');
        setError('');
        setSuccess('');
    };

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
                patientId: user.id,
                serviceId: parseInt(selectedService),
                slotId: selectedSlot.id
            });

            if (res.status === 201) {
                cartDispatch({ type: "add", payload: res.data });
                setSuccess("Đặt lịch thành công! Vui lòng kiểm tra trong mục 'Lịch đã đặt'.");
                setTimeout(onHide, 2000);
            }
        } catch (ex) {
            setError("Lỗi: Khung giờ này có thể đã được đặt. Vui lòng thử lại.");
            console.error(ex);
        } finally {
            setLoading(false);
        }
    };

    if (!schedule) return null;

    return (
        <Modal show={show} onHide={onHide} onExited={handleExited} size="lg">
            <Modal.Header closeButton>
                <Modal.Title>
                    Đặt lịch ngày: {new Date(schedule.dateWork).toLocaleDateString('vi-VN')}
                    <br/>
                    <small className="text-muted">Với BS. {doctor?.user?.lastName} {doctor?.user?.firstName}</small>
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {error && <Alert variant="danger">{error}</Alert>}
                {success && <Alert variant="success">{success}</Alert>}

                <Form.Group className="mb-3">
                    <Form.Label><strong>1. Chọn dịch vụ khám</strong></Form.Label>
                    <Form.Select value={selectedService} onChange={(e) => setSelectedService(e.target.value)}>
                        <option value="">-- Dịch vụ thuộc chuyên khoa của bác sĩ --</option>
                        {services.map(s => <option key={s.id} value={s.id}>{s.name} - {s.price.toLocaleString('vi-VN')} VNĐ</option>)}
                    </Form.Select>
                </Form.Group>
                
                <Form.Group>
                    <Form.Label><strong>2. Chọn khung giờ</strong></Form.Label>
                    {loading ? <div className="text-center"><Spinner /></div> : (
                        <div>
                            {slots.length > 0 ? slots.map(slot => (
                                <Button
                                    key={slot.id}
                                    variant={slot.isBooked ? "secondary" : (selectedSlot?.id === slot.id ? "success" : "outline-primary")}
                                    className="me-2 mb-2"
                                    onClick={() => !slot.isBooked && setSelectedSlot(slot)}
                                    disabled={slot.isBooked}
                                    style={{ textDecoration: slot.isBooked ? 'line-through' : 'none' }}
                                >
                                    {new Date(`1970-01-01T${slot.startTime}Z`).toLocaleTimeString('en-GB', { hour: '2-digit', minute: '2-digit', timeZone: 'UTC' })}
                                </Button>
                            )) : <Alert variant="warning">Không có khung giờ nào cho lịch làm việc này.</Alert>}
                        </div>
                    )}
                </Form.Group>
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

export default ScheduleBookingModal;