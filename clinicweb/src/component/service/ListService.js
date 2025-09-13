import { useEffect, useState } from "react";
import Apis, { endpoints } from "../../configs/Apis";
import { useSearchParams } from "react-router-dom";
import { Alert, Button, Card, Col, Row, Spinner } from "react-bootstrap";

const ListService = () => {

  const [services, setServices] = useState([]);
  const [loading, setLoading] = useState(false);
  const [q] = useSearchParams();

  const loadService = async () => {
    try {
      setLoading(true);
      const url =endpoints["services"] + `?specializeName=${q.get("specializeName")}`;
      console.log(url);
      const res = await Apis.get(url);
      setServices(res.data);
    } catch (error) {
      console.error("Lỗi", error);
    }
    finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadService();
  }, [q]);
  
  return(
    <>
        <h1 className="text-center text-primary m-3">
            DANH SÁCH DỊCH VỤ KHOA {q.get("specializeName").toUpperCase()}
        </h1>
        {loading && <Spinner animation="border" />}
        {services.length === 0 && !loading && <Alert variant="danger">
         Chưa có dịch vụ nào!
        </Alert>}

        <Row>
            {services.map(s =>
          <Col className="p-1" key={s.id} md={3} xs={6}>
                      <Card border="info"  style={{ width: '18rem' }}>
                          <Card.Img variant="top" src="/serviceimg.jpg" />
                          <Card.Body>
                            <Card.Title className="text-truncate">{s.name}</Card.Title>
                            <Card.Text>
                              Giá khám tham khảo: {s.price} VNĐ
                            </Card.Text>
                            <Button variant="primary">Xem</Button>
                          </Card.Body>
                        </Card>
                    </Col>)
                    }
                    </Row>

        
    </>
  );
}
export default ListService;