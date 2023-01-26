import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route, } from 'react-router-dom'
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import 'bootstrap/dist/css/bootstrap.min.css';

import Login from './constans/Auth/Login';
import Register from './constans/Auth/Register';
import HomePage from './constans/Home/HomePage';
import About from './constans/Page/about';
import Contact from './constans/Page/contact';
import Bmi from './constans/Page/bmi';
import Save from './constans/Page/save';
import ForgotPassword from './constans/Auth/forgotPassword';
import Profile from './constans/User/profile';
import ListView from './components/List/ListView';

import NotFound from './components/Error/NotFound';
import SearchBar from './components/Search/SearchBar';
import ChangePassword from './constans/Auth/ChangePassword';
import ViewDetail from './constans/ViewDetail/viewDetail';



const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  // <React.StrictMode>
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<App />} >
        <Route path='/home' exact element={<HomePage />} />
        <Route path='/test' element={<homeTest />} />
        <Route path='/about' element={<About />} />
        <Route path='/save' element={<Save />} />
        <Route path='/contact' element={<Contact />} />
        <Route path='/bmi' element={<Bmi />} />
        <Route path='/signin' element={<Login />} />
        <Route path='/profile/:id' element={<Profile />} />
        <Route path='/sign-up' element={<Register />} />
        <Route path='/forgot-password' element={<ForgotPassword />} />
        <Route path='/change-password' element={<ChangePassword />} />
        <Route path='/list-cartegory' element={<ListView />} />
        <Route path='/view-detail/:dishId' element={<ViewDetail />} />
        <Route path='/search/:searchTitle' element={<SearchBar />} />


        <Route path='*' element={<NotFound />} />
        <Route index element={<HomePage />} />
      </Route>
    </Routes>
  </BrowserRouter>
  // </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
