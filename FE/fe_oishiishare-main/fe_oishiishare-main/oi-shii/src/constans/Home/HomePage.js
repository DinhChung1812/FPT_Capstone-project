
import React, { useState, useEffect } from 'react';
import Carousel from 'react-bootstrap/Carousel';
import ListCategory from '../../components/List/listCategory';
import ListTopMonth from '../../components/List/listTopMoth';
import ListTopNew from '../../components/List/listTopNew';
import ListTopWeek from '../../components/List/listTopWeek';
import img1 from '../../img/img1.jpg';
import img2 from '../../img/img2.jpg';
import img3 from '../../img/img3.jpeg';
import './homePage.css'
import SearchBar from '../../components/Search/SearchBar';
import Filter from '../../components/Search/filter';
import ListRecipe from '../../adminControl/list/listRecipe';
const HomePage = (props) => {
  const [index, setIndex] = useState(0);


  const handleSelect = (selectedIndex, e) => {
    setIndex(selectedIndex);
  };



  return (
    <div>
      <Carousel activeIndex={index} onSelect={handleSelect}>
        <Carousel.Item className='img'>
          <img
            className="d-block w-100 h-10"
            src={img1}
            alt="First slide"
          />
          <Carousel.Caption >
            <h3>First slide</h3>
            <p>Nulla vitae elit libero, a pharetra augue mollis interdum.</p>
          </Carousel.Caption>
        </Carousel.Item>
        <Carousel.Item className='img'>
          <img
            className="d-block w-100 h-10"
            src={img3}
            alt="Second slide"
          />

          <Carousel.Caption>
            <h3>Second slide </h3>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
          </Carousel.Caption>
        </Carousel.Item>
        <Carousel.Item className='img'>
          <img
            className="d-block w-100 h-10"
            src={img2}
            alt="Third slide"
          />

          <Carousel.Caption>
            <h3>Third slide</h3>
            <p>
              Praesent commodo cursus magna, vel scelerisque nisl consectetur.
            </p>
          </Carousel.Caption>
        </Carousel.Item>
      </Carousel>
      <div>
        {/* <Filter/> */}
        <SearchBar/>
        <ListCategory />
        <ListTopWeek />
        <ListTopMonth />
        <ListTopNew />
        {/* <ListRecipe/> */}
      </div>


    </div>
  );
}

export default HomePage;