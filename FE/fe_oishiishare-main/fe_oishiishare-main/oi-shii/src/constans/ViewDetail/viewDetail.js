import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios'
import './index.css'

import ViewComments from '../ViewComment/ViewComment';
import { Button, Checkbox, Divider } from 'antd';
import { SaveOutlined } from '@ant-design/icons';


const ViewDetail = () => {
    const { dishId } = useParams();
    const [recipe, setRecipe] = useState([]);
    const [loading, setLoading] = useState(false);
    const CheckboxGroup = Checkbox.Group;
    const [ingredient, setIngredient] = useState([]);
    const [ingredientList, setIngredientList] = useState([]);


    const [checkedList, setCheckedList] = useState([]);
    const [indeterminate, setIndeterminate] = useState(true);
    const [checkAll, setCheckAll] = useState(false);
    const list = ingredient;


    const getDataDetail = () => {
        axios.get(`/getRecipeDetail?dishId=${dishId}`)
            .then(response => {
                setRecipe(response.data);
                setIngredient(response.data.ingredientDetailList);
                setCheckedList(indeterminate.ingredientDetailId)
            }).catch(error => console.log(error))
        setLoading(false);
    }
    useEffect(() => {
        setLoading(true);
        getDataDetail();

    }, []);



    const Loading = () => {
        return (
            <>
                Loading.....
            </>
        )
    }
    const src = "https://youtu.be/t4zTeFeP79Q";

    const ShowRecipe = () => {
        const [loadings, setLoadings] = useState([]);

        const enterLoading = (index) => {
            setLoadings((prevLoadings) => {
                const newLoadings = [...prevLoadings];
                newLoadings[index] = true;
                return newLoadings;
            });
            setTimeout(() => {
                setLoadings((prevLoadings) => {
                    const newLoadings = [...prevLoadings];
                    newLoadings[index] = false;
                    return newLoadings;
                });
            }, 2000);
        };
        const onChange = (list) => {
            setCheckedList(list);
            setIndeterminate(!!list.length && list.length < ingredient.length);
            setCheckAll(list.length === ingredient.length);
        };
        const onCheckAllChange = (e) => {
            setCheckedList(e.target.checked ? ingredient : []);
            setIndeterminate(false);
            setCheckAll(e.target.checked);
        };
        const changeIngredient = (id) => {
            axios.get(`/getIngredientChange?ingredientDetailId=${id}`)
                .then(response => {
                    setIngredientList(response.data)
                    console.log(response.data)
                }).catch(error => console.log(error))
            setLoading(false);
        }
        return (
            <>
                <div className="container-list" key={recipe.dishId}>
                    <div className="left-view">
                        <img src="https://media.moitruongvadothi.vn/images/2021/09/16/9860-1631782087-thit-heo-luoc-2.jpg"
                            alt={recipe.dishName}
                            className="img-view-detail" />
                        {/* {recipe.dishImageList?.map((listImg) => {
                                return (
                                    <div key={listImg.dishImageID}>
                                        <img
                                            className='img-view-show'
                                            src={listImg.url}
                                            alt="img"
                                        />
                                       
                                    </div>

                                )
                            })} */}
                        <div>
                            {recipe.dishImageList?.map((listImg) => {
                                return (
                                    <div key={listImg.dishImageID}>
                                        <img
                                            className='img-view-show'
                                            src={listImg.url}
                                            alt="img"
                                        />

                                    </div>

                                )
                            })}

                        </div>
                    </div>
                    <div className="right-view">
                        <h1 className='h1-text'>{recipe.dishName}</h1>
                        <div className="text-view">Tổng quan:{recipe.summary}</div>
                        <div className="text-view">Mô tả: {recipe.formulaDescribe}</div>
                        <div className="text-view">Tổng kalo: {recipe.totalCalo} </div>
                        <div className="text-view">Mức độ: {recipe.level}</div>
                        <div className="text-view">Số người đánh giá: {recipe.numberPeopleForDish}</div>
                        <div className="text-view">Tạo ngày: {recipe.createDate} bởi {recipe.verifier} </div>

                    </div>

                </div>
                <div className="view-comment">
                    <hr />
                    <h3 className='view-title'>Nguyên liệu:</h3>
                    <Checkbox onChange={onCheckAllChange} checked={checkAll}>
                        Check all
                    </Checkbox>
                    <Divider />
                    {ingredient?.map((listIngredient) => {
                        return (
                            <div className="view-ingredien" key={listIngredient.ingredientDetailId}>

                                <Checkbox >
                                    {listIngredient.name}: {listIngredient.quantity} {listIngredient.unit}
                                </Checkbox>


                            </div>
                        )

                    })}
                    Nguyên liệu thay thế: 
                    {ingredientList?.map((list)=>{
                        return(
                            <div key={list.ingredientDetailId}>
                                {list.name} {list.quantity} {list.unit}
                            </div>
                        )
                    })}
                    <Button
                        style={{ float: "right", marginRight:"20%", borderRadius:"5px"}}
                        type="primary"
                        loading={loadings[1]}
                        onClick={() => changeIngredient(1)}
                        checked={checkAll}
                    >
                    Tìm kiếm 
                    </Button>
                    
                    <Divider />

                    <h3 className='view-title'>Cách làm :</h3>
                    <div className='view-ingredien'>B1: .....Loading......</div>
                    <div className='view-ingredien'>B2:.....Loading......</div>
                    <div className='view-ingredien'>B3:.....Loading......</div>
                    <h3 className='view-title'>Video Hướng Dẫn :</h3>
                    {/* <div className="view-video">
                        link video this this here
                        <iframe
                            width="562"
                            height="395"
                            src={src}
                            title="Youtube Player"
                            frameborder="0"
                        />
                    </div> */}
                    <hr />
                    <div>
                        <ViewComments />
                    </div>



                </div>


            </>
        )
    }



    // handleTab = (index) => {
    //     this.setState({ index: index })
    //     const images = this.myRef.current.children;
    //     for (let i = 0; i < images.length; i++) {
    //         images[i].className = images[i].className.replace("active", "");
    //     }
    //     images[index].className = "active";
    // };


    return (
        <>
            <div className="container">
                <div className="row">
                    {loading ? <Loading /> : <ShowRecipe />}

                </div>
            </div>
        </>
    )
}
export default ViewDetail;