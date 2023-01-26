
import React, { useState, useEffect, useRef } from 'react';
import { Link } from "react-router-dom";
import apiUrl from '../../api/apiUrl';
import axios from "axios";

import './index.css'
const ListRecipe = () => {
    const [list, setList] = useState();
    const [errMsg, setErrMsg] = useState('');

    // useEffect(() => {
    //     const currentAccessToken = localStorage.getItem('token');
    //     const token = `Bearer ${currentAccessToken}`;
    //     if (currentAccessToken) {
    //         async function getData() {
    //             const res = await axios.get(apiUrl.Admin_GetListRecipe_URL,
    //                 {
    //                     headers: {
    //                         Authorization: token
    //                     }
    //                 });
    //             return res;
    //         }
    //         getData().then((res) => setList(res?.data));
    //         getData().catch(((error) => setErrMsg(error.message)));
    //     }
    // }, []);

    const onDelete = () => {
        console.log("Delete");
    }
    const onEdit = () => {
        console.log("Edit");
    }
    return (
        <>
            <div style={{ padding: "20px" }}>
                <table className="styled-table">
                    <thead>
                        <tr>
                            <th className="table-th"> Stt</th>
                            <th className="table-th"> Công thức</th>
                            {/* <th className="table-th"> Nguyên liệu</th> */}
                            <th className="table-th"> Mô tả</th>
                            <th className="table-th"> Ngày tạo</th>
                            <th className="table-th"> Tạo bởi</th>
                            <th className="table-th" style={{ float: "right", marginRight: "72px" }}> Action</th>
                        </tr>
                    </thead>

                </table>
            </div>
        </>
    )
}

export default ListRecipe;
