import React from 'react';
import { Alert, Button, Flex, List, Modal } from 'antd';
import { useState, useEffect } from 'react';
import { signOut, useSession } from 'next-auth/react';
import TopicAddForm from './add';
import LoginForm from '@/app/ui/login-form';
import ListCard from './list-card';

const textStyle = {
  padding: 32,
  color: 'black',
  fontWeight: '600',
}

const ListTopic: React.FC = () => {
  const [topics, setTopics] = useState([]);
  const { data: session } = useSession({ required: true });
  const [addForm, setAddForm] = useState(false);
  const [showAlert, setShowAlert] = useState(false);
  const [errorMsg, setErrorMsg] = useState('');
  // const router = useRouter();

  const handleFetchData = async () => {
    const response = await fetch('http://localhost:3000/service/topics', {
      method: 'GET',
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + session?.accessToken
      }
    });

    if (response.ok) {
      const data = await response?.json();
      setTopics(data);
    }
    if (!response.ok && response.status == 403) {
      // window.location.reload()
      // router.push('/');
      return (<LoginForm />)
    }
  }

  useEffect(() => {
    handleFetchData();
  }, []);

  useEffect(() => {
  }, [topics]);

  if (session == null) {
    return (<LoginForm />)
  } else {
    return (
      <>
        {
          showAlert &&
          <>
            <Alert
              message="Error"
              description={errorMsg}
              type="error"
              showIcon
              closable
              onClose={() => setShowAlert(false)}
            />
            <br />
          </>
        }
        <Flex justify="space-between">
          <span style={textStyle}>
            List of Topics
          </span>
          <Flex justify="space-between" style={{ padding: 32 }} wrap gap="small">
            <Button type="primary" onClick={() => setAddForm(true)}>Add New</Button>
            <Button danger onClick={() => signOut()}>Logout</Button>
          </Flex>
        </Flex>
        <Modal
          title="Add new Topic"
          centered
          open={addForm}
          onCancel={() => setAddForm(false)}
          footer=""
          width={{
            xs: '90%',
            sm: '80%',
            md: '70%',
            lg: '60%',
            xl: '50%',
            xxl: '40%',
          }}
        >
          <TopicAddForm onTopicsChange={setTopics} />
        </Modal>
        <List
          itemLayout="vertical"
          size="large"
          dataSource={topics}
          renderItem={(item) => (
            <List.Item>
              <ListCard
                topicId={item.topicId}
                title={item.title}
                avatar="https://images.pexels.com/photos/2067569/pexels-photo-2067569.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
                description={item.description}
                author={item.author}
                likes={item.likes}
                dislikes={item.dislikes}
                liked={item.liked}
                disliked={item.disliked}
                onShowAlert={setShowAlert}
                setAlertMsg={setErrorMsg}
              />
            </List.Item>
          )}
        />
      </>
    )
  }
};

export default ListTopic;