
import React from 'react';
import { Select, Button, Tabs, Checkbox } from 'antd';
import { Option } from 'antd/lib/mentions';
import { useState } from 'react';
import { Container, Row, Form } from 'react-bootstrap';
import DefaultUserPic from "../../img/team-male.jpg";
import { SaveOutlined } from '@ant-design/icons'
import User from './UserTest';
const Bmi = () => {
  const [loadings, setLoadings] = useState([]);
  const enterLoading = (index) => {
    setLoadings((prevLoadings) => {
      const newLoadings = [...prevLoadings];
      newLoadings[index] = true;
      return newLoadings;
    });
    setTimeout(() => {
      setLoadings((prevLoadings) => {
        const newLoadings = [...prevLoadings];
        newLoadings[index] = false;
        return newLoadings;
      });
    }, 2000);
  };
  const handleChange = (value) => {
    console.log(`selected ${value}`);
  };
  const onChange = (key) => {
    console.log(key);
  };

  return (
    <>
      <Container className=''>
        <Row style={{ marginTop: '15px' }}>
          <>
            <div>
              <img src={DefaultUserPic} alt="profils pic" />
              <Form.Control className='bmiImage' type="file" name="bmiImage" />
              <Button className='btn-bmi' variant="primary">Update bmi</Button>
            </div>
            <div className="col-bmi">
              <div className='txt-bmi'>Name: Lê Thành Long</div>
              <div className='txt-bmi'>Email: tlongksa@gmail.com</div>
              <div className='txt-bmi'>Date Of Birth :2000-11-14</div>
              <div className='txt-bmi'>Số diện thoại: 039xxxx322</div>
              <div className='txt-bmi'>Địa chỉ: Ninh Bình</div>
              <div className='txt-bmi'>Chiều cao: 173 cm</div>
              <div className="txt-bmi"> Chỉ số R:
                <Select
                  defaultValue="Choose BMI"
                  className='select-bmi'
                  onChange={handleChange}
                >
                  <Option value="option 1">Option 1</Option>
                  <Option value="option 2">Option 2</Option>
                  <Option value="option 3">Option 3</Option>
                </Select>
              </div>
              <div>
                <Button
                  type="primary"
                  icon={<SaveOutlined />}
                  loading={loadings[1]}
                  onClick={() => enterLoading(1)}
                >
                  Save
                </Button>
              </div>
            </div>

          </>
        </Row>
        <div>
          <Tabs
            defaultActiveKey="1"
            onChange={onChange}
            items={[
              {
                label: `Chức năng 1`,
                key: '1',
                children: [<Checkbox>Checkbox1</Checkbox>,<Checkbox>Checkbox1.1</Checkbox>,<Checkbox>Checkbox1.2</Checkbox>],
              },
              {
                label: `Chức năng 2`,
                key: '2',
                children: [<Checkbox>Checkbox2</Checkbox>,<Checkbox>Checkbox2.1</Checkbox>],
              },
              {
                label: `Chức năng 3`,
                key: '3',
                children: [<Checkbox>Checkbox3</Checkbox>,<Checkbox>Checkbox3.1</Checkbox>],
              },
            ]}
          />

        </div>

      </Container>
      <User on/>
    </>
  );
};

export default Bmi;