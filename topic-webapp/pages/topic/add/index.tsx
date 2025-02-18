import React, { useState } from 'react';
import { Alert, Button, Form, FormProps, Input } from 'antd';
import TextArea from 'antd/es/input/TextArea';
import { useSession } from 'next-auth/react';
import ListCardType from "../list-card-type";

type FieldType = {
  title?: string;
  description?: string;
};

type Props = {
  topicsValue?: ListCardType[];
  onTopicsChange?: (newType: []) => void;
};

const TopicAddForm: React.FC = ({
          onTopicsChange
        }: Props) => {
  const [showAlert, setShowAlert] = useState(false);
  const [alertMsg, setAlertMsg] = useState('');
  const [alertDesc, setAlertDesc] = useState('');
  const [alertType, setAlertType] = useState('');
  const { data: session } = useSession({ required: true });

  const handleFetchData = async () => {
    const response = await fetch('http://localhost:3000/service/topics', {
      method: 'GET',
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + session?.accessToken
      }
    });

    if (response.ok) {
      const data = await response.json();
      onTopicsChange(data);
    }

    if (!response.ok && response.status == 403) {
      console.log('ERROR LOAD DATA');
      // signOut();
    }
  }
  
  const onFinish: FormProps<FieldType>['onFinish'] = async (fields) => {
    const response = await fetch('http://localhost:3000/service/topics', {
      method: 'POST',
      body: JSON.stringify(fields),
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + session?.accessToken
      }
    });
  
    const data = await response.json();

    if (response.ok) {
      setAlertType('success')
      setAlertMsg("Success");
      setAlertDesc("Successfully add new Topic.");
      setShowAlert(true);
      handleFetchData();
    }
  
    if (!response.ok) {
      setAlertType('error')
      setAlertMsg("Error");
      setAlertDesc(data?.message);
      setShowAlert(true);
    }
  };

  const onClosed = () => {
    setShowAlert(false)
  }

  return (
    <>
      {
        showAlert &&
        <>
          <Alert
            message={alertMsg}
            description={alertDesc}
            type={alertType}
            showIcon
            closable
            onClose={() => onClosed()}
          />
          <br />
        </>
      }
      <Form
        name="wrap"
        labelAlign="left"
        layout="vertical"
        labelWrap
        wrapperCol={{ flex: 1 }}
        colon={false}
        onFinish={onFinish}
      >
        <Form.Item label="Title" name="title" rules={[{ required: true }]}>
          <Input />
        </Form.Item>

        <Form.Item label="Description" name="description" rules={[{ required: true }]}>
          <TextArea />
        </Form.Item>

        <Form.Item label=" ">
          <Button type="primary" htmlType="submit">
            Submit
          </Button>
        </Form.Item>
      </Form>
    </>
  );
};

export default TopicAddForm;