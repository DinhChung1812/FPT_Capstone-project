import React, { useState, createElement, useEffect, useRef } from 'react'

import { CheckCircleTwoTone, CloseCircleOutlined, DeleteOutlined, DislikeFilled, DislikeOutlined, EditOutlined, FlagFilled, FlagOutlined, LikeFilled, LikeOutlined, SmileOutlined, StarOutlined } from '@ant-design/icons';
import { Avatar, Comment, notification, Pagination, Tooltip } from 'antd';
import axios from 'axios';
import { useParams } from 'react-router-dom';

import Moment from 'react-moment';
import Form from 'react-bootstrap/Form';
import { Button } from 'react-bootstrap';
import { Grid, Rating, Stack } from '@mui/material';


import './ViewComment.css';

const MyComment = (props) => {


    const { comment, cmtContent, onDelete, changed } = props;
    const { dishId } = useParams();// lưu dishid
    const [isEdit, setIsEdit] = useState(false);
    const [flags, setFlags] = useState(0);
    const [action, setAction] = useState(null);
    const [deletes, setDelete] = useState('');
    const [chang, setChanged] = useState(1);
    const [star, setStar] = useState(3);// lưu sao
    const [success, setSuccess] = useState('');
    const currentAccessToken = localStorage.getItem('token');
    const [commentContent, setCommentContent] = useState(cmtContent);
    const token = `Bearer ${currentAccessToken}`;
    const [checkLike, setCheckLike] = useState(0);
    const [checkDisLike, setDisCheckLike] = useState(0);
    const errRef = useRef();

    const myCommentRef = useRef();

    useEffect(() => {
        if (comment && comment.checkLike) {
            setAction('liked');
        }
        if (comment && comment.checkDislike) {
            setAction('disliked');
        }
    }, [comment]);

    const like = () => {

        if (currentAccessToken) {
            axios.post(
                `/likeDishComment?dishCommentId=${comment.dishCommentID}`,
                { commentContent },
                {
                    headers: {
                        Authorization: token
                    }
                }

            ).then(response => {
                onDelete()
                if (action === 'liked') {
                    setAction('');
                } else {
                    setAction('liked');
                }

                setChanged(comment.length)
            }).catch(err => {
                // setSuccess(err);
            })
        }
        console.log(checkLike)



    };
    const dislike = () => {
        if (currentAccessToken) {
            axios.post(
                `/dislikeDishComment?dishCommentId=${comment.dishCommentID}`,
                { commentContent },
                {
                    headers: {
                        Authorization: token
                    }
                }

            ).then(response => {
                if (action === 'disliked') {
                    setAction('');
                } else {
                    setAction('disliked');
                }
                onDelete()
                setFlags(1);
                setChanged(comment.length)
            }).catch(err => {
                // setSuccess(err);
            })
        }
    };

    const flag = () => {
        if (currentAccessToken) {
            axios.post(
                `/reportDishComment?dishCommentId=${comment.dishCommentID}`,
                { commentContent },
                {
                    headers: {
                        Authorization: token
                    }
                }

            ).then(response => {
                setAction('flaged');
                setChanged(comment.length)
                setSuccess(comment.messContent);
            }).catch(err => {
                setSuccess("Vui lòng thử lại!");
            })
        }
    };
    const LoadEditComment = async (e) => {
        e.preventDefault();
        console.log("tesst")
        if (currentAccessToken) {
            if (star) {
                const dishCommentID = comment.dishCommentID;
                console.log(comment)
                await axios.post(
                    `/saveDishComment`,
                    { dishId, content: commentContent, starRate: star, dishCommentId: dishCommentID },
                    {
                        headers: {
                            headers: { 'Content-Type': 'application/json' },
                            Authorization: token
                        }
                    }
                ).then(response => {
                    console.log(props.changed)
                    console.log(changed)
                    setSuccess(response.data.messContent);
                    setIsEdit(false);
                    onDelete();
                    openNotification("Sửa bình luận thành công!");
                }).catch(err => {
                    setSuccess(err);
                })



            }
            else {
                openNotification("Vui lòng đánh giá công thức nấu ăn trước khi bình luận!");
            }
        } else {
            openNotification('Vui lòng đăng nhập trước khi bình luận');
        }
    }


    function handDelete(id) {
        if (currentAccessToken) {
            axios.post(
                `/deleteDishComment?dishCommentId=${id}`,
                { commentContent },
                {
                    headers: {
                        Authorization: token
                    }
                }

            ).then(response => {
                setChanged(comment.length)
                // setSuccess(response.data.messContent);
                onDelete();
                openNotification("Bình luận đã được xóa");
                // <openNotification value="Bình luận đã được xóa"/>
                
            }).catch(err => {
                // setSuccess(err);
            })
        }

    }
    function openNotification(message) {

        notification.open({
            message: message,
            icon: (
                <CheckCircleTwoTone
                    style={{
                        color: '#108ee9',
                    }}
                />
            ),
        });
    };
    const Cancel = (value) => {
        setIsEdit(!isEdit);
    }
    const handleChangeEditComment = (value) => {
        setIsEdit(!isEdit);
        setCommentContent(comment.content)
    }

    const actions = [
        <Tooltip key="comment-basic-like" title="Thích" >

            <span onClick={like}>
                {React.createElement(action === 'liked' ? LikeFilled : LikeOutlined)}
                <span className="comment-action">{comment.totalLike}</span>
            </span>
        </Tooltip>,
        <Tooltip key="comment-basic-dislike" title="Không thích">
            <span onClick={dislike}>
                {React.createElement(action === 'disliked' ? DislikeFilled : DislikeOutlined)}
                <span className="comment-action">{comment.totalDisLike}</span>
            </span>
        </Tooltip>,
        <Tooltip key="comment-basic-flag" title="Đánh giá sao">

        </Tooltip>,

        <Tooltip key="comment-basic-flag" title="Báo cáo">

            <span onClick={flag} className="comment-action">
                {React.createElement(action === 'flaged' ? FlagFilled : FlagOutlined)}
            </span>
        </Tooltip>,
    ];

    return (
        // ref={myCommentRef}

        <div className='my-comment'>
            {/* <p ref={errRef} className={success ? "sucmsg" : "offscreen"}>{success}</p> */}
            <Comment
                actions={actions}
                author={<a>{comment.accountUserName}</a>}
                avatar={<Avatar src={comment.avatarImage} alt={comment.accountUserName} />}
                content={
                    <p>
                        <h6> {comment.content}</h6>
                        <div className='actions-hover'>

                            <DeleteOutlined onClick={() => handDelete(comment.dishCommentID)} className="styles-icon " />
                            <EditOutlined onClick={() => handleChangeEditComment(comment.dishCommentID)} className="styles-icon hidden" />

                        </div>
                        <div style={{ fontSize: '16px' }}>
                            Đánh giá :
                            <Rating
                                style={{ marginTop: '10px', fontSize: '16px' }}
                                name="read-only"
                                value={comment.startRate}
                                readOnly
                            />
                        </div>

                    </p>

                }

                datetime={
                    <Tooltip title="">
                        {/* <span><Moment >{comment.updateDate}</Moment></span> */}
                        <span>{comment.updateDate}

                        </span>
                    </Tooltip>
                }
            />
            {isEdit &&
                <Form onSubmit={LoadEditComment} autoComplete={'off'}>

                    <Form.Group className="mb-3" controlId="commentContent">
                        <CloseCircleOutlined onClick={(() => Cancel(comment.dishCommentID))} style={{ float: 'right' }} />
                        <Form.Control
                            as="textarea"
                            rows={3}
                            value={commentContent}
                            onChange={(e) => setCommentContent(e.target.value)}

                        />
                    </Form.Group>

                    <Stack spacing={1}> Đánh giá công thức nấu ăn :
                        <Rating

                            name="half-rating"
                            defaultValue={5}
                            precision={1}
                            value={star}
                            onChange={e => setStar(e.target.value)}
                        />

                    </Stack>

                    <Button className="cmt" variant='info' type='submit' >Lưu</Button>

                </Form>
            }


        </div>
    )
};

export default MyComment;