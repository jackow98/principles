import React from 'react';
import Result from "./Result";

export interface Principle {
    text: string,
    parentPrinciple: string,
    childPrinciple: string
    id: string,
    topic:string
}

export default function Results({principles}: {principles: Principle[]}) {
    return (
        <div className="container w-auto mx-auto z-0">
            {principles.map((principle) => {return <Result principle={principle}/>})}
        </div>
    )
}