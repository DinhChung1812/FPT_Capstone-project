import React, { Component } from 'react'

export class viewImg extends Component {
    render() {
        const {images, tab, myRef} = this.props;
        return (
            <div className="viewImg" ref={myRef}>
                {
                images.map((img, index) =>(
                    <img src={img} alt="" key={index} 
                    onClick={() => tab(index)}
                    />
                ))
                }
            </div>
        )
    }
}

export default viewImg;