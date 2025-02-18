export type LikesDisLikesTotal = {
    topicId?: number;
    totalLikes?: number;
    totalDislikes?: number;
    liked?: boolean;
    disliked?: boolean;
    onShowAlert: (newType: boolean) => void;
    setAlertMsg: (newType: string) => void;
};

type ListCardType = {
    topicId?: number;
    avatar?: string;
    title?: string;
    description?: string;
    author?: string;
    likes?: number;
    dislikes?: number;
    liked?: boolean;
    disliked?: boolean;
    onShowAlert: (newType: boolean) => void;
    setAlertMsg: (newType: string) => void;
};

export default ListCardType;