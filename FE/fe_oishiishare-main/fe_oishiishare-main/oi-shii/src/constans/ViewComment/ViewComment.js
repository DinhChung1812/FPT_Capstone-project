import React, { useState, useEffect, useRef, createElement } from 'react';
import { useParams } from 'react-router-dom';
import { DeleteOutlined, DislikeFilled, DislikeOutlined, EditOutlined, LikeFilled, LikeOutlined } from '@ant-design/icons'
import { Avatar, Comment, Pagination, Tooltip } from 'antd';
import { Grid, Rating, Stack } from '@mui/material';
import { Button, Col, Image, Row } from 'react-bootstrap';
import Form from 'react-bootstrap/Form';
import axios from 'axios'
import Actions from './action';
import MyComment from './MyComment';



const ViewComments = () => {
    const { dishId } = useParams();// lưu dishid
    const [content, setContent] = useState('');
    const [star, setStar] = useState(3);// lưu sao
    const [edit, setEdit] = useState(false);
    const [changed, setChanged] = useState(1);
    const [loading, setLoading] = useState(false);
    const [comment, setComment] = useState([]);
    const [commentList, setCommentList] = useState([]);


    const currentAccessToken = localStorage.getItem('token');
    const token = `Bearer ${currentAccessToken}`;
    const [success, setSuccess] = useState('');
    const errRef = useRef();

    const [index, setIndex] = useState(1);


    const getData = () => {
        axios.get(`/getListCommentOfRecipe?dishId=${dishId}`)
            .then(response => {
                setComment(response.data);
                setCommentList(response.data.dishCommentAccountVoList);
            }).catch(error => console.log(error));
    }
    useEffect(() => {
        getData();
    }, [changed]);


    useEffect(() => {
        getListComment();
    },[index])

    const getListComment= ()=>{
        axios.get(`/getListCommentOfRecipe?dishId=${dishId}&pageIndex=${index}`,
        {
                headers: {
                    Authorization: token
                }
            }
        ).then(response => {
            setCommentList(response.data.dishCommentAccountVoList);
        }).catch(err => console.log(err))
    }

    const AddComment = async (e) => {
        e.preventDefault();
        if (currentAccessToken) {
            if (star) {
                await axios.post(
                    `/saveDishComment`,
                    { dishId, content: content, starRate: star },
                    {
                        headers: {
                            headers: { 'Content-Type': 'application/json' },
                            Authorization: token
                        }
                    }

                ).then(response => {
                    setChanged(comment.length)
                    setTimeout(function (){
                        setSuccess(response.data.messContent);
                    },3000);
                }).catch(err => {
                    setSuccess(err);
                })
            }
            else {
                setSuccess('Vui lòng đánh giá công thức nấu ăn trước khi bình luận');
            }
        } else {
            setSuccess('Vui lòng đăng nhập trước khi bình luận');
        }
    }

    return (
        <>

            <div className="view-cmt" key={commentList.dishId}  >
                <Form onSubmit={AddComment} autoComplete={'off'}>

                    <Form.Group className="mb-3" controlId="commentContent">
                        <Form.Label>Bình luận</Form.Label>
                        <Form.Control
                            as="textarea"
                            placeholder='Nhập nội dung bình luận...'
                            rows={3}
                            value={content}
                            onChange={(e) => setContent(e.target.value)}

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
                    <hr />
                    <Button className="cmt" variant='info' type='submit' >Thêm bình luận</Button>

                </Form>



                {/* <p ref={errRef} className={success ? "sucmsg" : "offscreen"}>{success}</p> */}
                <br></br>
            </div>
            <div>
                {commentList?.map((listCmt, index) => {
                    return (
                        <>
                            <MyComment
                                key={index}
                                comment={listCmt}
                                changed={changed}
                                cmtContent={content}
                                onDelete={getListComment}
                                
                            />
                        </>
                    )
                })}
            </div>
            
            <Pagination
                className='page-cmt'
                size="small"
                total={comment.numOfPages * 10}
                onChange={(value)=>{
                    setIndex(value)
                }}

            />


        </>
    )

}
export default ViewComments;