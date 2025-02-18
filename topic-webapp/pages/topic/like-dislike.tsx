import { Flex, Tooltip, Button, message } from "antd";
import { useEffect, useState } from "react";
import { DislikeFilled, DislikeTwoTone, LikeFilled, LikeTwoTone } from '@ant-design/icons';
import { SizeType } from "antd/es/config-provider/SizeContext";
import { LikesDisLikesTotal } from "./list-card-type";
import { useSession } from "next-auth/react";

const LikeDislikeButtons: React.FC = (likesDislikes: LikesDisLikesTotal) => {
  const [size] = useState<SizeType>('medium');
  const { topicId, totalLikes, totalDislikes, liked, disliked, onShowAlert, setAlertMsg } = likesDislikes;
  const [currTotalLikes, setCurrTotalLikes] = useState(totalLikes);
  const [currTotalDisLikes, setCurrTotalDisLikes] = useState(totalDislikes);
  const [currLiked, setCurrLiked] = useState(liked);
  const [currDisliked, setCurrDisliked] = useState(disliked);
  const { data: session } = useSession({ required: true });
  const [messageApi, contextHolder] = message.useMessage();

  const likedTopic = async (topicId?: number) => {
    const response = await fetch('http://localhost:3000/service/topics/liked/' + topicId, {
      method: 'POST',
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + session?.accessToken
      }
    });

    const data = await response.json();

    if (response.ok) {
      setCurrTotalLikes(data.likes);
      setCurrTotalDisLikes(data.dislikes);
      setCurrLiked(data.liked);
      setCurrDisliked(data.disliked);
    }
    if (!response.ok) {
      setAlertMsg(data?.message)
      onShowAlert(true);
      messageApi.open({
        type: 'error',
        content: data?.message,
      });
    }
  }

  const dislikedTopic = async (topicId?: number) => {
    const response = await fetch('http://localhost:3000/service/topics/disliked/' + topicId, {
      method: 'POST',
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + session?.accessToken
      }
    });

    const data = await response.json();
    
    if (response.ok) {
      setCurrTotalLikes(data.likes);
      setCurrTotalDisLikes(data.dislikes);
      setCurrLiked(data.liked);
      setCurrDisliked(data.disliked);
    }
    if (!response.ok) {
      setAlertMsg(data?.message)
      onShowAlert(true);
      messageApi.open({
        type: 'error',
        content: data?.message,
      });
    }
  }

  useEffect(() => {
    // console.log('rendering complete, with new totalLike', currTotalLikes);
    // console.log('rendering complete, with new totalLike', currTotalDisLikes);
  }, [currTotalLikes, currTotalDisLikes, currLiked, currDisliked]);

  return (
    <>
      {contextHolder}
      <Flex gap="small" wrap justify="center" >
        <Tooltip title="Like">
          <Button icon={currLiked ? <LikeFilled /> : <LikeTwoTone />} type="text" size={size} onClick={() => likedTopic(topicId)}>{currTotalLikes}</Button>
        </Tooltip>
        <Tooltip title="Dislike">
          <Button icon={currDisliked ? <DislikeFilled /> : <DislikeTwoTone twoToneColor="#eb2f96" />} type="text" size={size} onClick={() => dislikedTopic(topicId)}>{currTotalDisLikes}</Button>
        </Tooltip>
      </Flex>
    </>
  );
}

export default LikeDislikeButtons;
