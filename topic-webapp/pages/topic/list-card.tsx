import { Card, Flex, Typography } from "antd";
import React from "react";
import ListCardType from "./list-card-type";
import './topic.css';
import LikeDislikeButtons from "./like-dislike";

const cardStyle: React.CSSProperties = {
  width: '100%',
};

const imgStyle: React.CSSProperties = {
  display: 'block',
  width: 273,
};

const ListCard: React.FC = (listCardType: ListCardType) => {
  const { topicId, title, avatar, description, author, likes, dislikes, liked, disliked, onShowAlert, setAlertMsg } = listCardType;

  return (
    <Card hoverable style={cardStyle} styles={{ body: { padding: 0, overflow: 'hidden' } }}>
      <Flex>
        <img
          alt="avatar"
          src={avatar}
          style={imgStyle}
        />
        <Flex vertical align="center" justify="space-between" style={{ padding: 32, width: '100%' }}>
          <Typography.Title level={3}>
            {title}
          </Typography.Title>
          <span>
            Created by {author}
          </span>
          <Typography.Title level={5}>
            {description}
          </Typography.Title>
          <br />
          <LikeDislikeButtons 
            topicId={topicId}
            totalLikes={likes}
            totalDislikes={dislikes}
            liked={liked}
            disliked={disliked}
            onShowAlert={onShowAlert}
            setAlertMsg={setAlertMsg}
          />
        </Flex>
      </Flex>
    </Card>
  );
}

export default ListCard;