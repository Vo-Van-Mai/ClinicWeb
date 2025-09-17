import { useContext, useEffect, useState } from "react";
import { Alert, Button, Form, Modal, Spinner } from "react-bootstrap";
import Apis, { authApis, endpoints } from "../../configs/Apis";
import { MyUserContext } from "../../configs/MyContext";
import { MyCartContext } from "../../configs/MyCartContext";

const DoctorBookingModal = ({ show, onHide, doctor, specializes }) => {
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
        const loadServicesBySpecialize = async () => {
            if (specializes && specializes.length > 0) {
                try {
                    const servicePromises = specializes.map(spec =>
                        Apis.get(endpoints['services'] + `?specializeName=${spec.name}`)
                    );
                    const serviceResponses = await Promise.all(servicePromises);
                    const allServices = serviceResponses.flatMap(res => res.data);
                    const uniqueServices = Array.from(new Map(allServices.map(s => [s.id, s])).values());
                    setServices(uniqueServices);
                } catch (ex) {
                    console.error("Lỗi khi tải dịch vụ:", ex);
                }
            }
        };

        if (show) {
            loadServicesBySpecialize();
        }
    }, [specializes, show]);

    useEffect(() => {
        if (selectedDate && doctor) {
            const loadSlots = async () => {
                setLoading(true);
                setError('');
                setAvailableSlots([]);
                try {
                    let url = `${endpoints['available-slots']}?doctorId=${doctor.id}&date=${selectedDate}`;
                    const res = await Apis.get(url);
                    setAvailableSlots(res.data);
                } catch (ex) {
                    setError("Không thể tải lịch hẹn cho ngày này.");
                    console.error(ex);
                } finally {
                    setLoading(false);
                }
            };
            loadSlots();
        } else {
            setAvailableSlots([]);
        }
    }, [selectedDate, doctor]);
    
    const handleExited = () => {
        setSelectedDate('');
        setSelectedService('');
        setSelectedSlot(null);
        setError('');
        setSuccess('');
        setAvailableSlots([]);
    };

    const handleBooking = async () => {
        if (!selectedSlot || !selectedService) {
            setError("Vui lòng chọn đầy đủ khung giờ và dịch vụ.");
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
                cartDispatch({
                    type: "add",
                    payload: res.data
                });
                setSuccess("Đặt lịch thành công! Vui lòng kiểm tra trong mục 'Lịch đã đặt'.");
                setTimeout(() => {
                    onHide();
                }, 2000);
            }
        } catch (ex) {
            setError("Lỗi: Khung giờ này có thể đã được đặt. Vui lòng tải lại trang và thử lại.");
            console.error(ex);
        } finally {
            setLoading(false);
        }
    };

    if (!doctor) return null;

    return (
        <Modal show={show} onHide={onHide} onExited={handleExited} size="lg">
            <Modal.Header closeButton>
                <Modal.Title>Đặt lịch khám với BS. {doctor.user?.lastName} {doctor.user?.firstName}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {error && <Alert variant="danger">{error}</Alert>}
                {success && <Alert variant="success">{success}</Alert>}
                
                <Form.Group className="mb-3">
                    <Form.Label><strong>1. Chọn ngày khám</strong></Form.Label>
                    <Form.Control type="date" value={selectedDate} onChange={(e) => setSelectedDate(e.target.value)} min={new Date().toISOString().split('T')[0]}/>
                </Form.Group>

                {services.length > 0 &&
                    <Form.Group className="mb-3">
                        <Form.Label><strong>2. Chọn dịch vụ khám</strong></Form.Label>
                        <Form.Select value={selectedService} onChange={(e) => setSelectedService(e.target.value)}>
                            <option value="">-- Dịch vụ thuộc chuyên khoa của bác sĩ --</option>
                            {services.map(s => <option key={s.id} value={s.id}>{s.name} - {s.price.toLocaleString('vi-VN')} VNĐ</option>)}
                        </Form.Select>
                    </Form.Group>
                }

                {loading && <div className="text-center"><Spinner /></div>}

                {selectedDate && !loading && (
                    <Form.Group>
                        <Form.Label><strong>3. Chọn khung giờ còn trống</strong></Form.Label>
                        <div>
                            {availableSlots.length > 0 ? availableSlots.map(slot => (
                                <Button
                                    key={slot.id}
                                    variant={selectedSlot?.id === slot.id ? "success" : "outline-primary"}
                                    className="me-2 mb-2"
                                    onClick={() => setSelectedSlot(slot)}
                                >
                                    {new Date(`1970-01-01T${slot.startTime}Z`).toLocaleTimeString('en-GB', { hour: '2-digit', minute: '2-digit', timeZone: 'UTC' })}
                                </Button>
                            )) : <Alert variant="warning">Không có lịch trống trong ngày này.</Alert>}
                        </div>
                    </Form.Group>
                )}
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

export default DoctorBookingModal;