import axios from 'axios';
import React, { useState, useEffect, useRef } from 'react';
import './index.css'
import apiUrl from '../../api/apiUrl';
import { ClockLoader } from 'react-spinners';


const ListCategory = (props) => {
    const [listCategory, setListCategory] = useState([]);
    const [loading, setLoading] = useState(false);
    useEffect(() => {
        setLoading(true)
        async function getData() {
            const res = await axios.get(apiUrl.CATEGORY_URL);
            return res;
        }
        getData().then((res) => setListCategory(res?.data));
        getData().catch(((error) => setErrMsg(error.message)));
        errRef.current.focus();
        setLoading(false);
    }, []);


    const errRef = useRef();
    const [errMsg, setErrMsg] = useState('');


    const Loading = () => {
        return (
            <>
                loading
            </>
        )
    }
    const ShowCategory = () => {
        return (
            <div>
                <div className="text">Catergory</div>

                <section className="view-container">
                    <p ref={errRef} className={errMsg ? "errmsg" : "offscreen"}>{errMsg}</p>

                    {listCategory.map((list) => {
                        return (
                            <div key={list.dishCategoryID}>

                                <div className="view">
                                    <img
                                        className='view-img-category'
                                        src={list.dishCategoryImage}
                                        alt="img"
                                    />
                                    <a className='list' href="/list-cartegory">{list.name} </a>
                                </div>
                            </div>

                        )
                    })}
                </section>

            </div>
        )
    }
    return (
        <>
            <div className="row">
                {loading ? <Loading /> : <ShowCategory />}

            </div>

        </>
    )
}
export default ListCategory;