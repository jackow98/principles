import React from 'react';
import './output.css';
import SearchFeature from "./components/SearchFeature";
import github from "./assets/icons/github.svg"
import notion from "./assets/icons/notion-logo.svg"

function App() {
    return (
        <>
            <div className="fixed m-6 h-12 z-20 w-3/12 bottom-0 right-0 shadow-md md:top-0 md:w-1/12 md:mr-6 md:mt-4">
                <div className="flex justify-center mb-2 h-full w-full bg-gray-100 rounded-lg">
                    <a className="cursor-pointer inline-block p-2 h-full w-auto"
                       href="https://github.com/jackow98/principles" target="blank">
                        <img className="h-full w-auto stroke-current" src={github} alt={""}/>
                    </a>
                    <a className="cursor-pointer inline-block p-2 h-full w-auto"
                       href="https://www.notion.so/Principles-4356911896ae48a28e7e08c921a45488" target="blank">
                        <img className="h-full w-auto" src={notion} alt={""}/>
                    </a>
                </div>
            </div>
            <SearchFeature/>
        </>
    );
}

export default App;
