import logo from './logo.svg';
import './App.css';
import Navbar from './components/NavBar';
import { Outlet } from "react-router-dom";
import React, { useState, useEffect, CSSProperties } from 'react';
import { ClockLoader } from 'react-spinners';


import Footer from './components/Footer/footer';



const override: CSSProperties = {
  display: "block",
  margin: "250px auto",
  borderColor: "red",
};

function App() {
  const [loading, setLoading] = useState(false);
  useEffect(() => {
    setLoading(true);
    setTimeout(() =>{
      setLoading(false);
    },800)

  }, []);

  return (
    <div className="App">
      <div className="header">
        <Navbar />
      </div>
      {
        loading ?
          <ClockLoader
            color='#f49f1d'
            loading={loading}
            size={150}
            cssOverride={override}
            aria-label="Loading Spinner"
            data-testid="loader"
            display="block"

          />
          :
          <div>

            <div className="content">
              <Outlet />

            </div>


          </div>
      }
      <div>
        <Footer />
      </div>
    </div>


  );
}

export default App;
