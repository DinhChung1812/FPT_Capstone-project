
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { filter } from 'lodash';
import { ClockLoader } from 'react-spinners';
import apiUrl from '../../api/apiUrl';


export default function SearchBar() {

  const [loading, setLoading] = useState(false);
  const [name, setName] = useState([]);
  // const [searchTitle, setSearchTitle] = useState("");
  const setSearchTitle = (value) =>{
    axios.get(`/searchdishbyname/${value}`)

      .then(response => {
        console.log(response.data);
        setName(response.data);
        setLoading(false);
      })
      .catch(error => console.log(error.message))
  }

  return (
    <div className="view-search">
      <input
        className='input-search'
        type="search"
        onChange={(e) => setSearchTitle(e.target.value)}
        placeholder='Tìm kiếm'
      />
      <button
        className="btn-search"
        type="submit"
      >
        Tìm kiếm
      </button>

      <div>
        {/* {
          loading ? (
          ) : (
            name.filter((value) => {
              if (searchTitle === "") {
                return value;
              } 
              else if (
                // value.title.toLowerCase().includes(searchTitle.toLowerCase())
              ) {
                return value;
              }
            })
              .map((item) => <h5 key={item.dishID}>{item.name}</h5>)
          )
        } */}
      </div>


    </div>


  );
}