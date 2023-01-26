
import React from "react";
import axios from "axios";

class User extends React.Component{
    name = localStorage.getItem('name');
    state ={
        listUsers:[]
    }
    async componentDidMount(){
        let res = await axios.get(`/getInformationBMIUser/${this.name}`);
        this.setState({
            listUsers: res && res.data && res.data.data ? res.data.data : []
            
        })
        console.log(this.name);

        console.log(res.data);
    }
    

    render(){
        let name = localStorage.getItem('name');
        console.log(name);
        let {listUsers} = this.state;
        return (
            <div className="demo"> 
                {listUsers && listUsers.length > 0 &&
                    listUsers.map((item, index) =>{
                        return (
                            <div className="child" key={item.User}>
                                    {/* {item.name} */}
                                    {item.email}
                            </div>       
                        )
                    })
                }
            </div>
        )
    }
}

export default User;