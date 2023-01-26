import React,{ useState } from "react";
import './index.css'

function Filter(props) {
    const [toggleState, setToggleState] = useState(1);

    const toggleTab = (index) => {
        setToggleState(index);
    };

    return (
        <>
            Món ngon mỗi ngày
            <div className="">
                <div className="bloc-tabs">
                    <button
                        className={toggleState === 1 ? "tabs active-tabs" : "tabs"}
                        onClick={() => toggleTab(1)}
                    >
                        Tab 1
                    </button>
                    <button
                        className={toggleState === 2 ? "tabs active-tabs" : "tabs"}
                        onClick={() => toggleTab(2)}
                    >
                        Tab 2
                    </button>
                    <button
                        className={toggleState === 3 ? "tabs active-tabs" : "tabs"}
                        onClick={() => toggleTab(3)}
                    >
                        Tab 3
                    </button>
                </div>

                <div className="content-tabs">
                    <div
                        className={toggleState === 1 ? "content-filter  active-content-filter" : "content"}
                    >
                        <h2>Content 1</h2>
                        <hr />
                       
                    </div>

                    <div
                        className={toggleState === 2 ? "content-filter  active-content-filter" : "content"}
                    >
                        <h2>Content 2</h2>
                        <hr />
                        
                    </div>

                    <div
                        className={toggleState === 3 ? "content-filter  active-content-filter" : "content"}
                    >
                        <h2>Content 3</h2>
                        <hr />
                    </div>
                </div>
            </div>
        </>
    )
}
export default Filter;