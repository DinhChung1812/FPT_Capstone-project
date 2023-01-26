
import { useState, useRef,State } from "react";
import { Link } from "react-router-dom";
import apiUrl from "../../api/apiUrl";

import './forgotPass.css';
import axios from "axios";
const ChangePassword = (props) => {
    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [success, setSuccess] = useState();

    const errRef = useRef();

    const currentAccessToken = localStorage.getItem('token');
    const token = `Bearer ${currentAccessToken}`
    const changePassword = async (e) => {
        e.preventDefault();
        const response = await axios.post(
            apiUrl.ChangePassword_URL,
            { oldPassword, newPassword },
            {
                headers: {
                    Authorization: token
                }
            }

        );
        
        setSuccess(response.data.messContent);
        console.log(success);
    }

    return (
        <div>
            <div className="bgr-fotgot-left">
            </div>
            <div className="bgr-fotgot-right">
                <div className="d-flex flex-column align-content-end">
                    <div className="auth-body mx-auto">
                        <h1>Change Password</h1>
                        <p>Change your password in three easy steps. This will help you to secure your password!</p>
                        <ol class="list-unstyled">
                            <li><span class="text-primary text-medium">1. </span>Enter your oldPassword.</li>
                            <li><span class="text-primary text-medium">2. </span>Enter your newPassword</li>
                            <li><span class="text-primary text-medium">3. </span>Enter your re-newPassword</li>
                        </ol>
                        <div>
                            <form className="auth-form" method="POST" onSubmit={changePassword} autoComplete={'off'}>
                                <div className="email mb-3">
                                    <input type="password"
                                        id="password"
                                        name="password"
                                        value={oldPassword}
                                        placeholder="Nhập mật khẩu cũ"
                                        onChange={(e) => setOldPassword(e.target.value)}
                                    />
                                </div>
                                <div className="email mb-3">
                                    <input type="password"
                                        id="password"
                                        name="password"
                                        value={newPassword}
                                        placeholder="Nhập mật khẩu mới"
                                        onChange={(e) => setNewPassword(e.target.value)}
                                    />
                                </div>
                                {/* <div className="email mb-3">
                                    <input type="password"
                                        id="password"
                                        name="password"
                                        value={newPassword}
                                        placeholder="Nhập lại mật khẩu mới"
                                        
                                    />
                                </div> */}

                                <div className="text-center">
                                    <button type="submit" className="btn btn-primary w-100 theme-btn mx-auto">Change Password</button>
                                </div>
                            </form>
                            <p ref={errRef} className={success ? "sucmsg" : "offscreen"}>{success}</p>

                            <hr />
                            <p className=""><Link className="text-back" to="/" >Back to Home</Link></p>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    );
}


export default ChangePassword;