import { useEffect, useState } from "react"
import axios from 'axios'
import { Link } from "react-router-dom";
import './index.css'
const ViewAccount = () => {
    
    

    


    const onDelete = () => {
        console.log("Delete");
    }
    const onEdit = () => {
        console.log("Edit");
    }
    return (
        <>
            <div className="contain-recipe">
                <table className="styled-table">
                    <thead>
                        <tr>
                            <th className="table-th"> No</th>
                            <th className="table-th"> Name</th>
                            <th className="table-th"> Genere</th>
                            <th className="table-th"> MyPersonalRating</th>
                            <th className="table-th" style={{ float:"right",marginRight:"72px"}}> Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <th scope="row">1.</th>
                            <td>name</td>
                            <td>gender</td>
                            <td>myPersonalRating</td>
                            <td style={{ float:"right"}}>
                                <Link>
                                    <button className="btn btn-edit"onClick={() => onEdit()}>Edit</button>
                                </Link>
                                <button className="btn btn-delete" onClick={() => onDelete()}>Delete</button>

                                <Link>
                                    <button className="btn btn-view">View</button>
                                </Link>
                            </td>
                        </tr>
                    </tbody>
                </table>

            </div></>
    )
}

export default ViewAccount;