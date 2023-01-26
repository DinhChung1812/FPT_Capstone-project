import axios from 'axios';
import React, { useState, useEffect,useRef} from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from 'react-bootstrap';
import './index.css'

const ListTopWeek = (props) => {
    const [ListTopWeek, setListTopWeek] = useState([]);

    useEffect(() => {
        async function getData() {
            const res = await axios.get("/getTop5VoteWeek");// link api get
            return res;
        }
        getData().then((res) => setListTopWeek(res?.data));
        getData().catch(((error) => setErrMsg(error.message)));
        errRef.current.focus();
    }, []);
    const navigateTo = useNavigate();

    const errRef = useRef();
    const [errMsg, setErrMsg] = useState('');


    return (
        <div>

            <p className="text" >Top With Week</p>
            <section className="view-container">
            <p ref={errRef} className={errMsg ? "errmsg" : "offscreen"}>{errMsg}</p>

                {ListTopWeek.map((list) => {
                    return (


                        <div key={list.dishId} onClick= {()=>{
                            navigateTo(`/view-detail/${list.dishId}`)
                        }} >

                            <div className="view-week">
                                <img
                                    className='view-img'
                                    src={list.urlImage}
                                    alt="img"
                                />

                                <div className="text-view">
                                    <a >{list.name}  </a>
                                    <div className='view-top'>Độ khó:  {list.level}</div> 
                                     <div className='view-top'>Mô tả:  {list.summary}</div>
                                     <div className='view-top'>Thời gian:  {list.time}</div>
                                    <div className='view-top'>Đánh giá:  {list.avgStarRate} ⭐ </div>
                                </div>
                            </div>
                        </div>

                    )
                })}



            </section>
        </div>
    )
}
export default ListTopWeek;