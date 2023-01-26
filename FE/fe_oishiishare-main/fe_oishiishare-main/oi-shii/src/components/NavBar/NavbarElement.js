import { FaBars } from 'react-icons/fa';
import { NavLink as Link } from 'react-router-dom';
import styled from 'styled-components';

export const Nav = styled.nav`
  background: #cbcde5;
  height: 60px;
  width: 100%;
  display: flex;
  justify-content: space-between;
  padding: 0px calc((100vw - 1000px) / 2);
  z-index: 10;
  color: #000;
  /* Third Nav */
`;
export const NavRight = styled.nav`
  width: 2%;
  background: #cbcde5;
  height: 60px;
  display: flex;
  float:right;
  margin-right: -180px ;
`;
export const NavLeft = styled.nav`
  width: 80%;
  background: #cbcde5;
  height: 60px;
  display: flex;
  float:left;
  z-index: 10;
  margin: 0px calc((50% - 1050px) / 2);

`;
export const NavLink = styled(Link)`
  color: #000;
  display: flex;
  align-items: center;
  text-decoration: none;
  padding: 0 28px;
  height: 100%;
  cursor: pointer;
  &.active {
    color: #0212ef;
  }
  &:hover {
    transition: all 0.2s ease-in-out;
    background: #b3aeae;
    color: #fff;
  }
  
`;
export const NavLinkRight = styled(Link)`
  color: #000;
  display: flex;
  align-items: center;
  text-decoration: none;
  height: 100%;
  cursor: pointer;
  margin-top: -5px;
  
`;



export const Bars = styled(FaBars)`
  display: none;
  color: #000;
  @media screen and (max-width: 768px) {
    display: block;
    position: absolute;
    top: 0;
    right: 0;
    transform: translate(-100%, 75%);
    font-size: 1.8rem;
    cursor: pointer;
  }
  
`;

export const NavMenu = styled.div`
  display: flex;
  align-items: center;
  @media screen and (max-width: 768px) {
    display: none;
  }
  
`;

export const NavBtn = styled.nav`
  display: flex;
  align-items: center;
  @media screen and (max-width: 768px) {
    display: none;
  }
`;

export const NavBtnLink = styled(Link)`
  border-radius: 4px;
  background: #256ce1;
  padding: 10px 22px;
  color: #000;
  outline: none;
  border: none;
  cursor: pointer;
  transition: all 0.2s ease-in-out;
  text-decoration: none;
  /* Second Nav */
  margin-left: 24px;
  &:hover {
    transition: all 0.2s ease-in-out;
    background: #8ba3ca;
    color: #010606;
  }
`;
