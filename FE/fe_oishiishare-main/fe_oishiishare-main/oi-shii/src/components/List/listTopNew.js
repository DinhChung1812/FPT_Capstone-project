import axios from 'axios';
import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import apiUrl from '../../api/apiUrl';
import './index.css'

const ListTopNew = (props) => {
    const [ListTopNew, setListTopNew] = useState([]);
    const navigateTo = useNavigate();

    useEffect(() => {
        async function getData() {
            const res = await axios.get(apiUrl.TOPNEW_URL);
            return res;
        }
        getData().then((res) => setListTopNew(res?.data));
        getData().catch(((error) => setErrMsg(error.message)));
        errRef.current.focus();

    }, []);


    const errRef = useRef();
    const [errMsg, setErrMsg] = useState('');

    return (
        <div>

            <div className="text">Top With New</div>
            <section className="view-container">
                <p ref={errRef} className={errMsg ? "errmsg" : "offscreen"}>{errMsg}</p>

                {ListTopNew.map((list) => {
                    return (
                        <div key={list.dishID} onClick={() => {
                            navigateTo(`/view-detail/${list.dishID}`)
                        }}>
                            <div className="view-week" >
                                <img
                                    className='view-img'
                                    src={list.image}
                                    alt="img"
                                />

                                <div className="text-view" >
                                    <a>{list.name}  </a>
                                    <div className='view-top'>Độ khó:  {list.level}</div>
                                    <div className='view-top'>Mô tả:  {list.description}</div>
                                    <div className='view-top'>Thời gian:  {list.size} </div>
                                    <div className='view-top'>Đánh giá:  {list.starRate} ⭐ </div>
                                </div>
                            </div>
                        </div>


                    )
                })}



            </section>
        </div>
    )
}
export default ListTopNew;